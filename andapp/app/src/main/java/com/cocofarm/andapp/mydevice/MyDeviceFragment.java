package com.cocofarm.andapp.mydevice;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.databinding.FragmentMyDeviceBinding;

public class MyDeviceFragment extends Fragment {

    FragmentMyDeviceBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyDeviceBinding.inflate(inflater, container, false);



        return binding.getRoot();
    }
}