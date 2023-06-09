package com.cocofarm.andapp.member;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocofarm.andapp.databinding.InputInfoBinding;

public class JoinPwFragment extends Fragment {

    InputInfoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = InputInfoBinding.inflate(inflater, container, false);

        binding.tvInfoTitle.setText("비밀번호");
        binding.tvInfoAssist.setText("비밀번호를 입력하세요.");

//        MemberVO vo = (MemberVO)getIntent().getSerializableExtra("nickname");
//
//        binding.btnOk.setOnClickListener(view -> {
//            MemberVO vo = new MemberVO();
//            vo.setPassword(binding.edtInfo.getText().toString());
//        });


        return binding.getRoot();
    }
}