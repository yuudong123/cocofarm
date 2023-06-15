package com.cocofarm.andapp.product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.databinding.FragmentProductReviewBinding;


public class ProductReviewFragment extends Fragment {

    FragmentProductReviewBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentProductReviewBinding.inflate(inflater,container,false);
        int product_id = getArguments().getInt("product_id");//해당 상품과 연관된 아이디만 받아오려고

        return binding.getRoot();
    }
}