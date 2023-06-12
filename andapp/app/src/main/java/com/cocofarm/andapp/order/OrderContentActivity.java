package com.cocofarm.andapp.order;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.databinding.ActivityOrderBinding;
import com.cocofarm.andapp.databinding.ActivityOrderContentBinding;

public class OrderContentActivity extends AppCompatActivity {

    ActivityOrderContentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      binding=ActivityOrderContentBinding.inflate(getLayoutInflater());
      setContentView(binding.getRoot());


    }
}