package com.example.yuefan.view.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.example.yuefan.R;
import com.example.yuefan.tool.RealFilePath;
import com.example.yuefan.view.CustomView.CircleImage;
import com.example.yuefan.view.activity.ChangePasswordActivity;
import com.example.yuefan.view.activity.ChangeZhiLiaoActivity;
import com.example.yuefan.view.activity.LoginActivity;
import com.example.yuefan.view.activity.MainActivity;
import com.example.yuefan.view.activity.PutYueFanActivity;
import com.example.yuefan.view.adapter.PutPhotoAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.FileNotFoundException;

/**
 * Created by 67698 on 2018/8/21.
 */

public class MyFragment extends Fragment implements View.OnClickListener {

    View view;
    CircleImage touXiang;
    TextView name;
    TextView jianJie;
    TextView changePassword;
    TextView changeZhiliao;
    Button outLogin;
    AVUser onAvuser;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_my,container,false);
        initview();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==23&&resultCode==-1)
        {
           Glide.with(this).load(Matisse.obtainResult(data).get(0)).into(touXiang);
            try {
                AVFile avFile=AVFile.withAbsoluteLocalPath("touxiang.png", RealFilePath.getPath(getContext(),Matisse.obtainResult(data).get(0)));
                avFile.saveInBackground();
                onAvuser.put("touxiang",avFile);
                onAvuser.saveInBackground();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private void initview()
    {
        onAvuser=AVUser.getCurrentUser();
        touXiang=view.findViewById(R.id.My_toutxiang);
        AVFile avFile=onAvuser.getAVFile("touxiang");
        if(avFile!=null)
        {
            Glide.with(this).load(avFile.getUrl()).into(touXiang);
        }
        name=view.findViewById(R.id.My_name);
        jianJie=view.findViewById(R.id.My_jianjie);
        if(onAvuser.getString("jianjie")!=null)
        {
            jianJie.setText(onAvuser.getString("jianjie"));
        }
        changePassword=view.findViewById(R.id.My_changepassword);
        changeZhiliao=view.findViewById(R.id.My_xiugai);
        outLogin=view.findViewById(R.id.My_outlogin);
        touXiang.setOnClickListener(this);
        name.setText(onAvuser.getUsername());
        jianJie.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        changeZhiliao.setOnClickListener(this);
        outLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.My_outlogin:
                Intent intent =new Intent(getActivity(), LoginActivity.class);
                AVUser.logOut();
                startActivity(intent);
                break;
            case R.id.My_toutxiang:
                selectPhoto();
                break;

            case R.id.My_changepassword:
                Intent intent1=new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent1);
                break;
            case R.id.My_xiugai:
                Intent intent2=new Intent(getActivity(), ChangeZhiLiaoActivity.class);
                startActivity(intent2);
                break;
        }
    }


    private void selectPhoto()
    {
        Matisse
                .from(this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(1)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .theme(R.style.Matisse_Zhihu)
                .imageEngine(new GlideEngine())
                .forResult(23);
    }

}
