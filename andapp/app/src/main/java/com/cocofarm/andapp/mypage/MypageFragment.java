package com.cocofarm.andapp.mypage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cocofarm.andapp.FirstActivity;
import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.R;
import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.databinding.FragmentMypageBinding;
import com.cocofarm.andapp.member.LoginActivity;
import com.cocofarm.andapp.member.MemberVO;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kakao.sdk.user.UserApiClient;
import com.navercorp.nid.NaverIdLoginSDK;
import com.navercorp.nid.oauth.NidOAuthLogin;
import com.navercorp.nid.oauth.OAuthLoginCallback;

public class MypageFragment extends Fragment {

    FragmentMypageBinding binding;
    MemberVO vo;
    kakao kakao;
    naver naver;
    google google;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMypageBinding.inflate(inflater, container, false);

        binding.tvNickname.setText(CommonVal.loginMember.getNickname());
        binding.tvEmail.setText(CommonVal.loginMember.getEmail());
        binding.tvSnsOut.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        if (CommonVal.loginMember.getSns().equals("KAKAO")) {
            binding.ivKakao.setVisibility(View.VISIBLE);
        } else if (CommonVal.loginMember.getSns().equals("NAVER")) {
            binding.ivNaver.setVisibility(View.VISIBLE);
        } else if (CommonVal.loginMember.getSns().equals("GOOGLE")) {
            binding.ivGoogle.setVisibility(View.VISIBLE);
        } else {
            binding.tvSnsOut.setVisibility(View.GONE);
        }

        binding.btnLogout.setOnClickListener(v-> {
            if (CommonVal.loginMember.getSns().equals("KAKAO")) {
                kakao.signOut();
            } else if (CommonVal.loginMember.getSns().equals("NAVER")) {

            } else if (CommonVal.loginMember.getSns().equals("GOOGLE")) {
                google.signOut();
            } else {
                CommonVal.loginMember = null;
                getActivity().finish();
                CommonVal.isCheckLogout = true;
                Toast.makeText(getActivity(), "로그아웃 완료", Toast.LENGTH_SHORT).show();
            }
        });

        binding.tvSnsOut.setOnClickListener(v->{
            if (CommonVal.loginMember.getSns().equals("KAKAO")) {
                kakao.revokeAccess();
            } else if (CommonVal.loginMember.getSns().equals("NAVER")){
               naver.revokeAccess();
            } else {
                google.revokeAccess();
            }
        });




        binding.tvAboutme.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(), AboutMeActivity.class);
            startActivity(intent);
        });
        binding.tvMyalert.setOnClickListener(v-> {
//            Intent intent = new Intent(getActivity(), AlertListActivity.class);
        });
        binding.tvMyorder.setOnClickListener(v-> {
//            Intent intent = new Intent(getActivity(), MyOrderListActivity.class);
        });
        binding.tvMyboard.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(), MyBoardActivity.class);
            startActivity(intent);
        });
        binding.tvCscenter.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(), CsCenterActivity.class);
            startActivity(intent);
        });
        binding.tvAway.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(), AwayActivity.class);
            startActivity(intent);
        });


        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public class kakao {
        private void signOut() {
            UserApiClient.getInstance().logout(throwable -> {
                if (throwable != null) {
                    Log.e("카카오 로그아웃", "로그아웃 실패. SDK에서 토큰 삭제됨", throwable);
                } else {
                    Log.i("카카오 로그아웃", "로그아웃 성공. SDK에서 토큰 삭제됨");
                }
                return null;
            });
        }

        private void revokeAccess() {
            UserApiClient.getInstance().unlink(throwable -> {
                if (throwable != null) {
                    Log.e("카카오 연동해제", "연결 끊기 실패", throwable);
                } else {
                    Log.i("카카오 연동해제", "연결 끊기 성공. SDK에서 토큰 삭제 됨");
                }
                return null;
            });
        }
    }

    public class naver {

        private void revokeAccess() {
            new NidOAuthLogin().callDeleteTokenApi(getActivity(), new OAuthLoginCallback() {
                @Override
                public void onSuccess() {
                    //서버에서 토큰 삭제에 성공한 상태입니다.
                }
                @Override
                public void onFailure(int i, @NonNull String s) {
                    Log.d("네이버 연동해제", "errorCode: ${NaverIdLoginSDK.getLastErrorCode().code}");
                    Log.d("네이버 연동해제", "errorDesc: ${NaverIdLoginSDK.getLastErrorDescription()}");
                    getActivity().finish();
                    Toast.makeText(getActivity(), "Naver 계정의 연동이 해제되었습니다.", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onError(int i, @NonNull String s) {
                    onFailure(i, "Error Message: "+ s);
                }
            });
        }
    }

    public class google {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        // 구글 로그아웃
        private void signOut() {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            getActivity().finish();
                        }
                    });
        }

        //구글 연동해제
        private void revokeAccess() {
            mGoogleSignInClient.revokeAccess()
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            getActivity().finish();
                            Toast.makeText(getActivity(), "Google 계정의 연동이 해제되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}