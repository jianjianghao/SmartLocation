package com.jianjianghao.smartlocation.advice;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jianjianghao.smartlocation.R;

/**
 * Created by Administrator on 2017/4/30.
 */

public class AdviceActivity extends Activity {
    private EditText contAdvice;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);

        contAdvice =(EditText) findViewById(R.id.txtAdvice);
        Button btnSub = (Button) findViewById(R.id.btnSendAdvice);

        btnSub.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String cont = contAdvice.getText().toString();
                if(TextUtils.isEmpty(cont)){
                    showToast("请输入反馈内容");
                    return;
                }else{
                    showToast("谢谢您的意见，工作人员会尽快回复");
                }

            }
        });

    }


    public void showToast(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        toast.show();
    }

    public void backHome(View view){ finish();}

}
