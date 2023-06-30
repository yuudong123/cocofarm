package com.cocofarm.andapp.board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.FragmentBoardReadReplyBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class BoardReadReplyFragment extends Fragment {

    FragmentBoardReadReplyBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBoardReadReplyBinding.inflate(inflater, container, false);
        int board_no = getArguments().getInt("board_no");

        CommonConn conn = new CommonConn(null, "reply/selectreplylist.and");
        conn.addParam("board_no", board_no);
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                ArrayList<ReplyVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<ReplyVO>>() {
                }.getType());
                ReplyAdapter adapter = new ReplyAdapter(getContext(), getActivity(), getActivity().getSupportFragmentManager(), board_no, list);
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                binding.recvReply.setAdapter(adapter);
                binding.recvReply.setLayoutManager(manager);
            } else {
                Toast.makeText(getContext(), "댓글을 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}