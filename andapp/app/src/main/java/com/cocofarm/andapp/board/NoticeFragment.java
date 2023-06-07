package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_NOTICE;
import static com.cocofarm.andapp.common.CodeTable.MEMBER_TYPE_ADMIN;
import static com.cocofarm.andapp.common.CommonVal.loginMemberAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.FragmentNoticeBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class NoticeFragment extends Fragment {

    FragmentNoticeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNoticeBinding.inflate(inflater, container, false);

        CommonConn conn = new CommonConn(null, "notice/selectboardlist.and");
        conn.addParam("code", BOARD_CATEGORY_NOTICE);
        conn.onExcute((isResult, data) -> {
            ArrayList<BoardVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<BoardVO>>() {
            }.getType());
            NoticeAdapter adapter = new NoticeAdapter(list, getContext());
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            binding.recvBoardList.setAdapter(adapter);
            binding.recvBoardList.setLayoutManager(manager);
        });

        if (loginMemberAdmin.getMember_type_cd() == MEMBER_TYPE_ADMIN) {
            binding.btnWrite.setVisibility(View.VISIBLE);
            binding.btnWrite.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(),BoardWriteActivity.class);
                startActivity(intent);
            });
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}