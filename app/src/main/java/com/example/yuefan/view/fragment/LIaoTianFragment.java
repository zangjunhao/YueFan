package com.example.yuefan.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationsQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.example.yuefan.R;
import com.example.yuefan.tool.getTime;
import com.example.yuefan.view.adapter.LiaoTianAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 67698 on 2018/8/21.
 */

public class LIaoTianFragment extends Fragment {

    AVIMClient tom ;
    View view;
    RecyclerView recyclerView;
    LiaoTianAdapter liaoTianAdapter;
    List<String> usernamelist=new ArrayList<>();
    List<String> timelist=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_liaotian,container,false);
        tom = AVIMClient.getInstance(AVUser.getCurrentUser().getUsername());
        initview();
        return view;

    }

    private void initview()
    {
        recyclerView=view.findViewById(R.id.liaotian_recyclerview);
        liaoTianAdapter=new LiaoTianAdapter(getActivity(),usernamelist,timelist);
        recyclerView.setAdapter(liaoTianAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tom.open(new AVIMClientCallback(){
            @Override
            public void done(AVIMClient client,AVIMException e){
                if(e==null){
                    AVIMConversationsQuery query = client.getConversationsQuery();
                    query.limit(20);
                    query.findInBackground(new AVIMConversationQueryCallback(){
                        @Override
                        public void done(List<AVIMConversation> convs, AVIMException e){
                            if(e==null){
                                AVIMConversation conversation;
                               for(int i=0;i<convs.size();i++)
                               {
                                   conversation=convs.get(i);
                                   timelist.add(getTime.getTimeFormatText(conversation.getUpdatedAt()));
                                   for(int j=0;j<conversation.getMembers().size();j++)
                                   {
                                       if(!conversation.getMembers().get(j).equals(AVUser.getCurrentUser().getUsername()))
                                       {
                                           usernamelist.add(conversation.getMembers().get(j));
                                       }
                                   }

                               }
                               liaoTianAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        });
    }

}
