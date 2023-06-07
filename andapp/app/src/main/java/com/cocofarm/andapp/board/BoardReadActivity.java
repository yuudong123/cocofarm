package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CommonVal.Md;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.common.CodeTable;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityBoardReadBinding;

public class BoardReadActivity extends AppCompatActivity {

    ActivityBoardReadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardReadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        binding.regdate.setText(Md.format(vo.getRegdate()));
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
                bundle.putSerializable("BoardVO", vo);
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

            CommonConn conn = new CommonConn(null, "insertreply.and");
            conn.addParam("board_no", vo.getBoard_no());
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
        });
        binding.btnReplyCancel.setOnClickListener(v -> {
            binding.bottomMenuBar.setVisibility(View.VISIBLE);
            binding.bottomReplyWriteBar.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.edtReplyWrite.getWindowToken(), 0);
            binding.edtReplyWrite.setText("");
        });
    }
}