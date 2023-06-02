package com.cocofarm.andapp.member;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.databinding.ActivityBannedBinding;

public class BannedActivity extends AppCompatActivity {

    ActivityBannedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBannedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}