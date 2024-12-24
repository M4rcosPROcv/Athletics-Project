package StudentUI;

import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Properties;

import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Message;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import DAO.UserDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ForgotPasswordScene {

    private Stage primaryStage;
    private Scene forgotPasswordScene;
    private AnchorPane forgotPasswordPanel;
    private Label forgotPasswordEmailLabel = new Label("Please enter your email to search for your account");
    private TextField forgotPasswordEmailField = new TextField();
    private Button forgotPasswordSubmitButton = new Button("Search");
    private Button forgotPasswordCancelButton = new Button("Cancel");

    public ForgotPasswordScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        forgotPasswordPanel = new AnchorPane();
            
        forgotPasswordOperations();  
    }

    public void forgotPasswordOperations(){
        forgotPasswordEmailLabel.setId("forgotEmailLabel");
        forgotPasswordEmailField.setPromptText("Email");
            
        forgotPasswordPanel.getChildren().add(forgotPasswordEmailLabel);
        AnchorPane.setTopAnchor(forgotPasswordEmailLabel, 10.0);
        AnchorPane.setLeftAnchor(forgotPasswordEmailLabel, 27.0);

        forgotPasswordPanel.getChildren().add(forgotPasswordEmailField);
        AnchorPane.setTopAnchor(forgotPasswordEmailField, 40.0);
        AnchorPane.setLeftAnchor(forgotPasswordEmailField, 125.0);

        forgotPasswordPanel.getChildren().add(forgotPasswordSubmitButton);
        AnchorPane.setTopAnchor(forgotPasswordSubmitButton, 75.0);
        AnchorPane.setLeftAnchor(forgotPasswordSubmitButton, 220.0);

        forgotPasswordPanel.getChildren().add(forgotPasswordCancelButton);
        AnchorPane.setTopAnchor(forgotPasswordCancelButton, 75.0);
        AnchorPane.setLeftAnchor(forgotPasswordCancelButton, 130.0);

        forgotPasswordCancelButton.setOnAction(e -> {
            primaryStage.setScene(LoginWindow.scene);
            LoginWindow.emailField.clear();
            LoginWindow.passwordField.clear();
            LoginWindow.passwordFieldViewed.clear();
            LoginWindow.emailField.requestFocus();
        });

        forgotPasswordSubmitButton.setOnAction(event -> {
            try {
                String email = UserDAO.getEmail(forgotPasswordEmailField.getText());

                if(email == null) {
                    forgotPasswordEmailLabel.setText("No account was found with this email. Try again");
                    forgotPasswordEmailField.clear();
                }
                else{
                    
                    SecureRandom secureRandom = new SecureRandom();
                    String otp = new DecimalFormat("000000").format(secureRandom.nextInt(999999));
                    
                    String finalOtp = otp;
                    
                    // sendEmail(email, otp);
                    
                    forgotPasswordEmailLabel.setText("Your account was found and a one-time code has been sent to your email address\n" +
                                                      "Please enter the one-time code you received in your email below");
                    forgotPasswordEmailField.clear();

                    // Pane => forgotPasswordPanel; 

                    // After sending the OTP, change the email field's prompt text to "Enter the OTP"
                    // forgotPasswordEmailField.setPromptText("Enter the OTP");

                    
                    // Reset the email field after sending the OTP.
                    forgotPasswordEmailField.clear();
                    forgotPasswordEmailField.setPromptText("One-Time Code");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

            
            
        forgotPasswordScene = new Scene(forgotPasswordPanel, 390, 280);
        String css = this.getClass().getResource("forgotPasswordWindow.css").toExternalForm();
        forgotPasswordScene.getStylesheets().add(css);
            
        primaryStage.setScene(forgotPasswordScene);
    }

    public void sendEmail(String email, String otp){

        // Email details
        final String senderEmail;
        final String senderPassword;
        final String receiverEmail = email;
        final String subject = "One-Time Code";
        final String body = "<html><body>" +
                            "Your one-time code is: <b>" + otp + "</b><br>" +
                            " This code will expire in 2 minutes." +
                            "</body></html>"
        
        ;

        // Set up the SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587"); // Port is 587 for TLS
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS

        try {
            Object o = new JSONParser().parse(new FileReader("config.json"));

            JSONArray emailArray = (JSONArray) o;

            JSONObject j = (JSONObject) emailArray.get(1);
            senderEmail = (String) j.get("email_username");
            senderPassword = (String) j.get("email_password");

            //Get a session object
            Session session = Session.getInstance(properties,
            new jakarta.mail.Authenticator() {
                protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new jakarta.mail.PasswordAuthentication(senderEmail, senderPassword);
                }
            });

            //Create a MimeMessage
            try{
                Message message = new MimeMessage(session);

                // Set the sender and recipient email addresses
                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));

                // Set the email message content
                message.setSubject(subject);
                message.setContent(body, "text/html");

                // Send the email
                Transport.send(message);

            } catch(Exception e){
                e.printStackTrace();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
