package com.cocofarm.andapp.member;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.InputInfoBinding;
import com.cocofarm.andapp.databinding.InputInfoEmailBinding;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.util.Random;
import java.util.UUID;

public class JoinEmailActivity extends AppCompatActivity {

    InputInfoEmailBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = InputInfoEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvInfoTitle.setText("이메일");
        binding.tvInfoAssist.setText("이메일을 입력하세요.");
        binding.btnSend.setText("인증하기");

        MemberVO vo = new MemberVO();

        binding.btnSend.setOnClickListener(view -> {
            String email = binding.edtInfo.getText().toString();

            if (email.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                vo.setEmail(email);
                binding.tiEmail.setVisibility(View.VISIBLE);

                String confirm_text = random_email();
                CommonConn conn = new CommonConn(this, "email");
                conn.addParam("confirm_text", confirm_text);
                conn.addParam("email", email);

                conn.onExcute((isResult, data) -> {
                    Log.d("인증번호", "onCreate: " + data);
                });


                binding.btnEmail.setOnClickListener(v->{
                    if (binding.edtConfirm.getText().toString().equals(confirm_text)) {
                        Intent intent = new Intent(JoinEmailActivity.this, JoinPwActivity.class);
                        intent.putExtra("join", vo);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "잘못된 인증번호입니다.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



        binding.tvInfoBtmClick.setOnClickListener(v-> {
            Intent intent = new Intent(JoinEmailActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }


    // 인증번호 생성
    public static String random_email() {
        Log.d("UUID", "random_email: " +  UUID.randomUUID().toString());
        String randomValue = UUID.randomUUID().toString().substring(0,7);

        return randomValue;
    }
}

