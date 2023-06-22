package com.cocofarm.andapp.member;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.InputInfoEmailBinding;
import com.cocofarm.andapp.databinding.InputInfoPwBinding;

public class ChangePwActivity extends AppCompatActivity {
    InputInfoPwBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = InputInfoPwBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String email = (String) getIntent().getSerializableExtra("email");

        binding.tvInfoTitle.setText("비밀번호 새로 입력");
        binding.tvInfoAssist.setText("새로 사용하실 비밀번호를 입력해주세요.");

        binding.tvOk.setOnClickListener(v-> {
            if (!binding.edtInfo.getText().toString().equals(binding.edtInfo2.getText().toString())) {
                Toast.makeText(this, "비밀번호를 동일하게 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                CommonConn conn = new CommonConn(this, "/member/modifypw");
                conn.addParam("email", email);
                conn.addParam("password", binding.edtInfo.getText().toString());
                conn.onExcute((isResult, data) -> {
                    if (!isResult) {
                        return;
                    } else {
                        Intent intent = new Intent (this, LoginActivity.class);
                        Toast.makeText(this, "비밀번호가 변경되었습니다.\n변경된 비밀번호로 로그인 해주세요.", Toast.LENGTH_SHORT).show();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });

            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}