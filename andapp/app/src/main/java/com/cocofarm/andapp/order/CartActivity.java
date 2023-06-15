package com.cocofarm.andapp.order;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityCartBinding;
import com.cocofarm.andapp.product.ProductActivity;
import com.cocofarm.andapp.product.ProductVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    ActivityCartBinding binding;


    public static TextView allPrice;
    public static Button allDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //프로덕트 vo받는 코드
        // ProductVO productVO = (ProductVO) getIntent().getSerializableExtra("productVO");

        //디비에서 리스트 불러오는 코드 필요.
        CommonConn conn = new CommonConn(this, "selectCartList.and");
        conn.addParam("member_no", CommonVal.loginMember.getMember_no());
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                ArrayList<CartDTO> list = new Gson().fromJson(data, new TypeToken<ArrayList<CartDTO>>() {
                }.getType());
                CartAdapter adapter = new CartAdapter(list);
                LinearLayoutManager manager = new LinearLayoutManager(this);
                binding.recvCart.setAdapter(adapter);
                binding.recvCart.setLayoutManager(manager);

                load(list);
                //상품 전체 삭제 후 비어있는 페이지 보여주게 하기..
                binding.btnCartAlldelete.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("").setMessage("상품 전체를 삭제하시겠습니까?").setCancelable(false)
                            .setPositiveButton("확인", (dialogInterface, i1) -> {

                            })
                            .setNegativeButton("취소", (dialogInterface, i1) -> {

                            }).create().show();
                });
            }

        });


        //삭제 버튼누르는게 db처리하고 남은것들도 db로 다시 불러오기. 불러오는 과정 여러번 이면..

        binding.btnGoCocomall.setOnClickListener(v -> {
            //finish();
            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        binding.btnOrder.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, OrderActivity.class);
            startActivity(intent);
        });
    }

    protected void load(ArrayList<CartDTO> list) {
        if (list.size() > 0) {
            binding.layoutCart.setVisibility(View.VISIBLE);
            binding.layoutCartEmpty.setVisibility(View.GONE);
        } else {
            binding.layoutCart.setVisibility(View.GONE);
            binding.layoutCartEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        allPrice = binding.tvCartAllPrice;
        allDelete = binding.btnCartAlldelete;
    }
}