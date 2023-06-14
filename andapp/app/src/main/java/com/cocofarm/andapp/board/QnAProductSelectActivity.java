package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.board.QnAWriteActivity.qnaselectedproduct;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityQnaProductSelectBinding;
import com.cocofarm.andapp.image.ImageDTO;
import com.cocofarm.andapp.product.ProductVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class QnAProductSelectActivity extends AppCompatActivity {

    ActivityQnaProductSelectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQnaProductSelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CommonConn conn = new CommonConn(null, "selectproductimagelist.and");
        conn.onExcute((isResult, data) -> {
            ArrayList<ProductVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<ProductVO>>() {
            }.getType());
            QnAProductAdapter adapter = new QnAProductAdapter(this, list);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            binding.recvQnaItemSelect.setAdapter(adapter);
            binding.recvQnaItemSelect.setLayoutManager(manager);
        });

        binding.btnConfirm.setOnClickListener(v -> {
            finish();
        });
        binding.btnCancel.setOnClickListener(v -> {
            qnaselectedproduct = null;
            finish();
        });
    }
}