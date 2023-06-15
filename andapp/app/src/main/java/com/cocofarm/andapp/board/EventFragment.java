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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.FragmentEventBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class EventFragment extends Fragment {

    FragmentEventBinding binding;
    ArrayList<BoardVO> boardList = new ArrayList<>();
    EventAdapter adapter;
    private int page = 1;
    private int total;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEventBinding.inflate(inflater, container, false);

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

        loadBoard();
        adapter = new EventAdapter(boardList, getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.recvBoardList.setAdapter(adapter);
        binding.recvBoardList.setLayoutManager(manager);

        binding.recvBoardList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager manager = (LinearLayoutManager) binding.recvBoardList.getLayoutManager();
                if (manager != null && manager.findLastCompletelyVisibleItemPosition() == boardList.size() - 1 && total > page*10) {
                    page++;
                    loadBoard();
                }
            }
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
        conn.onExcute((isResult, data) -> this.total = Integer.parseInt(data));

        conn = new CommonConn(null, "board/selectboardlist.and");
        conn.addParam("code", cri.getCode());
        conn.addParam("keyword", "");
        conn.addParam("page", cri.getPage());
        conn.addParam("boardPerPage", 10);
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                ArrayList<BoardVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<BoardVO>>() {
                }.getType());
                boardList.addAll(list);
                adapter.notifyDataSetChanged();
            }
        });
    }
}