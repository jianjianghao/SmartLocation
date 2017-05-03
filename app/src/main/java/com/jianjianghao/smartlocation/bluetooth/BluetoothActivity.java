package com.jianjianghao.smartlocation.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jianjianghao.smartlocation.AccountActivity;
import com.jianjianghao.smartlocation.MainActivity;
import com.jianjianghao.smartlocation.R;





/**
 * Created by Administrator on 2017/4/27.
 */

public class BluetoothActivity extends Activity {
    public static final String TAG="MainActivity";
    public static final int REQYEST_OPEN_BT=0x01;

    Button mBtnOpenBt;
    BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        //获取本地蓝牙的适配器
        mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        //判断蓝牙功能是否存在
        if(mBluetoothAdapter==null){
            showToast("该设备不支持蓝牙···");
            return;
        }
        //获取蓝牙名字 MAC地址
        String name = mBluetoothAdapter.getName();
        String mac = mBluetoothAdapter.getAddress();
        Log.e(TAG,"名字："+name+",mac:"+mac);
        //获取蓝牙当前状态
        int state = mBluetoothAdapter.getState();
        switch (state){
            case BluetoothAdapter.STATE_ON: //蓝牙已打开
                showToast("蓝牙已经打开");
                break;
            case BluetoothAdapter.STATE_OFF://蓝牙已关闭
                showToast("蓝牙已关闭");
                break;
            case BluetoothAdapter.STATE_TURNING_OFF://蓝牙正在关闭
                showToast("蓝牙正在关闭");
                break;
            case BluetoothAdapter.STATE_TURNING_ON://蓝牙正在打开
                showToast("蓝牙正在打开");
                break;
        }

        mBtnOpenBt=(Button) this.findViewById(R.id.btn_open_bt);
        mBtnOpenBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭--打开本地蓝牙设备
                //判断蓝牙功能是否已经打开
                if (mBluetoothAdapter.isEnabled()){
                    showToast("蓝牙已经处于打开状态···");
                    //关闭蓝牙
                    boolean isClose= mBluetoothAdapter.disable();
                    showToast("蓝牙是否关闭："+isClose);
                }else{
                    //蓝牙处于关闭状态，打开蓝牙
                //   boolean isOpen = mBluetoothAdapter.enable();
                    //  showToast("蓝牙的状态："+isOpen);
                    Intent open = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(open, REQYEST_OPEN_BT);
                }


            }
        });

    }

    public void showToast(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(REQYEST_OPEN_BT==requestCode){
            if(resultCode==RESULT_CANCELED){
                showToast("请求失败");
            }else{
                showToast("请求成功···");
            }
        }
    }

    public void showMain(View view){ finish(); }

}
