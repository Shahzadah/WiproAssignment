package com.wipro.wipro.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {

    public static final String PREF_FILE_NAME = "com.limra.ZangoMart";

    private SharedPreferences mPreferences;
    private static PreferencesHelper mPreferencesHelper;

    /**
     * Create Shared Preference.
     *
     * @param context
     */
    private PreferencesHelper(Context context) {
        if (mPreferencesHelper != null) {
            new UnsupportedOperationException("Singleton class: object already exist");
        }
        mPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Get Instance of Shared Preference.
     *
     * @param context
     * @return
     */
    public static PreferencesHelper getInstance(Context context) {
        if (mPreferencesHelper == null) {
            mPreferencesHelper = new PreferencesHelper(context);
        }
        return mPreferencesHelper;
    }

    /**
     * Clear Shared Pref. Object.
     */
    public void clear() {
        if (mPreferences != null) {
            mPreferences.edit().clear().apply();
        }
    }

    /**
     * Check whether Particular key Exist or not in Shared Pref.
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return mPreferences.contains(key);
    }

    /**
     * Remove Shared Pref for Particular Key.
     *
     * @param key
     */
    public void remove(String key) {
        if (mPreferences != null) {
            mPreferences.edit().remove(key).apply();
        }
    }

    /**
     * Put Integer value in Shared Pref of Particular Key.
     *
     * @param key
     * @param value
     */
    public void putInt(String key, int value) {
        if (mPreferences != null) {
            mPreferences.edit().putInt(key, value).apply();
        }
    }

    /**
     * Get Integer value from Shared Pref of Particular Key.
     *
     * @param key
     * @param defValue
     */
    public int getInt(String key, int... defValue) {
        int value = defValue.length > 0 ? defValue[0] : 0;
        if (mPreferences != null) {
            return mPreferences.getInt(key, value);
        }
        return value;
    }

    /**
     * Put Float value in Shared Pref of Particular Key.
     *
     * @param key
     * @param value
     */
    public void putFloat(String key, float value) {
        if (mPreferences != null) {
            mPreferences.edit().putFloat(key, value).apply();
        }
    }

    /**
     * Get Float value from Shared Pref of Particular Key.
     *
     * @param key
     * @param defValue
     */
    public float getFloat(String key, float... defValue) {
        float value = defValue.length > 0 ? defValue[0] : 0;
        if (mPreferences != null) {
            return mPreferences.getFloat(key, value);
        }
        return value;
    }

    /**
     * Put Long value in Shared Pref of Particular Key.
     *
     * @param key
     * @param value
     */
    public void putLong(String key, long value) {
        if (mPreferences != null) {
            mPreferences.edit().putLong(key, value).apply();
        }
    }

    /**
     * Get Long value from Shared Pref of Particular Key.
     *
     * @param key
     * @param defValue
     */
    public long getLong(String key, long... defValue) {
        long value = defValue.length > 0 ? defValue[0] : 0;
        if (mPreferences != null) {
            return mPreferences.getLong(key, value);
        }
        return value;
    }

    /**
     * Put String value in Shared Pref of Particular Key.
     *
     * @param key
     * @param value
     */
    public void putString(String key, String value) {
        if (mPreferences != null) {
            mPreferences.edit().putString(key, value).apply();
        }
    }


    /**
     * Get String value from Shared Pref of Particular Key.
     *
     * @param key
     * @param defValue
     */
    public String getString(String key, String... defValue) {
        String value = defValue.length > 0 ? defValue[0] : null;
        if (mPreferences != null) {
            return mPreferences.getString(key, value);
        }
        return value;
    }

    /**
     * Put boolean value in Shared Pref of Particular Key.
     *
     * @param key
     * @param value
     */
    public void putBoolean(String key, boolean value) {
        if (mPreferences != null) {
            mPreferences.edit().putBoolean(key, value).apply();
        }
    }


    /**
     * Get boolean value from Shared Pref of Particular Key.
     *
     * @param key
     * @param defValue
     */
    public boolean getBoolean(String key, boolean... defValue) {
        boolean value = defValue.length > 0 && defValue[0];
        if (mPreferences != null) {
            return mPreferences.getBoolean(key, value);
        }
        return value;
    }
}
