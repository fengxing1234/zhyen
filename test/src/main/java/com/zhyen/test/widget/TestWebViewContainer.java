package com.zhyen.test.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zhyen.test.R;

public class TestWebViewContainer extends FrameLayout {

    private static final String TAG = "TestWebViewContainer";
    private String text;

    public TestWebViewContainer(@NonNull Context context) {
        super(context);
    }

    public TestWebViewContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TestWebViewContainer);
        text = ta.getText(R.styleable.TestWebViewContainer_loadUrl).toString();
        ta.recycle();
        Log.d(TAG, "init: " + text);
    }

    public TestWebViewContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow: ");
        // TODO: 2019-10-07 WebView 代码重构
        // TODO: 2019-10-07 WebView 显示图片 失败
        //"Mixed Content: The page at 'https://hencoder.com/ui-1-8/' was loaded over HTTPS, but requested an insecure image 'http://ws3.sinaimg.cn/large/006tKfTcly1fjmygtwiutj30nb0o2ta8.jpg'. This content should also be served over HTTPS.", source: https://hencoder.com/ui-1-8/ (221)
        WebView webView = findViewById(R.id.web_view);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");//文本编码
        settings.setDomStorageEnabled(true);//设置DOM存储已启用（注释后文本显示变成js代码）
        // 设置可以支持缩放
        settings.setSupportZoom(false);
        // 设置出现缩放工具
        settings.setBuiltInZoomControls(false);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //webView.loadUrl("file:///android_asset/HardwareAccelerated.html");
        webView.loadUrl(text);
    }
}
