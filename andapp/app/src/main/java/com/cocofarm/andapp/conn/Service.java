package com.cocofarm.andapp.conn;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Service {
    //public static final String BASE_URL = "http://192.168.0.219:9090/"; //현재
    // public static final String BASE_URL = "http://172.30.1.82:9090/"; //현재 집
    // public static final String BASE_URL = "http://192.168.0.202:9090/"; // 명근
    public static final String BASE_URL = "http://192.168.0.208:9090/"; // 세진
    private static Retrofit retrofit;

    public static Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(new OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS).build())
                    .build();
        }
        return retrofit;
    }
}
