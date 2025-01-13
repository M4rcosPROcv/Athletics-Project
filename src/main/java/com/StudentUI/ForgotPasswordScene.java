package com.StudentUI;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.DAO.UserDAO;
import javafx.animation.PauseTransition;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class ForgotPasswordScene {

    private Scene forgotPasswordScene;
    private AnchorPane forgotPasswordPanel;
    private Label forgotPasswordEmailLabel = new Label("Please enter your email to search for your account.");
    private String email;
    private TextField forgotPasswordEmailField = new TextField();
    private Button forgotPasswordSubmitButton = new Button("Search");
    private Button forgotPasswordCancelButton = new Button("Cancel");
    private Button verifyCodeButton = new Button("Verify");
    private Label resendCodeLabel = new Label("Resend Code");
    private String otp;
    private Label resetPasswordLabel = new Label("Please reset your password.");
    private Label newPasswordLabel = new Label("New Password: ");
    private PasswordField newPasswordField = new PasswordField();
    private TextField newPasswordTextField = new TextField();
    private Label confirmPasswordLabel = new Label("Confirm Password: ");
    private PasswordField confirmPasswordField = new PasswordField();
    private TextField confirmPasswordTextField = new TextField();
    private Button resetButton = new Button("Reset");
    private CheckBox showPasswordCheckBox = new CheckBox("Show Password");
    private Label codeErrorMessage = new Label();

    private UserDAO userDAO;
    
    GridPane grid = new GridPane();

    public ForgotPasswordScene() {
        forgotPasswordPanel = new AnchorPane();

        forgotPasswordOperations();  
    }

    public Scene getForgotPasswordScene() {
        return forgotPasswordScene;
    }

    public void forgotPasswordOperations() {

        SecureRandom secureRandom = new SecureRandom();
        otp = new DecimalFormat("000000").format(secureRandom.nextInt(999999));
        
        forgotPasswordEmailLabel.setId("forgotEmailLabel");
        resendCodeLabel.setId("resendCodeLabel");
        codeErrorMessage.setId("codeErrorMessage");
        showPasswordCheckBox.setId("showPasswordCheckBox");

        forgotPasswordPanel.getChildren().add(forgotPasswordEmailLabel);
        AnchorPane.setTopAnchor(forgotPasswordEmailLabel, 10.0);
        AnchorPane.setLeftAnchor(forgotPasswordEmailLabel, 27.0);

        forgotPasswordPanel.getChildren().add(forgotPasswordEmailField);
        AnchorPane.setTopAnchor(forgotPasswordEmailField, 45.0);
        AnchorPane.setLeftAnchor(forgotPasswordEmailField, 125.0);

        forgotPasswordPanel.getChildren().add(forgotPasswordSubmitButton);
        AnchorPane.setTopAnchor(forgotPasswordSubmitButton, 90.0);
        AnchorPane.setLeftAnchor(forgotPasswordSubmitButton, 230.0);

        forgotPasswordPanel.getChildren().add(forgotPasswordCancelButton);
        AnchorPane.setTopAnchor(forgotPasswordCancelButton, 90.0);
        AnchorPane.setLeftAnchor(forgotPasswordCancelButton, 130.0);

        forgotPasswordCancelButton.setOnAction(e -> {
            SceneController.getInstance(null).switchTo("login");
            LoginWindow.emailField.clear();
            LoginWindow.passwordField.clear();
            LoginWindow.passwordFieldViewed.clear();
            LoginWindow.emailField.requestFocus();
            SceneController.getInstance(null).getPrimaryStage().centerOnScreen();
            SceneController.getInstance(null).getPrimaryStage().sizeToScene();
        });

        forgotPasswordSubmitButton.setOnAction(event -> {
            try {
                userDAO = new UserDAO(forgotPasswordEmailField.getText());
                email = userDAO.getUserEmail();

                if (email == null) {
                    forgotPasswordEmailLabel.setText("No account was found with this email. Try again.");
                    forgotPasswordEmailField.clear();
                    forgotPasswordEmailField.requestFocus();
                } else {

                    forgotPasswordEmailLabel.setText(
                            "Your account was found and a one-time code has been sent to your email address.\n" +
                                    "\t\t\tPlease enter the 6-digit code sent to your email.");
                    forgotPasswordEmailField.clear();
                    AnchorPane.setLeftAnchor(forgotPasswordEmailLabel, 15.0);

                    sendEmail(email, otp);

                    // Schedule a task after 2 minutes to expire the code sent.
                    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                    scheduler.schedule(() -> {
                        otp = null;
                        System.out.println("Code expired");
                        scheduler.shutdown();
                    }, 2, TimeUnit.MINUTES);

                    SceneController.getInstance(null).getPrimaryStage().setWidth(593);
                    SceneController.getInstance(null).getPrimaryStage().setHeight(250);
                    SceneController.getInstance(null).getPrimaryStage().centerOnScreen();
                    

                    // Reset the email field after sending the OTP.
                    forgotPasswordEmailField.clear();
                    forgotPasswordEmailField.setPromptText("One-Time Code");

                    AnchorPane.setTopAnchor(forgotPasswordEmailField, 65.0);
                    AnchorPane.setLeftAnchor(forgotPasswordEmailField, 175.0);

                    AnchorPane.setTopAnchor(forgotPasswordCancelButton, 145.0);
                    AnchorPane.setLeftAnchor(forgotPasswordCancelButton, 180.0);

                    forgotPasswordPanel.getChildren().remove(forgotPasswordSubmitButton);

                    forgotPasswordPanel.getChildren().add(verifyCodeButton);
                    AnchorPane.setTopAnchor(verifyCodeButton, 145.0);
                    AnchorPane.setLeftAnchor(verifyCodeButton, 285.0);

                    forgotPasswordPanel.getChildren().add(resendCodeLabel);
                    AnchorPane.setTopAnchor(resendCodeLabel, 120.0);
                    AnchorPane.setLeftAnchor(resendCodeLabel, 245.0);

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        resendCodeLabel.setOnMouseClicked(e -> {
            otp = new DecimalFormat("000000").format(secureRandom.nextInt(999999));
            codeErrorMessage.setText("New one-time code sent to your email.");
            forgotPasswordEmailField.clear();
            
            forgotPasswordPanel.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getId() != null && ((Label) node).getId().equals("codeErrorMessage"));
            
            forgotPasswordPanel.getChildren().add(codeErrorMessage);
            AnchorPane.setTopAnchor(codeErrorMessage, 95.0);
            AnchorPane.setLeftAnchor(codeErrorMessage, 180.0);
            forgotPasswordEmailField.requestFocus();

            sendEmail(email, otp);

            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.schedule(() -> {
                otp = null;
                System.out.println("Code expired");
                scheduler.shutdown();
            }, 2, TimeUnit.MINUTES);
        });

        verifyCodeButton.setOnAction(event -> {
            if (forgotPasswordEmailField.getText().equals(otp)) {
                
                forgotPasswordPanel.getChildren().clear();
                forgotPasswordPanel.getChildren().add(resetPasswordLabel);
                AnchorPane.setLeftAnchor(resetPasswordLabel, 95.0);
                AnchorPane.setTopAnchor(resetPasswordLabel, 20.0);
                
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setAlignment(Pos.CENTER);
                grid.add(newPasswordLabel, 0, 0);
                GridPane.setHalignment(newPasswordLabel, HPos.CENTER );
                grid.add(newPasswordField, 1, 0);
                grid.add(confirmPasswordLabel, 0, 1);
                grid.add(confirmPasswordField, 1, 1);
                
                forgotPasswordPanel.getChildren().add(grid);
                AnchorPane.setLeftAnchor(grid, 15.0); 
                AnchorPane.setTopAnchor(grid, 60.0);
                
                forgotPasswordPanel.getChildren().add(resetButton);
                AnchorPane.setTopAnchor(resetButton, 155.0);
                AnchorPane.setLeftAnchor(resetButton, 280.0);
                
                forgotPasswordPanel.getChildren().add(forgotPasswordCancelButton);
                AnchorPane.setTopAnchor(forgotPasswordCancelButton, 155.0);
                AnchorPane.setLeftAnchor(forgotPasswordCancelButton, 175.0);
    
                forgotPasswordPanel.getChildren().add(showPasswordCheckBox);
                AnchorPane.setTopAnchor(showPasswordCheckBox, 155.0);
                AnchorPane.setLeftAnchor(showPasswordCheckBox, 55.0);
    
                newPasswordField.textProperty().addListener((observable, oldValue, newValue) ->
                newPasswordTextField.setText(newValue));
    
                newPasswordTextField.textProperty().addListener((observable, oldValue,
                newValue) -> newPasswordField.setText(newValue));
    
                confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) ->
                confirmPasswordTextField.setText(newValue));
    
                confirmPasswordTextField.textProperty().addListener((observable, oldValue,
                newValue) -> confirmPasswordField.setText(newValue));
    
                showPasswordCheckBox.setOnAction(e ->{
                    if(showPasswordCheckBox.isSelected()){
                        newPasswordTextField.setText(newPasswordField.getText());
                        grid.getChildren().remove(newPasswordField);
                        grid.add(newPasswordTextField, 1, 0);
    
                        confirmPasswordTextField.setText(confirmPasswordField.getText());
                        grid.getChildren().remove(confirmPasswordField);
                        grid.add(confirmPasswordTextField, 1, 1);
                        
                        newPasswordTextField.requestFocus();
                        newPasswordTextField.positionCaret(newPasswordTextField.getText().length());
                    }
                    else{
                        newPasswordField.setText(newPasswordTextField.getText());
                        grid.getChildren().remove(newPasswordTextField);
                        grid.add(newPasswordField, 1, 0);
    
                        confirmPasswordField.setText(confirmPasswordTextField.getText());
                        grid.getChildren().remove(confirmPasswordTextField);
                        grid.add(confirmPasswordField, 1, 1);
    
    
                        newPasswordField.requestFocus();
                        newPasswordField.positionCaret(newPasswordField.getText().length());
                    }
                });
                
                SceneController.getInstance(null).getPrimaryStage().setWidth(400);
                SceneController.getInstance(null).getPrimaryStage().setHeight(250);
                SceneController.getInstance(null).getPrimaryStage().centerOnScreen();

            } else if (otp == null) {
                codeErrorMessage.setText("The code has expired. Try resending it.");
                forgotPasswordEmailField.clear();
                forgotPasswordEmailField.requestFocus();

                forgotPasswordPanel.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getId() != null && ((Label) node).getId().equals("codeErrorMessage"));

                forgotPasswordPanel.getChildren().add(codeErrorMessage);
                AnchorPane.setTopAnchor(codeErrorMessage, 95.0);
                AnchorPane.setLeftAnchor(codeErrorMessage, 180.0);
            } else {
                codeErrorMessage.setText("Invalid one-time code. Try again.");
                forgotPasswordEmailField.clear();
                forgotPasswordEmailField.requestFocus();

                forgotPasswordPanel.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getId() != null && ((Label) node).getId().equals("codeErrorMessage"));

                forgotPasswordPanel.getChildren().add(codeErrorMessage);
                AnchorPane.setTopAnchor(codeErrorMessage, 95.0);
                AnchorPane.setLeftAnchor(codeErrorMessage, 195.0);
            }
        });

        resetButton.setOnAction(e -> {
            if(!newPasswordField.getText().equals(confirmPasswordField.getText()) || newPasswordField.getText().isBlank() || confirmPasswordField.getText().isBlank()){
                codeErrorMessage.setText("Passwords do not match. Please try again.");
                newPasswordField.clear();
                confirmPasswordField.clear();
                newPasswordField.requestFocus();

                forgotPasswordPanel.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getId() != null && ((Label) node).getId().equals("codeErrorMessage"));

                forgotPasswordPanel.getChildren().add(codeErrorMessage);
                AnchorPane.setTopAnchor(codeErrorMessage, 130.0);
                AnchorPane.setLeftAnchor(codeErrorMessage, 170.0);
            }
            else{

                // Update the user's password
                userDAO.updateUserPassword(confirmPasswordField.getText());

                codeErrorMessage.setText("Password reset successful.");
                codeErrorMessage.setStyle("-fx-text-fill: #26FF21;");
                newPasswordField.clear();
                confirmPasswordField.clear();
                newPasswordField.setDisable(true);
                confirmPasswordField.setDisable(true);
                resetButton.setDisable(true);
                forgotPasswordCancelButton.setDisable(true);

                forgotPasswordPanel.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getId() != null && ((Label) node).getId().equals("codeErrorMessage"));

                forgotPasswordPanel.getChildren().add(codeErrorMessage);
                AnchorPane.setTopAnchor(codeErrorMessage, 130.0);
                AnchorPane.setLeftAnchor(codeErrorMessage, 170.0);
                
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(event -> {
                    LoginWindow.emailField.clear();
                    LoginWindow.passwordField.clear();
                    LoginWindow.passwordFieldViewed.clear();
                    LoginWindow.emailField.requestFocus();
                    SceneController.getInstance(null).switchTo("login");
                    SceneController.getInstance(null).getPrimaryStage().centerOnScreen();
                    SceneController.getInstance(null).getPrimaryStage().sizeToScene();
                });
                pause.play();
            }
        });

        forgotPasswordScene = new Scene(forgotPasswordPanel, 390, 155);
        String css = "stylesheets/forgotPasswordWindow.css";
        forgotPasswordScene.getStylesheets().add(css);

        KeyCodeCombination searchHotKey = new KeyCodeCombination(KeyCode.ENTER);
        forgotPasswordScene.getAccelerators().put(searchHotKey, () -> {
            if(forgotPasswordPanel.getChildren().contains(forgotPasswordSubmitButton)){
                forgotPasswordSubmitButton.fire();
            }
            else if(forgotPasswordPanel.getChildren().contains(resetButton)){
                resetButton.fire();
            }
            else if(forgotPasswordPanel.getChildren().contains(verifyCodeButton)){
                verifyCodeButton.fire();
            }
        });
    }

    public void sendEmail(String email, String otp) {

        // Email details
        final String senderEmail;
        final String senderPassword;
        final String receiverEmail = email;
        final String subject = "One-Time Code";
        final String body = "<html><body>" +
        "Your one-time code is: <b>" + otp + "</b><br>" +
        " This code will expire in 2 minutes.<br>" +
        " If you didn't request a security code, please contact your administrator." +
        "</body></html>";
        
        // Set up the SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587"); // Port is 587 for TLS
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS

        try {
            InputStream is = ForgotPasswordScene.class.getResourceAsStream("/config.json");
                
            String jsonContent = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            JsonArray dbArray = JsonParser.parseString(jsonContent).getAsJsonArray();

            JsonObject j = dbArray.get(1).getAsJsonObject();
            
            
            senderEmail = j.get("email_username").getAsString();
            senderPassword = j.get("email_password").getAsString();

            // Get a session object
            Session session = Session.getInstance(properties,
                    new jakarta.mail.Authenticator() {
                        protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                            return new jakarta.mail.PasswordAuthentication(senderEmail, senderPassword);
                        }
                    });

            // Create a MimeMessage
           
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
    }
}
