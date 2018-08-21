package com.example.yuefan.presenter;

import com.avos.avoscloud.AVException;
import com.example.yuefan.model.IUserModel;
import com.example.yuefan.model.UserModel;
import com.example.yuefan.view.activity.ILoginActivity;

/**
 * Created by 67698 on 2018/8/21.
 */

public class LoginPresenter implements ILoginPresenter {
    IUserModel userModel=new UserModel();
    ILoginActivity iLoginActivity;

    public LoginPresenter(ILoginActivity iLoginActivity)
    {
        this.iLoginActivity=iLoginActivity;
    }

    @Override
    public void Login(String username, String password) {
        userModel.login(username, password, new UserModel.UserListener() {
            @Override
            public void onSuccess() {
                iLoginActivity.isLogin(true);
            }

            @Override
            public void onError(AVException e) {
                iLoginActivity.isLogin(false);
            }
        });
    }
}
