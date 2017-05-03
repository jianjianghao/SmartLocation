package com.jianjianghao.smartlocation.device;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jianjianghao.smartlocation.R;

/**
 * Created by Administrator on 2017/5/2.
 */

public class DeviceShowActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_show);
    }



    public void backHome(View view){ finish();}
}
