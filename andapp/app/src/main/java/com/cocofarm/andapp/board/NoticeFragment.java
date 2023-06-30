package com.cocofarm.andapp.board;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;
import static com.cocofarm.andapp.board.BoardFragment.cri;
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

public class NoticeFragment extends Fragment {
    FragmentNoticeBinding binding;
    ArrayList<BoardVO> list;
    private int endPage;
    private boolean prev, next;
    private int total;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNoticeBinding.inflate(inflater, container, false);

        loadBoard();

        if (loginMember != null && loginMember.getMember_type_cd() == MEMBER_TYPE_ADMIN) {
            binding.btnWrite.setVisibility(VISIBLE);
            binding.btnWrite.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), BoardWriteActivity.class);
                intent.putExtra("category", BOARD_CATEGORY_NOTICE);
                startActivity(intent);
            });
        } else {
            binding.btnWrite.setVisibility(View.GONE);
        }

        binding.btnPrev.setOnClickListener(v -> {
            cri.setPage(cri.getPage() - 1);
            loadBoard();
        });
        binding.btnNext.setOnClickListener(v -> {
            cri.setPage(cri.getPage() + 1);
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
        CommonConn conn = new CommonConn(null, "board/getTotal.and");
        conn.addParam("code", cri.getCode());
        conn.addParam("keyword", cri.getKeyword());
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                this.total = Integer.parseInt(data);
                pager();
            }
        });
        conn = new CommonConn(null, "board/selectboardlist.and");
        conn.addParam("code", cri.getCode());
        conn.addParam("keyword", cri.getKeyword());
        conn.addParam("page", cri.getPage());
        conn.addParam("boardPerPage", 10);
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                list = new Gson().fromJson(data, new TypeToken<ArrayList<BoardVO>>() {
                }.getType());
                NoticeAdapter adapter = new NoticeAdapter(list, getContext());
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                binding.recvBoardList.setAdapter(adapter);
                binding.recvBoardList.setLayoutManager(manager);
            } else {
                Toast.makeText(getContext(), "게시글을 불러오지 못했습니다.", LENGTH_SHORT).show();
            }
        });
        binding.pagenum.setText(cri.getPage() + "");
    }

    protected void pager() {
        endPage = (total - 1) / 10 + 1;
        prev = cri.getPage() > 1;
        next = cri.getPage() < endPage;
        binding.btnPrev.setVisibility(prev ? VISIBLE : INVISIBLE);
        binding.btnNext.setVisibility(next ? VISIBLE : INVISIBLE);
    }
}