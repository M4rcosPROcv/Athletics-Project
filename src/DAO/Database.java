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
    private static String username = "";
    private static String password = "";

    // Static block to read the json file and set the variables also loading the driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 

            Object o = new JSONParser().parse(new FileReader("config.json"));

            JSONArray dbArray = (JSONArray) o;

            JSONObject j = (JSONObject) dbArray.get(0);
            url = (String) j.get("db_url");
            username = (String) j.get("db_username");
            password = (String) j.get("db_password");

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
