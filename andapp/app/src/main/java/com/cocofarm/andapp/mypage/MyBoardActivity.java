package com.cocofarm.andapp.mypage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cocofarm.andapp.board.BoardVO;
import com.cocofarm.andapp.board.QnAReadActivity;
import com.cocofarm.andapp.board.QnaDTO;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityMyBoardBinding;
import com.cocofarm.andapp.member.MemberVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Member;
import java.sql.Array;
import java.util.ArrayList;

public class MyBoardActivity extends AppCompatActivity {
    ActivityMyBoardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnReview.setOnClickListener(v->{
            CommonConn conn = new CommonConn(this, "/member/myreviewboard.and");
            conn.addParam("member_no", CommonVal.loginMember.getMember_no());
            conn.addParam("member_type_cd", CommonVal.loginMember.getMember_type_cd());

            conn.onExcute((isResult, data) -> {
                if (!isResult) {
                    return;
                } else {
                    Log.d("내가 쓴 리뷰", "onCreate: " + data);
                    ArrayList<BoardVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<BoardVO>>(){}.getType());
                    if (list.size() <= 0) {
                        binding.layoutEmpty.setVisibility(View.VISIBLE);
                    } else {
                        Log.d("데이터EMP LIST", "onCreateView: " + list.size());

                        binding.recvMyboard.setAdapter(new MyReviewBoardAdapter(list));
                        binding.recvMyboard.setLayoutManager(new LinearLayoutManager(this));

                    }
                }
            });
        });

        CommonConn conn = new CommonConn(this, "/member/myboard.and");
        conn.addParam("member_no", CommonVal.loginMember.getMember_no());

        conn.onExcute((isResult, data) -> {
            if (!isResult) {
                    return;
            } else {
                Log.d("내가쓴글", "onCreate: " + data);
                ArrayList<QnaDTO> list = new Gson().fromJson(data, new TypeToken<ArrayList<QnaDTO>>(){}.getType());
                if (list.size() <= 0) {
                    binding.layoutEmpty.setVisibility(View.VISIBLE);
                } else {
                    Log.d("데이터EMP LIST", "onCreateView: " + list.size());

                    binding.recvMyboard.setAdapter(new MyBoardAdapter(list));
                    binding.recvMyboard.setLayoutManager(new LinearLayoutManager(this));

                }
            }
        });
    }
}