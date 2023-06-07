package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CommonVal.loginMemberAdmin;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityBoardWriteBinding;

public class BoardWriteActivity extends AppCompatActivity {

    ActivityBoardWriteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnConfirm.setOnClickListener(v -> {
            int category = getIntent().getIntExtra("category", 202);
            if(binding.edtTitle.getText().toString().equals("")||binding.edtContent.getText().toString().equals("")){
                Toast.makeText(this, "제목과 내용을 확인해주세요", Toast.LENGTH_SHORT).show();
            }
            CommonConn conn = new CommonConn(null, "insertboard.and");
            conn.addParam("member_no", loginMemberAdmin.getMember_no());
            conn.addParam("nickname", loginMemberAdmin.getNickname());
            conn.addParam("board_category_cd", category);
            conn.addParam("title",binding.edtTitle.getText().toString());
            conn.addParam("content",binding.edtContent.getText().toString());
            conn.onExcute((isResult, data) -> {
                Log.d("글 작성", "onCreate: "+isResult);
            });
        });
        binding.btnCancel.setOnClickListener(v->{
            finish();
        });
    }
}