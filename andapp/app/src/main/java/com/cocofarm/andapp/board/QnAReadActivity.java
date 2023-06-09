package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CodeTable.MEMBER_TYPE_ADMIN;
import static com.cocofarm.andapp.common.CommonVal.loginMember;
import static com.cocofarm.andapp.common.CommonVal.yyyyMMddHHmmss;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityQnaReadBinding;
import com.google.gson.Gson;

public class QnAReadActivity extends AppCompatActivity {

    ActivityQnaReadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQnaReadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        QnaDTO dto = (QnaDTO) getIntent().getSerializableExtra("QnaDTO");

        if (loginMember.getMember_type_cd() == MEMBER_TYPE_ADMIN) {
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
            binding.tvProductId.setText(dto.getProduct_id() + "");
            binding.tvProductName.setText(dto.getProduct_name());
            binding.tvProductContent.setText(dto.getProduct_content());
            binding.qnaProduct.setVisibility(View.VISIBLE);
        }

        PopupMenu menu = new PopupMenu(binding.btnSeemore.getContext(), binding.btnSeemore);
        if (loginMember.getMember_no() != dto.getMember_no() && loginMember.getMember_type_cd() != MEMBER_TYPE_ADMIN) {
            menu.getMenuInflater().inflate(R.menu.seemore_no_perm, menu.getMenu());
        } else {
            menu.getMenuInflater().inflate(R.menu.qna_seemore,menu.getMenu());
        }
        menu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuQnASeemoreDelete:
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("게시글 삭제").setMessage("삭제하면 다시 복구할 수 없습니다. 정말 삭제하시겠습니까?").setCancelable(false)
                            .setPositiveButton("확인", (dialogInterface, i1) -> {
                                CommonConn conn = new CommonConn(this, "deleteboard.and");
                                conn.addParam("board_no", dto.getBoard_no());
                                conn.onExcute((isResult, data) -> {
                                    if (isResult) {
                                        Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                        this.finish();
                                    }
                                });
                            })
                            .setNegativeButton("취소", (dialogInterface, i1) -> {
                            }).create().show();
                    break;
                case R.id.menuQnASeemoreShare:
                    Intent intentShare = new Intent(Intent.ACTION_SEND);
                    intentShare.setType("text/plain");
                    intentShare.putExtra(Intent.EXTRA_TEXT, "http://localhost:9090/board/" + dto.getBoard_no());
                    startActivity(Intent.createChooser(intentShare, "공유하기"));
                    break;
                case R.id.menuQnASeemoreBrowser:
                    Intent intentBrowser = new Intent(Intent.ACTION_VIEW);
                    intentBrowser.setData(Uri.parse("http://localhost:9090/board/" + dto.getBoard_no()));
                    startActivity(intentBrowser);
                    break;
                default:
                    break;
            }
            return false;
        });
        binding.btnSeemore.setOnClickListener(v -> {
            menu.show();
        });
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
                if (loginMember.getMember_type_cd() == MEMBER_TYPE_ADMIN) {
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
        conn.addParam("member_no", loginMember.getMember_no());
        conn.addParam("nickname", loginMember.getNickname());
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