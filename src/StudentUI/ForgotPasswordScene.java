package StudentUI;

import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private String email;
    private TextField forgotPasswordEmailField = new TextField();
    private Button forgotPasswordSubmitButton = new Button("Search");
    private Button forgotPasswordCancelButton = new Button("Cancel");
    private Button verifyCodeButton = new Button("Verify");
    private Label resendCodeLabel = new Label("Resend Code");
    private String otp;
    private Label resetPasswordLabel = new Label("Please reset your password");
    private TextField newPasswordField = new TextField();
    private Label newPasswordLabel = new Label("New Password: ");
    private TextField confirmPasswordField = new TextField();
    private Label confirmPasswordLabel = new Label("Confirm Password: ");
    private Button resetButton = new Button("Reset");
    

    public ForgotPasswordScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        forgotPasswordPanel = new AnchorPane();
            
        forgotPasswordOperations();  
    }

    public void forgotPasswordOperations() {

        SecureRandom secureRandom = new SecureRandom();
        otp = new DecimalFormat("000000").format(secureRandom.nextInt(999999));

        forgotPasswordEmailLabel.setId("forgotEmailLabel");
        resendCodeLabel.setId("resendCodeLabel");
        resetPasswordLabel.setId("resetPasswordLabel");
        confirmPasswordLabel.setId("confirmPasswordLabel");
        newPasswordLabel.setId("newPasswordLabel");
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
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen();
        });

        forgotPasswordSubmitButton.setOnAction(event -> {
            try {
                email = UserDAO.getEmail(forgotPasswordEmailField.getText());

                if (email == null) {
                    forgotPasswordEmailLabel.setText("No account was found with this email. Try again.");
                    forgotPasswordEmailField.clear();
                } else {

                    forgotPasswordEmailLabel.setText(
                            "Your account was found and a one-time code has been sent to your email address.\n" +
                                    "\t\tPlease enter the 6-digit code sent to your email.");
                    forgotPasswordEmailField.clear();

                    sendEmail(email, otp);

                    // Schedule a task after 2 minutes to expire the code sent.
                    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                    scheduler.schedule(() -> {
                        otp = null;
                        System.out.println("Code expired");
                    }, 2, TimeUnit.MINUTES);

                    primaryStage.setWidth(593);
                    primaryStage.centerOnScreen();

                    // Reset the email field after sending the OTP.
                    forgotPasswordEmailField.clear();
                    forgotPasswordEmailField.setPromptText("One-Time Code");

                    AnchorPane.setTopAnchor(forgotPasswordEmailField, 60.0);
                    AnchorPane.setLeftAnchor(forgotPasswordEmailField, 215.0);

                    AnchorPane.setTopAnchor(forgotPasswordCancelButton, 125.0);
                    AnchorPane.setLeftAnchor(forgotPasswordCancelButton, 220.0);

                    forgotPasswordPanel.getChildren().remove(forgotPasswordSubmitButton);

                    forgotPasswordPanel.getChildren().add(verifyCodeButton);
                    AnchorPane.setTopAnchor(verifyCodeButton, 125.0);
                    AnchorPane.setLeftAnchor(verifyCodeButton, 310.0);

                    forgotPasswordPanel.getChildren().add(resendCodeLabel);
                    AnchorPane.setTopAnchor(resendCodeLabel, 95.0);
                    AnchorPane.setLeftAnchor(resendCodeLabel, 260.0);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        resendCodeLabel.setOnMouseClicked(e -> {
            otp = new DecimalFormat("000000").format(secureRandom.nextInt(999999));
            sendEmail(email, otp);
            forgotPasswordEmailLabel.setText("A code has been resent to your email address.");
            forgotPasswordEmailField.clear();
            
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.schedule(() -> {
                otp = null;
                System.out.println("Code expired");
            }, 2, TimeUnit.MINUTES);
        });

        verifyCodeButton.setOnAction(event -> {
            if (forgotPasswordEmailField.getText().equals(otp)) {
                forgotPasswordPanel.getChildren().clear();
                forgotPasswordPanel.getChildren().add(resetPasswordLabel);

                forgotPasswordPanel.getChildren().add(newPasswordLabel);

                forgotPasswordPanel.getChildren().add(newPasswordField);

                forgotPasswordPanel.getChildren().add(confirmPasswordLabel);

                forgotPasswordPanel.getChildren().add(confirmPasswordField);

                forgotPasswordPanel.getChildren().add(resetButton);

            } else if (otp == null) {
                forgotPasswordEmailLabel.setText("The code has expired. Try resending it.");
            } else {
                forgotPasswordEmailLabel.setText("Invalid one-time code. Try again.");
            }
        });

        forgotPasswordScene = new Scene(forgotPasswordPanel, 390, 280);
        String css = this.getClass().getResource("forgotPasswordWindow.css").toExternalForm();
        forgotPasswordScene.getStylesheets().add(css);

        primaryStage.setScene(forgotPasswordScene);
    }

    public void sendEmail(String email, String otp) {

        // Email details
        final String senderEmail;
        final String senderPassword;
        final String receiverEmail = email;
        final String subject = "One-Time Code";
        final String body = "<html><body>" +
                "Your one-time code is: <b>" + otp + "</b><br>" +
                " This code will expire in 2 minutes." +
                "</body></html>";

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

            // Get a session object
            Session session = Session.getInstance(properties,
                    new jakarta.mail.Authenticator() {
                        protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                            return new jakarta.mail.PasswordAuthentication(senderEmail, senderPassword);
                        }
                    });

            // Create a MimeMessage
            try {
                Message message = new MimeMessage(session);

                // Set the sender and recipient email addresses
                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));

                // Set the email message content
                message.setSubject(subject);
                message.setContent(body, "text/html");

                // Send the email
                Transport.send(message);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
