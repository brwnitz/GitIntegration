package com.example.gitintegration;

import com.example.gitintegration.Interfaces.InterfaceMain;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyClient {
    private static final String BASE_URL = "https://api.github.com/users/";
    private static Retrofit retrofit = null;


    public static InterfaceMain getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptorHTTP = new HttpLoggingInterceptor();
            interceptorHTTP.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder client = new OkHttpClient.Builder().addInterceptor(interceptorHTTP).addInterceptor(interceptorHTTP).readTimeout(20, TimeUnit.SECONDS);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
                    .build();

        }
        return retrofit.create(InterfaceMain.class);
    }

}
