package com.example.yuefan.view.activity;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by 67698 on 2018/8/20.
 */
public class MyLeanCloudApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        // 启用北美节点, 需要在 initialize 之前调用
//        AVOSCloud.useAVCloudUS();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"VpMWcDwesJSsvpGz42p5cecD-gzGzoHsz","furwvgAg6FPHHTw9EQFNKD6u");
    }
}
