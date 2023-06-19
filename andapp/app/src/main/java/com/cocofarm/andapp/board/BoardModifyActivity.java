package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CommonVal.boardselectedImage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

        if (boardVO.getMainimage() != null || boardVO.getMainimage().equals("")) {
            boardselectedImage = boardVO.getMainimage();
            ImageUtil.load(binding.ivMainImage,boardselectedImage);
            binding.tvFileName.setText(boardselectedImage);
            binding.mainImageSelect.setVisibility(View.GONE);
            binding.mainImageSelected.setVisibility(View.VISIBLE);
        } else {
            binding.mainImageSelected.setVisibility(View.GONE);
            binding.mainImageSelect.setVisibility(View.VISIBLE);
        }

        binding.btnConfirm.setOnClickListener(v -> {
            if (binding.edtTitle.getText().toString().equals("") || binding.edtContent.getText().toString().equals("")) {
                Toast.makeText(this, "제목과 내용을 확인해주세요", Toast.LENGTH_SHORT).show();
            } else {
                updateBoard();
            }
        });

        binding.btnCancel.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (boardselectedImage != null) {
            ImageUtil.load(binding.ivMainImage,boardselectedImage);
            binding.tvFileName.setText(boardselectedImage);
            binding.mainImageSelect.setVisibility(View.GONE);
            binding.mainImageSelected.setVisibility(View.VISIBLE);
        } else {
            binding.mainImageSelected.setVisibility(View.GONE);
            binding.mainImageSelect.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        boardselectedImage = null;
    }

    private void updateBoard() {
        CommonConn conn = new CommonConn(this, "board/updateboard.and");
        conn.addParam("board_no", boardVO.getBoard_no());
        conn.addParam("product_id", 0);
        conn.addParam("title", binding.edtTitle.getText().toString());
        conn.addParam("content", binding.edtContent.getText().toString());
        conn.onExcute((isResult, data) -> {
            Log.d("글 작성", "onCreate: " + isResult);
            if (isResult) {
                if (BoardReadActivity.getInstance() != null) {
                    BoardReadActivity.getInstance().finish();
                }
                this.finish();
                Intent intent = new Intent(BoardModifyActivity.this, BoardReadActivity.class);
                CommonConn newconn = new CommonConn(BoardModifyActivity.this, "board/selectboard.and");
                newconn.addParam("board_no", boardVO.getBoard_no());
                newconn.onExcute((isResult1, data1) -> {
                    BoardVO newvo = new Gson().fromJson(data1, BoardVO.class);
                    intent.putExtra("BoardVO", newvo);
                    startActivity(intent);
                });
            }
        });
    }
}