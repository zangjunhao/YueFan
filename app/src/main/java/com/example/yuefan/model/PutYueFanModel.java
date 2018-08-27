package com.example.yuefan.model;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.yuefan.presenter.PutYueFanPersenter;
import com.example.yuefan.tool.RealFilePath;
import com.example.yuefan.view.activity.PutYueFanActivity;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by 67698 on 2018/8/27.
 */

public class PutYueFanModel implements IPutModel {

    public interface PutYueFanListen {
        void onSuccess();

        void onError(AVException e);

    }

    @Override
    public void putYueFan(Context context, String title, String content, String name, double Latitude, double Longitude, List<Uri> list, final PutYueFanListen putYueFanListen) {
        AVObject todoFolder = new AVObject("YueFan");// 构建对象
        todoFolder.put("title", title);// 设置名称
        todoFolder.put("content",content );
        todoFolder.put("username", name);
        todoFolder.put("Latitude",Latitude);
        todoFolder.put("Longitude",Longitude);
        for(int i=0;i<list.size();i++)
        {
            try {
                AVFile avFile=AVFile.withAbsoluteLocalPath("yuefan.png", RealFilePath.getPath(context,list.get(i)));
                avFile.saveInBackground();
                todoFolder.put("image",avFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        todoFolder.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e==null)
                {
                    putYueFanListen.onSuccess();
                }
            }
        });

    }
}

