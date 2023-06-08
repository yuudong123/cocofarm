package com.cocofarm.andapp.member;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cocofarm.andapp.databinding.ActivityJoinBinding;
import com.cocofarm.andapp.R;

public class JoinActivity extends AppCompatActivity {

    ActivityJoinBinding binding;
    Button joinCoco;
    ImageView joinKakao, joinNaver, joinGoogle;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJoinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnJoin.setOnClickListener(v-> {

        });
    }
}