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
import android.widget.CompoundButton;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    CommonConn conn;
    ActivityCartBinding binding;

    public CartAdapter adapter;

    public TextView allPrice;
    public Button allDelete;

    public CheckBox allSelect;


    CompoundButton.OnCheckedChangeListener listenerAll = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            for (int i = 0; i < CommonVal.cart.size(); i++) {
                CommonVal.cart.get(i).setChecked(isChecked);
            }
            totalSum();
            adapter.notifyDataSetChanged();




        }
    };

    public void isAllCheckChange(){
        boolean allCheck = false;
        for (int i = 0; i < CommonVal.cart.size(); i++) {
            allCheck = true;
            if (!CommonVal.cart.get(i).isChecked()) {
                allCheck = false;
                break;
            }
        }

            allSelect.setOnCheckedChangeListener(null);
            allSelect.setChecked(allCheck);
            allSelect.setOnCheckedChangeListener(listenerAll);
            totalSum();


    }

    public void totalSum(){
        int total = 0 ;
        for (int i = 0; i < CommonVal.cart.size(); i++) {
            if (CommonVal.cart.get(i).isChecked()) {
                total += (CommonVal.cart.get(i).getProduct_price() * CommonVal.cart.get(i).getAmount());

            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String totalPrice = decimalFormat.format(total);
        allPrice.setText("￦" + totalPrice + "원");

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        load();

        allSelect = binding.checkCartAll;
        //전체상품 선택 , 해제 , 금액.

        allSelect.setOnCheckedChangeListener(listenerAll);
        allSelect.setOnClickListener(v -> {
//            boolean isChecked = allSelect.isChecked();
//
//            total = 0;
//            for (int i = 0; i < CommonVal.cart.size(); i++) {
//                CommonVal.cart.get(i).setChecked(isChecked);
//
//                //int intCartOrderPrice = CommonVal.cart.get(i).getProduct_price();
//                total +=  CommonVal.cart.get(i).getProduct_price();;
//            }
//            decimalFormat = new DecimalFormat("###,###");
//            String totalPrice = decimalFormat.format(total);
//
//            allPrice.setText("￦" + (isChecked ? totalPrice : 0) + "원");
//
//            adapter.notifyDataSetChanged();
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

        binding.btnGoCocomall.setOnClickListener(v -> {
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
                adapter = new CartAdapter(this);
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
    protected void onRestart() {
        super.onRestart();
        allPrice.setText("￦ " + 0 + "원");
        load();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}