package StudentUI;

import java.sql.SQLException;
import DAO.UserDAO;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginWindow {

    private Label emailLabel = new Label("Email : ");
    private Label passwordLabel = new Label("Password : ");
    private Label forgotPasswordLabel = new Label("Forgot Password?");
    protected static TextField emailField = new TextField();
    protected static PasswordField passwordField = new PasswordField();
    protected static TextField passwordFieldViewed = new TextField();
    private Button connectButton = new Button("Connect");
    private CheckBox showPassBox = new CheckBox("Show Password");

    private Stage primaryStage;
    protected static Scene scene;
    private AnchorPane mainPanel;
    private GridPane grid = new GridPane();

    public LoginWindow(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.mainPanel = new AnchorPane();
        LoginWindow.scene = new Scene(mainPanel, 280, 175);
        addComponents();
        start();
    }


    public void start() {
        primaryStage.setScene(scene);
        String css = this.getClass().getResource("/stylesheets/loginWindow.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setResizable(false);
        emailField.requestFocus();
        primaryStage.show();
    }

    public void addComponents(){
        
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        GridPane.setHalignment(emailLabel, HPos.CENTER );
        
        grid.add(emailLabel, 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);

        connectButton.setId("connectButton");
        mainPanel.getChildren().add(connectButton);
        AnchorPane.setTopAnchor(connectButton, 125.0);
        AnchorPane.setLeftAnchor(connectButton, 140.0);

        mainPanel.getChildren().add(grid);
        AnchorPane.setTopAnchor(grid, 10.0);
        AnchorPane.setLeftAnchor(grid, 15.0);

        forgotPasswordLabel.setId("forgotPasswordLabel");
        mainPanel.getChildren().add(forgotPasswordLabel);
        AnchorPane.setTopAnchor(forgotPasswordLabel, 100.0);
        AnchorPane.setLeftAnchor(forgotPasswordLabel, 138.0);

        showPassBox.setId("showPassBox");
        mainPanel.getChildren().add(showPassBox);
        AnchorPane.setTopAnchor(showPassBox, 100.0);
        AnchorPane.setLeftAnchor(showPassBox, 15.0);

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> passwordFieldViewed.setText(newValue));
        passwordFieldViewed.textProperty().addListener((observable, oldValue, newValue) -> passwordField.setText(newValue));

        showPassBox.setOnAction(e -> {
            if(showPassBox.isSelected()){
                passwordFieldViewed.setText(passwordField.getText());
                grid.getChildren().remove(passwordField);
                grid.add(passwordFieldViewed, 1, 1);
                passwordFieldViewed.requestFocus();
                passwordFieldViewed.positionCaret(passwordFieldViewed.getText().length());
            }
            else{
                passwordField.setText(passwordFieldViewed.getText());
                grid.getChildren().remove(passwordFieldViewed);
                grid.add(passwordField, 1, 1);
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
                    errorMessage.setId("errorMessage");

                    mainPanel.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getId() != null && ((Label) node).getId().equals("errorMessage")); 

                    mainPanel.getChildren().add(errorMessage);
                    AnchorPane.setTopAnchor(errorMessage, 80.0);
                    AnchorPane.setLeftAnchor(errorMessage, 120.0);
                    
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
