package com.example.yuefan.view.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.AVUtils;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
import com.example.yuefan.Event.DingWeiEvent;
import com.example.yuefan.R;
import com.example.yuefan.tool.getOnline;
import com.example.yuefan.tool.getTime;
import com.example.yuefan.view.CustomView.CircleImage;
import com.example.yuefan.view.activity.PersonXiangxiActivity;
import com.example.yuefan.view.activity.YueFanActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 67698 on 2018/8/22.
 */

public class YueFanAdapter extends RecyclerView .Adapter<YueFanAdapter.YueFanViewHoldr>{

    LatLng my;
    List<AVObject> list=new ArrayList<>();
    Context context;
    public YueFanAdapter(List<AVObject> list,Context context)
    {
            this.list=list;
        this.context=context;
    }

    @Override
    public YueFanViewHoldr onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yuefan,parent,false);
        return new YueFanViewHoldr(view);
    }

    @Override
    public void onBindViewHolder(YueFanViewHoldr holder, int position) {
        TextView onTime=holder.time;
        final Intent intent=new Intent(context,YueFanActivity.class);
        final AVObject object=list.get(position);
        final String time= getTime.getTimeFormatText(object.getCreatedAt());
        AVUtils.stringFromDate(object.getCreatedAt());
        onTime.setText(time);
        final RelativeLayout relativeLayout=holder.relativeLayout;
        TextView title=holder.title;
        final TextView name=holder.name;
        TextView juli=holder.juli;
        double Latitude=object.getDouble("Latitude");//纬度
        double Longitude=object.getDouble("Longitude");//经度
        final LatLng it = new LatLng(Latitude,Longitude);
        my = getOnline.getLonPoint(context);
        final int  distance =(int) AMapUtils.calculateLineDistance(it,my);
        if(distance>=10000000)
        {
            juli.setText("0米");
        }else  juli.setText(distance+"米");


        final TextView content=holder.content;
        ImageView imageView=holder.imageView;
        final CircleImage touxiang=holder.touxiang;
        AVQuery<AVObject> query = new AVQuery<>("_User");
        query.whereStartsWith("username", object.getString("username"));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                AVObject avObject = list.get(0);
                if (avObject!=null) {AVFile avFile =avObject.getAVFile("touxiang");
                if(avFile!=null)
                {
                    Glide.with(context).load(avFile.getUrl()).into(touxiang);
                    intent.putExtra("touxiang",avFile.getUrl());
                }
                }
            }
        });
        title.setText(object.getString("title"));
        name.setText(object.getString("username"));
        content.setText(object.getString("content"));
        final AVFile avFile=object.getAVFile("image");
        if(avFile!=null)
        {
            imageView.setVisibility(View.VISIBLE);
            Glide.with(context).load(avFile.getUrl()).into(imageView);
        }
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                intent.putExtra("name",object.getString("username"));
                intent.putExtra("title",object.getString("title"));
                intent.putExtra("content",object.getString("content"));
                intent.putExtra("time",time);
                if(avFile!=null) intent.putExtra("imageUrl",avFile.getUrl());
                if(distance>10000000)
                {
                    intent.putExtra("juli","定位错误");
                }
                else  intent.putExtra("juli",distance+"米");

                intent.putExtra("it",it);
                intent.putExtra("my",my);
                //  Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this, android.support.v4.util.Pair.create(textView1, "zhuche"), android.support.v4.util.Pair.create(submitButton, "button")).toBundle();
               context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context,touxiang,"yuefan").toBundle());
                if(context.equals(PersonXiangxiActivity.class) )((Activity) context).finish();
            }
        });


    }

    public void getEvent(DingWeiEvent dingWeiEvent)
    {
        my=dingWeiEvent.getLatLng();
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class YueFanViewHoldr extends RecyclerView.ViewHolder {
        TextView title;
        TextView name;
        TextView content;
        CircleImage touxiang;
        ImageView imageView;
        TextView juli;
        TextView time;
        RelativeLayout relativeLayout;
        public YueFanViewHoldr(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.yuefan_title);
            name=itemView.findViewById(R.id.yuefan_usename);
            content=itemView.findViewById(R.id.yuefan_content);
            touxiang=itemView.findViewById(R.id.yuefan_touxiang);
            imageView=itemView.findViewById(R.id.yuefan_image);
            juli=itemView.findViewById(R.id.yuefan_juli);
            time=itemView.findViewById(R.id.yuefan_time);
           relativeLayout=(RelativeLayout) itemView;
        }
    }
}
