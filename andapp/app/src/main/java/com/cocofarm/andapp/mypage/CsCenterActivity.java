package com.cocofarm.andapp.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.cocofarm.andapp.databinding.ActivityCsCenterBinding;

public class CsCenterActivity extends AppCompatActivity {

    ActivityCsCenterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCsCenterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnQna.setOnClickListener(v->{

        });

        binding.btnCall.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01012345678"));
            startActivity(intent);
        });

        binding.btnNotice.setOnClickListener(v->{

        });

        binding.btnEvent.setOnClickListener(v->{

        });
    }
}