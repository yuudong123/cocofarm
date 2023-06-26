package com.cocofarm.andapp.order;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cocofarm.andapp.common.CodeTable;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityOrderProductBinding;
import com.cocofarm.andapp.product.PlantAdapter;
import com.cocofarm.andapp.product.ProductVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class OrderProductActivity extends AppCompatActivity {

    CommonConn conn;

    ActivityOrderProductBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      binding=ActivityOrderProductBinding.inflate(getLayoutInflater());


        Intent intent1 = getIntent();

        String value = intent1.getStringExtra("order_id");

        conn = new CommonConn(this, "orderproductlist.and");
        conn.addParam("member_no", CommonVal.loginMember.getMember_no());

        conn.onExcute((isResult, data) -> {
            ArrayList<OrderProductVO> list = new Gson().fromJson(data,
                    new TypeToken<ArrayList<OrderProductVO>>(){}.getType());
            Log.d("데이터emp", "onCreateView: "+list.size());
            OrderProductAdapter adapter = new OrderProductAdapter(list, this);
            LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            binding.recvOrderFinish.setAdapter(adapter);
            binding.recvOrderFinish.setLayoutManager(manager);

            setContentView(binding.getRoot());
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent1 = getIntent();

        String value = intent1.getStringExtra("order_id");

        conn = new CommonConn(this, "orderproductlist.and");
        conn.addParam("member_no", CommonVal.loginMember.getMember_no());
        conn.onExcute((isResult, data) -> {
            ArrayList<OrderProductVO> list = new Gson().fromJson(data,
                    new TypeToken<ArrayList<OrderProductVO>>() {
                    }.getType());
            Log.d("데이터emp", "onCreateView: " + list.size());
            OrderProductAdapter adapter = new OrderProductAdapter(list, this);
            LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            binding.recvOrderFinish.setAdapter(adapter);
            binding.recvOrderFinish.setLayoutManager(manager);

            setContentView(binding.getRoot());
        });
    }
}