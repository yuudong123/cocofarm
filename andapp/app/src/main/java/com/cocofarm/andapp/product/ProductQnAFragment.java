package com.cocofarm.andapp.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cocofarm.andapp.board.CriteriaDTO;
import com.cocofarm.andapp.board.QnAWriteActivity;
import com.cocofarm.andapp.board.QnaDTO;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.FragmentProductQnaBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ProductQnAFragment extends Fragment {

    FragmentProductQnaBinding binding;
    ArrayList<QnaDTO> qnaList = new ArrayList<>();
    ProductQnAAdapter adapter;
    int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductQnaBinding.inflate(inflater, container, false);

        binding.btnWrite.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), QnAWriteActivity.class);
            intent.putExtra("productVO", getArguments().getSerializable("productVO"));
            startActivity(intent);
        });

        qnaList.addAll(loadQna());
        adapter = new ProductQnAAdapter(qnaList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.recvProductQna.setAdapter(adapter);
        binding.recvProductQna.setLayoutManager(manager);

        binding.btnLoadMore.setOnClickListener(v -> {
            page++;
            qnaList.addAll(loadQna());
            adapter.notifyDataSetChanged();
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        page = 1;
    }

    protected ArrayList<QnaDTO> loadQna() {
        CommonConn conn = new CommonConn(getContext(), "selectproductqnalist.and");
        ArrayList<QnaDTO> list = new ArrayList<>();
        conn.addParam("product_id", getArguments().getInt("product_id"));
        conn.addParam("page", page);
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                list.addAll(new Gson().fromJson(data, new TypeToken<ArrayList<QnaDTO>>() {
                }.getType()));
            }
        });
        return list;
    }
}