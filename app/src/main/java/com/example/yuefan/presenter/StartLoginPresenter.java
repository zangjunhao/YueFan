package com.example.yuefan.presenter;

import com.avos.avoscloud.AVAnonymousUtils;
import com.avos.avoscloud.AVUser;
import com.example.yuefan.view.activity.IStartActivity;

/**
 * Created by 67698 on 2018/8/21.
 */

public class StartLoginPresenter implements IStartLoginPresenter {

    IStartActivity startActivity;

    public StartLoginPresenter(IStartActivity startActivity)
    {
        this.startActivity=startActivity;
    }

    @Override
    public void autoLogin() {
        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser != null&&! AVAnonymousUtils.isLinked(currentUser)) {
            startActivity.Success();
        } else {
            startActivity.toLogin();
        }
    }
}
