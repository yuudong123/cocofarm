package com.cocofarm.andapp.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cocofarm.andapp.FirstActivity;
import com.cocofarm.andapp.databinding.FragmentNonMemberBinding;

public class NonMemberFragment extends Fragment {

    FragmentNonMemberBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNonMemberBinding.inflate(inflater, container, false);

        binding.btnFirst.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FirstActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        return binding.getRoot();
    }
}