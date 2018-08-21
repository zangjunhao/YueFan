package com.example.yuefan.presenter;

import com.avos.avoscloud.AVException;
import com.example.yuefan.model.IUserModel;
import com.example.yuefan.model.UserModel;
import com.example.yuefan.view.activity.IRegisterActivity;

/**
 * Created by 67698 on 2018/8/21.
 */

public class RegisterPresenter implements IRegisterPresenter {

    IRegisterActivity registerActivity;
    IUserModel userModel= new UserModel();
    public RegisterPresenter(IRegisterActivity registerActivity)
    {
        this.registerActivity=registerActivity;
    }

    @Override
    public void register(String usename, String password, String email) {
            userModel.register(usename, password, email, new UserModel.UserListener() {
                @Override
                public void onSuccess() {
                    registerActivity.Success();
                }

                @Override
                public void onError(AVException e) {
                    registerActivity.error();
                }
            });
    }
}
