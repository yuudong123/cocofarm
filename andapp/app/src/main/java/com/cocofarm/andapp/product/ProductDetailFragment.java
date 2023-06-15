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
        String productContent = getArguments().getString("product_content"); //해당상품과 관련된 내용만 받으려고.


        return binding.getRoot();
    }
}