package com.cocofarm.andapp.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ResultRemoveBinding;

// 회원탈퇴 확인
public class AwayRemoveActivity extends AppCompatActivity {

    ResultRemoveBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ResultRemoveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvRemove.setText("정말 탈퇴하시겠습니까?");
        binding.btnRemove.setText("탈퇴");

        binding.btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent(AwayRemoveActivity.this, MainActivity.class);
            startActivity(intent);
        });

        binding.btnRemove.setOnClickListener(v -> {
            CommonConn conn = new CommonConn(this, "/member/away.and");
            conn.addParam("email", CommonVal.loginMember.getEmail().toString());
            conn.onExcute((isResult, data) -> {
                if (isResult) {
                    Log.d("탈퇴", "onCreate: " + data);
                    Intent intent = new Intent(AwayRemoveActivity.this, AwayConfirmActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}