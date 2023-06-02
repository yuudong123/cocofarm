package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CommonVal.Md;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.databinding.ActivityBoardReadBinding;

import java.text.SimpleDateFormat;

public class BoardReadActivity extends AppCompatActivity {

    ActivityBoardReadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardReadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BoardVO vo = (BoardVO) getIntent().getSerializableExtra("BoardVO");
        binding.title.setText(vo.getTitle());
        String category="";
        if(vo.getBoard_category_cd()==201) {category = "QnA";}
        else if (vo.getBoard_category_cd()==202) {category="공지사항";}
        else if (vo.getBoard_category_cd()==204) {category="이벤트";}
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
            if (fragment!=null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.containerBoardRead, fragment).commit();
            }
        });
        binding.btnReplyWrite.setOnClickListener(v->{

        });
    }
}