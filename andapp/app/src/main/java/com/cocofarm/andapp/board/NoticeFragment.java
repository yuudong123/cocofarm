package com.cocofarm.andapp.board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.cocofarm.andapp.databinding.FragmentNoticeBinding;

public class NoticeFragment extends Fragment {

    FragmentNoticeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNoticeBinding.inflate(inflater, container, false);



        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}