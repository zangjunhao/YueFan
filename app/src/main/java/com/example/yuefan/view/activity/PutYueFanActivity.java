package com.example.yuefan.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.example.yuefan.R;
import com.example.yuefan.tool.MyService;
import com.example.yuefan.tool.RealFilePath;
import com.example.yuefan.view.adapter.PutPhotoAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

public class PutYueFanActivity extends AppCompatActivity implements IAddPhoto{

    PutPhotoAdapter putPhotoAdapter;
    EditText title;
    EditText content;
    double Latitude;
    double Longitude;
    List<Uri> list=new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_putyuefan);
        MyService.setStatusBar(this,"#fff9fa9b");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && this.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        }
        initview();
    }


    private void initview()
    {
        putPhotoAdapter=new PutPhotoAdapter(list,this,this);
        recyclerView= findViewById(R.id.put_rec);
        recyclerView.setAdapter(putPhotoAdapter);
        title=findViewById(R.id.put_title);
        content=findViewById(R.id.put_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button button=findViewById(R.id.put_yuefan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVObject todoFolder = new AVObject("YueFan");// 构建对象
        todoFolder.put("title", title.getText().toString());// 设置名称
        todoFolder.put("content",content.getText().toString() );
        todoFolder.put("username", AVUser.getCurrentUser().getUsername());
        todoFolder.put("Latitude",Latitude);
        todoFolder.put("Longitude",Longitude);
        for(int i=0;i<list.size();i++)
        {
            try {
                AVFile avFile=AVFile.withAbsoluteLocalPath("yuefan.png",RealFilePath.getPath(PutYueFanActivity.this,list.get(i)));
                avFile.saveInBackground();
                todoFolder.put("image",avFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        todoFolder.saveInBackground();
                Toast.makeText(PutYueFanActivity.this,"已发布约饭",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        AMapLocationClient mLocationClient = null;
        AMapLocationListener mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        Latitude =aMapLocation.getLatitude();
                        Longitude=aMapLocation.getLongitude();
                        Log.d("马飞飞", "onLocationChanged: "+aMapLocation.getLatitude()+"  "+aMapLocation.getLongitude());
                    }else {
                        Log.e("AmapError","location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        };
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationClient.setLocationListener(mLocationListener);
        AMapLocationClientOption mLocationOption = null;
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setInterval(1000);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==23&&resultCode==RESULT_OK)
        {
         list=Matisse.obtainResult(data);
         putPhotoAdapter=new PutPhotoAdapter(list,this,this);
            recyclerView.setAdapter(putPhotoAdapter);
        }
    }


    @Override
    public void addPhoto() {
        Matisse
                .from(PutYueFanActivity.this)
                .choose(MimeType.allOf())//照片视频全部显示
                .countable(true)//有序选择图片
                .maxSelectable(1)//最大选择数量为1
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))//图片显示表格的大小getResources()
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)//图像选择和预览活动所需的方向。
                .thumbnailScale(0.85f)//缩放比例
                .theme(R.style.Matisse_Zhihu)//主题  暗色主题 R.style.Matisse_Dracula
                .imageEngine(new GlideEngine())//加载方式
                .forResult(23);//请求码
    }
}
