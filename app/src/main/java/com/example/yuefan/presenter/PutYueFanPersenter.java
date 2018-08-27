package com.example.yuefan.presenter;

import android.content.Context;
import android.net.Uri;

import com.avos.avoscloud.AVException;
import com.example.yuefan.model.IPutModel;
import com.example.yuefan.model.PutYueFanModel;
import com.example.yuefan.view.activity.IPutYueFanActivity;

import java.util.List;

/**
 * Created by 67698 on 2018/8/27.
 */

public class PutYueFanPersenter implements IPutYueFanPresenter {

    IPutYueFanActivity putYueFanActivity;
    IPutModel putModel=new PutYueFanModel();
    public PutYueFanPersenter( IPutYueFanActivity putYueFanActivity)
    {
        this.putYueFanActivity=putYueFanActivity;
    }


    @Override
    public void putYueFan(Context context,String title, String content, String name, double Latitude, double Longitude, List<Uri> list) {
        putModel.putYueFan(context, title, content, name, Latitude, Longitude, list, new PutYueFanModel.PutYueFanListen() {
            @Override
            public void onSuccess() {
                putYueFanActivity.onSuccess();
            }

            @Override
            public void onError(AVException e) {
                putYueFanActivity.onError(e);
            }
        });
    }
}
