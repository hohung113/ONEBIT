package com.example.onebitmoblie.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";

    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;

    public SessionManager(Context context)
    {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void saveUserData(String username, String id)
    {
        editor.putString(KEY_USERNAME,username);
        editor.putString(KEY_ID,id);
        editor.apply();
    }
    public void saveExtendedData(String dataKey, String dataValue)
    {
        editor.putString(dataKey, dataValue);
        editor.apply();
    }
    public String getKeyUsername() {
        return sharedPreferences.getString(KEY_USERNAME,null);
    }
    public String getKeyId(){
        return sharedPreferences.getString(KEY_ID,null);
    }
    public void clearData(){
        editor.clear();
        editor.apply();
    }
}
