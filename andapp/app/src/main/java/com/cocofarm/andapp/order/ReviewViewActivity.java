package com.cocofarm.andapp.order;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.board.BoardVO;
import com.cocofarm.andapp.common.ResultVO;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityReviewViewBinding;
import com.cocofarm.andapp.image.ImageUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.internal.Util;

public class ReviewViewActivity extends AppCompatActivity {
    ActivityReviewViewBinding binding;

    OrderProductVO orderProductVO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityReviewViewBinding.inflate(getLayoutInflater());
        orderProductVO = (OrderProductVO) getIntent().getSerializableExtra("orderProductVO");
        CommonConn conn = new CommonConn(this, "/board/selectboard.and");
        conn.addParam("board_no", orderProductVO.getReview_board_no());
        conn.onExcute((isResult, data) -> {
            BoardVO vo = new Gson().fromJson(data, new TypeToken<BoardVO>(){}.getType());
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