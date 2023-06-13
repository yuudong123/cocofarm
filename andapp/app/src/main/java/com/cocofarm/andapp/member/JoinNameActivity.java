package com.cocofarm.andapp.member;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.InputInfoBinding;

public class JoinNameActivity extends AppCompatActivity {

    InputInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = InputInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvInfoTitle.setText("닉네임");
        binding.tvInfoAssist.setText("닉네임을 입력하세요.");
        binding.edtInfo.setHint("2글자 이상 입력해주세요.");
        binding.btnOk.setText("완료");

        MemberVO vo = (MemberVO) getIntent().getSerializableExtra("join");

        binding.btnOk.setOnClickListener(view -> {
            String nickname = binding.edtInfo.getText().toString();

            if (nickname.isEmpty() || (nickname.length() < 2)) {
                Toast.makeText(this, "2글자 이상의 닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                vo.setNickname(nickname);

                CommonConn conn = new CommonConn(this, "join");
                conn.addParam("email", vo.getEmail());
                conn.addParam("password", vo.getPassword());
                conn.addParam("nickname", vo.getNickname());

                conn.onExcute((isResult, data) -> {
                    if(isResult) {
                        Log.d("회원가입 정보", "onCreate: " + data);
                        Intent intent = new Intent(JoinNameActivity.this, JoinCompleteActivity.class);
                        startActivity(intent);
                    } else {
                        Log.d("오류 발생", "onfalse: ");
                        Toast.makeText(this, "오류 발생", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }
}