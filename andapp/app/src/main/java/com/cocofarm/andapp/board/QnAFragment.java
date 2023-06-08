package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_QNA;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.FragmentQnABinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class QnAFragment extends Fragment {

    FragmentQnABinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQnABinding.inflate(inflater, container, false);

        CommonConn conn = new CommonConn(getContext(), "selectqnalist.and");
        conn.addParam("code", BOARD_CATEGORY_QNA);
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                ArrayList<QnaDTO> list = new Gson().fromJson(data, new TypeToken<ArrayList<QnaDTO>>() {
                }.getType());
                QnAAdapter adapter = new QnAAdapter(list, getContext());
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                binding.recvBoardList.setAdapter(adapter);
                binding.recvBoardList.setLayoutManager(manager);
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
}