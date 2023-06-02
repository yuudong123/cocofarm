package com.cocofarm.andapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}