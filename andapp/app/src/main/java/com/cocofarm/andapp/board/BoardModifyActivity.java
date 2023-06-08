package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CommonVal.loginMemberAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityBoardModifyBinding;

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
                    finish();
                }
            });
        });
        binding.btnCancel.setOnClickListener(v->{
            finish();
        });
    }
}