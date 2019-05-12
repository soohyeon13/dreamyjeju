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

    private EditText emailEdit;
    private EditText passwordEdit;
    private Button signbtn;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox autoLogin;
    private boolean autoCheck;
    private String signId;
    private String signpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Login login = new Login();

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

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (login.isNetworkConnected(networkInfo)) {
                    signId = emailEdit.getText().toString();
                    signpassword = passwordEdit.getText().toString();
                    if (login.setAutoLogin(signId, signpassword, autoLogin, editor, MainActivity.this)) {
                        Intent intent = new Intent(MainActivity.this, WebViewPage.class);
                        intent.putExtra("id", signId);
                        intent.putExtra("password", signpassword);
                        startActivity(intent);
                    }
                }else {
                    Toast.makeText(MainActivity.this,"네트워크 상태 체크",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

