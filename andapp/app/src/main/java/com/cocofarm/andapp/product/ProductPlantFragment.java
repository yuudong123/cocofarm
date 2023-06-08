package com.cocofarm.andapp.product;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.databinding.FragmentEventBinding;
import com.cocofarm.andapp.databinding.FragmentProductBinding;
import com.cocofarm.andapp.databinding.FragmentProductPlantBinding;

import java.util.ArrayList;

public class ProductPlantFragment extends Fragment {

    FragmentProductPlantBinding binding;

    LinearLayout layout_product_item;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProductPlantBinding.inflate(inflater, container, false);

        PlantAdapter adapter = new PlantAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        binding.recvProductPlant.setAdapter(adapter);
        binding.recvProductPlant.setLayoutManager(manager);

        return binding.getRoot();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}