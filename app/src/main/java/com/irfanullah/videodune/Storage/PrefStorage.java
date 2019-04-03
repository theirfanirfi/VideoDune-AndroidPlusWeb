package com.irfanullah.videodune.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.irfanullah.videodune.Models.Settings;
import com.irfanullah.videodune.Models.User;

public class PrefStorage {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static String PREF_STORAGE_FILE = "users";
    public static String USER_PREF_DETAILS = "user";
    public static String USER_SETTINGS_PREF_DETAILS = "user_settings";

    public static SharedPreferences getSharedPreference(Context context)
    {
        return context.getSharedPreferences(PREF_STORAGE_FILE, context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(Context context)
    {
        if(sharedPreferences != null)
        {
            return sharedPreferences.edit();
        }
        else
        {
            sharedPreferences = getSharedPreference(context);
            return sharedPreferences.edit();
        }
    }

    public static String getUserData(Context context)
    {
        String userDetails = "";
        userDetails = getSharedPreference(context).getString(USER_PREF_DETAILS,"");
        return userDetails;
    }

    public static String getSettingsData(Context context)
    {
        String settings = "";
        settings = getSharedPreference(context).getString(USER_SETTINGS_PREF_DETAILS,"");
        return settings;
    }

    public static User getUser(Context context)
    {
        Gson gson = new Gson();
        User user = gson.fromJson(PrefStorage.getUserData(context),User.class);
        return user;
    }

    public static Settings getSettings(Context context)
    {
        Gson gson = new Gson();
        Settings settings = gson.fromJson(PrefStorage.getSettingsData(context),Settings.class);
        return settings;
    }

    public static Boolean isMe(Context context,int USER_ID){
        return getUser(context).getUSER_ID() == USER_ID ? true : false;
    }

    public static boolean checkUser(Context context){
        return getSharedPreference(context).contains(USER_PREF_DETAILS);
    }

    public static boolean checkSettings(Context context){
        return getSharedPreference(context).contains(USER_SETTINGS_PREF_DETAILS);
    }

}
