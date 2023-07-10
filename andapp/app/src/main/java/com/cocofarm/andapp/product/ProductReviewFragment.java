package com.cocofarm.andapp.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cocofarm.andapp.board.BoardVO;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.FragmentProductReviewBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class ProductReviewFragment extends Fragment {

    FragmentProductReviewBinding binding;
    ArrayList<BoardVO> reviewList = new ArrayList<>();
    ProductReviewAdapter adapter;

    int total;
    int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductReviewBinding.inflate(inflater, container, false);

        loadReview();

        adapter = new ProductReviewAdapter(reviewList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.recvProductReview.setAdapter(adapter);
        binding.recvProductReview.setLayoutManager(manager);
        binding.btnLoadMore.setOnClickListener(v -> {
            page++;
            loadReview();
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

    protected void loadReview() {
        int product_id = getArguments().getInt("product_id");
        CommonConn conn = new CommonConn(null, "selectproductreviewtotal.and");
        conn.addParam("product_id", product_id);
        conn.addParam("page", page);
        conn.onExcute((isResult, data) -> {
            total = Integer.parseInt(data);
            if (page * 10 >= total) {
                binding.btnLoadMore.setVisibility(View.GONE);
            } else {
                binding.btnLoadMore.setVisibility(View.VISIBLE);
            }
        });

        conn = new CommonConn(null, "selectproductreviewlist.and");
        conn.addParam("product_id", product_id);
        conn.addParam("page", page);
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                ArrayList<BoardVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<BoardVO>>() {
                }.getType());
                reviewList.addAll(list);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "리뷰를 불러오지 못했습니다..", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
