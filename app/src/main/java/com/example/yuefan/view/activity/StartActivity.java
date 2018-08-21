package com.example.yuefan.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
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
        MyService.setStatusBar(this);
        AVOSCloud.initialize(this,"VpMWcDwesJSsvpGz42p5cecD-gzGzoHsz","furwvgAg6FPHHTw9EQFNKD6u");
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
