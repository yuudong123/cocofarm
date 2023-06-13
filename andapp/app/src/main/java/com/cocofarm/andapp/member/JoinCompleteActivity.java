package com.cocofarm.andapp.member;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.databinding.ResultCompleteBinding;

public class JoinCompleteActivity extends AppCompatActivity {

    ResultCompleteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ResultCompleteBinding.inflate(getLayoutInflater());

        binding.tvComplete.setText("감사합니다.\n코코팜 회원가입이 완료되었습니다.");
        binding.btnOk.setText("로그인하기");

        binding.btnOk.setOnClickListener(v->{

            Intent intent = new Intent(JoinCompleteActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        setContentView(binding.getRoot());
    }
}