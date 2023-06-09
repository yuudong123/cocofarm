package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_QNA;
import static com.cocofarm.andapp.common.CommonVal.loginMember;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityQnaWriteBinding;

public class QnAWriteActivity extends AppCompatActivity {

    ActivityQnaWriteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQnaWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnProductChange.setOnClickListener(v -> {

        });
        binding.btnConfirm.setOnClickListener(v -> {
            if (binding.edtTitle.getText().toString().equals("") || binding.edtContent.getText().toString().equals("")) {
                Toast.makeText(this, "제목과 내용을 확인해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            CommonConn conn = new CommonConn(this, "insertboard.and");
            conn.addParam("member_no", loginMember.getMember_no());
            conn.addParam("nickname", loginMember.getNickname());
            conn.addParam("product_no", binding.tvProductNo.getText().toString());
            conn.addParam("board_category_cd", BOARD_CATEGORY_QNA);
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