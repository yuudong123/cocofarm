package com.cocofarm.andapp.order;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.databinding.ActivityOrderFinishBinding;

public class OrderFinishActivity extends AppCompatActivity {
    ActivityOrderFinishBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderFinishBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onBackPressed();
        Intent intent1 = getIntent();
        String value = intent1.getStringExtra("order_id");

        binding.btnGoOrderContentA.setOnClickListener(v -> {
            Intent intent = new Intent(OrderFinishActivity.this, OrderProductListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("order_id", value);
            startActivity(intent);
        });
        binding.btnGoCart.setOnClickListener(v -> {
            Intent intent = new Intent(OrderFinishActivity.this, CartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
        binding.btnGoMain.setOnClickListener(v -> {
            Intent intent = new Intent(OrderFinishActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finishAffinity();
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "버튼을 눌러 이동해주세요", Toast.LENGTH_SHORT).show();
    }
}