package com.example.yuefan.model;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
import com.example.yuefan.view.activity.PersonXiangxiActivity;
import com.example.yuefan.view.activity.PhotoActivity;

import java.util.List;

/**
 * Created by 67698 on 2018/8/27.
 */

public class PersonXiangxiModel implements IPersonXiangxiModel {


    public interface PersonUserListen {
        void onSuccess(AVUser avUser);

        void onError(AVException e);

    }

    @Override
    public void getPersonUser(final PersonUserListen personUserListen,String username) {
        final AVQuery<AVUser> userQuery = new AVQuery<>("_User");
        userQuery.whereStartsWith("username",username);
        userQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
              AVUser  avUser=list.get(0);
              if(e==null)
              {
                  personUserListen.onSuccess(avUser);
              }

              else personUserListen.onError(e);

            }
        });
    }
}
