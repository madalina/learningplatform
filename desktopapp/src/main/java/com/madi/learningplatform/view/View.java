package com.madi.learningplatform.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
public abstract class View {
    public abstract Parent getContent();
    
    public Parent loadFXML(String fxmlFile) throws IOException{
        return (Parent)FXMLLoader.load(getClass().getResource(fxmlFile));
    }
}
