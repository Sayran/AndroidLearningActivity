package com.example.andrejssileckis.fragmenttestactivity;

/**
 * Created by andrejs.sileckis on 10/19/2015.
 */
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.ConnectException;

public class BrowserUsageActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser);
        Uri url = getIntent().getData();
        WebView webView = (WebView) findViewById(R.id.WebView01);
        webView.setWebViewClient(new CaLLBack());
        webView.loadUrl(url.toString());
    }

    private class CaLLBack extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,String url){
            return (false);
        }
    }
}
