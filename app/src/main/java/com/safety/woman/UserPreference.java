package com.safety.woman;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {

    private SharedPreferences mSharedPref;
    public static final String FULL_NAME = "FULL_NAME";
    public static final String MOBILE = "MOBILE";

    public UserPreference(Context context) {
        init(context);
    }

    /// Init Shared Preference
    public void init(Context context) {
        if (mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    /// Read String data in Shared Preference
    public String read(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    /// Write String data in Shared Preference
    public void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    /// Write Boolean data in Shared Preference
    public boolean read(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    /// Write Boolean data in Shared Preference
    public void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    /// Read Integer data in Shared Preference
    public Integer read(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    /// Write Integer data in Shared Preference
    public void write(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value).apply();
    }

}