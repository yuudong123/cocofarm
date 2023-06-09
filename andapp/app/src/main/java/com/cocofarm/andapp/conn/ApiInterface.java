package com.cocofarm.andapp.conn;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiInterface {
    @FormUrlEncoded
    @POST
    Call<String> getPost(@Url String url, @FieldMap HashMap<String, Object> params);

    @GET("{url}")
    Call<String> getGet(@Path("url") String url, @QueryMap HashMap<String, String> params);

    @POST("file.f")
    @Multipart
        // Multipart 내부에서 enctype이 주어지면 모든 것들은 byte형태로 데이터가 전송된다.
    Call<String> sendFile(@Part MultipartBody.Part file);

    @POST
    @Multipart                                                                     //여러건은 HashMap<String, Part> map
    Call<String> sendFiles(@Url String url, @PartMap HashMap<String, RequestBody> maps, @Part MultipartBody.Part file);

    @GET("/image/{filename}")
    Call<ResponseBody> getImage(@Path("filename") String filename);
}
