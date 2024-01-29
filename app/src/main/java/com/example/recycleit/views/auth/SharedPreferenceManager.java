package com.example.recycleit.views.auth;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
    private static final String USER_AUTH = "user_auth";

    private static final String TYPE = "email";
    private static final String ADD_KEY = "key";



    private SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(USER_AUTH, Context.MODE_PRIVATE);
    }


    public String getType(Context context) {
        return getSharedPreferences(context).getString(TYPE, null);
    } public String getAddKey(Context context) {
        return getSharedPreferences(context).getString(ADD_KEY, null);
    }

    public void setType(Context context, String type) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(TYPE, type);
        editor.apply();
    }  public void setAddKey(Context context, String key) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(ADD_KEY, key);
        editor.apply();
    }

    public void remove(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear();
        editor.apply();
    }
}

