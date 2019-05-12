package com.example.findpassword;

import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.widget.CheckBox;
import android.widget.Toast;

public class Login {


    public boolean isNetworkConnected(NetworkInfo networkInfo) {
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }else {
            return false;
        }
    }

    public boolean setAutoLogin(String signId, String signPassword, CheckBox autoLogin, SharedPreferences.Editor editor, MainActivity mainActivity) {
        if (loginUnSuccess(signId,signPassword)) {
            if (autoLogin.isChecked()) {
                editor.putBoolean("autoLogin",true);
                editor.putString("id",signId);
                editor.putString("password",signPassword);
                editor.apply();
            }else {
                editor.clear();
                editor.apply();
            }
            return true;
        }else {
            Toast.makeText(mainActivity,"아이디 패스워드",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean loginUnSuccess(String signId, String signPassword) {
        if (!signId.isEmpty() && !signPassword.isEmpty()) {
            return true;
        }else {
            return false;
        }
    }
}
