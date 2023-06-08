package com.cocofarm.andapp.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.databinding.ActivityAmConfirmBinding;

public class AwayActivity extends AppCompatActivity {

    ActivityAmConfirmBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAmConfirmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tvTitle.setText("회원탈퇴");
        binding.tvEmail.setText(CommonVal.loginMember.getEmail());
        binding.btnOk.setOnClickListener(v->{
            if(binding.edtPw.getText().toString() == CommonVal.loginMember.getPassword()) {
                Intent intent = new Intent(AwayActivity.this, AmModifyActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                binding.edtPw.setSelection(binding.edtPw.getText().length());
            }
        });
    }
}