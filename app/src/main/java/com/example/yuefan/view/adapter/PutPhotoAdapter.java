package com.example.yuefan.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yuefan.R;
import com.example.yuefan.view.activity.IAddPhoto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 67698 on 2018/8/23.
 */

public class PutPhotoAdapter extends RecyclerView.Adapter<PutPhotoAdapter.PutPhotoViewHolder> {
    List<Uri> list=new ArrayList<>();
    Context context;
    IAddPhoto iAddPhoto;
    public PutPhotoAdapter(List<Uri> list, Context context, IAddPhoto iAddPhoto)
    {
        this.context=context;
        this.list=list;
        this.iAddPhoto=iAddPhoto;
    }

    @Override
    public PutPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_putimage,parent,false);
        return new PutPhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PutPhotoViewHolder holder, int position) {
        ImageView imageView=holder.imageView;
        if(position==list.size())
        {
            imageView.setImageResource(R.drawable.add);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iAddPhoto.addPhoto();
                }
            });
        }
        else {
            Glide.with(context).load(list.get(position)).into(imageView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    class PutPhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public PutPhotoViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.put_image);
        }
    }

}
