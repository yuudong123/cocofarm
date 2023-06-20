package com.cocofarm.andapp.order;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

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

    CommonConn conn;
    ActivityCartBinding binding;

    CartAdapter adapter;

    public static TextView allPrice;
    public static Button allDelete;

    public static CheckBox allSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //프로덕트 vo받는 코드
        // ProductVO productVO = (ProductVO) getIntent().getSerializableExtra("productVO");

        //디비에서 리스트 불러오는 메소드
        load();

        allSelect = binding.checkCartAll;
        //전체상품 선택 , 해제 , 금액.
        allSelect .setOnClickListener(v -> {
            boolean isChecked = allSelect.isChecked();

            int total = 0;

            for (int i = 0; i < CommonVal.cart.size(); i++) {
                CommonVal.cart.get(i).setChecked(isChecked);

                int intCartOrderPrice = CommonVal.cart.get(i).getProduct_price();
                total += intCartOrderPrice;
            }

            allPrice.setText("￦" + (isChecked ? total : 0) + "원");

            adapter.notifyDataSetChanged();
        });


        //상품 전체 삭제 후 비어있는 페이지 보여주게 하기..
        binding.btnCartAlldelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("").setMessage("상품 전체를 삭제하시겠습니까?").setCancelable(false)
                    .setPositiveButton("확인", (dialogInterface, i1) -> {
                        //해당 회원이 전체 삭제 눌렀을때 삭제
                        conn = new CommonConn(this, "deletecartlist.and");
                        conn.addParam("member_no", CommonVal.loginMember.getMember_no());
                        conn.onExcute((isResult, data) -> {
                            if(isResult){
                                adapter.notifyDataSetChanged();
                                //notifyDataSetChanged();만했을때 화면이 그대로여서 load();사용
                                //후에 코드 문제 있으면 고치는걸로.
                                load();
                            }
                        });

                    })
                    .setNegativeButton("취소", (dialogInterface, i1) -> {

                    }).create().show();
        });





        //삭제 버튼누르는게 db처리하고 남은것들도 db로 다시 불러오기. 불러오는 과정 여러번 이면..

        binding.btnGoCocomall.setOnClickListener(v -> {
            //finish();
            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        binding.btnOrder.setOnClickListener(v -> {
            boolean isChecked = false;
            for (int i = 0; i < CommonVal.cart.size() ; i++) {
                if(CommonVal.cart.get(i).isChecked()){
                    isChecked = true;
                    break;
                }
            }
            if (binding.checkCartAll.isChecked()||isChecked){
                Intent intent = new Intent(CartActivity.this, OrderActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(this,"구매하실 상품을 선택해주세요.",Toast.LENGTH_SHORT).show();
            }

        });
    }

    protected void load() {
        conn = new CommonConn(this, "selectCartList.and");
        conn.addParam("member_no", CommonVal.loginMember.getMember_no());
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                CommonVal.cart = new Gson().fromJson(data, new TypeToken<ArrayList<CartDTO>>() {
                }.getType());
                adapter = new CartAdapter();
                LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                binding.recvCart.setAdapter(adapter);
                binding.recvCart.setLayoutManager(manager);

                if (CommonVal.cart.size() > 0) {
                    binding.layoutCart.setVisibility(View.VISIBLE);
                    binding.layoutCartEmpty.setVisibility(View.GONE);
                } else {
                    binding.layoutCart.setVisibility(View.GONE);
                    binding.layoutCartEmpty.setVisibility(View.VISIBLE);
                }
            }

        });



    }


    @Override
    protected void onStart() {
        super.onStart();
        allPrice = binding.tvCartAllPrice;
        allDelete = binding.btnCartAlldelete;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}