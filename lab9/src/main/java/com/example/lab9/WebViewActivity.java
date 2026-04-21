package com.example.lab9;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String link = getIntent().getStringExtra("link");

        WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient()); // deschide in app
        webView.loadUrl(link);
    }
}