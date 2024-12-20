package DAO;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

public class UserDAO {

    private String retrievedPassword;
    private String userPassword;

    
    public UserDAO(String user, String pass) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        userPassword = pass;

        try{
            
            // Getting the credentials from users database
            conn = Database.getConnection();
            String sql = "SELECT pass FROM creds WHERE email = '" + user + "'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if(rs.next()){
                retrievedPassword = rs.getString("pass");
            }

        } finally{
            Database.closeConnections(rs, ps, conn);
        }
    }

    public boolean authenticate(){
        // Split the salt and the hashed password
        String[] parts = retrievedPassword.split(":");
        String encodedSalt = parts[0];
        String hashedPassword = parts[1];

        // Decode the salt and the hashed password from Base64
        byte[] salt = Base64.getDecoder().decode(encodedSalt);
        byte[] storedPassword = Base64.getDecoder().decode(hashedPassword);

        int memory = 65536;  // Memory in KB
        int iterations = 3;  // Number of iterations
        int parallelism = 1; // Number of threads
        
        // Compute the hash of the user's password using Argon2
        byte[] passwordBytes = userPassword.getBytes(StandardCharsets.UTF_8);
        byte[] computedHash = new byte[storedPassword.length]; // Same length as the stored hash

        // Perform Argon2 authentication
        Argon2Parameters parameters = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
            .withSalt(salt)
            .withMemoryAsKB(memory)
            .withIterations(iterations)
            .withParallelism(parallelism)
            .build();
        // Create a new hashed password with the salt
        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        generator.init(parameters);
        // Generate the password using the salt and length
        generator.generateBytes(passwordBytes, computedHash);

        // Compare the computed hash with the stored hash to authenticate the user
        boolean isMatch = java.util.Arrays.equals(storedPassword, computedHash);

        // Verify the password
        return isMatch;
    }
}

