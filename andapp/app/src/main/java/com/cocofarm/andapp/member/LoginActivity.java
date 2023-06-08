package com.cocofarm.andapp.member;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
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
    EditText id, pw;
    TextView join;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // 로그인
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        String id = preferences.getString("id", "");
        String pw = preferences.getString("pw", "");

        binding.edtId.setText(id);
        binding.edtPw.setText(pw);

        binding.btnLogin.setOnClickListener(v-> {
            CommonConn conn = new CommonConn(this, "login");
            conn.addParam("id", binding.edtId.getText().toString());
            conn.addParam("pw", binding.edtPw.getText().toString());

            conn.onExcute((isResult, data) -> {
                CommonVal.loginMember = new Gson().fromJson(data, MemberVO.class);

                if(CommonVal.loginMember != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent); // 로그인 성공
                } else {
                    Toast.makeText(this, "이메일 또는 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show(); // 로그인 실패
                }
            });
        });


        // 아이디 찾기 화면으로


        // 비밀번호 찾기 화면으로



        // 회원가입 화면으로
        join.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
            startActivity(intent);
        });
    }
}