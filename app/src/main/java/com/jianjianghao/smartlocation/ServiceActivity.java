package com.jianjianghao.smartlocation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jianjianghao.smartlocation.amap.GeoFence_Activity;
import com.jianjianghao.smartlocation.amap.LastLocation_Activity;
import com.jianjianghao.smartlocation.amap.Location_Activity;
import com.jianjianghao.smartlocation.amap.StartActivity;
import com.jianjianghao.smartlocation.camera.CameraActivity;
import com.jianjianghao.smartlocation.device.Device;
import com.jianjianghao.smartlocation.device.DeviceActivity;
import com.jianjianghao.smartlocation.device.DeviceDbAdapter;
import com.jianjianghao.smartlocation.device.DeviceSimpleCursorAdapter;
import com.jianjianghao.smartlocation.message.MessageActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceActivity extends Activity {
    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;

    private Spinner mListView;
    private DeviceDbAdapter mDbAdapter;
    private DeviceSimpleCursorAdapter mCursorAdapter;
    private Integer sumDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        mDbAdapter = new DeviceDbAdapter(this);
        mDbAdapter.open();
        Cursor cursor = mDbAdapter.fetchAllDevices();
        sumDevice = cursor.getCount();
        spinner = (Spinner) findViewById(R.id.device_spinner);
        //数据
        data_list = new ArrayList<String>();
        for (int i = 0; i < sumDevice; i++) {
            int nameColumnIndex = cursor.getColumnIndex(DeviceDbAdapter.COL_NAME);
            String name = cursor.getString(nameColumnIndex);
            data_list.add(name);
            cursor.moveToNext();
        }


        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);

    }

    public void backHome(View view){ finish(); }
    public void showCamera(View view){ startActivity(new Intent(this, CameraActivity.class)); }
    public void showMessage(View view){ startActivity(new Intent(this, MessageActivity.class)); }
    public void showSSDW(View view){ startActivity(new Intent(this, Location_Activity.class)); }
    public void showDZWL(View view){ startActivity(new Intent(this, GeoFence_Activity.class)); }

    public void showZHWZ(View view){ startActivity(new Intent(this, LastLocation_Activity.class)); }


    public void showMain(View view){ startActivity(new Intent(this, MainActivity.class)); }
    public void showDevice(View view){ startActivity(new Intent(this, DeviceActivity.class)); }
    public void showService(View view){ startActivity(new Intent(this, ServiceActivity.class)); }
    public void showAccount(View view){ startActivity(new Intent(this, AccountActivity.class)); }

}
