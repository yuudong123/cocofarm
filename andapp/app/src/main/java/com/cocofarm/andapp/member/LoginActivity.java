package com.cocofarm.andapp.member;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityLoginBinding;
import com.cocofarm.andapp.R;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 로그인 정보
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        String email = preferences.getString("email", "");
        String pw = preferences.getString("password", "");

        binding.edtId.setText(email);
        binding.edtPw.setText(pw);


        // 로그인
        binding.btnLogin.setOnClickListener(v-> {
            CommonConn conn = new CommonConn(this, "login");
            conn.addParam("email", binding.edtId.getText().toString());
            conn.addParam("password", binding.edtPw.getText().toString());

            conn.onExcute((isResult, data) -> {
                Log.d("로그인", "onCreate: " + data);
                CommonVal.loginMember = new Gson().fromJson(data, MemberVO.class);
                if(CommonVal.loginMember != null) {
                    if(CommonVal.loginMember.getIsactivated().equals("Y")) {
                        Log.d("로그인", "onCreate: " + CommonVal.loginMember.getNickname());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(LoginActivity.this, BannedActivity.class);
                        CommonVal.loginMember = null;
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "이메일 또는 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            });
        });


        // 아이디 찾기 화면으로


        // 비밀번호 찾기 화면으로



        // 회원가입 화면으로
        binding.tvJoin.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
            startActivity(intent);
        });

        // 비회원
        binding.tvNonmember.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });





    }
}