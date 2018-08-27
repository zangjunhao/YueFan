package com.example.yuefan.view.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.bumptech.glide.Glide;
import com.example.yuefan.R;
import com.example.yuefan.view.CustomView.CircleImage;
import com.github.chrisbanes.photoview.PhotoView;

public class YueFanActivity extends AppCompatActivity {

    TextView nameT;
    TextView titleT;
    TextView contentT;
    ImageView imageViewI=null;
    TextView juliT;
    Toolbar toolbar;
    TextView map;
    String touxiangUrl;
    String time;
    CircleImage touxiang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuefanxiangqing);
        final Intent intent=getIntent();
        final String title=intent.getStringExtra("title");
        String content=intent.getStringExtra("content");
        final String name =intent.getStringExtra("name");
        final String imageUrl=intent.getStringExtra("imageUrl");
        String juli=intent.getStringExtra("juli");
        touxiangUrl=intent.getStringExtra("touxiang");
        time=intent.getStringExtra("time");
        TextView textView=findViewById(R.id.xiangqing_time);
        textView.setText(time);
        final LatLng it=intent.getParcelableExtra("it");
        LatLng my=intent.getParcelableExtra("my");
        toolbar=findViewById(R.id.xiangqing_tbar);
        nameT=findViewById(R.id.xiangqing_usename);
        titleT=findViewById(R.id.xiangqing_title);
        contentT=findViewById(R.id.xiangqing_content);
        imageViewI=findViewById(R.id.xiangqing_image);
        map=findViewById(R.id.xiangqing_map);
        touxiang=findViewById(R.id.xiangqing_touxiang);
        if(touxiangUrl!=null)
        {
            Glide.with(this).load(touxiangUrl).into(touxiang);
        }

        touxiang.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(YueFanActivity.this,PersonXiangxiActivity.class);
                intent1.putExtra("username",name);
                intent1.putExtra("imageUrl",touxiangUrl);
                startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation((Activity)YueFanActivity.this,nameT,"name").toBundle());
            }
        });
        if (imageUrl!=null) Glide.with(this).load(imageUrl).into(imageViewI);
        imageViewI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(YueFanActivity.this,PhotoActivity.class);
                intent1.putExtra("imageurl",imageUrl);
                startActivity(intent1);
            }
        });
        juliT=findViewById(R.id.xiangqing_juli);
        nameT.setText(name);
        titleT.setText(title);
        contentT.setText(content);
        juliT.setText(juli);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent1=new Intent(YueFanActivity.this,MapActivity.class);
            intent1.putExtra("name",name);
            intent1.putExtra("it",it);
            intent1.putExtra("title",title);
            startActivity(intent1);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
