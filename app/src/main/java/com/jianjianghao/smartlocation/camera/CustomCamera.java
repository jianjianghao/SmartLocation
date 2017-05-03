package com.jianjianghao.smartlocation.camera;


import android.app.Activity;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.jianjianghao.smartlocation.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by Administrator on 2017/4/27.
 */

public class CustomCamera extends Activity implements SurfaceHolder.Callback{

    private Camera mCamera;
    private SurfaceView mPreview;
    private SurfaceHolder mHolder;
    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback(){
        public void onPictureTaken(byte[] data, Camera camera){
            File temFile = new File("/sdcard/temp.png");
            try{
                FileOutputStream fos= new FileOutputStream(temFile);
                fos.write(data);
                fos.close();
                Intent intent = new Intent(CustomCamera.this,ResultCamera.class);
                intent.putExtra("picPath", temFile.getAbsolutePath());
                startActivity(intent);
                CustomCamera.this.finish();
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_custom);
        mPreview = (SurfaceView) findViewById(R.id.preview);
        mHolder = mPreview.getHolder();
        mHolder.addCallback(this);
        mPreview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCamera.autoFocus(null);
            }
        });

    }

    public void capture(View view){
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setPreviewSize(800,400);
        parameters.setFlashMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.autoFocus(new Camera.AutoFocusCallback(){
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if(success){
                    mCamera.takePicture(null, null, mPictureCallback);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if( mCamera == null){
            mCamera = getCamera();
            if(mHolder != null){
                setStartPreview(mCamera, mHolder);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    //获取camera对象
    private Camera getCamera(){
        Camera camera;
        try{
            camera = Camera.open();
        }catch(Exception e){
            camera = null;
            e.printStackTrace();
        }
        return camera;
    }

    //开始预览相机内容
    private void setStartPreview(Camera camera, SurfaceHolder holder){
        try{
            camera.setPreviewDisplay(holder);
            //将系统camera预览角度进行调整
            camera.setDisplayOrientation(0);
            camera.startPreview();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    //释放相机资源
    private void releaseCamera(){
        if(mCamera != null){
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setStartPreview(mCamera, mHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();
        setStartPreview(mCamera, mHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }
}
