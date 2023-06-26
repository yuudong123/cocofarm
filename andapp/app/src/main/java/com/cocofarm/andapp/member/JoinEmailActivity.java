package com.cocofarm.andapp.member;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.InputInfoEmailBinding;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.UUID;

public class JoinEmailActivity extends AppCompatActivity {

    InputInfoEmailBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = InputInfoEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tvSend.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        YoYo.with(Techniques.FadeInUp).duration(1000).repeat(0).playOn(binding.tvTitle);
        YoYo.with(Techniques.FadeInUp).duration(1000).repeat(0).playOn(binding.tvSubtitle);
        YoYo.with(Techniques.FadeInUp).duration(1000).repeat(0).playOn(binding.edtInfo);
        YoYo.with(Techniques.FadeInUp).duration(1000).repeat(0).playOn(binding.tvSend);

        MemberVO vo = new MemberVO();


        binding.tvSend.setOnClickListener(view -> {
            String email = binding.edtInfo.getText().toString();

            if (email.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                CommonConn email_conn = new CommonConn(this, "/member/email_search.and");
                email_conn.addParam("email", email);

                email_conn.onExcute((isResult, data) -> {
                    if(isResult) {
                        if (data.equals("1")) {
                            Toast.makeText(this, "이미 등록된 이메일입니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            vo.setEmail(email);
                            vo.setSns("N");
                            binding.tvSend.setVisibility(View.GONE);
                            binding.tvComplete.setVisibility(View.VISIBLE);
                            binding.layoutConfirm.setVisibility(View.VISIBLE);

                            String confirm_text = random_email();

                            CommonConn conn = new CommonConn(this, "/email/send");
                            conn.addParam("confirm_text", confirm_text);
                            conn.addParam("email", email);

                            conn.onExcute((isResult1, data1) -> {
                                Log.d("인증번호", "onCreate: " + data1);
                            });

                            binding.tvOk.setOnClickListener(v->{
                                if (binding.edtConfirm.getText().toString().equals(confirm_text)) {
                                    Intent intent = new Intent(JoinEmailActivity.this, JoinInfoActivity.class);
                                    intent.putExtra("join", vo);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(this, "잘못된 인증번호입니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        Toast.makeText(this, "오류 발생", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });


            }
        });



//        binding.tvInfoBtmClick.setOnClickListener(v-> {
//            Intent intent = new Intent(JoinEmailActivity.this, LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//        });
    }




    // 인증번호 생성
    public static String random_email() {
        Log.d("UUID", "random_email: " +  UUID.randomUUID().toString());
        String randomValue = UUID.randomUUID().toString().substring(0,7);

        return randomValue;
    }
}

