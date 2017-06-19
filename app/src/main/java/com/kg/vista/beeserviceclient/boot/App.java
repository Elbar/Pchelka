package com.kg.vista.beeserviceclient.boot;

import android.app.Application;

import com.kg.vista.beeserviceclient.prefs.LaunchScreenPrefs;


/**
 * Created by Vista on 08.02.2017.
 */

public class App extends Application {

    private static LaunchScreenPrefs prefs;
    private static Application app;

   /* @Override
    public void onCreate() {
        super.onCreate();
    }*/

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        prefs = new LaunchScreenPrefs(this);
    }

    public static Application getApp() {
        return app;
    }

    public static  LaunchScreenPrefs getPrefs() {
        return prefs;
    }

    public void setPrefs(LaunchScreenPrefs prefs) {
        this.prefs = prefs;
    }
}

