package com.cocofarm.andapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.cocofarm.andapp.databinding.ActivityFirstBinding;
import com.cocofarm.andapp.member.JoinActivity;
import com.cocofarm.andapp.member.LoginActivity;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class FirstActivity extends AppCompatActivity {

    ActivityFirstBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirstBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        YoYo.with(Techniques.FadeInUp).duration(1500).repeat(0).playOn(binding.ivCheck);
        YoYo.with(Techniques.FadeInUp).duration(1800).repeat(0).playOn(binding.tvStart);

        TextView function_text = binding.tvJoinInfo;
        binding.tvJoin.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        String content = function_text.getText().toString();
        SpannableString spannableString = new SpannableString(content);

        String word = "서비스 이용약관, 개인정보 이용약관, 위치정보 이용약관";
        int start = content.indexOf(word);
        int end = start + word.length();
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        function_text.setText(spannableString);


        binding.btnLogin.setOnClickListener(v->{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        binding.tvJoin.setOnClickListener(v->{
            Intent intent = new Intent(this, JoinActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}