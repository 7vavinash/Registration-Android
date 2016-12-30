package com.example.avinash.myauth;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by avinash on 12/30/2016.
 */

public class SharedPref {

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setToken(Context ctx, String token) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("token", token);
        editor.commit();
    }

    public static String getToken(Context ctx) {
        return getSharedPreferences(ctx).getString("token", "missing");
    }
}
