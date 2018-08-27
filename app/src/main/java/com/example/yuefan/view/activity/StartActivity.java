package com.example.yuefan.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.example.yuefan.R;
import com.example.yuefan.presenter.IStartLoginPresenter;
import com.example.yuefan.presenter.StartLoginPresenter;
import com.example.yuefan.tool.MyService;

public class StartActivity extends AppCompatActivity implements IStartActivity{
    IStartLoginPresenter iStartLoginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        MyService.setStatusBar(this,"#6600541a");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && this.checkSelfPermission(
//                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            this.requestPermissions(new String[]{
//                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.READ_PHONE_STATE,
//            }, 1);
//        }
        AVOSCloud.initialize(this,"VpMWcDwesJSsvpGz42p5cecD-gzGzoHsz","furwvgAg6FPHHTw9EQFNKD6u");
        AVIMClient.setUnreadNotificationEnabled(true);
//        AVObject todoFolder = new AVObject("YueFan");// 构建对象
//        todoFolder.put("title", "妹子约饭了");// 设置名称
//        todoFolder.put("content","快来跟妹纸约饭吧" );
//        todoFolder.put("username",AVUser.getCurrentUser().getUsername());
//        todoFolder.saveInBackground();// 保存到服务端
        iStartLoginPresenter=new StartLoginPresenter(this);
        autoLogin();
    }

    @Override
    public void autoLogin() {
        iStartLoginPresenter.autoLogin();
    }

    @Override
    public void Success() {
        final Intent intent=new Intent(StartActivity.this,MainActivity.class);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(intent);
                            finish();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Toast.makeText(this,"没有权限无法正常工作",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toLogin() {
        final Intent intent=new Intent(StartActivity.this,LoginActivity.class);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(intent);
                            finish();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
