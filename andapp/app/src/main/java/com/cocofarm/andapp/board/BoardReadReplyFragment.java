package com.cocofarm.andapp.board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        CommonConn conn = new CommonConn(null, "board/selectreplylist.and");
        conn.addParam("board_no", board_no);
        conn.onExcute((isResult, data) -> {
            ArrayList<ReplyVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<ReplyVO>>() {
            }.getType());
            ReplyAdapter adapter = new ReplyAdapter(getContext(), getActivity(), getActivity().getSupportFragmentManager(), board_no, list);
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            binding.recvReply.setAdapter(adapter);
            binding.recvReply.setLayoutManager(manager);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}