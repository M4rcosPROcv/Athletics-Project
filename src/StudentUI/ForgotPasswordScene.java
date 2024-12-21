package StudentUI;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Random;

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
    private Label forgotPasswordMessageLabel = new Label("Your account was found and a one-time code has been sent to your email address");

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

        forgotPasswordSubmitButton.setOnAction(event -> {
            try {
                String email = UserDAO.getEmail(forgotPasswordEmailField.getText());

                if(email == null) {
                    forgotPasswordMessageLabel.setText("No account found with this email. Try again");
                }
                else{
                    forgotPasswordMessageLabel.setText("A one-time code has been sent to your email address");
                    String otp= new DecimalFormat("000000").format(new Random().nextInt(999999));
                    //sendEmail(email, otp); // Commented out for brevity, replace with actual email sending method.
                    // Reset the email field after sending the OTP.
                    forgotPasswordEmailField.clear();
                    forgotPasswordEmailField.setPromptText("Email");
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
}
