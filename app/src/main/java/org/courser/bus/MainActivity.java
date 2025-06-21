package org.courser.bus;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

        // 设置 WebChromeClient 以处理定位权限请求
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                // 检查是否已授予定位权限
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    // 允许网页使用定位
                    callback.invoke(origin, true, false);
                } else {
                    // 请求定位权限
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            LOCATION_PERMISSION_REQUEST_CODE);
                }
            }
        });

        // 加载网页
        webView.loadUrl("https://web.chelaile.net.cn/ch5/index.html?src=webapp_meizu_map");
    }

    // 处理权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限已授予，重新加载网页以启用定位功能
                webView.reload();
            } else {
                // 权限被拒绝，提示用户
                Toast.makeText(this, "定位权限被拒绝，部分功能可能无法使用", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
