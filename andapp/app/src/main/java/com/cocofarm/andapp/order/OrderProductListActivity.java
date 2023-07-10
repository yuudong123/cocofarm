package com.cocofarm.andapp.order;

import static com.cocofarm.andapp.common.CommonVal.loginMember;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityOrderProductListBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class OrderProductListActivity extends AppCompatActivity {
    ActivityOrderProductListBinding binding;
    CommonConn conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderProductListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnClose.setOnClickListener(v -> finish());
    }

    public void load() {
        conn = new CommonConn(this, "myorderlist.and");
        conn.addParam("member_no", loginMember.getMember_no());
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                ArrayList<OrderProductDTO> list = new Gson().fromJson(data, new TypeToken<ArrayList<OrderProductDTO>>() {
                }.getType());
                OrderProductAddAdapter adapter = new OrderProductAddAdapter(list);
                LinearLayoutManager manager = new LinearLayoutManager(this);
                binding.recvOrderFinish.setAdapter(adapter);
                binding.recvOrderFinish.setLayoutManager(manager);
            } else {
                Toast.makeText(this, "주문상품을 불러오지 못했습니다..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        load();
    }
}
