package com.cocofarm.andapp.product;

import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_QNA;
import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_REVIEW;
import static com.cocofarm.andapp.common.CommonVal.loginMember;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.board.BoardVO;
import com.cocofarm.andapp.common.CodeTable;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityReviewWriteBinding;
import com.cocofarm.andapp.image.ImageUtil;
import com.cocofarm.andapp.order.OrderProductVO;
import com.google.gson.Gson;

public class ReviewWriteActivity extends AppCompatActivity {

    ActivityReviewWriteBinding binding;
    OrderProductVO orderProductVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        orderProductVO = (OrderProductVO) getIntent().getSerializableExtra("orderProductVO");

        ImageUtil.load(binding.ivProductImage,orderProductVO.getFilename());
        binding.tvProductName.setText(orderProductVO.getName());


        //리뷰작성 페이지에 상품이미지, 상품이름떠야함.

        binding.btnConfirm.setOnClickListener(v->{
            if (binding.edReview.getText().toString().equals("")) {
                Toast.makeText(this, "내용을 확인해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("리뷰 작성")
                    .setMessage("작성을 완료하시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("확인", (dialogInterface, i1) -> writeReview())
                    .setNegativeButton("취소", (dialogInterface, i1) -> {
                    }).create().show();
        });
        binding.btnCancel.setOnClickListener(v -> {
            finish();
        });
    }

    private void writeReview(){
        String review = String.valueOf(binding.edReview.getText());
        CommonConn conn = new CommonConn(this,"/board/insertboard.and");
        conn.addParam("member_no", loginMember.getMember_no());
        conn.addParam("nickname", loginMember.getNickname());
        conn.addParam("product_id", orderProductVO.getProduct_id());
        conn.addParam("board_category_cd", BOARD_CATEGORY_REVIEW);
        conn.addParam("content", review);
        conn.addParam("title", "");
        conn.addParam("mainimage", orderProductVO.getFilename());
        conn.addParam("orderproduct_id", orderProductVO.getOrderproduct_id());
        conn.onExcute((isResult, data) -> {
            Log.d("리뷰작성 성공", "onCreate: " + isResult);
            if (isResult){
                finish();
            }
        });
    }
}