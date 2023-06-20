package com.cocofarm.andapp.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.common.CodeTable;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityOrderBinding;
import com.cocofarm.andapp.product.ProductActivity;
import com.cocofarm.andapp.product.ProductVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class OrderActivity extends AppCompatActivity {

    CommonConn conn;

    ActivityOrderBinding binding;
    ArrayList<CartDTO> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderBinding.inflate(getLayoutInflater());

        Intent intent = getIntent();
        int value = intent.getIntExtra("value",0);
        if(value==1){
            //바로 구매에서 주문 결제로 보내기 위함.
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
            orderFinish(); //결제하기 메소드


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

    private void orderFinish(){
        Date date = new Date();
        String orderId=makeOrderId(date);
        //결제 메소드
        conn = new CommonConn(this, ".and");
        conn.addParam("order_id", orderId);
        conn.addParam("member_no", CommonVal.loginMember.getMember_no());
        conn.addParam("orderdate", date);
        conn.addParam("price", getAllPrice(list));
        conn.addParam("address", CommonVal.loginMember.getAddress());
        conn.addParam("order_status_cd", CodeTable.ORDER_STATUS_ONREADY);
        conn.onExcute((isResult, data) -> {
            Log.d("성공 번호", "orderFinish: "+orderId);
            if (!isResult){
                Toast.makeText(this, "결제에 실패했습니다.",Toast.LENGTH_SHORT).show();
            }

        });
    }

    private String makeOrderId(Date date){
        //주문번호(문자열) 만드는 메소드
        String sDate= new SimpleDateFormat("yyyyMMddHHmmss").format(date);
        String member_no = CommonVal.loginMember.getMember_no()+"";
        String random=generateRandomNumber(1000,9999)+"";
        String result = sDate+member_no+random;
        return result;
    }
    private int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}