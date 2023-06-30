package com.cocofarm.andapp.order;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cocofarm.andapp.board.BoardVO;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityReviewViewBinding;
import com.cocofarm.andapp.image.ImageUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ReviewViewActivity extends AppCompatActivity {
    ActivityReviewViewBinding binding;

    OrderProductVO orderProductVO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityReviewViewBinding.inflate(getLayoutInflater());
        orderProductVO = (OrderProductVO) getIntent().getSerializableExtra("orderProductVO");
        CommonConn conn = new CommonConn(this, "orderproductreviewone.and");
        conn.addParam("orderproduct_id", orderProductVO.getOrderproduct_id());
        conn.onExcute((isResult, data) -> {
            BoardVO vo = new Gson().fromJson(data, BoardVO.class);
            if (isResult){
                ImageUtil.load(binding.ivProductImage,orderProductVO.getFilename());
                binding.tvProductName.setText(orderProductVO.getName());
                binding.tvReviewview.setText(vo.getContent());
            }
        });
        binding.btnConfirm.setOnClickListener(v->{
            finish();
        });

        setContentView(binding.getRoot());
    }
}