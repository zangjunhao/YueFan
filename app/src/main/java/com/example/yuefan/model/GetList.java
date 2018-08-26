package com.example.yuefan.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.List;

/**
 * Created by 67698 on 2018/8/22.
 */

public class GetList implements IGetList {


    @Override
    public void getYueFan(final GetListListener getListListener) {

        AVQuery<AVObject> query = new AVQuery<>("YueFan");
        query.whereExists("username");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
               if (e==null)
               {
                   getListListener.onSuccess(list);
               }
               else {
                   getListListener.onError(e);
               }
            }
        });


    }


    public interface GetListListener {
        void onSuccess(List<AVObject> list);

        void onError(AVException e);
    }


}
