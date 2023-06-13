package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CodeTable.MEMBER_TYPE_ADMIN;
import static com.cocofarm.andapp.common.CommonVal.loginMember;
import static com.cocofarm.andapp.common.CommonVal.yyyyMMddHHmmss;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.common.CodeTable;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityBoardReadBinding;

public class BoardReadActivity extends AppCompatActivity {

    ActivityBoardReadBinding binding;
    private static BoardReadActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardReadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        instance = this;

        BoardVO vo = (BoardVO) getIntent().getSerializableExtra("BoardVO");
        binding.title.setText(vo.getTitle());
        String category = "";
        if (vo.getBoard_category_cd() == CodeTable.BOARD_CATEGORY_QNA) {
            category = "QnA";
        } else if (vo.getBoard_category_cd() == CodeTable.BOARD_CATEGORY_NOTICE) {
            category = "공지사항";
        } else if (vo.getBoard_category_cd() == CodeTable.BOARD_CATEGORY_EVENT) {
            category = "이벤트";
        }
        binding.tvCategory.setText(category);
        binding.regdate.setText(yyyyMMddHHmmss.format(vo.getRegdate()));
        Fragment readFragment = new BoardReadFragment();
        Bundle readBundle = new Bundle();
        readBundle.putSerializable("BoardVO", vo);
        readFragment.setArguments(readBundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.containerBoardRead, readFragment).commit();
        binding.btnReply.setOnClickListener(v -> {
            Fragment fragment = null;
            if (binding.btnReply.getText().toString().equals("댓글 보기")) {
                binding.btnReply.setIcon(getDrawable(R.drawable.icon_close));
                binding.btnReply.setText("댓글 닫기");
                fragment = new BoardReadReplyFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("board_no", vo.getBoard_no());
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
            binding.bottomMenuBar.setVisibility(View.GONE);
            binding.bottomReplyWriteBar.setVisibility(View.VISIBLE);
            binding.edtReplyWrite.requestFocus();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInput(binding.edtReplyWrite, InputMethodManager.SHOW_IMPLICIT);
        });

        binding.btnReplyConfirm.setOnClickListener(v -> {
            if (binding.edtReplyWrite.getText().toString().equals("")) {
                Toast.makeText(this, "댓글을 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            writeReply(vo.getBoard_no());
            Fragment fragment = new BoardReadReplyFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("board_no", vo.getBoard_no());
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.containerBoardRead, fragment).commit();
        });
        binding.btnReplyCancel.setOnClickListener(v -> {
            binding.bottomMenuBar.setVisibility(View.VISIBLE);
            binding.bottomReplyWriteBar.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.edtReplyWrite.getWindowToken(), 0);
            binding.edtReplyWrite.setText("");
        });
        binding.btnSeemore.setOnClickListener(v -> {
            PopupMenu menu = new PopupMenu(v.getContext(), v);
            if (loginMember.getMember_no() != vo.getMember_no() && loginMember.getMember_type_cd() != MEMBER_TYPE_ADMIN) {
                menu.getMenuInflater().inflate(R.menu.seemore_no_perm, menu.getMenu());
            } else {
                menu.getMenuInflater().inflate(R.menu.board_seemore,menu.getMenu());
            }
            menu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menuBoardSeemoreModify:
                        Intent intent = new Intent(this, BoardModifyActivity.class);
                        intent.putExtra("BoardVO", vo);
                        startActivity(intent);
                        break;
                    case R.id.menuBoardSeemoreDelete:
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("게시글 삭제").setMessage("삭제하면 다시 복구할 수 없습니다. 정말 삭제하시겠습니까?").setCancelable(false)
                                .setPositiveButton("확인", (dialogInterface, i1) -> {
                                    CommonConn conn = new CommonConn(this, "board/deleteboard.and");
                                    conn.addParam("board_no", vo.getBoard_no());
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
                    case R.id.menuBoardSeemoreShare:
                        Intent intentShare = new Intent(Intent.ACTION_SEND);
                        intentShare.setType("text/plain");
                        intentShare.putExtra(Intent.EXTRA_TEXT, "http://localhost:9090/board/" + vo.getBoard_no());
                        startActivity(Intent.createChooser(intentShare, "공유하기"));
                        break;
                    case R.id.menuBoardSeemoreBrowser:
                        Intent intentBrowser = new Intent(Intent.ACTION_VIEW);
                        intentBrowser.setData(Uri.parse("http://localhost:9090/board/" + vo.getBoard_no()));
                        startActivity(intentBrowser);
                        break;
                    default:
                        break;
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
                binding.bottomMenuBar.setVisibility(View.VISIBLE);
                binding.bottomReplyWriteBar.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.edtReplyWrite.getWindowToken(), 0);
            }
        });
    }

    public static BoardReadActivity getInstance() {
        return instance;
    }
}