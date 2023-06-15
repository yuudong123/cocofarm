package com.cocofarm.andapp.conn;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonConn {
    private HashMap<String, Object> params;
    private ConnCallback callback;
    private Context context;
    private String mapping;
    private ProgressDialog dialog;

    public CommonConn(Context context, String mapping) {
        this.context = context;
        this.mapping = mapping;
        params = new HashMap<>();
    }

    public CommonConn(Context context, String mapping, HashMap<String, Object> params) {
        this.context = context;
        this.mapping = mapping;
        this.params = params;
    }

    public void addParam(String key, Object value) {
        if (key != null && value != null) {
            params.put(key, value);
        }
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
        apiInterface.getPost(mapping, params).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("Post 요청", "onResponse: ");
                onPostExcute(true, response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Post 요청", "onFailure: ");
                onPostExcute(false, t.getMessage());
            }
        });
    }

    private void onPostExcute(boolean isResult, String data) {
        if (dialog != null) {
            dialog.dismiss();
        }
        callback.onResult(isResult, data);
    }

    public interface ConnCallback {
        void onResult(boolean isResult, String data);
    }
}
