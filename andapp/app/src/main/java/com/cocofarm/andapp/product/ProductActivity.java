package com.cocofarm.andapp.product;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.databinding.ActivityProductBinding;
import com.cocofarm.andapp.databinding.BtnSheetProductBinding;
import com.cocofarm.andapp.databinding.FragmentProductBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import lombok.val;

public class ProductActivity extends AppCompatActivity {

   ActivityProductBinding binding;
   Button btn_minus, btn_plus;
   TextView tv_product_buy_amount;
   BtnSheetProductBinding bindingSheet;
    int number = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnProductBuy.setOnClickListener(v->{
            bindingSheet = BtnSheetProductBinding.inflate(getLayoutInflater(), null , false);

            BottomSheetDialog dialog = new BottomSheetDialog(this);

            dialog.setContentView(bindingSheet.getRoot());
            dialog.show();


                bindingSheet.btnMinus.setOnClickListener(view->{
                    if(number !=0){
                        number--;
                    }
                bindingSheet.tvProductBuyAmount.setText(number+"");

            });
            bindingSheet.btnPlus.setOnClickListener(view->{
                number++;
                bindingSheet.tvProductBuyAmount.setText(number+"");
            });
        });

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
        binding=null;
    }
}