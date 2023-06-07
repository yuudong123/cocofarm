package com.cocofarm.andapp.board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.databinding.FragmentBoardReadBinding;


public class BoardReadFragment extends Fragment {

    FragmentBoardReadBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBoardReadBinding.inflate(inflater, container, false);

        binding.ivMainImage.setImageResource(R.drawable.temp_image_event1); // 나중에 첨부파일로 바꿀 것

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}