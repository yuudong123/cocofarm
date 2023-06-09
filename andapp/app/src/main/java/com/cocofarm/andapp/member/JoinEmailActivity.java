package com.cocofarm.andapp.member;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.databinding.InputInfoBinding;

public class JoinEmailActivity extends AppCompatActivity {

    InputInfoBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = InputInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvInfoTitle.setText("이메일");
        binding.tvInfoAssist.setText("이메일을 입력하세요.");

        binding.edtInfo.getText().toString();


    }
}