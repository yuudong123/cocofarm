package com.cocofarm.andapp.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityOrderBinding;
import com.cocofarm.andapp.product.ProductActivity;
import com.cocofarm.andapp.product.ProductVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    CommonConn conn;

    ActivityOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderBinding.inflate(getLayoutInflater());
        ArrayList<CartDTO> list = new ArrayList<>();
        Intent intent = getIntent();
        int value = intent.getIntExtra("value",0);
        if(value==1){
            int number = intent.getIntExtra("number",0);
            ProductVO productVO = (ProductVO) intent.getSerializableExtra("productVO");
            CartDTO cartDTO = new CartDTO();
            cartDTO.setCart_id(1);
            cartDTO.setChecked(true);
            cartDTO.setMember_no(CommonVal.loginMember.getMember_no());
            cartDTO.setAmount(number);
            cartDTO.setProduct_name(productVO.getName());
            cartDTO.setProduct_price(productVO.getPrice());
            cartDTO.setProduct_image(productVO.getFilename());
            list.add(cartDTO);
        }else {
            for (CartDTO cartDTO : CommonVal.cart) {
                if (cartDTO.isChecked()) {
                    list.add(cartDTO);
                }
            }
        }
        binding.tvOrderAllPrice.setText(getAllPrice(list)+"");



        binding.etOrderAddress.setText(CommonVal.loginMember.getAddress());
        binding.tvOrderPersonname.setText(CommonVal.loginMember.getNickname());

        OrderAdapter adapter = new OrderAdapter(list);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.recvOrderProduct.setAdapter(adapter);
        binding.recvOrderProduct.setLayoutManager(manager);

        setContentView(binding.getRoot());

        binding.btnOrderFinish.setOnClickListener(v->{
            Intent intent1 = new Intent(OrderActivity.this, OrderFinishActivity.class);
            startActivity(intent1);
        });
        }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
    }

    private int getAllPrice(List<CartDTO> list){
        int allPrice = 0;
        for (CartDTO cartDTO : list){
            allPrice += cartDTO.getProduct_price()*cartDTO.getAmount();

        }
        return allPrice;
    }
}