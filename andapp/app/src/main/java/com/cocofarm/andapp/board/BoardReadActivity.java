package com.cocofarm.andapp.board;

import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.ACTION_VIEW;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT;
import static android.widget.Toast.LENGTH_SHORT;
import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_EVENT;
import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_NOTICE;
import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_QNA;
import static com.cocofarm.andapp.common.CodeTable.MEMBER_TYPE_ADMIN;
import static com.cocofarm.andapp.common.CommonVal.loginMember;
import static com.cocofarm.andapp.common.CommonVal.yyyyMMddHHmmss;
import static com.cocofarm.andapp.conn.Service.BASE_URL;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityBoardReadBinding;
import com.cocofarm.andapp.report.ReportActivity;

public class BoardReadActivity extends AppCompatActivity {

    ActivityBoardReadBinding binding;
    private static BoardReadActivity instance;
    private BoardVO boardVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardReadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        instance = this;

        boardVO = (BoardVO) getIntent().getSerializableExtra("BoardVO");
        binding.title.setText(boardVO.getTitle());
        String category = "";
        int code = boardVO.getBoard_category_cd();
        if (code == BOARD_CATEGORY_QNA) {
            category = "QnA";
        } else if (code == BOARD_CATEGORY_NOTICE) {
            category = "공지사항";
        } else if (code == BOARD_CATEGORY_EVENT) {
            category = "이벤트";
        }
        binding.tvCategory.setText(category);
        binding.regdate.setText(yyyyMMddHHmmss.format(boardVO.getRegdate()));
        if (boardVO.getRegdate().getTime() != boardVO.getUpddate().getTime()) {
            binding.tvUpdated.setText(yyyyMMddHHmmss.format(boardVO.getUpddate()) + " 에 수정됨");
        }
        Fragment readFragment = new BoardReadFragment();
        Bundle readBundle = new Bundle();
        readBundle.putSerializable("BoardVO", boardVO);
        readFragment.setArguments(readBundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.containerBoardRead, readFragment).commit();
        binding.btnReply.setOnClickListener(v -> {
            Fragment fragment;
            if (binding.btnReply.getText().toString().equals("댓글 보기")) {
                binding.btnReply.setIcon(getDrawable(R.drawable.icon_close));
                binding.btnReply.setText("댓글 닫기");
                fragment = new BoardReadReplyFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("board_no", boardVO.getBoard_no());
                fragment.setArguments(bundle);
            } else {
                binding.btnReply.setIcon(getDrawable(R.drawable.icon_reply));
                binding.btnReply.setText("댓글 보기");
                fragment = new BoardReadFragment();
                fragment.setArguments(readBundle);
            }
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.containerBoardRead, fragment).commit();
            }
        });
        binding.btnReplyWrite.setOnClickListener(v -> {
            binding.bottomMenuBar.setVisibility(GONE);
            binding.bottomReplyWriteBar.setVisibility(VISIBLE);
            binding.edtReplyWrite.requestFocus();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInput(binding.edtReplyWrite, SHOW_IMPLICIT);
        });

        binding.btnReplyConfirm.setOnClickListener(v -> {
            if (binding.edtReplyWrite.getText().toString().equals("")) {
                Toast.makeText(this, "댓글을 입력해주세요.", LENGTH_SHORT).show();
                return;
            }
            writeReply(boardVO.getBoard_no());
            Fragment fragment = new BoardReadReplyFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("board_no", boardVO.getBoard_no());
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.containerBoardRead, fragment).commit();
        });
        binding.btnReplyCancel.setOnClickListener(v -> {
            binding.bottomMenuBar.setVisibility(VISIBLE);
            binding.bottomReplyWriteBar.setVisibility(GONE);
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.edtReplyWrite.getWindowToken(), 0);
            binding.edtReplyWrite.setText("");
        });
        binding.btnSeemore.setOnClickListener(v -> {
            PopupMenu menu = new PopupMenu(v.getContext(), v);
            if (loginMember.getMember_no() != boardVO.getMember_no() && loginMember.getMember_type_cd() != MEMBER_TYPE_ADMIN) {
                menu.getMenuInflater().inflate(R.menu.seemore_no_perm, menu.getMenu());
            } else {
                menu.getMenuInflater().inflate(R.menu.board_seemore, menu.getMenu());
            }
            menu.setOnMenuItemClickListener(item -> {
                String url = BASE_URL + "board/";

                if (code == BOARD_CATEGORY_NOTICE) {
                    url += "notice/";
                } else if (code == BOARD_CATEGORY_EVENT) {
                    url += "event/";
                } else if (code == BOARD_CATEGORY_QNA) {
                    url += "qna/";
                }
                url += boardVO.getBoard_no();

                int itemId = item.getItemId();
                Log.d("더보기", "onCreate: "+itemId);
                if (itemId == R.id.menuBoardSeemoreModify) {
                    Intent intent = new Intent(this, BoardModifyActivity.class);
                    intent.putExtra("BoardVO", boardVO);
                    startActivity(intent);
                } else if (itemId == R.id.menuBoardSeemoreDelete) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("게시글 삭제").setMessage("삭제하면 다시 복구할 수 없습니다. 정말 삭제하시겠습니까?").setCancelable(false).setPositiveButton("확인", (dialogInterface, i1) -> {
                        CommonConn conn = new CommonConn(this, "board/deleteboard.and");
                        conn.addParam("board_no", boardVO.getBoard_no());
                        conn.onExcute((isResult, data) -> {
                            if (isResult) {
                                Toast.makeText(this, "삭제되었습니다.", LENGTH_SHORT).show();
                                this.finish();
                            }
                        });
                    }).setNegativeButton("취소", (dialogInterface, i1) -> {
                    }).create().show();
                } else if (itemId == R.id.menuBoardSeemoreReport) {
                    Intent intent = new Intent(this, ReportActivity.class);
                    intent.putExtra("reported_board", boardVO.getBoard_no());
                    intent.putExtra("reported_member", boardVO.getMember_no());
                    intent.putExtra("reported_nickname", boardVO.getNickname());
                    intent.putExtra("title", boardVO.getTitle());
                    intent.putExtra("content", boardVO.getContent());
                    startActivity(intent);
                } else if (itemId == R.id.menuBoardSeemoreShare) {
                    Intent intentShare = new Intent(ACTION_SEND);
                    intentShare.setType("text/plain");
                    intentShare.putExtra(Intent.EXTRA_TEXT, url);
                    startActivity(Intent.createChooser(intentShare, "공유하기"));
                } else if (itemId == R.id.menuBoardSeemoreBrowser) {
                    Intent intentBrowser = new Intent(ACTION_VIEW);
                    intentBrowser.setData(Uri.parse(url));
                    startActivity(intentBrowser);
                }
                return false;
            });
            menu.show();
        });
    }

    protected void writeReply(int board_no) {
        CommonConn conn = new CommonConn(this, "reply/insertreply.and");
        conn.addParam("board_no", board_no);
        conn.addParam("member_no", CommonVal.loginMember.getMember_no());
        conn.addParam("nickname", CommonVal.loginMember.getNickname());
        conn.addParam("content", binding.edtReplyWrite.getText().toString());
        conn.onExcute((isResult, data) -> {
            Log.d("Reply", "onCreate: " + isResult);
            if (isResult) {
                binding.edtReplyWrite.setText("");
                binding.bottomMenuBar.setVisibility(VISIBLE);
                binding.bottomReplyWriteBar.setVisibility(GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.edtReplyWrite.getWindowToken(), 0);
            } else {
                Toast.makeText(instance, "오류가 발생했습니다.", LENGTH_SHORT).show();
            }
        });
    }

    public static BoardReadActivity getInstance() {
        return instance;
    }
}