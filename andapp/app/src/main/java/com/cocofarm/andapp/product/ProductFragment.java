package com.cocofarm.andapp.product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.databinding.FragmentProductBinding;


public class ProductFragment extends Fragment {
    FragmentProductBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentProductBinding.inflate(inflater, container, false);
        binding.activityPruductPlant.addTab(binding.activityPruductPlant.newTab().setText("식물"));
        binding.activityPruductPlant.addTab(binding.activityPruductPlant.newTab().setText("관리 기기"));


        return binding.getRoot();


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}