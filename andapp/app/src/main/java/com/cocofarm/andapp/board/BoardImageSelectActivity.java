package com.cocofarm.andapp.board;

import static android.widget.Toast.LENGTH_SHORT;
import static com.cocofarm.andapp.common.CommonVal.boardselectedImage;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityBoardImageSelectBinding;
import com.cocofarm.andapp.image.ImageDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class BoardImageSelectActivity extends AppCompatActivity {

    ActivityBoardImageSelectBinding binding;
    private static BoardImageSelectActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardImageSelectBinding.inflate(getLayoutInflater());
        activity = this;
        setContentView(binding.getRoot());

        CommonConn conn = new CommonConn(null, "selectimagelist.and");
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                ArrayList<ImageDTO> list = new Gson().fromJson(data, new TypeToken<ArrayList<ImageDTO>>() {
                }.getType());
                BoardImageAdapter adapter = new BoardImageAdapter(this, list);
                LinearLayoutManager manager = new LinearLayoutManager(this);
                binding.recvBoardImageSelect.setAdapter(adapter);
                binding.recvBoardImageSelect.setLayoutManager(manager);
            } else {
                Toast.makeText(activity, "이미지를 불러오지 못했습니다.", LENGTH_SHORT).show();
            }
        });

        binding.btnConfirm.setOnClickListener(v -> {
            finish();
        });

        binding.btnCancel.setOnClickListener(v -> {
            boardselectedImage = "";
            finish();
        });
    }

    public static void finishActivity() {
        activity.finish();
    }
}