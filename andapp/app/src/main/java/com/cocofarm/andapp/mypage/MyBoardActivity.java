package com.cocofarm.andapp.mypage;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cocofarm.andapp.board.BoardVO;
import com.cocofarm.andapp.board.QnaDTO;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityMyBoardBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MyBoardActivity extends AppCompatActivity {
    ActivityMyBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnReview.setOnClickListener(v -> {
            CommonConn conn = new CommonConn(this, "/member/myreviewboard.and");
            conn.addParam("member_no", CommonVal.loginMember.getMember_no());
            conn.addParam("member_type_cd", CommonVal.loginMember.getMember_type_cd());
            conn.onExcute((isResult, data) -> {
                if (isResult) {
                    ArrayList<BoardVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<BoardVO>>() {
                    }.getType());
                    if (list.size() <= 0) {
                        binding.layoutEmpty.setVisibility(View.VISIBLE);
                    } else {
                        Log.d("데이터EMP LIST", "onCreateView: " + list.size());
                        binding.recvMyboard.setAdapter(new MyReviewBoardAdapter(list));
                        binding.recvMyboard.setLayoutManager(new LinearLayoutManager(this));
                    }
                } else {
                    Toast.makeText(this, "정보를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        });

        CommonConn conn = new CommonConn(this, "/member/myboard.and");
        conn.addParam("member_no", CommonVal.loginMember.getMember_no());
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                ArrayList<QnaDTO> list = new Gson().fromJson(data, new TypeToken<ArrayList<QnaDTO>>() {
                }.getType());
                if (list.size() <= 0) {
                    binding.layoutEmpty.setVisibility(View.VISIBLE);
                } else {
                    Log.d("데이터EMP LIST", "onCreateView: " + list.size());
                    binding.recvMyboard.setAdapter(new MyBoardAdapter(list));
                    binding.recvMyboard.setLayoutManager(new LinearLayoutManager(this));
                }
            } else {
                Toast.makeText(this, "정보를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}