package com.StudentUI;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class StudentUI {
    
    private Scene scene;
    AnchorPane mainPanel;

    public StudentUI(){
        mainPanel = new AnchorPane();
        scene = new Scene(mainPanel, 600, 500);
    }

    public Scene getScene() {
        return scene;
    }
}
