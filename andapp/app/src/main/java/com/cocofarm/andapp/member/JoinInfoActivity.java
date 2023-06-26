package com.cocofarm.andapp.member;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityJoinInfoBinding;
import com.cocofarm.andapp.mypage.RoadSearchActivity;

public class JoinInfoActivity extends AppCompatActivity {

    ActivityJoinInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJoinInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MemberVO vo = (MemberVO) getIntent().getSerializableExtra("join");
        binding.tvEmail.setText(vo.getEmail());

        binding.tvAddress.setOnClickListener(v-> {
            Intent intent = new Intent(this, RoadSearchActivity.class);
            getSearchResult.launch(intent);
        });



        binding.tvOk.setOnClickListener(view -> {
            String password = binding.edtPw.getText().toString();
            String nickname= binding.edtName.getText().toString();
            String phone = binding.edtPhone.getText().toString();
            String address_road = binding.tvAddress.getText().toString() + ", ";
            String address = address_road + binding.edtAddress.getText().toString();


            if (nickname.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "필수 정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                if (address.equals(", ")) {
                    address = "";
                }

                vo.setPassword(password);
                vo.setNickname(nickname);
                vo.setPhonenumber(phone);
                vo.setAddress(address);


                CommonConn conn = new CommonConn(this, "/member/join.and");
                conn.addParam("email", vo.getEmail());
                conn.addParam("password", vo.getPassword());
                conn.addParam("nickname", vo.getNickname());
                conn.addParam("phonenumber", vo.getPhonenumber());
                conn.addParam("address", vo.getAddress());
                conn.addParam("sns", vo.getSns());

                conn.onExcute((isResult, data) -> {
                    if (isResult) {
                        Log.d("회원가입 정보", "onCreate: " + data);
                        Intent intent = new Intent(JoinInfoActivity.this, JoinCompleteActivity.class);
                        MemberVO loginVo = new MemberVO();
                        loginVo.setEmail(vo.getEmail());
                        loginVo.setPassword(vo.getPassword());
                        intent.putExtra("login", loginVo);
                        startActivity(intent);
                    } else {
                        Log.d("오류 발생", "onfalse: ");
                        Toast.makeText(this, "오류 발생", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }

    private final ActivityResultLauncher<Intent> getSearchResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        String data = result.getData().getStringExtra("address");
                        binding.tvAddress.setText(data);
                    }
                }
            }
    );

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}