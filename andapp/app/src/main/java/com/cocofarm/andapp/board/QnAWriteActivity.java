package com.cocofarm.andapp.board;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;
import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_QNA;
import static com.cocofarm.andapp.common.CommonVal.loginMember;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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

        if (getIntent().getSerializableExtra("productVO") != null) {
            qnaselectedproduct = (ProductVO) getIntent().getSerializableExtra("productVO");
        }

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
                Toast.makeText(this, "제목과 내용을 확인해주세요", LENGTH_SHORT).show();
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("게시글 작성")
                    .setMessage("QnA는 이후 수정이 불가능합니다. 작성을 완료하시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("확인", (dialogInterface, i1) -> writeQna())
                    .setNegativeButton("취소", (dialogInterface, i1) -> {
                    }).create().show();
        });

        binding.btnCancel.setOnClickListener(v -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (qnaselectedproduct != null) {
            if (qnaselectedproduct.getFilename() == null) {
                ImageUtil.load(binding.ivProductImage, qnaselectedproduct.getImg().get(0).getFilename());
            } else {
                ImageUtil.load(binding.ivProductImage, qnaselectedproduct.getFilename());
            }
            binding.tvProductName.setText(qnaselectedproduct.getName());
            binding.tvProductContent.setText(qnaselectedproduct.getContent());
            binding.qnaProductSelect.setVisibility(GONE);
            binding.qnaProductSelected.setVisibility(VISIBLE);
        } else {
            binding.qnaProductSelected.setVisibility(GONE);
            binding.qnaProductSelect.setVisibility(VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        qnaselectedproduct = null;
    }

    private void writeQna() {
        CommonConn conn = new CommonConn(this, "board/insertboard.and");
        conn.addParam("member_no", loginMember.getMember_no());
        conn.addParam("nickname", loginMember.getNickname());
        conn.addParam("product_id", qnaselectedproduct.getProduct_id());
        conn.addParam("board_category_cd", BOARD_CATEGORY_QNA);
        conn.addParam("title", binding.edtTitle.getText().toString());
        conn.addParam("content", binding.edtContent.getText().toString());
        conn.addParam("mainimage", qnaselectedproduct.getFilename());
        conn.onExcute((isResult, data) -> {
            if (!isResult) {
                Toast.makeText(this, "오류가 발생했습니다.", LENGTH_SHORT).show();
                return;
            }
            finish();
        });
    }
}