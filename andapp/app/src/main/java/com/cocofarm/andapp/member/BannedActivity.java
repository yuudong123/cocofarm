package com.cocofarm.andapp.member;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.databinding.ActivityBannedBinding;
import com.cocofarm.andapp.util.BackPressedHandler;

public class BannedActivity extends AppCompatActivity {

    ActivityBannedBinding binding;
    BackPressedHandler backPressedHandler = new BackPressedHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBannedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnOk.setOnClickListener(v -> {
            Intent intent = new Intent(BannedActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        backPressedHandler.onBackPressed();
    }
}