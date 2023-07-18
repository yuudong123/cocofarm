package com.cocofarm.andapp.member;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.InputInfoPwBinding;
import com.cocofarm.andapp.util.BackPressedHandler;

public class ChangePwActivity extends AppCompatActivity {
    InputInfoPwBinding binding;
    BackPressedHandler backPressedHandler = new BackPressedHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = InputInfoPwBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String email = (String) getIntent().getSerializableExtra("email");

        binding.tvInfoTitle.setText("비밀번호 새로 입력");
        binding.tvInfoAssist.setText("새로 사용하실 비밀번호를 입력해주세요.");

        binding.tvOk.setOnClickListener(v -> {
            if (!binding.edtInfo.getText().toString().equals(binding.edtInfo2.getText().toString())) {
                Toast.makeText(this, "비밀번호를 동일하게 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            CommonConn conn = new CommonConn(this, "/member/modifypw.and");
            conn.addParam("email", email);
            conn.addParam("password", binding.edtInfo.getText().toString());
            conn.onExcute((isResult, data) -> {
                if (isResult) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    Toast.makeText(this, "비밀번호가 변경되었습니다.\n변경된 비밀번호로 로그인 해주세요.", Toast.LENGTH_SHORT).show();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public void onBackPressed() {
        backPressedHandler.onBackPressed();
    }
}