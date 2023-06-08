package com.cocofarm.andapp.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.databinding.ResultCompleteBinding;
import com.cocofarm.andapp.member.LoginActivity;

// 회원탈퇴 완료
public class AwayConfirmActivity extends AppCompatActivity {

    ResultCompleteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ResultCompleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onBackPressed();
        binding.tvComplete.setText("탈퇴가 완료되었습니다.\n이용해주셔서 감사합니다.");
        binding.btnOk.setText("확인");

        binding.btnOk.setOnClickListener(v->{
            Intent intent = new Intent(AwayConfirmActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}