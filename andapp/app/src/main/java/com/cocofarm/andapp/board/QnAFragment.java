package com.cocofarm.andapp.board;

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
import java.util.List;

public class QnAFragment extends Fragment {

    FragmentQnABinding binding;
    ArrayList<QnaDTO> list;
    private int startPage;
    private int endPage;
    private boolean prev, next;
    private int total;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQnABinding.inflate(inflater, container, false);

        loadBoard();

        binding.btnPrev.setOnClickListener(v -> {
            pager();
            if (!prev) {
                Toast.makeText(getContext(), "이전 페이지가 없습니다.", Toast.LENGTH_SHORT).show();
            } else {
                cri.setPage(cri.getPage()-1);
                loadBoard();
            }
        });
        binding.btnNext.setOnClickListener(v -> {
            pager();
            if (!next) {
                Toast.makeText(getContext(), "다음 페이지가 없습니다.", Toast.LENGTH_SHORT).show();
            } else {
                cri.setPage(cri.getPage()+1);
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
        conn.addParam("keyword", "");
        conn.onExcute((isResult, data) -> {
            if(isResult) {
                this.total = Integer.parseInt(data);
            }
        });
        conn = new CommonConn(null, "board/selectqnalist.and");
        conn.addParam("code", cri.getCode());
        conn.addParam("keyword", "");
        conn.onExcute((isResult, data) -> {
            if (!isResult) {
                return;
            }
            list = new Gson().fromJson(data, new TypeToken<ArrayList<QnaDTO>>() {
            }.getType());
        });
        binding.pagenum.setText(cri.getPage() + "");
    }

    protected void pager() {
        endPage = (int) (Math.ceil(cri.getPage() / 10.0)) * 10;
        startPage = endPage - 9;
        int realEnd = (int) (Math.ceil((total * 1.0) / cri.getBoardPerPage()));
        if (realEnd < endPage) {
            endPage = realEnd;
        }
        prev = startPage > 1;
        next = endPage < realEnd;
    }
}