package StudentUI;

import java.sql.SQLException;

import DAO.UserDAO;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginWindow {
    private Stage primaryStage;
    private Scene scene;
    private BorderPane mainPanel;

    public LoginWindow(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.mainPanel = new BorderPane();
        this.scene = new Scene(mainPanel, 280, 115);
        addComponents();
        start();
    }


    public void start() {
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setScene(scene);
        String css = this.getClass().getResource("loginWindow.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void addComponents(){
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Username : ");
        Label passwordLabel = new Label("Password : ");
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);

        Button connectButton = new Button("Connect");
        connectButton.setId("connectButton");
        grid.add(connectButton, 1, 2);
        GridPane.setHalignment(connectButton, HPos.CENTER );
        mainPanel.setTop(grid);

        // Button handler
        connectButton.setOnAction(event ->{
            UserDAO userDAO = null;
            try {
                userDAO = new UserDAO(usernameField.getText(), passwordField.getText());
                if(userDAO.authenticate()){ // Authentication
                    primaryStage.close(); // Close the login screen
                    StudentUI.mainWindow(); // Move to main window
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                usernameField.clear(); // Clear the username field
                passwordField.clear(); // Clear the password field
            }
        });
    }
}
