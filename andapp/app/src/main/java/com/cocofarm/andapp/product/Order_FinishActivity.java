package com.cocofarm.andapp.product;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.databinding.ActivityOrderFinishBinding;

public class Order_FinishActivity extends AppCompatActivity {
    ActivityOrderFinishBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_finish);
    }
}