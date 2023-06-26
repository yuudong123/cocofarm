package com.cocofarm.andapp.product;

import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_QNA;
import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_REVIEW;
import static com.cocofarm.andapp.common.CommonVal.loginMember;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.board.BoardVO;
import com.cocofarm.andapp.common.CodeTable;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityReviewWriteBinding;
import com.cocofarm.andapp.order.OrderProductVO;
import com.google.gson.Gson;

public class ReviewWriteActivity extends AppCompatActivity {

    ActivityReviewWriteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        OrderProductVO orderProductVO = (OrderProductVO) getIntent().getSerializableExtra("orderProductVO");
        Log.d("있는지", "onCreate: "+orderProductVO);

        binding.btnConfirm.setOnClickListener(v->{
            String review = String.valueOf(binding.edReview.getText());
            CommonConn conn = new CommonConn(this,"/board/insertboard.and");
            conn.addParam("member_no", loginMember.getMember_no());
            conn.addParam("nickname", loginMember.getNickname());
            conn.addParam("product_id", orderProductVO.getProduct_id());
            conn.addParam("board_category_cd", BOARD_CATEGORY_REVIEW);
            conn.addParam("content", review);
            conn.addParam("title", "");
            conn.addParam("mainimage", orderProductVO.getFilename());
            conn.onExcute((isResult, data) -> {
            });
        });


    }
}