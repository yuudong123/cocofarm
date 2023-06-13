package com.cocofarm.andapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.databinding.ActivitySplashBinding;
import com.cocofarm.andapp.member.LoginActivity;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        YoYo.with(Techniques.FadeIn).duration(1500).repeat(0).playOn(binding.ivLogo);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, FirstActivity.class);
            startActivity(intent);
            finish();
        }, 1 * 3000);
    }
}