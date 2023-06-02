package com.cocofarm.andapp.board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cocofarm.andapp.databinding.FragmentNoticeBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NoticeFragment extends Fragment {

    FragmentNoticeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNoticeBinding.inflate(inflater, container, false);

        ArrayList<BoardVO> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            BoardVO vo = new BoardVO();
            vo.setRownum(i);
            vo.setTitle("제목테스트"+i);
            vo.setNickname("사용자"+i);
            vo.setRegdate(new Date());
            list.add(vo);
        }
        NoticeAdapter adapter = new NoticeAdapter(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.recvBoardList.setAdapter(adapter);
        binding.recvBoardList.setLayoutManager(manager);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}