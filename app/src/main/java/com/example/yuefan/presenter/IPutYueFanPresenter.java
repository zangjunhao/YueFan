package com.example.yuefan.presenter;

import android.content.Context;
import android.net.Uri;

import java.util.List;

/**
 * Created by 67698 on 2018/8/27.
 */

public interface IPutYueFanPresenter {
    void putYueFan(Context context,String title , String content, String name , double Latitude, double Longitude, List<Uri> list);
}
