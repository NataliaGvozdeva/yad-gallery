package com.mersiyanov.dmitry.yadg.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroHelper {


    private static Retrofit getRetrofit(){
        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();

        return new Retrofit.Builder()
                .baseUrl("https://cloud-api.yandex.net/v1/disk/resources/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

    }

    public static YaDiskApi getApi() {
        return getRetrofit().create(YaDiskApi.class);
    }
}
