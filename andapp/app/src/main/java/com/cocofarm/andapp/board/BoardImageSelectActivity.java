package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CommonVal.boardselectedImage;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityBoardImageSelectBinding;
import com.cocofarm.andapp.image.ImageDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class BoardImageSelectActivity extends AppCompatActivity {

    ActivityBoardImageSelectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardImageSelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CommonConn conn = new CommonConn(null, "selectimagelist.and");
        conn.onExcute((isResult, data) -> {
            ArrayList<ImageDTO> list = new Gson().fromJson(data, new TypeToken<ArrayList<ImageDTO>>() {
            }.getType());
            BoardImageAdapter adapter = new BoardImageAdapter(this,list);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            binding.recvBoardImageSelect.setAdapter(adapter);
            binding.recvBoardImageSelect.setLayoutManager(manager);
        });

        binding.btnConfirm.setOnClickListener(v->{
            finish();
        });
        binding.btnCancel.setOnClickListener(v->{
            boardselectedImage = null;
            finish();
        });
    }
}