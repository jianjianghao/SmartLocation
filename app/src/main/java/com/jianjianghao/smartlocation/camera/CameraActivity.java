package com.jianjianghao.smartlocation.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.jianjianghao.smartlocation.AccountActivity;
import com.jianjianghao.smartlocation.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Administrator on 2017/4/27.
 */

public class CameraActivity extends AccountActivity {
    private static int REQ_1 = 1;
    private static int REQ_2 = 2;
    private ImageView mImageView;
    private String mFilePath;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mImageView =(ImageView) findViewById(R.id.iv);
        mFilePath = Environment.getExternalStorageDirectory().getPath();
        mFilePath = mFilePath + "/" + "tmp.png";
    }

    public void backHome(View view){ finish();}

    public void openCamera1(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQ_1);
    }

    public void openCamera2(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoUri = Uri.fromFile(new File(mFilePath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, REQ_2);
    }

    public void customCamera(View vies){
        startActivity(new Intent(this, CustomCamera.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode== REQ_1){
                Bundle bundle = data.getExtras();
                Bitmap bitmap= (Bitmap) bundle.get("data");
                mImageView.setImageBitmap(bitmap);
            }else if(requestCode == REQ_2){
                FileInputStream fis = null;
                try{
                    fis = new FileInputStream(mFilePath);
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    mImageView.setImageBitmap(bitmap);
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }finally{
                    try{
                        fis.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
