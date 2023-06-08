package com.cocofarm.andapp.mypage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.databinding.ActivityAboutMeBinding;
import com.cocofarm.andapp.member.MemberVO;

public class AboutMeActivity extends AppCompatActivity {

    ActivityAboutMeBinding binding;
    MemberVO vo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutMeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.tvModify.setOnClickListener(v->{
            Intent intent = new Intent(AboutMeActivity.this, AmConfirmActivity.class);
            startActivity(intent);
        });

        try {
            binding.tvEmail.setText(CommonVal.loginMember.getEmail());
            binding.tvPw.setText(CommonVal.loginMember.getPassword());
            binding.tvNickname.setText(CommonVal.loginMember.getNickname());
            binding.tvPhone.setText(CommonVal.loginMember.getPhoneNumber());
        } catch (NullPointerException e) {

        }

    }



}