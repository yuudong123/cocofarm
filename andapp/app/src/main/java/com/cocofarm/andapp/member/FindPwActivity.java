package com.cocofarm.andapp.member;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.InputInfoEmailBinding;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class FindPwActivity extends AppCompatActivity {
    InputInfoEmailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = InputInfoEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvTitle.setText("이메일 확인");
        binding.tvSubtitle.setText("가입 시 등록한 이메일을 입력해주세요.");

        YoYo.with(Techniques.FadeInUp).duration(1000).repeat(0).playOn(binding.tvTitle);
        YoYo.with(Techniques.FadeInUp).duration(1000).repeat(0).playOn(binding.tvSubtitle);
        YoYo.with(Techniques.FadeInUp).duration(1000).repeat(0).playOn(binding.edtInfo);
        YoYo.with(Techniques.FadeInUp).duration(1000).repeat(0).playOn(binding.tvSend);


        binding.tvSend.setOnClickListener(view -> {
            String email = binding.edtInfo.getText().toString();

            if (email.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                CommonConn email_conn = new CommonConn(this, "/member/email_search.and");
                email_conn.addParam("email", email);

                email_conn.onExcute((isResult, data) -> {
                    if(isResult) {
                            binding.tvSend.setVisibility(View.GONE);
                            binding.tvComplete.setVisibility(View.VISIBLE);
                            binding.layoutConfirm.setVisibility(View.VISIBLE);

                            String confirm_text = JoinEmailActivity.random_email();

                            CommonConn conn = new CommonConn(this, "/email/findpw");
                            conn.addParam("confirm_text", confirm_text);
                            conn.addParam("email", email);

                            conn.onExcute((isResult1, data1) -> {
                                Log.d("인증번호", "onCreate: " + data1);
                            });

                            binding.tvOk.setOnClickListener(v->{
                                if (binding.edtConfirm.getText().toString().equals(confirm_text)) {
                                    Intent intent = new Intent(this, ChangePwActivity.class);
                                    intent.putExtra("email", email);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(this, "잘못된 인증번호입니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                    } else {
                        Toast.makeText(this, "오류 발생", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });


            }
        });
    }
}