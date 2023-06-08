package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CommonVal.loginMemberAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityBoardModifyBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BoardModifyActivity extends AppCompatActivity {

    ActivityBoardModifyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBoardModifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BoardVO vo = (BoardVO) getIntent().getSerializableExtra("BoardVO");

        binding.edtTitle.setText(vo.getTitle());
        binding.edtContent.setText(vo.getContent());

        binding.btnConfirm.setOnClickListener(v -> {
            if(binding.edtTitle.getText().toString().equals("")||binding.edtContent.getText().toString().equals("")){
                Toast.makeText(this, "제목과 내용을 확인해주세요", Toast.LENGTH_SHORT).show();
            }
            CommonConn conn = new CommonConn(this, "updateboard.and");
            conn.addParam("board_no", vo.getBoard_no());
            conn.addParam("product_id", vo.getProduct_id());
            conn.addParam("title",binding.edtTitle.getText().toString());
            conn.addParam("content",binding.edtContent.getText().toString());
            conn.onExcute((isResult, data) -> {
                Log.d("글 작성", "onCreate: "+isResult);
                if(isResult) {
                    if (BoardReadActivity.getInstance()!=null){
                        BoardReadActivity.getInstance().finish();
                    }
                    this.finish();
                    Intent intent = new Intent(BoardModifyActivity.this,BoardReadActivity.class);
                    CommonConn newconn = new CommonConn(BoardModifyActivity.this,"selectboard.and");
                    newconn.addParam("board_no", vo.getBoard_no());
                    newconn.onExcute((isResult1, data1) -> {
                        BoardVO newvo = (BoardVO) new Gson().fromJson(data1,new TypeToken<BoardVO>(){}.getType());
                        intent.putExtra("BoardVO", newvo);
                        startActivity(intent);
                    });
                }
            });
        });
        binding.btnCancel.setOnClickListener(v->{
            finish();
        });
    }
}