package com.DAO;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Database {
    private static String url = "";
    private static String username = "";
    private static String password = "";
    
        // Static block to read the json file and set the variables also loading the driver
        static {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); 
                InputStream is = Database.class.getResourceAsStream("/config.json");
                
                String jsonContent = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                JsonArray dbArray = JsonParser.parseString(jsonContent).getAsJsonArray();
                JsonObject j = dbArray.get(0).getAsJsonObject();

        
                url = j.get("db_url").getAsString();
                username = j.get("db_username").getAsString();
                password = j.get("db_password").getAsString();
                    
            } catch (Exception e) {
                System.out.println("Error in static initializer:");
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
