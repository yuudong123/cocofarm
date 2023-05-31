package com.cocofarm.andapp.board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cocofarm.andapp.databinding.FragmentQnABinding;

public class QnAFragment extends Fragment {

    FragmentQnABinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQnABinding.inflate(inflater, container, false);


        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}