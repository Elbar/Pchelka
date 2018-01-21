package com.kg.vista.pchelka.boot;

import android.app.Application;

import com.kg.vista.pchelka.prefs.LaunchScreenPrefs;


public class App extends Application {

    private static LaunchScreenPrefs prefs;
    private static Application app;


    public static Application getApp() {
        return app;
    }

    public static LaunchScreenPrefs getPrefs() {
        return prefs;
    }

    public void setPrefs(LaunchScreenPrefs prefs) {
        App.prefs = prefs;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        prefs = new LaunchScreenPrefs(this);
    }
}

