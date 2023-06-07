package com.cocofarm.andapp.product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.databinding.FragmentEventBinding;
import com.cocofarm.andapp.databinding.FragmentProductBinding;
import com.cocofarm.andapp.databinding.FragmentProductPlantBinding;

public class ProductPlantFragment extends Fragment {

    FragmentProductPlantBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProductPlantBinding.inflate(inflater, container, false);

        PlantAdapter adapter = new PlantAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.recvProduct.setAdapter(adapter);
        binding.recvProduct.setLayoutManager(manager);

        return binding.getRoot();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}