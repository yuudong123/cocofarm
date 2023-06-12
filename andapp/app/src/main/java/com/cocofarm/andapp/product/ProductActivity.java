package com.cocofarm.andapp.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.databinding.ActivityProductBinding;
import com.cocofarm.andapp.databinding.BtnSheetProductBinding;
import com.cocofarm.andapp.databinding.FragmentProductBinding;
import com.cocofarm.andapp.order.CartActivity;
import com.cocofarm.andapp.order.Cart_PopupActivity;
import com.cocofarm.andapp.order.OrderActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import lombok.val;

public class ProductActivity extends AppCompatActivity {

    ActivityProductBinding binding;
    BtnSheetProductBinding bindingSheet;

    int number = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        bindingSheet = BtnSheetProductBinding.inflate(getLayoutInflater(), null, false);
        binding.btnProductBuy.setOnClickListener(v -> {


            BottomSheetDialog dialog = new BottomSheetDialog(this);

            dialog.setContentView(bindingSheet.getRoot());
            dialog.show();
            bindingSheet.btnMinus.setOnClickListener(view -> {
                if (number != 0) {
                    number--;
                }
                bindingSheet.tvProductBuyAmount.setText(number + "");
                allPrice();
            });
            bindingSheet.btnPlus.setOnClickListener(view -> {
                number++;
                bindingSheet.tvProductBuyAmount.setText(number + "");
                allPrice();
            });
        });
        allPrice();


        bindingSheet.btnGoCart.setOnClickListener(view -> {

            if (number <= 0) {
                Toast.makeText(this, "수량을 추가해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
                Intent intent = new Intent(ProductActivity.this, Cart_PopupActivity.class);
                startActivity(intent);

        });

        bindingSheet.btnGoBuy.setOnClickListener(view -> {
            if (number <= 0) {
                Toast.makeText(this, "수량을 추가해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(ProductActivity.this, OrderActivity.class);
            startActivity(intent);
        });


        //tv_all_price에 tv_product_buy_amount*tv_sheet_order_price 한 가격을 넣어줘야 함.


        // tv_product_buy_amount = findViewById(R.id.tv_product_buy_amount);


//            btn_minus.setOnClickListener(v->{
//                number--;
//                tv_product_buy_amount.setText(number);
//
//                    }
//
//            );


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public void allPrice() {
        int value1 = number;
        int value2 = Integer.parseInt(bindingSheet.tvSheetOrderPrice.getText().toString());

        int result = value1 * value2;
        bindingSheet.tvAllPrice.setText(String.valueOf(result));
    }
}
