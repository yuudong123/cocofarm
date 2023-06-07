package com.cocofarm.andapp.board;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cocofarm.andapp.databinding.ActivityQnaWriteBinding;

public class QnAWriteActivity extends AppCompatActivity {

    ActivityQnaWriteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQnaWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}