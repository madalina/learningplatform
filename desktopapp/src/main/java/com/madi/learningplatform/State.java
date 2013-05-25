package com.madi.learningplatform;

public class State {
    private MainApp mainApp;
    private String username = "mada";
    
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
    
    public static Collection getSelectedCollection() {
        return getInstance().selectedCollection;
    }

    public static void setSelectedCollection(Collection selectedCollection) {
        getInstance().selectedCollection = selectedCollection;
    }
}
