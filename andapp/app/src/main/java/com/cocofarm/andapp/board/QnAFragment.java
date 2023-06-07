package com.cocofarm.andapp.board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.databinding.FragmentQnABinding;

import java.util.ArrayList;
import java.util.Date;

public class QnAFragment extends Fragment {

    FragmentQnABinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQnABinding.inflate(inflater, container, false);

        ArrayList<BoardVO> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            BoardVO vo = new BoardVO();
            vo.setRownum(i);
            vo.setTitle("제목테스트" + i);
            vo.setRegdate(CommonVal.Md.format(new Date()));
            list.add(vo);
        }
        QnAAdapter adapter = new QnAAdapter(list);
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