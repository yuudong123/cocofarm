package com.cocofarm.andapp.order;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cocofarm.andapp.databinding.ActivityOrderProductBinding;

public class OrderProductActivity extends AppCompatActivity {

    ActivityOrderProductBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      binding=ActivityOrderProductBinding.inflate(getLayoutInflater());
      setContentView(binding.getRoot());


    }
}