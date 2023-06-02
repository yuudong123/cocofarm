package com.cocofarm.andapp.board;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cocofarm.andapp.databinding.ActivityBoardWriteBinding;

public class BoardWriteActivity extends AppCompatActivity {

    ActivityBoardWriteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBoardWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}