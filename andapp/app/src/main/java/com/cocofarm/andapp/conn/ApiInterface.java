package com.cocofarm.andapp.conn;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiInterface {
    @FormUrlEncoded
    @POST
    Call<String> getPost(@Url String url, @FieldMap HashMap<String, Object> params);

    @GET("/image/{filename}")
    Call<ResponseBody> getImage(@Path("filename") String filename);
}
