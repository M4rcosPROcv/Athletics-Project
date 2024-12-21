package StudentUI;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Random;

import DAO.UserDAO;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginWindow {

    private Label emailLabel = new Label("Email : ");
    private Label passwordLabel = new Label("Password : ");
    private Label forgotPasswordLabel = new Label("Forgot Password?");
    private TextField emailField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private TextField passwordFieldViewed = new TextField();
    private Button connectButton = new Button("Connect");
    private CheckBox showPassBox = new CheckBox("Show Password");

    private Stage primaryStage;
    private Scene scene;
    private AnchorPane mainPanel;
    private GridPane grid = new GridPane();

    public LoginWindow(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.mainPanel = new AnchorPane();
        this.scene = new Scene(mainPanel, 280, 135);
        addComponents();
        start();
    }


    public void start() {
        primaryStage.setScene(scene);
        String css = this.getClass().getResource("loginWindow.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setResizable(false);
        emailField.requestFocus();
        primaryStage.show();
    }

    public void addComponents(){
        
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        
        
        grid.add(emailLabel, 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);

        connectButton.setId("connectButton");
        mainPanel.getChildren().add(connectButton);
        AnchorPane.setTopAnchor(connectButton, 100.0);
        AnchorPane.setLeftAnchor(connectButton, 145.0);
        GridPane.setHalignment(connectButton, HPos.CENTER );
        mainPanel.getChildren().add(grid);
        AnchorPane.setTopAnchor(grid, 10.0);
        AnchorPane.setLeftAnchor(grid, 15.0);

        forgotPasswordLabel.setId("forgotPasswordLabel");
        mainPanel.getChildren().add(forgotPasswordLabel);
        AnchorPane.setTopAnchor(forgotPasswordLabel, 75.0);
        AnchorPane.setLeftAnchor(forgotPasswordLabel, 145.0);

        showPassBox.setId("showPassBox");
        mainPanel.getChildren().add(showPassBox);
        AnchorPane.setTopAnchor(showPassBox, 75.0);
        AnchorPane.setLeftAnchor(showPassBox, 15.0);

        mainPanel.getChildren().add(passwordFieldViewed);
        AnchorPane.setTopAnchor(passwordFieldViewed, 46.0);
        AnchorPane.setLeftAnchor(passwordFieldViewed, 110.0);

        passwordFieldViewed.setManaged(false);
        passwordFieldViewed.setVisible(false);

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> passwordFieldViewed.setText(newValue));
        passwordFieldViewed.textProperty().addListener((observable, oldValue, newValue) -> passwordField.setText(newValue));

        showPassBox.setOnAction(e -> {
            if(showPassBox.isSelected()){
                passwordFieldViewed.setText(passwordField.getText());
                passwordFieldViewed.setManaged(true);
                passwordFieldViewed.setVisible(true);
                passwordField.setManaged(false);
                passwordField.setVisible(false);
                passwordFieldViewed.requestFocus();
                passwordFieldViewed.positionCaret(passwordFieldViewed.getText().length());
            }
            else{
                passwordField.setText(passwordFieldViewed.getText());
                passwordFieldViewed.setManaged(false);
                passwordFieldViewed.setVisible(false);
                passwordField.setManaged(true);
                passwordField.setVisible(true);
                passwordField.requestFocus();
                passwordField.positionCaret(passwordField.getText().length());
            }
        });
        
        // Button handler
        connectButton.setOnAction(event ->{
            UserDAO userDAO = null;
            try {
                userDAO = new UserDAO(emailField.getText(), passwordField.getText());
                if(userDAO.authenticate()){ // Authentication
                    primaryStage.close(); // Close the login screen
                    new StudentUI(); // Move to main window
                }
                else{
                    // Show error message
                    Label errorMessage = new Label("Invalid email or password.");
                    errorMessage.setTextFill(Color.RED);
                    primaryStage.setHeight(150);
                    mainPanel.getChildren().add(errorMessage);
                    AnchorPane.setTopAnchor(errorMessage, 72.0);
                    AnchorPane.setLeftAnchor(errorMessage, 67.0);
                    
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                emailField.clear(); // Clear the email field
                emailField.requestFocus();
                passwordField.clear(); // Clear the password field
            }
        });
         
        KeyCodeCombination connectHotKey = new KeyCodeCombination(KeyCode.ENTER);
        scene.getAccelerators().put(connectHotKey, () -> connectButton.fire());

        forgotPasswordLabel.setOnMouseClicked(e -> {new ForgotPasswordScene(this.primaryStage);});
    }
}
