package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.board.BoardFragment.cri;
import static com.cocofarm.andapp.board.BoardFragment.pagenum;
import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_NOTICE;
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
import com.cocofarm.andapp.databinding.FragmentNoticeBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class NoticeFragment extends Fragment {

    FragmentNoticeBinding binding;
    ArrayList<BoardVO> list;
    int startnum;
    int endnum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNoticeBinding.inflate(inflater, container, false);

        loadBoard();

        if (loginMember.getMember_type_cd() == MEMBER_TYPE_ADMIN) {
            binding.btnWrite.setVisibility(View.VISIBLE);
            binding.btnWrite.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), BoardWriteActivity.class);
                intent.putExtra("category", BOARD_CATEGORY_NOTICE);
                startActivity(intent);
            });
        } else {
            binding.btnWrite.setVisibility(View.GONE);
        }

        binding.btnFirst.setOnClickListener(v -> {
            pagenum = 1;
            loadBoard();
        });
        binding.btnPrev.setOnClickListener(v -> {
            if (pagenum <= 1) {
                Toast.makeText(getContext(), "이전 페이지가 없습니다.", Toast.LENGTH_SHORT).show();
            } else {
                pagenum--;
                loadBoard();
            }
        });
        binding.btnNext.setOnClickListener(v -> {
            if (endnum == list.size()) {
                Toast.makeText(getContext(), "다음 페이지가 없습니다.", Toast.LENGTH_SHORT).show();
            } else {
                pagenum++;
                loadBoard();
            }
        });
        binding.btnLast.setOnClickListener(v -> {
            pagenum = ((list.size() - 1) / 10 + 1);
            loadBoard();
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    protected void loadBoard() {
        CommonConn conn = new CommonConn(null, "board/selectboardlist.and");
        conn.addParam("code", cri.getCode());
        conn.addParam("keyword", cri.getKeyword());
        conn.onExcute((isResult, data) -> {
            if (!isResult) {
                return;
            }
            list = new Gson().fromJson(data, new TypeToken<ArrayList<BoardVO>>() {
            }.getType());
            pager();
        });
        binding.pagenum.setText(pagenum + "");
    }

    protected void pager() {
        endnum = pagenum * 10;
        startnum = endnum - 9;
        if (endnum > list.size()) {
            endnum = list.size();
        }
        List blist = list.subList(startnum - 1, endnum);
        NoticeAdapter adapter = new NoticeAdapter(blist, getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.recvBoardList.setAdapter(adapter);
        binding.recvBoardList.setLayoutManager(manager);
    }
}