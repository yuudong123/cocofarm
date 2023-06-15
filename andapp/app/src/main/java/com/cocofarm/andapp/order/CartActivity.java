package com.cocofarm.andapp.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityCartBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    ActivityCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     binding= ActivityCartBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

     //디비에서 리스트 불러오는 코드 필요.
        CommonConn conn = new CommonConn(this,"");
        conn.addParam("member_no", CommonVal.loginMember.getMember_no());
        conn.onExcute((isResult, data) -> {
            if(isResult){
                ArrayList<CartDTO>list = new Gson().fromJson(data,new TypeToken<ArrayList<CartDTO>>(){}.getType());
            CartAdapter adapter = new CartAdapter(list);
                LinearLayoutManager manager= new LinearLayoutManager(this);
                binding.recvCart.setAdapter(adapter);
                binding.recvCart.setLayoutManager(manager);

            }
        });

        load();
        //삭제 버튼누르는게 db처리하고 남은것들도 db로 다시 불러오기. 불러오는 과정 여러번 이면..

        binding.btnGoCocomall.setOnClickListener(v->{
            //finish();
            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        binding.btnOrder.setOnClickListener(v->{
            Intent intent = new Intent(CartActivity.this, OrderActivity.class);
            startActivity(intent);
        });
    }

    protected void load(){
        List<OrderProductVO>list = new ArrayList<>();
        if(list.size() > 0){
            binding.layoutCart.setVisibility(View.VISIBLE);
            binding.layoutCartEmpty.setVisibility(View.GONE);
        }else{
            binding.layoutCart.setVisibility(View.GONE);
            binding.layoutCartEmpty.setVisibility(View.VISIBLE);
        }
    }
}