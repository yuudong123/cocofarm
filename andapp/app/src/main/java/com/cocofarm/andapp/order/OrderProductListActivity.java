package com.cocofarm.andapp.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.common.CommonVal;
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
        conn = new CommonConn(this, "myorderlist.and");
        conn.addParam("member_no", CommonVal.loginMember.getMember_no());
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                ArrayList<OrderProductDTO> list = new Gson().fromJson(data,
                        new TypeToken<ArrayList<OrderProductDTO>>() {
                        }.getType());
                OrderProductAddAdapter adapter = new OrderProductAddAdapter(list);
                LinearLayoutManager manager = new LinearLayoutManager(this);
                binding.recvOrderFinish.setAdapter(adapter);
                binding.recvOrderFinish.setLayoutManager(manager);

            }
        });

        setContentView(binding.getRoot());
        binding.btnClose.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    protected void onRestart() {
        super.onRestart();
    }
}