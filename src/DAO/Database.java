package DAO;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Database {
    private static String url = "";
    private static String passUrl = "";
    private static String username = "";
    private static String passUsername = "";
    private static String password = "";
    private static String passPassword = "";

    // Static block to read the json file and set the variables also loading the driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 

            Object o = new JSONParser().parse(new FileReader("config.json"));
            JSONArray array = (JSONArray) o;

            JSONObject j1 = (JSONObject) array.get(0);
            url = (String) j1.get("db_url");
            username = (String) j1.get("db_username");
            password = (String) j1.get("db_password");

            JSONObject j2 = (JSONObject) array.get(1);
            passUrl = (String) j2.get("db_url");
            passUsername = (String) j2.get("db_username");
            passPassword = (String) j2.get("db_password");

        } catch (IOException | ParseException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Database(){}

    // Returns the connection from the students database
    public static Connection getConnection() throws SQLException{
        Connection connection = null;
        connection = DriverManager.getConnection(url, username, password);

        return connection;
    }

    // Returns the connection from the users database
    public static Connection getConnection2() throws SQLException{
        Connection connection = null;
        connection = DriverManager.getConnection(passUrl, passUsername, passPassword);

        return connection;
    }

    // Closes all connections to the database
    public static void closeConnections(ResultSet resultSet, Statement statement, Connection connection){
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
