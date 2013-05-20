package com.madi.learningplatform;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class State {
    private static final Logger log = LoggerFactory.getLogger(State.class);
    private MainApp mainApp;
    private String username = "mada";
    private Connection databaseConn;
    private Collection selectedCollection;
   
    private static final State instance = new State();
    private State() {}
    
    public static State getInstance() {
        return instance;
    }
    
    public static MainApp getMainApp() {
        return getInstance().mainApp;
    }
    
    public static void setMainApp(MainApp app) {
        getInstance().mainApp = app;
    }
    
    public static void setUsername(String name) {
        getInstance().username = name;
    }
    
    public static String getUsername() {
        return getInstance().username;
    }
    
    public static Connection getDatabaseConn() {
        if(getInstance().databaseConn == null)
        {
            try {
                Class.forName("com.mysql.jdbc.Driver");

                getInstance().databaseConn = DriverManager
                        .getConnection("jdbc:mysql://debianhomeserver.local/learningplatform?"
                                + "user=root&password=debianmysql");;
            } catch (ClassNotFoundException e) {
                log.error(e.getMessage(), e);
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
            }
        }
        return getInstance().databaseConn;
    }
    
    public static Collection getSelectedCollection() {
        return getInstance().selectedCollection;
    }

    public static void setSelectedCollection(Collection selectedCollection) {
        getInstance().selectedCollection = selectedCollection;
    }
}
