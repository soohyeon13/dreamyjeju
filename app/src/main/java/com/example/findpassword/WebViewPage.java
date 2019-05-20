package com.example.findpassword;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class WebViewPage extends AppCompatActivity {

    private WebView myWebView;
    private Button attendCodeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webviewpage);
        myWebView = findViewById(R.id.webView);

        String id = getIntent().getStringExtra("ID");
        String password = getIntent().getStringExtra("PW");
        String url_Home = "https://elearning.jejunu.ac.kr/MMain.do?cmd=viewIndexPage";
        String url_Login = "https://elearning.jejunu.ac.kr/MUser.do";
        String url_Destination = "https://elearning.jejunu.ac.kr/MSmartatt.do?cmd=viewAttendCourseList";
        String host = "elearning.jejunu.ac.kr";
        String referer = "http://elearning.jejunu.ac.kr/MMain.do?cmd=viewIndexPage";
        String origin = "http://elearning.jejunu.ac.kr";
        String body = "cmd=loginUser&userDTO.userId=" + id + "&userDTO.password=" + password + "&userDTO.localeKey=ko";

        WebViewPageSetting webViewPageSetting = new WebViewPageSetting(myWebView);
        webViewPageSetting.setWebSettings();
        webViewPageSetting.setWebChromeClient();
        webViewPageSetting.setWebViewClient(this);

        RequestHttpsUrlConnection requestHttpsUrlConnection = new RequestHttpsUrlConnection(myWebView,WebViewPage.this);
        requestHttpsUrlConnection.setUrl_Home(url_Home);
        requestHttpsUrlConnection.setUrl_Login(url_Login);
        requestHttpsUrlConnection.setUrl_Destination(url_Destination);
        requestHttpsUrlConnection.setHost(host);
        requestHttpsUrlConnection.setReferer(referer);
        requestHttpsUrlConnection.setOrigin(origin);
        requestHttpsUrlConnection.setBody(body);
        requestHttpsUrlConnection.execute(id,password);

        attendCodeBtn = (Button)findViewById(R.id.attPathCode);
        attendCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (myWebView.canGoBack()) {

                myWebView.goBack();
                return false;

            }

        }

        return super.onKeyDown(keyCode, event);

    }
}