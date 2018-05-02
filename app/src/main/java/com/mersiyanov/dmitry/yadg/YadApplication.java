package com.mersiyanov.dmitry.yadg;

import android.app.Application;
import android.content.Context;

import com.mersiyanov.dmitry.yadg.di.AppComponent;
import com.mersiyanov.dmitry.yadg.di.DaggerAppComponent;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import io.realm.Realm;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YadApplication extends Application {

    private static Context mContext;
    private static final String PREFERENCES_SESSION = "session";
    private static final String KEY_AUTH_TOKEN = "auth-token";
    public static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.create();
        mContext = getApplicationContext();
        Realm.init(this);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("X-TOKEN", "VAL")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this, Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);

    }
//
//    public void setAuthToken(String authToken) {
//        getSharedPreferences(PREFERENCES_SESSION, MODE_PRIVATE).edit().putString(KEY_AUTH_TOKEN, authToken).apply();
//    }
//
//    public  String getAuthToken() {
//        return getSharedPreferences(PREFERENCES_SESSION, MODE_PRIVATE).getString(KEY_AUTH_TOKEN, "");
//    }
//
//    public boolean isLoggedIn() {
//        return !TextUtils.isEmpty(getAuthToken());
//    }

    public static Context getAppContext() {
        return mContext;
    }


}
