package com.cocofarm.andapp.member;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ResultCompleteLottieBinding;
import com.cocofarm.andapp.util.BackPressedHandler;
import com.google.gson.Gson;

public class JoinCompleteActivity extends AppCompatActivity {

    ResultCompleteLottieBinding binding;
    BackPressedHandler backPressedHandler = new BackPressedHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ResultCompleteLottieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MemberVO vo = (MemberVO) getIntent().getSerializableExtra("login");

        binding.tvComplete.setText("감사합니다.\n코코팜 회원가입이 완료되었습니다.");
        binding.btnOk.setText("로그인하기");

        binding.btnOk.setOnClickListener(v -> {
            CommonConn conn = new CommonConn(this, "/member/login.and");
            conn.addParam("email", vo.getEmail());
            conn.addParam("password", vo.getPassword());
            conn.onExcute((isResult, data) -> {
                if (isResult) {
                    CommonVal.loginMember = new Gson().fromJson(data, MemberVO.class);
                    Intent intent = new Intent(JoinCompleteActivity.this, MainActivity.class);
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