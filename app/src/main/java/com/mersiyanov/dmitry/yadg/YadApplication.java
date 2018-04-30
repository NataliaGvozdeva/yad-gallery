package com.mersiyanov.dmitry.yadg;

import android.app.Application;
import android.text.TextUtils;

import com.mersiyanov.dmitry.yadg.di.AppComponent;
import com.mersiyanov.dmitry.yadg.di.DaggerAppComponent;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class YadApplication extends Application {

    private static final String PREFERENCES_SESSION = "session";
    private static final String KEY_AUTH_TOKEN = "auth-token";
    public static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.create();

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);

    }

    public void setAuthToken(String authToken) {
        getSharedPreferences(PREFERENCES_SESSION, MODE_PRIVATE).edit().putString(KEY_AUTH_TOKEN, authToken).apply();
    }

    public String getAuthToken() {
        return getSharedPreferences(PREFERENCES_SESSION, MODE_PRIVATE).getString(KEY_AUTH_TOKEN, "");
    }

    public boolean isLoggedIn() {
        return !TextUtils.isEmpty(getAuthToken());
    }


}
