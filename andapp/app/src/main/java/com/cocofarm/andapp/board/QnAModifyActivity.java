package com.cocofarm.andapp.board;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cocofarm.andapp.databinding.ActivityQnaModifyBinding;

public class QnAModifyActivity extends AppCompatActivity {

    ActivityQnaModifyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQnaModifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}