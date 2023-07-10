package com.cocofarm.andapp.board;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;
import static com.cocofarm.andapp.common.CommonVal.boardselectedImage;
import static com.cocofarm.andapp.common.CommonVal.loginMember;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityBoardWriteBinding;
import com.cocofarm.andapp.image.ImageUtil;

public class BoardWriteActivity extends AppCompatActivity {

    ActivityBoardWriteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.mainImageSelect.setOnClickListener(v -> {
            Intent intent = new Intent(this, BoardImageSelectActivity.class);
            startActivity(intent);
        });
        binding.btnImageChange.setOnClickListener(v -> {
            Intent intent = new Intent(this, BoardImageSelectActivity.class);
            startActivity(intent);
        });

        binding.btnConfirm.setOnClickListener(v -> {
            if (binding.edtTitle.getText().toString().equals("") || binding.edtContent.getText().toString().equals("")) {
                Toast.makeText(this, "제목과 내용을 확인해주세요", LENGTH_SHORT).show();
                return;
            }
            writeBoard();
        });
        binding.btnCancel.setOnClickListener(v -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (boardselectedImage != "") {
            ImageUtil.load(binding.ivMainImage, boardselectedImage);
            binding.tvFileName.setText(boardselectedImage);
            binding.mainImageSelect.setVisibility(GONE);
            binding.mainImageSelected.setVisibility(VISIBLE);
        } else {
            binding.mainImageSelected.setVisibility(GONE);
            binding.mainImageSelect.setVisibility(VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        boardselectedImage = "";
    }

    private void writeBoard() {
        CommonConn conn = new CommonConn(this, "board/insertboard.and");
        conn.addParam("member_no", loginMember.getMember_no());
        conn.addParam("nickname", loginMember.getNickname());
        conn.addParam("board_category_cd", getIntent().getIntExtra("category", 0));
        conn.addParam("title", binding.edtTitle.getText().toString());
        conn.addParam("content", binding.edtContent.getText().toString());
        conn.addParam("mainimage", boardselectedImage);
        conn.onExcute((isResult, data) -> {
            if (!isResult) {
                Toast.makeText(this, "오류가 발생했습니다.", LENGTH_SHORT).show();
                return;
            }
            finish();
        });
    }
}