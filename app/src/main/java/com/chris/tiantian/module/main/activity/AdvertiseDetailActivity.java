package com.chris.tiantian.module.main.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.anima.componentlib.toolbar.Toolbar;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.ImageDetail;
import com.chris.tiantian.view.MultipleStatusView;

public class AdvertiseDetailActivity extends AppCompatActivity {
    public static final String ADVERTISE_DETAIL = "ADVERTISE_DETAIL";
    private MultipleStatusView statusView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_advertise_detail);
        ImageDetail advertise = getIntent().getParcelableExtra(ADVERTISE_DETAIL);

        statusView = findViewById(R.id.adDetailAct_status_view);
        statusView.showLoading();
        Toolbar toolbar = findViewById(R.id.activity_toolBar);
        WebView webView = findViewById(R.id.advertise_image);

        toolbar.setTitle(advertise.getTitle());
        statusView.showLoading();
        webView.getSettings().setSupportZoom(true);//缩放
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);//不显示控制器
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl(advertise.getDetail());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 加载完成
                statusView.showContent();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
                // 加载开始
                statusView.showLoading();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (request.isForMainFrame()) {//是否是为 main frame创建
                    view.loadUrl("about:blank");// 避免出现默认的错误界面
                    Toast.makeText(AdvertiseDetailActivity.this, "显示出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                // 这个方法在 android 6.0才出现
                int statusCode = errorResponse.getStatusCode();
                if (404 == statusCode || 500 == statusCode) {
                    view.loadUrl("about:blank");// 避免出现默认的错误界面
                    Toast.makeText(AdvertiseDetailActivity.this, "显示出错", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
