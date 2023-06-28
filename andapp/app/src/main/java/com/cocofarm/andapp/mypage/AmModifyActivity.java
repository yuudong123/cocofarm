package com.cocofarm.andapp.mypage;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityAmModifyBinding;
import com.cocofarm.andapp.member.MemberVO;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;

public class AmModifyActivity extends AppCompatActivity {

    ActivityAmModifyBinding binding;
    MemberVO vo = CommonVal.loginMember;
    public String oldEdtPhone = "";
    TextWatcher watcher;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityAmModifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvEmail.setText(CommonVal.loginMember.getEmail());
        binding.tvName.setText(CommonVal.loginMember.getNickname());
        binding.tvPw.setText(CommonVal.loginMember.getPassword());
        binding.tvPhone.setText(CommonVal.loginMember.getPhonenumber());
        binding.tvAddress.setText(CommonVal.loginMember.getAddress());

        // 비밀번호
        binding.tvPwModify.setOnClickListener(v->{
            binding.tvPwModify.setVisibility(View.INVISIBLE);
            binding.tvPw.setVisibility(View.INVISIBLE);
            binding.edtPw.setVisibility(View.VISIBLE);
            //binding.edtPw.setText(binding.tvPw.getText().toString());
        });

        // 닉네임
        binding.tvNameModify.setOnClickListener(v->{
            String name = (String)binding.tvName.getText();

            binding.tvNameModify.setVisibility(View.INVISIBLE);
            binding.tvName.setVisibility(View.INVISIBLE);
            binding.edtName.setVisibility(View.VISIBLE);
            binding.edtName.setText(name);
        });

        // 전화번호
        binding.tvPhoneModify.setOnClickListener(v->{
            String phone = (String)binding.tvPhone.getText();

            binding.tvPhoneModify.setVisibility(View.INVISIBLE);
            binding.tvPhone.setVisibility(View.INVISIBLE);
            binding.edtPhone.setVisibility(View.VISIBLE);

            binding.edtPhone.setText(phone);
        });

        // 주소
        binding.tvAddressModify.setOnClickListener(v->{
            String address = (String)binding.tvAddress.getText();

            Intent intent = new Intent(AmModifyActivity.this, RoadSearchActivity.class);
            getSearchResult.launch(intent);
            binding.edtAddress.setVisibility(View.VISIBLE);
        });



        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                oldEdtPhone = charSequence.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setEdtChangeText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(oldEdtPhone.equals(editable.toString())) return;;


                setEdtChangeText(phone_format(editable.toString().toString()));
            }
        };


        binding.edtPhone.addTextChangedListener(watcher);


        // 완료
        binding.btnOk.setOnClickListener(v->{
            CommonConn conn = new CommonConn(this, "/member/modify.and");

            if (binding.edtPw.getVisibility() == View.VISIBLE) {
                conn.addParam("password", binding.edtPw.getText().toString());
            } else {
                conn.addParam("password", binding.tvPw.getText().toString());
            }

            if (binding.edtName.getVisibility() == View.VISIBLE) {
                conn.addParam("nickname", binding.edtName.getText().toString());
            } else {
                conn.addParam("nickname", binding.tvName.getText().toString());
            }

            if (binding.edtPhone.getVisibility() == View.VISIBLE) {
                conn.addParam("phonenumber", binding.edtPhone.getText().toString());
            } else {
                conn.addParam("phonenumber", binding.tvPhone.getText().toString());
            }

            if (binding.edtAddress.getVisibility() == View.VISIBLE) {
                conn.addParam("address", binding.tvAddress.getText().toString() + ", " + binding.edtAddress.getText().toString());
            } else {
                conn.addParam("address", binding.tvAddress.getText().toString());
            }

            conn.addParam("email", CommonVal.loginMember.getEmail());

            conn.onExcute((isResult, data) -> {
                if (isResult) {
                    Log.d("정보수정", "onCreate: " + data);
                    CommonVal.loginMember = new Gson().fromJson(data, new TypeToken<MemberVO>(){}.getType());
                    Intent intent = new Intent(AmModifyActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(this, "정보 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("정보수정", "데이터를 가져오지 못했습니다.");
                }
            });

        });
    }

    public void setEdtChangeText(String text){
        binding.edtPhone.removeTextChangedListener(watcher);
        binding.edtPhone.setText(text);
        binding.edtPhone.setSelection(binding.edtPhone.getText().toString().length());
        binding.edtPhone.addTextChangedListener(watcher);

    }

    public String phone_format(String number) {
        String regEx = "(\\d{3})(\\d{4})(\\d{4})";
        return number.replaceAll(regEx, "$1-$2-$3");
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

}