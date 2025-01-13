package com.portal;

import com.StudentUI.ForgotPasswordScene;
import com.StudentUI.LoginWindow;
import com.StudentUI.SceneController;
import com.StudentUI.StudentUI;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

        SceneController sceneController = SceneController.getInstance(primaryStage);

        sceneController.addScene("login", new LoginWindow().getScene());
        sceneController.addScene("studentUI", new StudentUI().getScene());
        sceneController.addScene("forgot", new ForgotPasswordScene().getForgotPasswordScene());
        

        Image icon = new Image("icon.png");
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Athletics Portal");
        sceneController.switchTo("login");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
