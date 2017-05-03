package com.jianjianghao.smartlocation.user;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jianjianghao.smartlocation.R;


public class LoginActivity extends Activity {
    private EditText telTV;
    private EditText pwdTV;
    private LinearLayout loginLay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




    }

    public void backHome(View view){ finish(); }
}
