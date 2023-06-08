package com.cocofarm.andapp.member;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.databinding.ActivityInputInfoBinding;

public class JoinNameActivity extends AppCompatActivity {

    ActivityInputInfoBinding binding;
    TextView title, assist;
    EditText info;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInputInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvInfoTitle.setText("닉네임");
        binding.tvInfoAssist.setText("닉네임을 입력하세요.");


//        binding.btnOk.setOnClickListener(v-> {
//            Intent intent = new Intent(JoinNameActivity.this, JoinPwActivity.class);
//            startActivity(intent);
//        });

    }
}