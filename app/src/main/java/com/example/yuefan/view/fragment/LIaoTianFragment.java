package com.example.yuefan.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationEventHandler;
import com.avos.avoscloud.im.v2.AVIMConversationsQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.example.yuefan.Event.MessageEvent;
import com.example.yuefan.R;
import com.example.yuefan.tool.getTime;
import com.example.yuefan.view.activity.MessageActivity;
import com.example.yuefan.view.adapter.LiaoTianAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    SwipeRefreshLayout swipeRefreshLayout;
    List<String> usernamelist=new ArrayList<>();
    List<String> timelist=new ArrayList<>();
    List<Integer> WeiduConversation=new ArrayList<>();

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
        swipeRefreshLayout=view.findViewById(R.id.liaotian_swipe);
        AVIMMessageManager.setConversationEventHandler(new changeConversation());
        recyclerView=view.findViewById(R.id.liaotian_recyclerview);
        liaoTianAdapter=new LiaoTianAdapter(getActivity(),usernamelist,timelist,WeiduConversation);
        EventBus.getDefault().register(this);
        recyclerView.setAdapter(liaoTianAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getConversation();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            getConversation();
            swipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(MessageEvent messageEvent) {
        if (messageEvent.getChangeSign()==1)
        {
            getConversation();
        }
    }

    public class changeConversation extends AVIMConversationEventHandler{

        @Override
        public void onUnreadMessagesCountUpdated(AVIMClient client, AVIMConversation conversation) {
            super.onUnreadMessagesCountUpdated(client, conversation);
            Log.d("update", "onUnreadMessagesCountUpdated: ");
            getConversation();
        }

        @Override
        public void onMemberLeft(AVIMClient avimClient, AVIMConversation avimConversation, List<String> list, String s) {

        }

        @Override
        public void onMemberJoined(AVIMClient avimClient, AVIMConversation avimConversation, List<String> list, String s) {

        }

        @Override
        public void onKicked(AVIMClient avimClient, AVIMConversation avimConversation, String s) {

        }

        @Override
        public void onInvited(AVIMClient avimClient, AVIMConversation avimConversation, String s) {

        }
    }

    private void getConversation()
    {
        final List<String> usernamelist1=new ArrayList<>();
        final List<String> timelist1=new ArrayList<>();
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
                                    timelist1.add(getTime.getTimeFormatText(conversation.getUpdatedAt()));
                                    for(int j=0;j<conversation.getMembers().size();j++)
                                    {
                                        if(!conversation.getMembers().get(j).equals(AVUser.getCurrentUser().getUsername()))
                                        {
                                            usernamelist1.add(conversation.getMembers().get(j));
                                            WeiduConversation.add(conversation.getUnreadMessagesCount());
                                        }
                                    }

                                }
                                liaoTianAdapter=new LiaoTianAdapter(getActivity(),usernamelist1,timelist1,WeiduConversation);
                                recyclerView.setAdapter(liaoTianAdapter);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        tom.close(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
            }
        });
    }
}
