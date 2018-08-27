package com.example.yuefan.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.yuefan.view.activity.PhotoActivity;

import java.util.List;

/**
 * Created by 67698 on 2018/8/25.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private static final int leftText=0,rightText=1,leftPic=2,rightPic=3;
    Context context;
    List<String>contentlist;
    List<Integer> typelist;
    String itTouxiang;
    String MyTouxiang;
    String username;
    public MessageAdapter(Context context,List<String>contentlist,List<Integer> typelist,String itTouxiang1,String username)
    {
        this.context=context;
        this.contentlist=contentlist;
        this.typelist=typelist;

        this.username=username;
        if(AVUser.getCurrentUser().getAVFile("touxiang")!=null)
        {
            MyTouxiang=AVUser.getCurrentUser().getAVFile("touxiang").getUrl();
        }
        AVQuery<AVUser> userQuery = new AVQuery<>("_User");
        userQuery.whereStartsWith("username",username);
        userQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                AVUser avUser=list.get(0);
                AVFile avFile=avUser.getAVFile("touxiang");
                if(avFile!=null)
                {
                 itTouxiang =avFile.getUrl();
                }
            }
        });
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==leftText)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.item_massage_lefttext, parent, false);
            return new MessageViewHolder(view,viewType);
        }
        else if(viewType==rightText)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.item_massage_righttext, parent, false);
            return new MessageViewHolder(view,viewType);
        }
        else if(viewType==leftPic)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.item_massage_leftpic, parent, false);
            return new MessageViewHolder(view,viewType);
        }
        else if(viewType==rightPic)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.item_massage_rightpic, parent, false);
            return new MessageViewHolder(view,viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, final int position) {
        Log.d("message", "onBindViewHolder: "+itTouxiang);
        switch (typelist.get(position))
        {
            case leftText :
                holder.lefttext.setText(contentlist.get(position));
                break;
            case rightText:
                holder.righttext.setText(contentlist.get(position));
                break;
            case leftPic:
                Glide.with(context).load(contentlist.get(position)).into(holder.leftpic);
                holder.leftpic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, PhotoActivity.class);
                        intent.putExtra("imageurl",contentlist.get(position));
                        context.startActivity(intent);
                    }
                });
                break;
            case rightPic:
                Glide.with(context).load(contentlist.get(position)).into(holder.rightpic);
                holder.rightpic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, PhotoActivity.class);
                        intent.putExtra("imageurl",contentlist.get(position));
                        context.startActivity(intent);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return contentlist.size();
    }

    @Override
    public int getItemViewType(int position) {
        return typelist.get(position);
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView lefttext,righttext;
        ImageView leftpic,rightpic;
        CircleImage lefttexttou,righttexttou,leftpictou,rightpictou;
        public MessageViewHolder(View itemView,int viewType) {
            super(itemView);
            switch (viewType)
            {
                case leftText:
                    lefttext=itemView.findViewById(R.id.message_lefttext);
                    lefttexttou=itemView.findViewById(R.id.message_lefttext_touxiang);
                    if (itTouxiang!="")
                    {
                        Glide.with(context).load(itTouxiang).placeholder(R.mipmap.icon).into(lefttexttou);
                    }
                    else lefttexttou.setImageResource(R.mipmap.icon);
                    break;
                case rightText:
                    righttext=itemView.findViewById(R.id.message_righttext);
                    righttexttou=itemView.findViewById(R.id.message_righttext_touxiang);
                    if(MyTouxiang!="")
                    {
                        Glide.with(context).load(MyTouxiang).placeholder(R.mipmap.icon).into(righttexttou);
                    }
                    else righttexttou.setImageResource(R.mipmap.icon);
                    break;
                case leftPic:
                       leftpic =itemView.findViewById(R.id.message_leftpic);
                       leftpictou=itemView.findViewById(R.id.message_leftpic_touxiang);
                    if (itTouxiang!="")
                    {
                        Glide.with(context).load(itTouxiang).placeholder(R.mipmap.icon).into(leftpictou);
                    }
                    else leftpictou.setImageResource(R.mipmap.icon);
                        break;
                case rightPic:
                    rightpic=itemView.findViewById(R.id.message_rightpic);
                    rightpictou=itemView.findViewById(R.id.message_rightpic_touxiang);
                    if(MyTouxiang!="")
                    {
                        Glide.with(context).load(MyTouxiang).placeholder(R.mipmap.icon).into(rightpictou);
                    }
                    else rightpictou.setImageResource(R.mipmap.icon);
                    break;

            }

        }
    }

}
