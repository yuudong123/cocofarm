package com.cocofarm.andapp.board;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.databinding.ActivityBoardReadBinding;

public class BoardReadActivity extends AppCompatActivity {

    ActivityBoardReadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardReadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.containerBoardRead, new BoardReadFragment()).commit();
        binding.btnReply.setOnClickListener(v -> {
            Fragment fragment = null;
            if (binding.btnReply.getText().toString().equals("댓글 열기")) {
                binding.btnReply.setIcon(getDrawable(R.drawable.icon_close));
                binding.btnReply.setText("댓글 닫기");
                fragment = new BoardReadReplyFragment();
            } else {
                binding.btnReply.setIcon(getDrawable(R.drawable.icon_reply));
                binding.btnReply.setText("댓글 열기");
                fragment = new BoardReadFragment();
            }
            if (fragment!=null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.containerBoardRead, fragment).commit();
            }
        });
        binding.btnReplyWrite.setOnClickListener(v->{

        });
    }
}