package com.example.yuefan.model;

import android.content.Context;
import android.net.Uri;

import com.example.yuefan.presenter.PutYueFanPersenter;

import java.util.List;

/**
 * Created by 67698 on 2018/8/27.
 */

public interface IPutModel {
    void putYueFan(Context context,String title, String content, String name, double Latitude, double Longitude, List<Uri> list, PutYueFanModel.PutYueFanListen putYueFanListen);
}
