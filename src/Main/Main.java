package Main;

import StudentUI.LoginWindow;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image icon = new Image("/resources/icon.png");
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Athletics Portal");
        new LoginWindow(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
