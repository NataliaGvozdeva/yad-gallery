package com.mersiyanov.dmitry.yadg.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.mersiyanov.dmitry.yadg.YadApplication;

import static android.content.Context.MODE_PRIVATE;


public class NetworkUtils {

    private static final String PREFERENCES_SESSION = "session";
    private static final String KEY_AUTH_TOKEN = "auth-token";

    static public boolean isNetworkAvailable(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


    }


    public static boolean isLoggedIn() {
        return !TextUtils.isEmpty(getAuthToken());
    }

    public static void setAuthToken(String authToken) {
        YadApplication.getAppContext().getSharedPreferences(PREFERENCES_SESSION, MODE_PRIVATE).edit().putString(KEY_AUTH_TOKEN, authToken).apply();
    }

    public static void deleteAuthToken() {
        SharedPreferences sharedPreference = YadApplication.getAppContext().getSharedPreferences(PREFERENCES_SESSION, MODE_PRIVATE);
        sharedPreference.edit().remove(KEY_AUTH_TOKEN).apply();
    }

    public static String getAuthToken() {
        return YadApplication.getAppContext().getSharedPreferences(PREFERENCES_SESSION, MODE_PRIVATE).getString(KEY_AUTH_TOKEN, "");
    }





}
