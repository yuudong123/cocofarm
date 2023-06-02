package com.cocofarm.andapp.board;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cocofarm.andapp.databinding.ActivityBoardModifyBinding;

public class BoardModifyActivity extends AppCompatActivity {

    ActivityBoardModifyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBoardModifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}