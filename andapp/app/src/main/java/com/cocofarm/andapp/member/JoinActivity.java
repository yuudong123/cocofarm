package com.cocofarm.andapp.member;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cocofarm.andapp.databinding.ActivityJoinBinding;
import com.cocofarm.andapp.R;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApi;
import com.kakao.sdk.user.UserApiClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class JoinActivity extends AppCompatActivity {

    ActivityJoinBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getHashKey();
        binding = ActivityJoinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // 코코팜 회원가입
        binding.btnJoin.setOnClickListener(v-> {

        });


        // 카카오 회원가입 ( 네이티브 앱 키 : 73eaa0878647dc013752cd7b307750b3 )
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

        binding.ivJoinKakao.setOnClickListener(v->{
            if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(this)){
                Log.d("카카오톡", "onCreate: true");
                UserApiClient.getInstance().loginWithKakaoTalk(this, callback); // KakaoTalk 앱 이용
            } else {
                Log.d("카카오톡", "onCreate: false");
                UserApiClient.getInstance().loginWithKakaoAccount(this, callback); // KakaoTalk 미설치 기기
            }
        });
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