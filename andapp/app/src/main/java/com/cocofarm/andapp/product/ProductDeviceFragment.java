package com.cocofarm.andapp.product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.databinding.FragmentProductDeviceBinding;

public class ProductDeviceFragment extends Fragment {
    //판매페이지에 관리기기 물품 판매 플래그먼트 페이지
    FragmentProductDeviceBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentProductDeviceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}