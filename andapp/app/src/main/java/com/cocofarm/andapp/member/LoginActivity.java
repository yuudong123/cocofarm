package com.cocofarm.andapp.member;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.FirstActivity;
import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityLogin2Binding;
import com.cocofarm.andapp.databinding.ActivityLoginBinding;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {
    ActivityLogin2Binding binding;
    Boolean isfirst_flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogin2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tvFirst.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        if (CommonVal.isCheckLogout) {
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
        binding.btnLogin.setOnClickListener(v -> {
            login(binding.edtId.getText().toString(), binding.edtPw.getText().toString());

            if (binding.chkLogin.isChecked()) {
                saveLoginInfo();
            } else {
                nonSaveLoginInfo();
            }
        });

        // 비밀번호 찾기
        binding.tvFindpw.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this, FindPwActivity.class);
            startActivity(intent);
        });

        // 비회원
        binding.tvNonmember.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // 첫화면
        binding.tvFirst.setOnClickListener(v-> {
            Intent intent = new Intent(this, FirstActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

        CommonConn conn = new CommonConn(this, "/member/login.and");
        conn.addParam("email", email);
        conn.addParam("password", pw);


        conn.onExcute((isResult, data) -> {
            Log.d("로그인", "onCreate: " + data);

            if (!isResult) {
                Toast.makeText(this, "로그인 서버가 응답하지 않습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (data == null || data.equals("null")) {
                Toast.makeText(this, "이메일 또는 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            CommonVal.loginMember = new Gson().fromJson(data, MemberVO.class);
            if (CommonVal.loginMember.getIsactivated().equals("Y")) {
                Log.d("로그인", "onCreate: " + CommonVal.loginMember.getNickname());
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(LoginActivity.this, BannedActivity.class);
                CommonVal.loginMember = null;
                startActivity(intent);
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