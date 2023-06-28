package com.cocofarm.andapp.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.util.Date;

public class BackPressedHandler {
    private long backPressedMills;
    Activity activity;

    public BackPressedHandler(Activity activity) {
        this.activity = activity;
    }

    public void onBackPressed(){
        long currentTimeMills = new Date().getTime();

        if (currentTimeMills - backPressedMills > 2000) {
            backPressedMills = new Date().getTime();
            Toast.makeText(activity, "\'뒤로가기\' 버튼을 한번 더 누를 시, 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else {
            activity.finish();
        }
    }
}
