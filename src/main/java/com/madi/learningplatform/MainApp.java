package com.madi.learningplatform;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.madi.learningplatform.view.DisplayScreenView;
import com.madi.learningplatform.view.LoginScreenView;
import com.madi.learningplatform.view.View;


public class MainApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(MainApp.class);
    private Map<Integer, Scene> scenes = new HashMap<Integer, Scene>();
    private Stage primaryStage;
    
    public static final int LOGIN = 0;
    public static final int DISPLAY = 1;
    
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        State.setMainApp(this);
        log.debug("Showing JFX scene");
        Scene scene = getScene(DISPLAY); 
        setDefaultStageTitle();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private Scene getScene(int choice) {
        Scene scene = null;
        switch(choice) {
            case LOGIN:
                scene = scenes.get(LOGIN);
                if(scene == null) {
                    View loginScreenModule = new LoginScreenView();
                    scene = createNewScene(loginScreenModule.getContent(), 400, 200);
                    scenes.put(LOGIN, scene);
                }
                break;
                
            case DISPLAY:
                scene = scenes.get(DISPLAY);
                if(scene == null) {
                    View displayModule = new DisplayScreenView();
                    scene = createNewScene(displayModule.getContent(), 1000, 600);
                    scenes.put(DISPLAY, scene);
                }
                break;
        }
        return scene;
    }
    
    public Stage getStage() {
        return primaryStage;
    }
    
    public void setStageTitle(String title) {
        primaryStage.setTitle("Learning platform - " + title);
    }
    
    public void setDefaultStageTitle() {
        primaryStage.setTitle("Learning platform");
    }
    
    private Scene createNewScene(Parent sceneContent, int windowWidth, int windowHeight) {
        Scene newScene = new Scene(sceneContent, windowWidth, windowHeight);
        newScene.getStylesheets().add("/styles/styles.css");
        return newScene;
    }
    
    public void setScene(int choice) {
        primaryStage.setScene(getScene(choice));
        primaryStage.show();
        primaryStage.centerOnScreen();
    }
}
