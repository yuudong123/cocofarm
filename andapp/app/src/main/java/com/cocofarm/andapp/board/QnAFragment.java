package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.board.BoardFragment.cri;
import static com.cocofarm.andapp.board.BoardFragment.pagenum;

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
    int startnum;
    int endnum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQnABinding.inflate(inflater, container, false);

        loadBoard();
        binding.btnFirst.setOnClickListener(v -> {
            pagenum = 1;
            loadBoard();
        });
        binding.btnPrev.setOnClickListener(v -> {
            if (pagenum >= 1) {
                Toast.makeText(getContext(), "이전 페이지가 없습니다.", Toast.LENGTH_SHORT).show();
            } else {
                pagenum--;
                loadBoard();
            }
        });
        binding.btnNext.setOnClickListener(v -> {
            if (pagenum > list.size() / 10) {
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
        CommonConn conn = new CommonConn(null, "selectqnalist.and");
        conn.addParam("code", cri.getCode());
        conn.addParam("keyword", cri.getKeyword());
        conn.onExcute((isResult, data) -> {
            if (!isResult) {
                return;
            }
            list = new Gson().fromJson(data, new TypeToken<ArrayList<QnaDTO>>() {
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
        QnAAdapter adapter = new QnAAdapter(blist, getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.recvBoardList.setAdapter(adapter);
        binding.recvBoardList.setLayoutManager(manager);
    }
}