package DAO;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Security;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Database {
    private static String url = "";
    private static String username = "";
    private static String password = "";
    private static String keyBase64 = "wdLku0Kt+bhOrQA31FsKFyHLNKZEAUPugZz2YhTSIe0=";
    private static String ivBase64 = "6pHjApAZoeZM1uDTlkUIRg==";
    
        // Static block to read the json file and set the variables also loading the driver
        static {
            Security.addProvider(new BouncyCastleProvider());
            try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
    
            String encryptedPath = "src/resources/EncryptConfig.json";
    
            byte[] keyBytes = Base64.getDecoder().decode(keyBase64);
            byte[] ivBytes = Base64.getDecoder().decode(ivBase64);

            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

            byte[] encryptedData = Files.readAllBytes(Paths.get(encryptedPath));
            byte[] decryptedData = decrypt(encryptedData, secretKey, ivBytes);

            String jsonContent = new String(decryptedData);

            JsonArray dbArray = JsonParser.parseString(jsonContent).getAsJsonArray();
            JsonObject j = dbArray.get(0).getAsJsonObject();

            url = j.get("db_url").getAsString();
            username = j.get("db_username").getAsString();
            password = j.get("db_password").getAsString();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Database(){}

    public static byte[] decrypt(byte[] encryptedData, SecretKey secretKey, byte[] iv) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        return cipher.doFinal(encryptedData);
    }

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
