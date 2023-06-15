package com.cocofarm.andapp.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

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
    ProductVO productVO;
    ProductQnAAdapter adapter;
    private int page = 1;
    private int total;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductQnaBinding.inflate(inflater, container, false);

        productVO = (ProductVO) getArguments().getSerializable("productVO");

        binding.btnWrite.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), QnAWriteActivity.class);
            intent.putExtra("productVO", productVO);
            startActivity(intent);
        });

        loadQna();
        adapter = new ProductQnAAdapter(qnaList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.recvProductQna.setAdapter(adapter);
        binding.recvProductQna.setLayoutManager(manager);

        binding.btnLoadMore.setOnClickListener(v -> {
            if (page * 10 < total) {
                page++;
                loadQna();
            }
            if (page * 10 >= total) {
                binding.btnLoadMore.setVisibility(View.GONE);
            }
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

    protected void loadQna() {
        CommonConn conn = new CommonConn(null, "selectproductqnatotal.and");
        conn.addParam("product_id", productVO.getProduct_id());
        conn.addParam("page", page);
        conn.onExcute((isResult, data) -> total = Integer.parseInt(data));

        conn = new CommonConn(null, "selectproductqnalist.and");
        conn.addParam("product_id", productVO.getProduct_id());
        conn.addParam("page", page);
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                ArrayList<QnaDTO> list = new Gson().fromJson(data, new TypeToken<ArrayList<QnaDTO>>() {
                }.getType());
                qnaList.addAll(list);
                adapter.notifyDataSetChanged();
            }
        });
    }
}