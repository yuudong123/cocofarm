package com.cocofarm.andapp.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.databinding.ActivityAmModifyBinding;
import com.cocofarm.andapp.member.MemberVO;

public class AmModifyActivity extends AppCompatActivity {

    ActivityAmModifyBinding binding;
    MemberVO vo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAmModifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvName.setText(CommonVal.loginMember.getNickname());
        binding.tvPw.setText(CommonVal.loginMember.getPassword());
        binding.tvName.setText(CommonVal.loginMember.getNickname());
        binding.tvPhone.setText(CommonVal.loginMember.getPhoneNumber());

        // 비밀번호
        binding.tvPwModify.setOnClickListener(v->{
            binding.tvPwModify.setVisibility(View.INVISIBLE);
            binding.tvPw.setVisibility(View.INVISIBLE);
            binding.edtPw.setVisibility(View.VISIBLE);
            binding.edtPw.setText(" ");
        });

        // 닉네임
        binding.tvNameModify.setOnClickListener(v->{
            String name = (String)binding.tvName.getText();

            binding.tvNameModify.setVisibility(View.INVISIBLE);
            binding.tvName.setVisibility(View.INVISIBLE);
            binding.edtName.setVisibility(View.VISIBLE);
            binding.edtName.setText(name);
        });

        // 전화번호
        binding.tvPhoneModify.setOnClickListener(v->{
            String phone = (String)binding.tvPhone.getText();

            binding.tvPhoneModify.setVisibility(View.INVISIBLE);
            binding.tvPhone.setVisibility(View.INVISIBLE);
            binding.edtPhone.setVisibility(View.VISIBLE);
            binding.edtPhone.setText(phone);
        });


        // 완료
        binding.btnOk.setOnClickListener(v->{
            vo.setPassword(binding.edtPw.getText().toString());
            vo.setNickname(binding.edtName.getText().toString());
            vo.setPhoneNumber(binding.edtPhone.getText().toString());

            Intent intent = new Intent(AmModifyActivity.this, AboutMeActivity.class);
            startActivity(intent);
        });
    }
}