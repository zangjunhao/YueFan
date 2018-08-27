package com.example.yuefan.presenter;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.example.yuefan.model.IPersonXiangxiModel;
import com.example.yuefan.model.PersonXiangxiModel;
import com.example.yuefan.view.activity.IPersonXiangxiActivity;

/**
 * Created by 67698 on 2018/8/27.
 */

public class PersonXiangxiPresenter implements IPersonXiangxiPresenter {

    IPersonXiangxiActivity personXiangxiActivity;
    IPersonXiangxiModel personXiangxiModel=new PersonXiangxiModel();
    public PersonXiangxiPresenter(IPersonXiangxiActivity personXiangxiActivity)
    {
        this.personXiangxiActivity=personXiangxiActivity;
    }

    @Override
    public void getPersonUser(String username) {
        personXiangxiModel.getPersonUser(new PersonXiangxiModel.PersonUserListen() {
            @Override
            public void onSuccess(AVUser avUser) {
                personXiangxiActivity.getUser(avUser);
            }

            @Override
            public void onError(AVException e) {
                Log.d("PersonXiangxiPresenter", "onError: "+e);
            }
        },username);
    }
}
