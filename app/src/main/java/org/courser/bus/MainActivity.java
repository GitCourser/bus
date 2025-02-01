package org.courser.bus;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1; // 定义权限请求代码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 启用 JavaScript
        webSettings.setDomStorageEnabled(true); // 启用 DOM 存储（localStorage）
        // webSettings.setDatabaseEnabled(true);   // 启用数据库存储（IndexedDB）
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // 启用缓存
        webSettings.setGeolocationEnabled(true); // 启用定位功能

        // 加载网页
        webView.loadUrl("https://web.chelaile.net.cn/ch5/index.html?src=webapp_meizu_map");
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true); // 将应用退到后台
    }
}
