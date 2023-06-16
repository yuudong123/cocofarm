package com.cocofarm.andapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cocofarm.andapp.databinding.ActivityFirstBinding;
import com.cocofarm.andapp.databinding.BtmSheetSnsBinding;
import com.cocofarm.andapp.member.JoinEmailActivity;
import com.cocofarm.andapp.member.LoginActivity;
import com.cocofarm.andapp.member.MemberVO;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.navercorp.nid.oauth.NidOAuthLogin;
import com.navercorp.nid.profile.NidProfileCallback;
import com.navercorp.nid.profile.data.NidProfileResponse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class FirstActivity extends AppCompatActivity {

    ActivityFirstBinding binding;
    BtmSheetSnsBinding bindingSheet;
    BottomSheetDialog bottomSheetDialog;
    boolean isSheetVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getHashKey();
        binding = ActivityFirstBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        YoYo.with(Techniques.FadeInUp).duration(1800).repeat(0).playOn(binding.layoutFirst);


        TextView function_text = binding.tvJoinInfo;
        binding.tvJoin.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        String content = function_text.getText().toString();
        SpannableString spannableString = new SpannableString(content);

        String word = "서비스 이용약관, 개인정보 이용약관, 위치정보 이용약관";
        int start = content.indexOf(word);
        int end = start + word.length();
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        function_text.setText(spannableString);


        // 로그인
        binding.btnLogin.setOnClickListener(v->{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        // BottomSheet
        bindingSheet = BtmSheetSnsBinding.inflate(getLayoutInflater(), null, false);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(bindingSheet.getRoot());
        binding.btnSns.setOnClickListener(v->{
            toggleBottomSheet();
        });
        bindingSheet.btnDrop.setOnClickListener(v -> {
            toggleBottomSheet();
        });

        // 카카오 로그인
        Function2 callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(oAuthToken != null) {
                    kakaoProfile();
                    Log.d("카카오톡", "invoke: " + oAuthToken);
                }
                if (throwable != null) {
                    Log.d("카카오톡", "invoke: " + throwable.getMessage());
                }
                return null;
            }
        };
        KakaoSdk.init(this, "73eaa0878647dc013752cd7b307750b3");
        bindingSheet.tvKakao.setOnClickListener(v->{
            if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(this)){
                Log.d("카카오톡", "onCreate: true");
                UserApiClient.getInstance().loginWithKakaoTalk(this, callback); // KakaoTalk 앱 이용
            } else {
                Log.d("카카오톡", "onCreate: false");
                UserApiClient.getInstance().loginWithKakaoAccount(this, callback); // KakaoTalk 미설치 기기
            }
        });

        // 네이버 로그인
        bindingSheet.tvNaver.setOnClickListener(v->{

        });

        // 구글 로그인
        bindingSheet.tvGoogle.setOnClickListener(v->{

        });



        binding.tvJoin.setOnClickListener(v->{
            Intent intent = new Intent(this, JoinEmailActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void toggleBottomSheet() {
        if (isSheetVisible) {
            bottomSheetDialog.dismiss();
            isSheetVisible = false;
        } else {
            bottomSheetDialog.show();
            isSheetVisible = true;
        }
    }


    private void kakaoProfile() {
        UserApiClient.getInstance().me((user, throwable) -> {
            if(throwable != null) {
                Log.d("카카오", "kakaoProfile: " + throwable.getMessage());
            } else {
                Log.d("카카오프로필", "kakaoProfile: " + user.getKakaoAccount().getEmail());
                Log.d("카카오프로필", "kakaoProfile: " + user.getKakaoAccount().getProfile().getNickname());
            }
            return null;
        });
    }

    private void naverProfile() {
        NidOAuthLogin nidOAuthLogin = new NidOAuthLogin();
        nidOAuthLogin.callProfileApi(new NidProfileCallback<NidProfileResponse>() {
            @Override
            public void onSuccess(NidProfileResponse nidProfileResponse) {
                Log.d("네이버", "onSuccess: " + nidProfileResponse.getProfile().getEmail());
                Log.d("네이버", "onSuccess: " + nidProfileResponse.getProfile().getMobile());
                Log.d("네이버", "onSuccess: " + nidProfileResponse.getProfile().getName());
            }

            @Override
            public void onFailure(int i, @NonNull String s) {
                Log.d("네이버", "onFailure: ");
            }

            @Override
            public void onError(int i, @NonNull String s) {
                Log.d("네이버", "onError: ");
            }
        });
    }

    // HashKey
    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }
}