package com.cocofarm.andapp.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.databinding.ActivityCartPopupBinding;
import com.cocofarm.andapp.databinding.BtnSheetProductBinding;
import com.cocofarm.andapp.product.ProductActivity;

public class Cart_PopupActivity extends AppCompatActivity {

    ActivityCartPopupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCartPopupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCartStay.setOnClickListener(v->{
            onBackPressed();
        });
        binding.btnGoCartAct.setOnClickListener(v->{
            Intent intent = new Intent(Cart_PopupActivity.this, Order_ContentActivity.class);
            startActivity(intent);
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       finish();
        //판매페이지 ProductActivity로 이동하기 위한 코드

    }
}
