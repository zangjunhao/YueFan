package com.example.yuefan.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.example.yuefan.R;
import com.example.yuefan.view.CustomView.CircleImage;
import com.example.yuefan.view.activity.MessageActivity;

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
    public MessageAdapter(Context context,List<String>contentlist,List<Integer> typelist,String itTouxiang)
    {
        this.context=context;
        this.contentlist=contentlist;
        this.typelist=typelist;
        this.itTouxiang=itTouxiang;
        if(AVUser.getCurrentUser().getAVFile("touxiang")!=null)
        {
            MyTouxiang=AVUser.getCurrentUser().getAVFile("touxiang").getUrl();
        }
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
    public void onBindViewHolder(MessageViewHolder holder, int position) {
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

                break;
            case rightPic:
                Glide.with(context).load(contentlist.get(position)).into(holder.rightpic);
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
                    if (itTouxiang!=null)
                    {
                        Glide.with(context).load(itTouxiang).into(lefttexttou);
                    }
                    break;
                case rightText:
                    righttext=itemView.findViewById(R.id.message_righttext);
                    righttexttou=itemView.findViewById(R.id.message_righttext_touxiang);
                    if(MyTouxiang!=null)
                    {
                        Glide.with(context).load(MyTouxiang).into(righttexttou);
                    }
                    break;
                case leftPic:
                       leftpic =itemView.findViewById(R.id.message_leftpic);
                       leftpictou=itemView.findViewById(R.id.message_leftpic_touxiang);
                    if (itTouxiang!=null)
                    {
                        Glide.with(context).load(itTouxiang).into(leftpictou);
                    }
                        break;
                case rightPic:
                    rightpic=itemView.findViewById(R.id.message_rightpic);
                    rightpictou=itemView.findViewById(R.id.message_rightpic_touxiang);
                    if(MyTouxiang!=null)
                    {
                        Glide.with(context).load(MyTouxiang).into(rightpictou);
                    }
                    break;

            }

        }
    }

}
