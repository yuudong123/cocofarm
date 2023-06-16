package com.cocofarm.andapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityFirstBinding;
import com.cocofarm.andapp.databinding.BtmSheetSnsBinding;
import com.cocofarm.andapp.member.BannedActivity;
import com.cocofarm.andapp.member.JoinEmailActivity;
import com.cocofarm.andapp.member.JoinInfoActivity;
import com.cocofarm.andapp.member.LoginActivity;
import com.cocofarm.andapp.member.MemberVO;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.navercorp.nid.NaverIdLoginSDK;
import com.navercorp.nid.oauth.NidOAuthLogin;
import com.navercorp.nid.oauth.OAuthLoginCallback;
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


        // 일반 로그인 (시작하기)
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
                Log.e("callback", "CallBack Method");
                if(oAuthToken != null) {
                    kakaoProfile();
                    Log.d("카카오톡", "invoke: " + oAuthToken);
                }
                if (throwable != null) {
                    Log.d("카카오톡", "로그인 실패: " + throwable.getMessage());
                }
                return null;
            }
        };
        KakaoSdk.init(this, "73eaa0878647dc013752cd7b307750b3");


        bindingSheet.tvKakao.setOnClickListener(v->{
            if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(this)) {
                Log.d("카카오톡", "onCreate: true");
                UserApiClient.getInstance().loginWithKakaoTalk(this, callback); // KakaoTalk 앱 이용
            } else {
                Log.d("카카오톡", "onCreate: false");
                UserApiClient.getInstance().loginWithKakaoAccount(this, callback); // KakaoTalk 미설치 기기
            }
        });


        // 네이버 로그인
        NaverIdLoginSDK.INSTANCE.initialize(this, "mhQYny1CjQCSHvaCAkND","GPDbK27wHv", "Cocofarm");
        bindingSheet.tvNaver.setOnClickListener(v->{
            bindingSheet.buttonOAuthLoginImg.setOAuthLogin(new OAuthLoginCallback() {
                @Override
                public void onSuccess() {
                    naverProfile();
                    Log.d("네이버 로그인", "onSuccess: " + NaverIdLoginSDK.INSTANCE.getAccessToken());
                }

                @Override
                public void onFailure(int i, @NonNull String s) {

                }

                @Override
                public void onError(int i, @NonNull String s) {

                }
            });
            bindingSheet.buttonOAuthLoginImg.performClick();
        });


        // 구글 로그인
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        bindingSheet.tvGoogle.setOnClickListener(v->{
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });


        // 이메일로 회원가입
        binding.tvJoin.setOnClickListener(v->{
            Intent intent = new Intent(this, JoinEmailActivity.class);
            startActivity(intent);
        });

    }

    final int RC_SIGN_IN = 1000;
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            googleProfile(account);
        } catch (ApiException e) {
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
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
                Log.d("요청 실패", "실패: " + throwable.getMessage());
            } else {
                Log.d("카카오프로필", "email: " + user.getKakaoAccount().getEmail());
                CommonConn conn = new CommonConn(this, "email_search");
                conn.addParam("email", user.getKakaoAccount().getEmail());
                conn.onExcute((isResult, data) -> {
                    if (isResult) {
                        Log.d("카카오로그인", "onCreate: " + data);
                        // 정보 없으면 > 회원가입
                        if (data.equals("0")) {
                            MemberVO vo = new MemberVO();
                            vo.setEmail(user.getKakaoAccount().getEmail());
                            vo.setSns("KAKAO");
                            Intent intent = new Intent(this, JoinInfoActivity.class);
                            intent.putExtra("join", vo);
                            startActivity(intent);
                            // 있으면 > 로그인 처리
                        } else if (data.equals("1")) {
                            CommonConn login_conn = new CommonConn(this, "sns_login");
                            login_conn.addParam("email", user.getKakaoAccount().getEmail());
                            login_conn.onExcute((isResult1, data1) -> {
                                CommonVal.loginMember = new Gson().fromJson(data1, MemberVO.class);
                                if(CommonVal.loginMember != null) {
                                    if (CommonVal.loginMember.getIsactivated().equals("Y")) {
                                        Log.d("로그인", "onCreate: " + CommonVal.loginMember.getNickname());
                                        Intent intent = new Intent(this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(this, BannedActivity.class);
                                        CommonVal.loginMember = null;
                                        startActivity(intent);
                                    }
                                }
                            });
                        } else {
                            finish();
                        }
                    } else {
                        finish();
                    }
                });

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
                CommonConn conn = new CommonConn(FirstActivity.this, "email_search");
                conn.addParam("email", nidProfileResponse.getProfile().getEmail());

                conn.onExcute((isResult, data) -> {
                    if (isResult) {
                        Log.d("카카오로그인", "onCreate: " + data);
                        // 정보 없으면 > 회원가입
                        if (data.equals("0")) {
                            MemberVO vo = new MemberVO();
                            vo.setEmail(nidProfileResponse.getProfile().getEmail());
                            vo.setSns("NAVER");
                            Intent intent = new Intent(FirstActivity.this, JoinInfoActivity.class);
                            intent.putExtra("join", vo);
                            startActivity(intent);
                            // 있으면 > 로그인 처리
                        } else if (data.equals("1")) {
                            CommonConn login_conn = new CommonConn(FirstActivity.this, "sns_login");
                            login_conn.addParam("email", nidProfileResponse.getProfile().getEmail());

                            login_conn.onExcute((isResult1, data1) -> {
                                CommonVal.loginMember = new Gson().fromJson(data1, MemberVO.class);
                                if (CommonVal.loginMember != null) {
                                    if (CommonVal.loginMember.getIsactivated().equals("Y")) {
                                        Log.d("로그인", "onCreate: " + CommonVal.loginMember.getNickname());
                                        Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(FirstActivity.this, BannedActivity.class);
                                        CommonVal.loginMember = null;
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    }
                });
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

    private void googleProfile(GoogleSignInAccount account) {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personEmail = acct.getEmail();
            CommonConn conn = new CommonConn(FirstActivity.this, "email_search");
            conn.addParam("email", personEmail);
            conn.onExcute((isResult, data) -> {
                if (isResult) {
                    Log.d("구글로그인", "onCreate: " + data);
                    // 정보 없으면 > 회원가입
                    if (data.equals("0")) {
                        MemberVO vo = new MemberVO();
                        vo.setEmail(personEmail);
                        vo.setSns("GOOGLE");
                        Intent intent = new Intent(FirstActivity.this, JoinInfoActivity.class);
                        intent.putExtra("join", vo);
                        startActivity(intent);
                        // 있으면 > 로그인 처리
                    } else if (data.equals("1")) {
                        CommonConn login_conn = new CommonConn(FirstActivity.this, "sns_login");
                        login_conn.addParam("email", personEmail);

                        login_conn.onExcute((isResult1, data1) -> {
                            CommonVal.loginMember = new Gson().fromJson(data1, MemberVO.class);
                            if (CommonVal.loginMember != null) {
                                if (CommonVal.loginMember.getIsactivated().equals("Y")) {
                                    Log.d("로그인", "onCreate: " + CommonVal.loginMember.getNickname());
                                    Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(FirstActivity.this, BannedActivity.class);
                                    CommonVal.loginMember = null;
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }
            });
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
}