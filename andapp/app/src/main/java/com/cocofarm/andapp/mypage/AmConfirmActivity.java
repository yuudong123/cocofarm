package com.cocofarm.andapp.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.databinding.ActivityAmConfirmBinding;

public class AmConfirmActivity extends AppCompatActivity {

    ActivityAmConfirmBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAmConfirmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvEmail.setText(CommonVal.loginMember.getEmail());

        binding.btnOk.setOnClickListener(v -> {
            if (binding.edtPw.getText().toString().equals(CommonVal.loginMember.getPassword().toString())) {
                Intent intent = new Intent(AmConfirmActivity.this, AmModifyActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                binding.edtPw.setSelection(binding.edtPw.getText().length());
            }
        });
    }
}