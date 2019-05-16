package com.example.findpassword;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewPageSetting {
    WebView webView;

    public WebViewPageSetting(WebView webView) {
        this.webView = webView;
    }

    protected void setWebSettings() {

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

    }

    protected void setWebViewClient(final Activity activity){

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;

            }

        });

    }
        protected void setWebChromeClient () {

            webView.setWebChromeClient(new WebChromeClient() {

                @Override
                public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {

                    new AlertDialog.Builder(view.getContext())
                            .setTitle("알림")
                            .setMessage(message)
                            .setPositiveButton(android.R.string.ok,
                                    new AlertDialog.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            result.confirm();
                                        }
                                    })
                            .setCancelable(false)
                            .create()
                            .show();

                    return true;

                }

                @Override
                public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {

                    new AlertDialog.Builder(view.getContext())
                            .setTitle("확인")
                            .setMessage(message)
                            .setPositiveButton("예",
                                    new AlertDialog.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            result.confirm();
                                        }
                                    })
                            .setNegativeButton("아니오",
                                    new AlertDialog.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            result.cancel();
                                        }
                                    })
                            .setCancelable(false)
                            .create()
                            .show();

                    return true;
                }
            });
        }
    }