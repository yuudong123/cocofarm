package com.cocofarm.andapp.order;

import static com.cocofarm.andapp.common.CommonVal.loginMember;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityOrderBinding;
import com.cocofarm.andapp.product.ProductVO;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class OrderActivity extends AppCompatActivity {

    CommonConn conn;

    ActivityOrderBinding binding;
    ArrayList<CartDTO> list = new ArrayList<>(); //받아온 상품 목록

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());

        Intent intent = getIntent(); //value 받아옴 바로 구매, 장바구니 구별.
        int value = intent.getIntExtra("value", 0);
        if (value == 1) {
            ProductVO productVO = (ProductVO) getIntent().getSerializableExtra("productVO");
            //바로 구매에서 주문 결제로 보내기 위함.
            int number = intent.getIntExtra("number", 0);
            CartDTO cartDTO = new CartDTO();
            cartDTO.setChecked(true);
            cartDTO.setMember_no(loginMember.getMember_no());
            cartDTO.setAmount(number);
            cartDTO.setProduct_name(productVO.getName());
            cartDTO.setProduct_price(productVO.getPrice());
            cartDTO.setProduct_image(productVO.getFilename());
            cartDTO.setProduct_id(productVO.getProduct_id());
            list.add(cartDTO);
        } else {
            for (CartDTO cartDTO : CommonVal.cart) {
                if (cartDTO.isChecked()) {
                    list.add(cartDTO);
                }
            }
        }
        binding.tvOrderAllPrice.setText(CommonVal.comma(getAllPrice(list))); //전체 값이 나옴.
        binding.etOrderAddress.setText("배송지: "+loginMember.getAddress());
        binding.tvOrderPersonname.setText("주문인: "+loginMember.getNickname());

        OrderAdapter adapter = new OrderAdapter(list);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.recvOrderProduct.setAdapter(adapter);
        binding.recvOrderProduct.setLayoutManager(manager);

        setContentView(binding.getRoot());

        binding.btnOrderFinish.setOnClickListener(v -> {
            String order_id = orderFinish(); //결제하기 메소드
            Intent intent1 = new Intent(OrderActivity.this, OrderFinishActivity.class);
            intent1.putExtra("order_id", order_id);
            startActivity(intent1);
            //결제하기로 넘어가면서 cart에 있던것들 삭제
        });

        binding.btnClose.setOnClickListener(v -> finish());
    }


    private int getAllPrice(List<CartDTO> list) {
        int allPrice = 0;
        for (CartDTO cartDTO : list) {
            allPrice += cartDTO.getProduct_price() * cartDTO.getAmount();
        }
        return allPrice;
    }

    private String orderFinish() {
        Date date = new Date();
        String orderId = makeOrderId(date);

        //결제 메소드
        conn = new CommonConn(this, "orderinsert.and");
        OrderVO vo = new OrderVO();
        vo.setMember_no(loginMember.getMember_no());
        vo.setPrice(getAllPrice(list));
        vo.setOrderProductVOList(list);
        vo.setAddress(loginMember.getAddress());

        conn.addParam("vo", new Gson().toJson(vo)); //장바구니 리스트로 amount
        conn.onExcute((isResult, data) -> {
            ResultVO resultvo = new Gson().fromJson(data, ResultVO.class);
            Log.d("성공 번호", "orderFinish: " + resultvo.getResult());
            if (!isResult) {
                Toast.makeText(this, "결제에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        return orderId;
    }

    private String makeOrderId(Date date) {
        String sDate = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
        String member_no = loginMember.getMember_no() + "";
        String random = generateRandomNumber(1000, 9999) + "";
        String result = sDate + member_no + random;
        return result;
    }

    private int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

}