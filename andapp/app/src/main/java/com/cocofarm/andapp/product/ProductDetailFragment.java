package com.cocofarm.andapp.product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.databinding.FragmentProductDetailBinding;


public class ProductDetailFragment extends Fragment {

    FragmentProductDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     
        binding=FragmentProductDetailBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}