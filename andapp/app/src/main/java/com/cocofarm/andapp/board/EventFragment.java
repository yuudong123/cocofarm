package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_EVENT;
import static com.cocofarm.andapp.common.CodeTable.MEMBER_TYPE_ADMIN;
import static com.cocofarm.andapp.common.CommonVal.loginMemberAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cocofarm.andapp.common.CodeTable;
import com.cocofarm.andapp.databinding.FragmentEventBinding;

import java.util.ArrayList;
import java.util.Date;

public class EventFragment extends Fragment {

    FragmentEventBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEventBinding.inflate(inflater, container, false);

        ArrayList<BoardVO> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            BoardVO vo = new BoardVO();
            vo.setRownum(i);
            vo.setTitle("제목테스트"+i);
            vo.setRegdate(new Date());
            list.add(vo);
        }
        EventAdapter adapter = new EventAdapter(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.recvBoardList.setAdapter(adapter);
        binding.recvBoardList.setLayoutManager(manager);

        if (loginMemberAdmin.getMember_type_cd() == MEMBER_TYPE_ADMIN) {
            binding.btnWrite.setVisibility(View.VISIBLE);
            binding.btnWrite.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(),BoardWriteActivity.class);
                intent.putExtra("category", BOARD_CATEGORY_EVENT);
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