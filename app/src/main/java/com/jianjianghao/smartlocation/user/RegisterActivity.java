package com.jianjianghao.smartlocation.user;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jianjianghao.smartlocation.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;


public class RegisterActivity extends Activity {
    private EditText telTV;
    private EditText pwdTV;
    private LinearLayout registerLay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        telTV =(EditText) findViewById(R.id.telTV);
        pwdTV =(EditText)findViewById(R.id.pwdTV);
        registerLay =(LinearLayout)findViewById(R.id.registerLay);

        registerLay.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                String tel = telTV.getText().toString();
                String pwd = pwdTV.getText().toString();

                if(TextUtils.isEmpty(tel)){
                    Toast.makeText(getApplicationContext(),"手机号不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pwd)){
                    Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }





            }
        });
    }

    public void backHome(View view){ finish(); }
}
