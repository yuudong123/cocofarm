package com.cocofarm.andapp.mypage;

import static com.cocofarm.andapp.common.CommonVal.loginMember;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cocofarm.andapp.common.CommonVal;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.FragmentMypageBinding;
import com.cocofarm.andapp.order.OrderProductListActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.kakao.sdk.user.UserApiClient;
import com.navercorp.nid.NaverIdLoginSDK;
import com.navercorp.nid.oauth.NidOAuthLogin;
import com.navercorp.nid.oauth.OAuthLoginCallback;

import java.util.HashMap;
import java.util.Map;

public class MypageFragment extends Fragment implements View.OnClickListener {

    FragmentMypageBinding binding;
    Map<TextView, Class<?>> activityMap = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMypageBinding.inflate(inflater, container, false);

        binding.tvNickname.setText(loginMember.getNickname());
        binding.tvEmail.setText(loginMember.getEmail());
        binding.tvSnsOut.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        if (loginMember.getSns().equals("KAKAO")) {
            binding.ivKakao.setVisibility(View.VISIBLE);
        } else if (loginMember.getSns().equals("NAVER")) {
            binding.ivNaver.setVisibility(View.VISIBLE);
        } else if (loginMember.getSns().equals("GOOGLE")) {
            binding.ivGoogle.setVisibility(View.VISIBLE);
        } else {
            binding.tvSnsOut.setVisibility(View.GONE);
        }

        binding.btnLogout.setOnClickListener(v -> {
            if (loginMember.getSns().equals("KAKAO")) {
                UserApiClient.getInstance().logout(throwable -> {
                    if (throwable != null) {
                        Log.e("카카오 로그아웃", "로그아웃 실패. SDK에서 토큰 삭제됨", throwable);
                    } else {
                        Log.i("카카오 로그아웃", "로그아웃 성공. SDK에서 토큰 삭제됨");
                    }
                    return null;
                });
            } else if (loginMember.getSns().equals("NAVER")) {
                NaverIdLoginSDK.INSTANCE.logout();
            } else if (loginMember.getSns().equals("GOOGLE")) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
                mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), task -> getActivity().finish());
            } else if (loginMember.getSns().equals("N")) {
                loginMember = null;
                Intent intent = getActivity().getIntent();
                getActivity().finish();
                startActivity(intent);
                CommonVal.isCheckLogout = true;
                Toast.makeText(getActivity(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "로그아웃에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

        binding.tvSnsOut.setOnClickListener(v -> {
            if (loginMember.getSns().equals("KAKAO")) {
                UserApiClient.getInstance().unlink(throwable -> {
                    if (throwable != null) {
                        Log.e("카카오 연동해제", "연결 끊기 실패", throwable);
                    } else {
                        Log.i("카카오 연동해제", "연결 끊기 성공. SDK에서 토큰 삭제 됨");
                        CommonConn conn = new CommonConn(getActivity(), "/member/snsout");
                        conn.addParam("email", loginMember.getEmail());
                        conn.onExcute((isResult, data) -> {
                            if(!isResult) {
                                return;
                            } else {
                                Toast.makeText(getActivity(), "연동이 해제되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    return null;
                });
            } else if (loginMember.getSns().equals("NAVER")) {
                new NidOAuthLogin().callDeleteTokenApi(getActivity(), new OAuthLoginCallback() {
                    @Override
                    public void onSuccess() {
                        //서버에서 토큰 삭제에 성공한 상태입니다
                    }

                    @Override
                    public void onFailure(int i, @NonNull String s) {
                        Log.d("네이버 연동해제", "errorCode: ${NaverIdLoginSDK.getLastErrorCode().code}");
                        Log.d("네이버 연동해제", "errorDesc: ${NaverIdLoginSDK.getLastErrorDescription()}");
                        getActivity().finish();
                    }

                    @Override
                    public void onError(int i, @NonNull String s) {
                        onFailure(i, "Error Message: " + s);
                    }
                });
            } else {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
                mGoogleSignInClient.revokeAccess().addOnCompleteListener(getActivity(), task -> {
                    getActivity().finish();
                    Toast.makeText(getActivity(), "Google 계정의 연동이 해제되었습니다.", Toast.LENGTH_SHORT).show();
                });
            }
        });

        activityMap.put(binding.tvAboutme, AboutMeActivity.class);
//        activityMap.put(binding.tvMyalert, AlertListActivity.class);
        activityMap.put(binding.tvMyorder, OrderProductListActivity.class);
        activityMap.put(binding.tvMyboard, MyBoardActivity.class);
        activityMap.put(binding.tvCscenter, CsCenterActivity.class);
        activityMap.put(binding.tvAway, AwayActivity.class);

        for (TextView textView : activityMap.keySet()) {
            textView.setOnClickListener(this);
        }
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        Class<?> targetActivityClass = activityMap.get(v);
        if (targetActivityClass != null) {
            Intent intent = new Intent(getActivity(), targetActivityClass);
            startActivity(intent);
        }
    }
}