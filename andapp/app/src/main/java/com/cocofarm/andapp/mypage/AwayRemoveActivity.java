package com.cocofarm.andapp.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.R;
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



        binding.btnCancel.setOnClickListener(v->{
            Intent intent = new Intent(AwayRemoveActivity.this, MainActivity.class);
            startActivity(intent);
        });

        binding.btnRemove.setOnClickListener(v->{
            Intent intent = new Intent(AwayRemoveActivity.this, AwayConfirmActivity.class);
            CommonConn conn = new CommonConn(this, "Away");
            conn.addParam("email", CommonVal.loginMember.getEmail().toString());

            conn.onExcute((isResult, data) -> {
                Log.d("탈퇴", "onCreate: " + data);

            });
        });


    }
}