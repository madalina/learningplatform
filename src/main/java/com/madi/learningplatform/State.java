package com.madi.learningplatform;

import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class State {
    private static final Logger log = LoggerFactory.getLogger(State.class);
    private MainApp mainApp;
    private String username = "mada";
    private DB databaseConn;
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
    
    public static DB getDatabaseConn() {
        if(getInstance().databaseConn == null)
        {
            try {
                MongoClient mongoClient = new MongoClient("debianhomeserver.local", 27017);
                DB db = mongoClient.getDB("learningplatform");
                getInstance().databaseConn = db;
            } catch (UnknownHostException e) {
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
