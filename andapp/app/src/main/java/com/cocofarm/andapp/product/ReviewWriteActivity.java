package com.cocofarm.andapp.product;

import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_REVIEW;
import static com.cocofarm.andapp.common.CommonVal.loginMember;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityReviewWriteBinding;
import com.cocofarm.andapp.image.ImageUtil;
import com.cocofarm.andapp.order.OrderProductVO;

public class ReviewWriteActivity extends AppCompatActivity {

    ActivityReviewWriteBinding binding;
    OrderProductVO orderProductVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        orderProductVO = (OrderProductVO) getIntent().getSerializableExtra("orderProductVO");

        ImageUtil.load(binding.ivProductImage, orderProductVO.getFilename());
        binding.tvProductName.setText(orderProductVO.getName());

        binding.btnConfirm.setOnClickListener(v -> {
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
        binding.btnCancel.setOnClickListener(v -> finish());
    }

    private void writeReview() {
        String review = binding.edReview.getText().toString();
        CommonConn conn = new CommonConn(this, "/board/insertboard.and");
        conn.addParam("member_no", loginMember.getMember_no());
        conn.addParam("nickname", loginMember.getNickname());
        conn.addParam("product_id", orderProductVO.getProduct_id());
        conn.addParam("board_category_cd", BOARD_CATEGORY_REVIEW);
        conn.addParam("content", review);
        conn.addParam("title", "");
        conn.addParam("mainimage", orderProductVO.getFilename());
        conn.addParam("orderproduct_id", orderProductVO.getOrderproduct_id());
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                finish();
            } else {
                Toast.makeText(this, "오류가 발생했습니다..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}