package com.cocofarm.andapp.conn;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Service {
    private static final String BASE_URL = "http://localhost:9090/"; //개발시 각자 아이피가 다르므로 localhost 사용. 에러시 자신의 아이피로 바꿔보기
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
