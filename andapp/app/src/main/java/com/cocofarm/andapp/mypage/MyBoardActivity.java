package com.cocofarm.andapp.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cocofarm.andapp.databinding.ActivityMyBoardBinding;

public class MyBoardActivity extends AppCompatActivity {
    ActivityMyBoardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}