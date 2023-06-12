package com.cocofarm.andapp.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.databinding.ActivityOrderFinishBinding;

public class OrderFinishActivity extends AppCompatActivity {
    ActivityOrderFinishBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderFinishBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnGoOrderContentA.setOnClickListener(v->{
            Intent intent = new Intent(OrderFinishActivity.this, OrderContentActivity.class);
            startActivity(intent);
        });

    }
}