package com.kg.vista.pchelka.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class LaunchScreenPrefs {
    // file name
    private static final String PREF_NAME = "spaceo-demo";
    private static final String IS_FIRST_TIME = "IsFirstTime";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    //mode
    int PRIVATE_MODE = 0;

    public LaunchScreenPrefs(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME, isFirstTime);
        editor.commit();
    }

}