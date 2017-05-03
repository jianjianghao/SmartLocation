package com.jianjianghao.smartlocation.camera;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.widget.ImageView;

import com.jianjianghao.smartlocation.R;



/**
 * Created by Administrator on 2017/4/27.
 */

public class ResultCamera extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_result);
        String path = getIntent().getStringExtra("picPath");
        ImageView imageView= (ImageView)findViewById(R.id.pic);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        imageView.setImageBitmap(bitmap);
    }
}
