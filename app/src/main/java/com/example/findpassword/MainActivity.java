package com.example.findpassword;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Login login = new Login();


    EditText emailEdit;
    EditText passwordEdit;
    Button signbtn;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    CheckBox autoLogin;
    Boolean autoCheck;
    String idString;
    String passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo =
                connectivityManager.getActiveNetworkInfo();
        emailEdit = (EditText) findViewById(R.id.email);
        passwordEdit = (EditText) findViewById(R.id.password);
        signbtn = (Button) findViewById(R.id.email_sign_in_button);
        autoLogin = (CheckBox) findViewById(R.id.autoCheck);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        autoCheck = pref.getBoolean("autoCheck", false);
        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login.isOnline(networkInfo)) {
                    idString = emailEdit.getText().toString();
                    passwordString = passwordEdit.getText().toString();

                    if (login.setAutoLogin(idString, passwordString, autoLogin, editor, MainActivity.this)) {
                        Intent intent = new Intent(MainActivity.this, WebViewPage.class);
                        intent.putExtra("ID", idString);
                        intent.putExtra("PW", passwordString);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "연결확인", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (autoCheck) {
            if (login.getAutoLogin(idString, passwordString, MainActivity.this)) {
                emailEdit.setText(idString);
                passwordEdit.setText(passwordString);
                autoLogin.setChecked(true);
                if (login.isOnline(networkInfo)) {
                    Intent intent = new Intent(MainActivity.this, WebViewPage.class);
                    intent.putExtra("ID", idString);
                    intent.putExtra("PASSWORD", passwordString);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "연결확인", Toast.LENGTH_SHORT).show();
                }
            }


        }
    }
}

