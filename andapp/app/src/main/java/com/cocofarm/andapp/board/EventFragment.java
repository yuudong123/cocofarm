package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.board.BoardFragment.cri;
import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_EVENT;
import static com.cocofarm.andapp.common.CodeTable.MEMBER_TYPE_ADMIN;
import static com.cocofarm.andapp.common.CommonVal.loginMember;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.FragmentEventBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class EventFragment extends Fragment {

    FragmentEventBinding binding;
    ArrayList<BoardVO> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEventBinding.inflate(inflater, container, false);

        loadBoard();

        binding.btnFirst.setOnClickListener(v -> {
            cri.setPagenum(1);
            loadBoard();
        });
        binding.btnPrev.setOnClickListener(v -> {
            if (cri.getPagenum() >= 1) {
                Toast.makeText(getContext(), "이전 페이지가 없습니다.", Toast.LENGTH_SHORT).show();
            } else {
                cri.setPagenum(cri.getPagenum() - 1);
                loadBoard();
            }
        });
        binding.btnNext.setOnClickListener(v -> {
            if (cri.getPagenum() > list.size() / 10) {
                Toast.makeText(getContext(), "다음 페이지가 없습니다.", Toast.LENGTH_SHORT).show();
            } else {
                cri.setPagenum(cri.getPagenum() + 1);
                loadBoard();
            }
        });
        binding.btnLast.setOnClickListener(v -> {
            cri.setPagenum((list.size() - 1) / 10 + 1);
            loadBoard();
        });

        if (loginMember.getMember_type_cd() == MEMBER_TYPE_ADMIN) {
            binding.btnWrite.setVisibility(View.VISIBLE);
            binding.btnWrite.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), BoardWriteActivity.class);
                intent.putExtra("category", BOARD_CATEGORY_EVENT);
                startActivity(intent);
            });
        } else {
            binding.btnWrite.setVisibility(View.GONE);
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    protected void loadBoard() {
        CommonConn conn = new CommonConn(null, "selectboardlistcri.and");
        conn.addParam("code", cri.getCode());
        conn.addParam("pagenum", cri.getPagenum());
        conn.addParam("board_per_page", cri.getBoard_per_page());
        conn.addParam("startnum", cri.getStartnum());
        conn.addParam("endnum", cri.getEndnum());
        conn.addParam("keyword", cri.getKeyword());
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                list = new Gson().fromJson(data, new TypeToken<ArrayList<BoardVO>>() {
                }.getType());
                EventAdapter adapter = new EventAdapter(list, getContext());
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                binding.recvBoardList.setAdapter(adapter);
                binding.recvBoardList.setLayoutManager(manager);
            }
        });
    }
}