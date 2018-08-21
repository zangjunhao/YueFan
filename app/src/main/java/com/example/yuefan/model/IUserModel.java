package com.example.yuefan.model;

/**
 * Created by 67698 on 2018/8/21.
 */

public interface IUserModel {
    void login(String username, String password, UserModel.UserListener userListener);

    void register(String username, String password,String email,UserModel.UserListener userListener);
}
