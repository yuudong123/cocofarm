package com.cocofarm.andapp.mypage;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.cocofarm.andapp.conn.Service;
import com.cocofarm.andapp.databinding.ActivityRoadSearchBinding;

public class RoadSearchActivity extends AppCompatActivity {
    ActivityRoadSearchBinding binding;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoadSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init_webView();
    }

    public void init_webView() {
        // WebView 설정
        webView = binding.daumWebview;

        // JavaScript 허용
        webView.getSettings().setJavaScriptEnabled(true);

        // JavaScript의 window.open 허용
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        webView.addJavascriptInterface(new AndroidBridge(), "Cocofarm");

        //DOMStorage 허용
        webView.getSettings().setDomStorageEnabled(true);

        //ssl 인증이 없는 경우 해결을 위한 부분
        webView.setWebChromeClient(new WebChromeClient() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                request.grant(request.getResources());
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // SSL 에러가 발생해도 계속 진행
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            // 페이지 로딩 시작시 호출
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.e("페이지 시작", url);
                //   binding.webProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //binding.webProgress.setVisibility(View.GONE);

                Log.e("페이지 로딩", url);
                webView.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });
        // webview url load. php or html 파일 주소
        webView.loadUrl(Service.getApiClient().baseUrl() + "roadSearch");
    }

    class AndroidBridge {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processDATA(String roadAdd) {
            new Handler().post(() -> {
                Log.d("주소", "run: " + roadAdd);
                Intent intent = new Intent();
                intent.putExtra("address", roadAdd);
                setResult(RESULT_OK, intent);
                finish();
            });
        }
    }
}