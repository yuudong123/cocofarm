package com.cocofarm.andapp.board;

import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.ACTION_VIEW;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;
import static com.cocofarm.andapp.common.CodeTable.MEMBER_TYPE_ADMIN;
import static com.cocofarm.andapp.common.CommonVal.loginMember;
import static com.cocofarm.andapp.common.CommonVal.yyyyMMddHHmmss;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityQnaReadBinding;
import com.cocofarm.andapp.image.ImageUtil;
import com.cocofarm.andapp.product.ProductActivity;
import com.cocofarm.andapp.product.ProductVO;
import com.cocofarm.andapp.report.ReportActivity;
import com.google.gson.Gson;

public class QnAReadActivity extends AppCompatActivity {

    ActivityQnaReadBinding binding;
    ProductVO productVO;
    QnaDTO qnaDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQnaReadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        qnaDTO = (QnaDTO) getIntent().getSerializableExtra("QnaDTO");

        if (loginMember.getMember_type_cd() == MEMBER_TYPE_ADMIN) {
            binding.btnAnswerConfirm.setOnClickListener(v -> writeAnswer(qnaDTO.getBoard_no()));
        }
        loadAnswer(qnaDTO.getBoard_no());
        binding.tvTitle.setText(qnaDTO.getTitle());
        binding.tvQuestionNickname.setText(qnaDTO.getNickname());
        binding.tvContentQ.setText(qnaDTO.getContent());
        binding.tvRegdate.setText(yyyyMMddHHmmss.format(qnaDTO.getRegdate()));

        if (qnaDTO.getProduct_id() != 0) {
            binding.tvProductName.setText(qnaDTO.getProduct_name());
            binding.tvProductContent.setText(qnaDTO.getProduct_content());
            ImageUtil.load(binding.ivProductImage, qnaDTO.getMainimage());
            binding.qnaProduct.setVisibility(VISIBLE);
            binding.qnaProduct.setOnClickListener(v -> {
                Intent intent = new Intent(QnAReadActivity.this, ProductActivity.class);
                CommonConn conn = new CommonConn(this, "selectProductContent.and");
                conn.addParam("product_id", qnaDTO.getProduct_id());
                conn.onExcute((isResult, data) -> {
                    productVO = new Gson().fromJson(data, ProductVO.class);
                    intent.putExtra("productVO", productVO);
                    startActivity(intent);
                });
            });
        }

        PopupMenu menu = new PopupMenu(binding.btnSeemore.getContext(), binding.btnSeemore);
        if (loginMember.getMember_no() != qnaDTO.getMember_no() && loginMember.getMember_type_cd() != MEMBER_TYPE_ADMIN) {
            menu.getMenuInflater().inflate(R.menu.seemore_no_perm, menu.getMenu());
        } else {
            menu.getMenuInflater().inflate(R.menu.qna_seemore, menu.getMenu());
        }
        menu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menuBoardSeemoreDelete) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("게시글 삭제")
                        .setMessage("삭제하면 다시 복구할 수 없습니다. 정말 삭제하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("확인", (dialogInterface, i1) -> {
                            CommonConn conn = new CommonConn(this, "board/deleteboard.and");
                            conn.addParam("board_no", qnaDTO.getBoard_no());
                            conn.onExcute((isResult, data) -> {
                                if (isResult) {
                                    Toast.makeText(this, "삭제되었습니다.", LENGTH_SHORT).show();
                                    this.finish();
                                } else {
                                    Toast.makeText(this, "에러가 발생했습니다.", LENGTH_SHORT).show();
                                }
                            });
                        })
                        .setNegativeButton("취소", (dialogInterface, i1) -> {
                        }).create().show();
            } else if (itemId == R.id.menuBoardSeemoreReport) {
                Intent intent = new Intent(this, ReportActivity.class);
                intent.putExtra("reported_board", qnaDTO.getBoard_no());
                intent.putExtra("reported_member", qnaDTO.getMember_no());
                intent.putExtra("reported_nickname", qnaDTO.getNickname());
                intent.putExtra("title", qnaDTO.getTitle());
                intent.putExtra("content", qnaDTO.getContent());
                startActivity(intent);
            } else if (itemId == R.id.menuBoardSeemoreShare) {
                Intent intentShare = new Intent(ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.putExtra(Intent.EXTRA_TEXT, "http://localhost:9090/board/" + qnaDTO.getBoard_no());
                startActivity(Intent.createChooser(intentShare, "공유하기"));
            } else if (itemId == R.id.menuBoardSeemoreBrowser) {
                Intent intentBrowser = new Intent(ACTION_VIEW);
                intentBrowser.setData(Uri.parse("http://localhost:9090/board/" + qnaDTO.getBoard_no()));
                startActivity(intentBrowser);
            }
            return false;
        });

        binding.btnSeemore.setOnClickListener(v -> menu.show());
    }

    protected void loadAnswer(int board_no) {
        CommonConn conn = new CommonConn(this, "reply/selectanswer.and");
        conn.addParam("board_no", board_no);
        conn.onExcute((isResult, data) -> {
            if (!isResult) {
                return;
            }
            if (data.equals("null")) {
                binding.tvContentA.setText("답변을 준비중입니다.");
                binding.tvAnswerNickname.setText("");
                if (loginMember.getMember_type_cd() == MEMBER_TYPE_ADMIN) {
                    binding.bottomAnswerWriteBar.setVisibility(VISIBLE);
                }
            } else {
                ReplyVO reply = new Gson().fromJson(data, ReplyVO.class);
                binding.tvContentA.setText(reply.getContent());
                binding.tvAnswerNickname.setText(reply.getNickname());
                binding.bottomAnswerWriteBar.setVisibility(GONE);
            }
        });
    }

    protected void writeAnswer(int board_no) {
        CommonConn conn = new CommonConn(this, "reply/insertreply.and");
        conn.addParam("member_no", loginMember.getMember_no());
        conn.addParam("nickname", loginMember.getNickname());
        conn.addParam("board_no", board_no);
        conn.addParam("content", binding.edtAnswer.getText().toString());
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.edtAnswer.getWindowToken(), 0);
                binding.bottomAnswerWriteBar.setVisibility(GONE);
                loadAnswer(board_no);
            } else {
                Toast.makeText(this, "오류가 발생했습니다.", LENGTH_SHORT).show();
            }
        });
    }
}