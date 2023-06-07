package com.cocofarm.andapp.board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.databinding.FragmentBoardReadReplyBinding;

import java.util.ArrayList;
import java.util.Date;

public class BoardReadReplyFragment extends Fragment {

    FragmentBoardReadReplyBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBoardReadReplyBinding.inflate(inflater, container, false);

        ArrayList<ReplyVO> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ReplyVO vo = new ReplyVO();
            vo.setRegdate(CommonVal.Md.format(new Date()));
            vo.setContent("우와우와우와"+i);
            vo.setNickname("사용자"+i);
            list.add(vo);
        }
        ReplyAdapter adapter = new ReplyAdapter(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.recvReply.setAdapter(adapter);
        binding.recvReply.setLayoutManager(manager);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}