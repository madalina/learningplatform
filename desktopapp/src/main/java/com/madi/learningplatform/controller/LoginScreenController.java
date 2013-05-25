package com.madi.learningplatform.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.security.auth.login.LoginException;

import com.madi.learningplatform.MainApp;
import com.madi.learningplatform.State;

public class LoginScreenController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;
    
    public void login() throws LoginException{

        String username = usernameField.getText();
        String pass = passwordField.getText();

        try
        {
            if(username.equals("") && pass.equals(""))
                 throw new LoginException("Username and password can't be empty");
            if(!(username.equals("mada") && pass.equals("")))
                throw new LoginException("Invalid login");
        }
        catch(LoginException ex)
        {
            messageLabel.setText(ex.getMessage());
            return;
        }
        
        onLoginSuccessful(username);
    }

    private void onLoginSuccessful(String username) {
        State.setUsername(username);
        State.getMainApp().setScene(MainApp.DISPLAY);
    }

}
