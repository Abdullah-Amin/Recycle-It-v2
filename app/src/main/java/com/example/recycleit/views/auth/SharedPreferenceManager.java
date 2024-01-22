package com.example.recycleit.views.auth;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
    private static final String USER_AUTH = "user_auth";

    private static final String TYPE = "email";

    private SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(USER_AUTH, Context.MODE_PRIVATE);
    }


    public String getType(Context context) {
        return getSharedPreferences(context).getString(TYPE, null);
    }

    public void setType(Context context, String type) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(TYPE, type);
        editor.apply();
    }

    public void remove(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear();
        editor.apply();
    }
}

