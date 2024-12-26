package StudentUI;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StudentUI {
    
    private static Stage primaryStage;
    private Scene scene;
    AnchorPane mainPanel;

    public StudentUI(){
        primaryStage = new Stage();
        mainPanel = new AnchorPane();
        scene = new Scene(mainPanel, 600, 500);

        primaryStage.setTitle("Student");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void mainWindow() {
        primaryStage.show();
    }

}
