package com.cocofarm.andapp.mypage;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.databinding.ResultCompleteBinding;
import com.cocofarm.andapp.member.LoginActivity;
import com.cocofarm.andapp.util.BackPressedHandler;

// 회원탈퇴 완료
public class AwayConfirmActivity extends AppCompatActivity {

    ResultCompleteBinding binding;
    BackPressedHandler backPressedHandler = new BackPressedHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ResultCompleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvComplete.setText("탈퇴가 완료되었습니다.\n이용해주셔서 감사합니다.");
        binding.btnOk.setText("확인");

        binding.btnOk.setOnClickListener(v -> {
            Intent intent = new Intent(AwayConfirmActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        backPressedHandler.onBackPressed();
    }
}