package com.rohan.loginapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Rohan Sampson on 5/24/2016.
 */
public class PrefManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    private static final String PREF_NAME = "LoginPref";
    private static final String LOGGED_IN = "LoggedIn";

    public PrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoggedIn(boolean logged){
        editor.putBoolean(LOGGED_IN, logged);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(LOGGED_IN,false);
    }
}
