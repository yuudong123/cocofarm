package com.cocofarm.andapp.board;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;
import static com.cocofarm.andapp.board.BoardFragment.cri;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.FragmentQnABinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class QnAFragment extends Fragment {

    FragmentQnABinding binding;
    ArrayList<QnaDTO> list;
    private int endPage;
    private boolean prev, next;
    private int total;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQnABinding.inflate(inflater, container, false);

        loadBoard();

        binding.btnPrev.setOnClickListener(v -> {
            if (!prev) {
                Toast.makeText(getContext(), "이전 페이지가 없습니다.", LENGTH_SHORT).show();
            } else {
                cri.setPage(cri.getPage() - 1);
                loadBoard();
            }
        });
        binding.btnNext.setOnClickListener(v -> {
            if (!next) {
                Toast.makeText(getContext(), "다음 페이지가 없습니다.", LENGTH_SHORT).show();
            } else {
                cri.setPage(cri.getPage() + 1);
                loadBoard();
            }
        });

        binding.btnWrite.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), QnAWriteActivity.class);
            startActivity(intent);
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
        conn = new CommonConn(null, "board/selectqnalist.and");
        conn.addParam("code", cri.getCode());
        conn.addParam("keyword", "");
        conn.addParam("page", cri.getPage());
        conn.addParam("boardPerPage", 10);
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                list = new Gson().fromJson(data, new TypeToken<ArrayList<QnaDTO>>() {
                }.getType());
                QnAAdapter adapter = new QnAAdapter(list, getContext());
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