import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;


public class StudentDAO {
    
    private String url = "";
    private String username = "";
    private String password = "";

    public StudentDAO(){
       
        // Reading the db credentials from json and assigning them to the variables
        try {
            Object o = new JSONParser().parse(new FileReader("config.json"));
            JSONObject j = (JSONObject) o;
            url = (String) j.get("db_url");
            username = (String) j.get("db_username");
            password = (String) j.get("db_password");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
           
        // Load the MySQL JDBC driver and connect to MySQL database.
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

}
