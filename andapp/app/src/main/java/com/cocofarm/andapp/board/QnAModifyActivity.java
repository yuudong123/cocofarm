package com.cocofarm.andapp.board;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityQnaModifyBinding;

public class QnAModifyActivity extends AppCompatActivity {

    ActivityQnaModifyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQnaModifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        QnaDTO dto = (QnaDTO) getIntent().getSerializableExtra("QnaDTO");

        binding.edtTitle.setText(dto.getTitle());
        binding.edtContent.setText(dto.getContent());
        binding.tvProductId.setText(dto.getProduct_id());
        binding.tvProductName.setText(dto.getProduct_name());
        binding.tvProductContent.setText(dto.getProduct_content());

        binding.btnProductChange.setOnClickListener(v -> {

        });
        binding.btnConfirm.setOnClickListener(v -> {
            if (binding.edtTitle.getText().toString().equals("") || binding.edtContent.getText().toString().equals("")) {
                Toast.makeText(this, "제목과 내용을 확인해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            CommonConn conn = new CommonConn(this, "board/updateboard.and");
            conn.addParam("board_no", dto.getBoard_no());
            conn.addParam("product_id", binding.tvProductId.getText().toString());
            conn.addParam("title", binding.edtTitle.getText().toString());
            conn.addParam("content", binding.edtContent.getText().toString());
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
}