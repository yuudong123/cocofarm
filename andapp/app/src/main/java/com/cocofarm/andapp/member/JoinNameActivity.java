package com.cocofarm.andapp.member;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.cocofarm.andapp.databinding.InputInfoBinding;

public class JoinNameActivity extends AppCompatActivity {

    InputInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = InputInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvInfoTitle.setText("닉네임");
        binding.tvInfoAssist.setText("닉네임을 입력하세요.");

        binding.btnOk.setOnClickListener(view -> {
            
//           try {
//               MemberVO vo = new MemberVO();
//               vo.setNickname(binding.edtInfo.getText().toString());
//                Intent intent = new Intent(JoinNameActivity.this, JoinPwFragment.class);
//                intent.putExtra("nickname", vo);
//               FragmentManager manager = getSupportFragmentManager();
//               FragmentTransaction transaction = manager.beginTransaction();
//
//               MemberVO vo = new MemberVO();
//               vo.setNickname(binding.edtInfo.getText().toString());
//
//
//               Bundle bundle = new Bundle();
//
//            } catch (NullPointerException e) {
//                Toast.makeText(this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
//            }
        });
    }
}