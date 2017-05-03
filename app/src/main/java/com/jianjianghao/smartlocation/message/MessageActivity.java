package com.jianjianghao.smartlocation.message;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jianjianghao.smartlocation.R;



public class MessageActivity extends Activity {
    private EditText phoneNo;
    private EditText message;
    private Button sendSMS;
    public static final String ACTION_SEND_MSG ="ACTION_SEND_MSG";
    public static final String ACTION_DELIVER_MSG ="ACTION_DELIVER_MSG";
    private PendingIntent mSendPi;
    private PendingIntent mDeliverPi;
    private BroadcastReceiver mSendBroadcastReceiver;
    private BroadcastReceiver mDeliverBroadcastReceiver;
    private SMSbiz mSMSBiz = new SMSbiz();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        phoneNo =(EditText) findViewById(R.id.txtPhoneNo);
        message =(EditText)findViewById(R.id.txtMessage);
        sendSMS =(Button) findViewById(R.id.btnSendSMS);

        initRecivers();

        sendSMS.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String telNum = phoneNo.getText().toString();
                String mesgCont = message.getText().toString();

                if(telNum.length()==0)
                {
                    Toast.makeText(MessageActivity.this,"请填写联系人",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mesgCont))
                {
                    Toast.makeText(MessageActivity.this,"请填写短信内容",Toast.LENGTH_SHORT).show();
                    return;
                }

                mSMSBiz.sendMsg(telNum,mesgCont,mSendPi,mDeliverPi);

            }


        });



    }

    private void initRecivers() {
        Intent sendTntent = new Intent(ACTION_SEND_MSG);
        mSendPi = PendingIntent.getBroadcast(this,0,sendTntent,0);
        Intent deliverTntent = new Intent(ACTION_DELIVER_MSG);
        mDeliverPi = PendingIntent.getBroadcast(this,0,deliverTntent,0);

        registerReceiver(mSendBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(getResultCode()== RESULT_OK)
                {
                    Log.e("TAG","短信发送成功");
                }
                else
                {
                    Log.e("TAG","短信发送失败");
                }
            }
        },new IntentFilter(ACTION_SEND_MSG));

        registerReceiver(mDeliverBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("TAG","联系人成功接收到短信");
            }
        },new IntentFilter(ACTION_DELIVER_MSG));

    }

    protected void onDestroy()
    {
        super.onDestroy();;
        unregisterReceiver(mSendBroadcastReceiver);
        unregisterReceiver(mDeliverBroadcastReceiver);
    }

    public void backHome(View view){ finish();}

}
