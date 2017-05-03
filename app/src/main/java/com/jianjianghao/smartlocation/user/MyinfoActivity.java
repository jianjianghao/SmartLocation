package com.jianjianghao.smartlocation.user;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import android.widget.ListView;
import android.widget.SimpleAdapter;


import com.jianjianghao.smartlocation.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MyinfoActivity extends Activity {
    private ListView list = null;

    private String[] rowName = {  "昵称", "性别", "年龄", "生日","所在地", "邮箱", "手机号", "个性签名" };

    private String[] rowCont = {  "乐趣", "男", "25", "1月13日","浙江杭州", "12345@qq.com", "188888888", "哈哈哈" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        list = (ListView) findViewById(R.id.view_myInfo);
        //组织数据源
        List<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        for(int i=0;i<rowName.length;i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("name", rowName[i]);
            map.put("cont", rowCont[i]);
            mylist.add(map);
        }
        //配置适配器
        SimpleAdapter adapter = new SimpleAdapter(this,
                mylist,//数据源
                R.layout.myinfo_row,//显示布局
                new String[] {"name", "cont"}, //数据源的属性字段
                new int[] {R.id.row_name,R.id.row_text}); //布局里的控件id
        //添加并且显示
        list.setAdapter(adapter);
    }

    public void backHome(View view){ finish(); }
}
