package com.example.yuefan.presenter;

import android.support.design.widget.Snackbar;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.example.yuefan.model.GetList;
import com.example.yuefan.model.IGetList;
import com.example.yuefan.view.fragment.IYueFanFragment;

import java.util.Collections;
import java.util.List;

/**
 * Created by 67698 on 2018/8/22.
 */

public class YueFanPresenter implements IYueFanPresenter {

    IGetList getList=new GetList();
    IYueFanFragment yueFanFragment;
    public YueFanPresenter(IYueFanFragment fanFragment)
    {
        this.yueFanFragment=fanFragment;
    }

    @Override
    public void getLists() {
            getList.getYueFan(new GetList.GetListListener() {
                @Override
                public void onSuccess(List<AVObject> list) {
                    Collections.reverse(list);
                    yueFanFragment.getYueFan(list);

                }
                @Override
                public void onError(AVException e) {
                    Log.d("YueFanGetList", "onError: ");
                }
            });
    }


}
