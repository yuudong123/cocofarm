package com.cocofarm.andapp.mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cocofarm.andapp.FirstActivity;
import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.R;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.databinding.FragmentMypageBinding;
import com.cocofarm.andapp.member.LoginActivity;
import com.cocofarm.andapp.member.MemberVO;

public class MypageFragment extends Fragment {

    FragmentMypageBinding binding;
    MemberVO vo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMypageBinding.inflate(inflater, container, false);

        binding.tvNickname.setText(CommonVal.loginMember.getNickname());
        binding.tvEmail.setText(CommonVal.loginMember.getEmail());

        binding.btnLogout.setOnClickListener(v-> {
            CommonVal.loginMember = null;
            getActivity().finish();
            Toast.makeText(getActivity(), "로그아웃 완료", Toast.LENGTH_SHORT).show();
        });

        binding.tvAboutme.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(), AboutMeActivity.class);
            startActivity(intent);
        });
        binding.tvMyalert.setOnClickListener(v-> {
//            Intent intent = new Intent(getActivity(), AlertListActivity.class);
        });
        binding.tvMyorder.setOnClickListener(v-> {
//            Intent intent = new Intent(getActivity(), MyOrderListActivity.class);
        });
        binding.tvMyboard.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(), MyBoardActivity.class);
            startActivity(intent);
        });
        binding.tvCscenter.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(), CsCenterActivity.class);
            startActivity(intent);
        });
        binding.tvAway.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(), AwayActivity.class);
            startActivity(intent);
        });


        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}