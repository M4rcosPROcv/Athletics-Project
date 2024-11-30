package StudentUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StudentUI extends Application {
    
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage loginStage = new Stage();
        LoginWindow loginWindow = new LoginWindow(loginStage);
        StudentUI.primaryStage = primaryStage;
        Pane pane = new BorderPane();
        Scene scene = new Scene(pane, 600, 500);


        primaryStage.setTitle("Student");
        primaryStage.setScene(scene);
    }

    public static void mainWindow() {
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
