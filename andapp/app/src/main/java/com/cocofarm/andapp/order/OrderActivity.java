package com.cocofarm.andapp.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.databinding.ActivityOrderBinding;

public class OrderActivity extends AppCompatActivity {

    ActivityOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderBinding.inflate(getLayoutInflater());

        OrderAdapter adapter =new OrderAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recvOrderProduct.setAdapter(adapter);
        binding.recvOrderProduct.setLayoutManager(manager);

        setContentView(binding.getRoot());

        binding.btnOrderFinish.setOnClickListener(v->{
            Intent intent = new Intent(OrderActivity.this, OrderFinishActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}