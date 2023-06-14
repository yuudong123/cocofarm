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
        return binding.getRoot();
    }
}