package com.cocofarm.andapp.board;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;
import static com.cocofarm.andapp.common.CommonVal.boardselectedImage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityBoardModifyBinding;
import com.cocofarm.andapp.image.ImageUtil;
import com.google.gson.Gson;

public class BoardModifyActivity extends AppCompatActivity {

    ActivityBoardModifyBinding binding;
    private BoardVO boardVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardModifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        boardVO = (BoardVO) getIntent().getSerializableExtra("BoardVO");

        binding.edtTitle.setText(boardVO.getTitle());
        binding.edtContent.setText(boardVO.getContent());

        if (!"".equals(boardVO.getMainimage())) {
            boardselectedImage = boardVO.getMainimage();
            ImageUtil.load(binding.ivMainImage, boardselectedImage);
            binding.tvFileName.setText(boardselectedImage);
            binding.mainImageSelected.setVisibility(VISIBLE);
        } else {
            binding.mainImageSelect.setVisibility(VISIBLE);
        }

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
            updateBoard();
        });

        binding.btnCancel.setOnClickListener(v -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!"".equals(boardselectedImage)) {
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

    private void updateBoard() {
        CommonConn conn = new CommonConn(this, "board/updateboard.and");
        conn.addParam("board_no", boardVO.getBoard_no());
        conn.addParam("product_id", 0);
        conn.addParam("title", binding.edtTitle.getText().toString());
        conn.addParam("mainimage", boardselectedImage);
        conn.addParam("content", binding.edtContent.getText().toString());
        conn.onExcute((isResult, data) -> {
            if (!isResult) {
                Toast.makeText(this, "오류가 발생했습니다.", LENGTH_SHORT).show();
                return;
            }
            if (BoardReadActivity.getInstance() != null) {
                BoardReadActivity.getInstance().finish();
            }
            this.finish();
            Intent intent = new Intent(BoardModifyActivity.this, BoardReadActivity.class);
            CommonConn newconn = new CommonConn(BoardModifyActivity.this, "board/selectboard.and");
            newconn.addParam("board_no", boardVO.getBoard_no());
            newconn.onExcute((isResult1, data1) -> {
                if (isResult1) {
                    BoardVO newvo = new Gson().fromJson(data1, BoardVO.class);
                    intent.putExtra("BoardVO", newvo);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "오류가 발생했습니다.", LENGTH_SHORT).show();
                }
            });
        });
    }
}