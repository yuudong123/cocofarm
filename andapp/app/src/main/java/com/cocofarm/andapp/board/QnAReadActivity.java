package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CommonVal.loginMemberAdmin;
import static com.cocofarm.andapp.common.CommonVal.yyyyMMddHHmmss;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.common.CodeTable;
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
        BoardVO vo = (BoardVO) getIntent().getSerializableExtra("BoardVO");

        if (loginMemberAdmin.getMember_type_cd() == CodeTable.MEMBER_TYPE_ADMIN) {
            binding.btnAnswerConfirm.setOnClickListener(v -> {
                writeAnswer(vo.getBoard_no());
            });
        }

        loadAnswer(vo.getBoard_no());
        binding.tvTitle.setText(vo.getTitle());
        binding.tvQuestionNickname.setText(vo.getNickname());
        binding.tvContentQ.setText(vo.getContent());
        binding.tvRegdate.setText(yyyyMMddHHmmss.format(vo.getRegdate()));
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
                if (loginMemberAdmin.getMember_type_cd() == CodeTable.MEMBER_TYPE_ADMIN) {
                    binding.bottomAnswerWriteBar.setVisibility(View.VISIBLE);
                }
            } else {
                ReplyVO reply = new Gson().fromJson(data, new TypeToken<ReplyVO>() {
                }.getType());
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