package Main;

import StudentUI.LoginWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        new LoginWindow(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
