package com.cocofarm.andapp.member;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.databinding.InputInfoPwBinding;

public class JoinPwActivity extends AppCompatActivity {

    InputInfoPwBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = InputInfoPwBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvInfoTitle.setText("비밀번호");
        binding.tvInfoAssist.setText("비밀번호를 입력하세요.");
        binding.edtInfo.setHint("6자 이상의 영문,숫자 혼합");
        binding.btnOk.setText("다음");

        MemberVO vo = (MemberVO) getIntent().getSerializableExtra("join");

        binding.btnOk.setOnClickListener(view -> {
            String password = binding.edtInfo.getText().toString();

            if (password.isEmpty() || (password.length() < 6)) {
                Toast.makeText(this, "6자 이상의 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                vo.setPassword(password);

                Intent intent = new Intent(JoinPwActivity.this, JoinNameActivity.class);
                intent.putExtra("join", vo);
                startActivity(intent);
            }
        });
    }
}