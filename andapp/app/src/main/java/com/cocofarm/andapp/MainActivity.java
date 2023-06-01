package com.cocofarm.andapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.board.BoardFragment;
import com.cocofarm.andapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new BoardFragment()).commit();
        setContentView(binding.getRoot());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}