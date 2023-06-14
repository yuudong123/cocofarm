package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_QNA;
import static com.cocofarm.andapp.common.CommonVal.boardselectedImage;
import static com.cocofarm.andapp.common.CommonVal.loginMember;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityQnaWriteBinding;
import com.cocofarm.andapp.image.ImageUtil;
import com.cocofarm.andapp.product.ProductVO;

public class QnAWriteActivity extends AppCompatActivity {

    ActivityQnaWriteBinding binding;
    public static ProductVO qnaselectedproduct = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQnaWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.qnaProductSelect.setOnClickListener(v -> {
            Intent intent = new Intent(QnAWriteActivity.this, QnAProductSelectActivity.class);
            startActivity(intent);
        });
        binding.btnProductChange.setOnClickListener(v -> {
            Intent intent = new Intent(QnAWriteActivity.this, QnAProductSelectActivity.class);
            startActivity(intent);
        });
        binding.btnConfirm.setOnClickListener(v -> {
            if (binding.edtTitle.getText().toString().equals("") || binding.edtContent.getText().toString().equals("")) {
                Toast.makeText(this, "제목과 내용을 확인해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            CommonConn conn = new CommonConn(this, "board/insertboard.and");
            conn.addParam("member_no", loginMember.getMember_no());
            conn.addParam("nickname", loginMember.getNickname());
            conn.addParam("product_id", qnaselectedproduct.getProduct_id());
            conn.addParam("board_category_cd", BOARD_CATEGORY_QNA);
            conn.addParam("title", binding.edtTitle.getText().toString());
            conn.addParam("content", binding.edtContent.getText().toString());
            conn.addParam("mainimage", "");
            conn.onExcute((isResult, data) -> {
                Log.d("글 작성", "onCreate: " + isResult);
                if (isResult) {
                    finish();
                }
            });
        });

        binding.btnCancel.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (qnaselectedproduct != null) {
            ImageUtil.load(binding.ivProductImage, qnaselectedproduct.getFilename());
            binding.tvProductName.setText(qnaselectedproduct.getName());
            binding.tvProductContent.setText(qnaselectedproduct.getContent());
            binding.qnaProductSelect.setVisibility(View.GONE);
            binding.qnaProductSelected.setVisibility(View.VISIBLE);
        } else {
            binding.qnaProductSelected.setVisibility(View.GONE);
            binding.qnaProductSelect.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        qnaselectedproduct = null;
    }
}