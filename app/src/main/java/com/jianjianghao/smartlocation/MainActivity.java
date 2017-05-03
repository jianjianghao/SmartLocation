package com.jianjianghao.smartlocation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.jianjianghao.smartlocation.advice.AdviceActivity;
import com.jianjianghao.smartlocation.amap.GeoFence_Activity;
import com.jianjianghao.smartlocation.amap.Location_Activity;
import com.jianjianghao.smartlocation.camera.CameraActivity;
import com.jianjianghao.smartlocation.device.DeviceActivity;
import com.jianjianghao.smartlocation.message.MessageActivity;
import com.jianjianghao.smartlocation.phone.PhoneActivity;
import com.jianjianghao.smartlocation.user.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private GridView gview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    private int[] icon = {
            R.drawable.ic_my_location_black_24dp, R.drawable.ic_transform_black_24dp,
            R.drawable.ic_forum_black_24dp, R.drawable.ic_camera_alt_black_24dp, R.drawable.ic_devices_black_24dp,
            R.drawable.ic_assignment_black_24dp, R.drawable.ic_contact_mail_black_24dp, R.drawable.ic_border_color_black_24dp };
    private String[] iconName = {  "设备定位", "电子围栏", "即时通讯", "拍照摄影","添加设备", "联系人", "账户登录", "意见反馈" };


       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

           gview = (GridView) findViewById(R.id.gridView1);
           data_list = new ArrayList<Map<String, Object>>();
           getData();
           String[] from = { "image", "text" };
           int[] to = { R.id.image, R.id.text };
           sim_adapter = new SimpleAdapter(this, data_list, R.layout.main_item, from,to);
           gview.setAdapter(sim_adapter);
           gview.setOnItemClickListener(new ItemClickListener());
    }


    public List<Map<String, Object>> getData() {
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }
        return data_list;
    }

    class ItemClickListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
            // 显示所选Item的ItemText
            setTitle((String) item.get("text"));// the item is map,you can
            switch(arg2){
                case 0:
                    startActivity(new Intent(MainActivity.this, Location_Activity.class));
                    break;
                case 1:
                    startActivity(new Intent(MainActivity.this, GeoFence_Activity.class));
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, MessageActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(MainActivity.this, CameraActivity.class));
                    break;
                case 4:
                    startActivity(new Intent(MainActivity.this, DeviceActivity.class));
                    break;
                case 5:
                    startActivity(new Intent(MainActivity.this, PhoneActivity.class));
                    break;
                case 6:
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    break;
                case 7:
                    startActivity(new Intent(MainActivity.this, AdviceActivity.class));
                    break;
            }
        }
    }








    public void showMain(View view){ startActivity(new Intent(this, MainActivity.class)); }
    public void showDevice(View view){ startActivity(new Intent(this, DeviceActivity.class)); }
    public void showService(View view){ startActivity(new Intent(this, ServiceActivity.class)); }
    public void showAccount(View view){ startActivity(new Intent(this, AccountActivity.class)); }



}
