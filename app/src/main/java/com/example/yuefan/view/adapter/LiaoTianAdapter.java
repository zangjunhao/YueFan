package com.example.yuefan.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
import com.example.yuefan.R;
import com.example.yuefan.view.CustomView.CircleImage;
import com.example.yuefan.view.activity.MessageActivity;

import java.util.List;

/**
 * Created by 67698 on 2018/8/26.
 */

public class LiaoTianAdapter extends RecyclerView.Adapter<LiaoTianAdapter.LiaoTianViewHolder> {
    Context context;
    List<String>nameList;
    List<String>timelist;
    String imageUrl;

    public LiaoTianAdapter(Context context, List<String>nameList,List<String>time)
    {
        this.context=context;
        this.nameList=nameList;
        this.timelist=time;
    }

    @Override
    public LiaoTianViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_liaotian, parent, false);
        return new LiaoTianViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LiaoTianViewHolder holder, final int position) {
        TextView name=holder.name;
        TextView time=holder.time;
        final CircleImage touxiang=holder.touxiang;
        name.setText(nameList.get(position));
        time.setText(timelist.get(position));
        AVQuery<AVUser> userQuery = new AVQuery<>("_User");
        userQuery.whereStartsWith("username",nameList.get(position));
        userQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                AVUser avUser=list.get(0);
              AVFile avFile=avUser.getAVFile("touxiang");
              if(avFile!=null)
              {
                  imageUrl=avFile.getUrl();
                  if(imageUrl!=null)
                  {
                      Glide.with(context).load(imageUrl).into(touxiang);
                  }
                  touxiang.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          Intent intent=new Intent(context, MessageActivity.class);
                          intent.putExtra("username",nameList.get(position));
                          intent.putExtra("imageUrl",imageUrl);
                          context.startActivity(intent);
                      }
                  });
              }
            }
        });
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MessageActivity.class);
                intent.putExtra("username",nameList.get(position));
                intent.putExtra("imageUrl",imageUrl);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    class LiaoTianViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView time;
        CircleImage touxiang;
        public LiaoTianViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.liaotian_name);
            time=itemView.findViewById(R.id.liaotian_time);
            touxiang=itemView.findViewById(R.id.liaotian_touxiang);

        }
    }


}
