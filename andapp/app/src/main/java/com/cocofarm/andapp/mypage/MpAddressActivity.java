package com.cocofarm.andapp.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cocofarm.andapp.databinding.ActivityMpAddressBinding;

public class MpAddressActivity extends AppCompatActivity {

    ActivityMpAddressBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMpAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}