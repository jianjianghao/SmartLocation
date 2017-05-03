package com.jianjianghao.smartlocation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jianjianghao.smartlocation.advice.AdviceActivity;
import com.jianjianghao.smartlocation.device.DeviceActivity;
import com.jianjianghao.smartlocation.phone.PhoneActivity;
import com.jianjianghao.smartlocation.user.MyinfoActivity;

public class AccountActivity extends Activity {
    private LinearLayout mTabDevice;
    private LinearLayout mTabService;
    private LinearLayout mTabAccount;

    private ImageView mImageTabDevice;
    private ImageView mImageTabService;
    private ImageView mImageTabAccount;

    private TextView mTextTabDevice;
    private TextView mTextTabService;
    private TextView mTextTabAccount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mTabDevice = (LinearLayout) findViewById(R.id.mTabDevice);
        mTabService = (LinearLayout) findViewById(R.id.mTabService);
        mTabAccount = (LinearLayout) findViewById(R.id.mTabAccount);

        mImageTabDevice = (ImageView) findViewById(R.id.mImageTabDevice);
        mImageTabService = (ImageView) findViewById(R.id.mImageTabService);
        mImageTabAccount = (ImageView) findViewById(R.id.mImageTabAccount);

        mTextTabDevice = (TextView) findViewById(R.id.mTextTabDevice);
        mTextTabService = (TextView) findViewById(R.id.mTextTabService);
        mTextTabAccount = (TextView) findViewById(R.id.mTextTabAccount);





    }

    public void backHome(View view){ finish(); }
    public void showMyinfo(View view){ startActivity(new Intent(this, MyinfoActivity.class)); }
    public void showPhoneList(View view){ startActivity(new Intent(this, PhoneActivity.class)); }
    public void showAdvice(View view){ startActivity(new Intent(this, AdviceActivity.class)); }

    public void showMain(View view){ startActivity(new Intent(this, MainActivity.class)); }
    public void showDevice(View view){ startActivity(new Intent(this, DeviceActivity.class)); }
    public void showService(View view){ startActivity(new Intent(this, ServiceActivity.class)); }
    public void showAccount(View view){ startActivity(new Intent(this, AccountActivity.class)); }

}
