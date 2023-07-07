package com.cocofarm.andapp.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityCartBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.util.ArrayList;

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

    public void isAllCheckChange() {
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

    public void totalSum() {
        int total = 0;
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

        //상품 전체 삭제 후 비어있는 페이지 보여주게 하기..
        binding.btnCartAlldelete.setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle("").setMessage("상품 전체를 삭제하시겠습니까?").setCancelable(false)
                .setPositiveButton("확인", (dialogInterface, i1) -> {
                    conn = new CommonConn(this, "deletecartlist.and");
                    conn.addParam("member_no", CommonVal.loginMember.getMember_no());
                    conn.onExcute((isResult, data) -> {
                        if (isResult) {
                            adapter.notifyDataSetChanged();
                            load();
                        }
                    });
                })
                .setNegativeButton("취소", (dialogInterface, i1) -> {
                }).create().show());

        binding.btnGoCocomall.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        binding.btnOrder.setOnClickListener(v -> {
            boolean isChecked = false;
            for (int i = 0; i < CommonVal.cart.size(); i++) {
                if (CommonVal.cart.get(i).isChecked()) {
                    isChecked = true;
                    break;
                }
            }
            if (binding.checkCartAll.isChecked() || isChecked) {
                Intent intent = new Intent(CartActivity.this, OrderActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "구매하실 상품을 선택해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnClose.setOnClickListener(v -> {
            finish();
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
            } else {
                Toast.makeText(this, "장바구니를 불러오지 못했습니다..", Toast.LENGTH_SHORT).show();
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
        binding = null;
    }
}