package com.example.recycleit.views.auth;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.recycleit.views.model.firebase.PostItem;

public class SharedPreferenceManager {
    private static final String USER_AUTH = "user_auth";

    private static final String TYPE = "email";
    private static final String ADD_KEY = "key";
    private static final String ADD_POST_IMAGE = "image";
    private static final String ADD_POST_PRICE = "price";
    private static final String ADD_POST_DESCR = "description";
    private static final String ITEM_POST = "item";




    private SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(USER_AUTH, Context.MODE_PRIVATE);
    }


    public String getType(Context context) {
        return getSharedPreferences(context).getString(TYPE, null);
    }
    public String getAddPostImage(Context context) {
        return getSharedPreferences(context).getString(ADD_POST_IMAGE, null);
    }

    public void setAddPostImage(Context context, String image) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(ADD_POST_IMAGE, image);
        editor.apply();
    }
    public String getPostItem(Context context) {
        return getSharedPreferences(context).getString(ITEM_POST, null);
    }

    public void setPostItem(Context context, PostItem item) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(ITEM_POST, item.toString());
        editor.apply();
    }
    public String getAddPostPrice(Context context) {
        return getSharedPreferences(context).getString(ADD_POST_PRICE, null);
    }

    public void setAddPostPrice(Context context, String price) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(ADD_POST_PRICE, price);
        editor.apply();
    }    public String getAddPostDescr(Context context) {
        return getSharedPreferences(context).getString(ADD_POST_DESCR, null);
    }

    public void setAddPostDescr(Context context, String desc) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(ADD_POST_DESCR, desc);
        editor.apply();
    }
    public String getAddKey(Context context) {
        return getSharedPreferences(context).getString(ADD_KEY, null);
    }

    public void setType(Context context, String type) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(TYPE, type);
        editor.apply();
    }
    public void setAddKey(Context context, String key) {
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

