package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CodeTable.MEMBER_TYPE_ADMIN;
import static com.cocofarm.andapp.common.CommonVal.loginMemberAdmin;
import static com.cocofarm.andapp.common.CommonVal.yyyyMMddHHmmss;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityQnaReadBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class QnAReadActivity extends AppCompatActivity {

    ActivityQnaReadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQnaReadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        QnaDTO dto = (QnaDTO) getIntent().getSerializableExtra("QnaDTO");

        if (loginMemberAdmin.getMember_type_cd() == MEMBER_TYPE_ADMIN) {
            binding.btnAnswerConfirm.setOnClickListener(v -> {
                writeAnswer(dto.getBoard_no());
            });
        }

        loadAnswer(dto.getBoard_no());
        binding.tvTitle.setText(dto.getTitle());
        binding.tvQuestionNickname.setText(dto.getNickname());
        binding.tvContentQ.setText(dto.getContent());
        binding.tvRegdate.setText(yyyyMMddHHmmss.format(dto.getRegdate()));
        if (dto.getProduct_id() != 0) {
            binding.tvProductId.setText(dto.getProduct_id()+"");
            binding.tvProductName.setText(dto.getProduct_name());
            binding.tvProductContent.setText(dto.getProduct_content());
            binding.qnaProduct.setVisibility(View.VISIBLE);
        }
    }

    protected void loadAnswer(int board_no) {
        CommonConn conn = new CommonConn(this, "selectanswer.and");
        conn.addParam("board_no", board_no);
        conn.onExcute((isResult, data) -> {
            if (!isResult) {
                return;
            }
            if (data.equals("null")) {
                binding.tvContentA.setText("답변을 준비중입니다.");
                binding.tvAnswerNickname.setText("");
                if (loginMemberAdmin.getMember_type_cd() == MEMBER_TYPE_ADMIN) {
                    binding.bottomAnswerWriteBar.setVisibility(View.VISIBLE);
                }
            } else {
                ReplyVO reply = new Gson().fromJson(data, ReplyVO.class);
                binding.tvContentA.setText(reply.getContent());
                binding.tvAnswerNickname.setText(reply.getNickname());
                binding.bottomAnswerWriteBar.setVisibility(View.GONE);
            }
        });
    }

    protected void writeAnswer(int board_no) {
        CommonConn conn = new CommonConn(this, "insertreply.and");
        conn.addParam("member_no", loginMemberAdmin.getMember_no());
        conn.addParam("nickname", loginMemberAdmin.getNickname());
        conn.addParam("board_no", board_no);
        conn.addParam("content", binding.edtAnswer.getText().toString());
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.edtAnswer.getWindowToken(), 0);
                binding.bottomAnswerWriteBar.setVisibility(View.GONE);
                loadAnswer(board_no);
            }
        });
    }
}