package com.example.yuefan.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
import com.example.yuefan.R;
import com.example.yuefan.model.UserModel;
import com.example.yuefan.presenter.IYueFanPresenter;
import com.example.yuefan.presenter.YueFanPresenter;
import com.example.yuefan.view.adapter.YueFanAdapter;
import com.example.yuefan.view.fragment.IYueFanFragment;

import java.util.List;

public class PersonXiangxiActivity extends AppCompatActivity implements IYueFanFragment {

    String TAG="难受呀马飞";
    ImageView touxiang;
    TextView name;
    TextView jianjie;
    String username;
    TextView aihao;
    String imageUrl;
    Button liaotian;
    IYueFanPresenter yueFanPresenter;
     AVUser avUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_xiangxi);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        imageUrl=intent.getStringExtra("imageUrl");
        Log.d(TAG, "onCreate: "+username);
        initview();
    }

    private void initview()
    {
        touxiang=findViewById(R.id.person_touxiang);
        name=findViewById(R.id.person_name);
        jianjie=findViewById(R.id.person_jianjie);
        aihao=findViewById(R.id.person_aihao);
        liaotian=findViewById(R.id.person_liaotian);
        if(AVUser.getCurrentUser().getUsername().equals(username))
        {
            liaotian.setVisibility(View.GONE);
        }
        final AVQuery<AVUser> userQuery = new AVQuery<>("_User");
        userQuery.whereStartsWith("username",username);
        userQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                avUser=list.get(0);
                if(imageUrl!=null)
                {
                    Log.d(TAG, "done: "+imageUrl);
                        Glide.with(getApplicationContext()).load(imageUrl).into(touxiang);
                }
                name.setText(username);
                if (avUser!=null)
                    jianjie.setText(avUser.getString("jianjie"));
                    aihao.setText(avUser.getString("aihao"));
            }
        });
        liaotian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(PersonXiangxiActivity.this, MessageActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("imageUrl",imageUrl);
                    startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void getYueFan(List<AVObject> list) {

    }
}
