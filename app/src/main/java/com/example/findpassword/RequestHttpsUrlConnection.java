package com.example.findpassword;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class RequestHttpsUrlConnection {

    private final WebView myWebView;
    private final Activity activity;
    private String url_Home,url_Login,url_Destination,host,referer,origin,body;

    public RequestHttpsUrlConnection(WebView webView, Activity activity) {
        myWebView = webView;
        this.activity = activity;
    }

    public void setUrl_Home(String url_home){this.url_Home = url_home;}
    public void setUrl_Login(String url_login){this.url_Login=url_login;}
    public void setUrl_Destination(String url_destination){this.url_Destination = url_destination;}
    public void setHost(String host){this.host=host;}
    public void setReferer(String referer){this.referer = referer;}
    public void setOrigin(String origin){this.origin =origin;}
    public void setBody(String body){this.body=body;}

    public void execute(String id, String password) {
        HttpsAsynTask httpsAsynTask = new HttpsAsynTask();
        httpsAsynTask.execute(id,password);
    }

    private class HttpsAsynTask extends AsyncTask<String,Void,Void> {

        private HttpURLConnection conn;
        private List<String> cookies;

        @Override
        protected void  onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            Log.d("ID","ID :" + strings[0]);
            Log.d("Password","Password :" + strings[1]);
            try {
                URL url = new URL(url_Home);
                TrustAllHost trustAllHost = new TrustAllHost();
                trustAllHost.httpsUrlConnection();
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });

                conn.setRequestMethod("POST");
                conn.setDoOutput(true);//write mode
                conn.setDoInput(true);//read mode
                conn.setUseCaches(false);
                conn.setDefaultUseCaches(false);

                cookies = conn.getHeaderFields().get("Set-Cookie");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                conn.disconnect();
            }

            try {
                URL url = new URL(url_Login);
                TrustAllHost trustAllHost = new TrustAllHost();
                trustAllHost.httpsUrlConnection();
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                conn.setDefaultUseCaches(false);

                RequestHeader();

                OutputStream os = conn.getOutputStream();
                os.write(body.getBytes("UTF-8"));
                os.flush();
                os.close();

                if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK){
                    return null;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                conn.disconnect();
            }
            return null;
        }


        protected void onPostExecute(Void aVoid) {
            if (LoginSuccess()) {

                CookieSyncManager.createInstance(myWebView.getContext());
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptCookie(true);
                cookieManager.removeAllCookie();

                for (String cookie :cookies) {
                    cookieManager.setCookie(url_Destination,cookie);
                }

                CookieSyncManager.getInstance().sync();
                myWebView.loadUrl(url_Destination);
            }else{
                AlertDialog dialog = null;
                String loginFailedMassege = "로그인에 실패하셨습니다. \n다시 로그인 해주세요.";

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(loginFailedMassege);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { activity.finish(); }

                });

                dialog = builder.create();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

            }
        }

        private boolean LoginSuccess() {
            String loginCheck = conn.getHeaderField(2);
            return loginCheck.equals("text/html;charset=UTF-8");
        }

        private String Cookies(List<String>cookies){
        String cookie ="";

        if (cookie != null) {
            for (String temp_cookie : cookies) {
                cookie += temp_cookie.split(";\\s*")[0] + "; ";
                Log.d("@COOKIE", temp_cookie.split(";\\s*")[0]);
            }

            cookie = cookie.substring(0,cookie.length()-1);
        }

        return cookie;
    }
    private void RequestHeader() {
        String cookie = Cookies(cookies);

        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        conn.setRequestProperty("Accept-Encoding", "gzip,deflate,br");
        conn.setRequestProperty("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
        conn.setRequestProperty("Cache-Control", "max-age=0");
        conn.setRequestProperty("Cookie", cookie);
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Host", host);
        conn.setRequestProperty("Origin", origin);
        conn.setRequestProperty("Referer", referer);
        conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
        conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) "
                        + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Mobile Safari/537.36");

        }
    }
}
