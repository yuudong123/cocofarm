package com.cocofarm.andapp.member;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.databinding.ActivityJoinBinding;
import com.cocofarm.andapp.R;

public class JoinActivity extends AppCompatActivity {

    ActivityJoinBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJoinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}