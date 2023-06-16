package com.cocofarm.andapp.member;

import android.app.Activity;
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

import com.cocofarm.andapp.FirstActivity;
import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityLoginBinding;
import com.cocofarm.andapp.R;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    Boolean isfirst_flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(CommonVal.isCheckLogout){
            nonSaveLoginInfo();
        }

        // 로그인 정보
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean isChecked = preferences.getBoolean("checked", false);
        binding.chkLogin.setChecked(isChecked);


        if (binding.chkLogin.isChecked()) {
            String email = preferences.getString("email", "");
            String pw = preferences.getString("password", "");
            binding.edtId.setText(email);
            binding.edtPw.setText(pw);

            login(email, pw);
        }


        // 로그인
        binding.btnLogin.setOnClickListener(v-> {
            login(binding.edtId.getText().toString(), binding.edtPw.getText().toString());

            if (binding.chkLogin.isChecked()) {
                saveLoginInfo();
            } else {
                nonSaveLoginInfo();
            }
        });



        // 아이디 찾기


        // 비밀번호 찾기


        // 비회원
        binding.tvNonmember.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void saveLoginInfo() {
        // 로그인 정보 저장
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", binding.edtId.getText().toString());
        editor.putString("password", binding.edtPw.getText().toString());
        editor.putBoolean("checked", binding.chkLogin.isChecked());
        editor.apply();
    }

    private void nonSaveLoginInfo() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", "");
        editor.putString("password", "");
        editor.putBoolean("checked", false);
        editor.apply();
    }

    private void login(String email, String pw) {

        CommonConn conn = new CommonConn(this, "login");
        conn.addParam("email", email);
        conn.addParam("password", pw);


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
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isfirst_flag) {
            isfirst_flag = false;
        } else {
            binding.edtId.setText("");
            binding.edtPw.setText("");
            binding.chkLogin.setChecked(false);
        }
    }
}