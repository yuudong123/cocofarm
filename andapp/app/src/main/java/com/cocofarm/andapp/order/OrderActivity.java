package com.cocofarm.andapp.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.databinding.ActivityOrderBinding;

public class OrderActivity extends AppCompatActivity {

    ActivityOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnOrderFinish.setOnClickListener(v->{
            Intent intent = new Intent(OrderActivity.this, OrderFinishActivity.class);
            startActivity(intent);
        });
    }

}