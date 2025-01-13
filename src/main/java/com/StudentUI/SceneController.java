package com.StudentUI;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
    private static SceneController instance; // Singleton instance
    private final Stage primaryStage;
    private final Map<String, Scene> scenes = new HashMap<>();

    private SceneController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public static SceneController getInstance(Stage primaryStage) {
        if (instance == null) {
            instance = new SceneController(primaryStage);
        }
        return instance;
    }

    public void addScene(String name, Scene scene) {
        scenes.put(name, scene);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void switchTo(String name){
        Scene scene = scenes.get(name);
        if (scene != null) {
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.sizeToScene();
        } else {
            throw new RuntimeException("Scene not found: " + name);
        }
    }

}
