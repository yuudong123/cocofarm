package com.cocofarm.andapp.product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        DeviceAdapter adapter = new DeviceAdapter();
        LinearLayoutManager manager= new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recvProductDevice.setAdapter(adapter);
        binding.recvProductDevice.setLayoutManager(manager);

        return binding.getRoot();
    }
}