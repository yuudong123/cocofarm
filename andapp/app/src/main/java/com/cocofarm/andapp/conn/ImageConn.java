package com.cocofarm.andapp.conn;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageConn {
    private ConnCallback callback;
    private Context context;
    private String filename;
    private ProgressDialog dialog;

    public ImageConn(Context context, String filename) {
        this.context = context;
        this.filename = filename;
    }

    private void onPreExcute() {
        if (context != null && dialog == null) {
            dialog = new ProgressDialog(context);
            dialog.setProgress(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.setTitle("코코팜");
            dialog.setMessage("잠시만 기다려주세요..");
            dialog.show();
        }
    }

    public void onExcute(ConnCallback callback) {
        onPreExcute();
        this.callback = callback;
        ApiInterface apiInterface = Service.getApiClient().create(ApiInterface.class);
        apiInterface.getImage(filename).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                onPostExcute(true, response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(context, "이미지를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onPostExcute(boolean isResult, ResponseBody data) {
        if (dialog != null) {
            dialog.dismiss();
        }
        callback.onResult(isResult, data);
    }

    public interface ConnCallback {
        void onResult(boolean isResult, ResponseBody data);
    }
}
