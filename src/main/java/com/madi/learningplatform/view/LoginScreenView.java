package com.madi.learningplatform.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginScreenView extends View
{
    private static final Logger log = LoggerFactory.getLogger(LoginScreenView.class);
   
    @Override
    public Parent getContent() {
        String fxmlFile = "/fxml/login.fxml";
        log.debug("Loading FXML for login view from: {}", fxmlFile);
        Parent rootNode = null;
        try {
            rootNode = loadFXML(fxmlFile);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return rootNode;
    }

}
