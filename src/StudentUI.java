import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StudentUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new BorderPane();
        Scene scene = new Scene(pane, 600, 500);

        primaryStage.setTitle("Student");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
