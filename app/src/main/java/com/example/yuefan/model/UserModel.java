package com.example.yuefan.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;

/**
 * Created by 67698 on 2018/8/21.
 */

public class UserModel implements IUserModel {

    private static String onUsername;

    public interface UserListener {
        void onSuccess();

        void onError(AVException e);

    }
    public static  String getOnUsername()
    {
        return onUsername;
    }
    @Override
    public void login(final String username, String password, final UserListener userListener) {

        AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                userListener.onSuccess();
                onUsername=username;
                    AVIMClient client = AVIMClient.getInstance(avUser);
                    client.open(new AVIMClientCallback() {
                        @Override
                        public void done(final AVIMClient avimClient, AVIMException e) {
                            if (e == null) {
                                userListener.onSuccess();
                            } else {
                                userListener.onError(e);
                            }
                        }
                    });
                } else {
                userListener.onError(e);
                }

            }
        });
    }


    @Override
    public void register(String username, String password, String email, final UserListener userListener) {
        AVUser user = new AVUser();// 新建 AVUser 对象实例
        user.setUsername(username);// 设置用户名
        user.setPassword(password);// 设置密码
        user.setEmail(email);// 设置邮箱
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    userListener.onSuccess();
                } else {
                   userListener.onError(e);
                }
            }
        });
    }
}
