package com.example.yuefan.view.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.yuefan.Event.DingWeiEvent;
import com.example.yuefan.R;
import com.example.yuefan.presenter.IYueFanPresenter;
import com.example.yuefan.presenter.YueFanPresenter;
import com.example.yuefan.view.activity.PutYueFanActivity;
import com.example.yuefan.view.adapter.YueFanAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by 67698 on 2018/8/21.
 */

public class YueFanFragment extends Fragment implements IYueFanFragment {
    RecyclerView recyclerView;
    IYueFanPresenter yueFanPresenter;
    FloatingActionButton floatingActionButton;
    SwipeRefreshLayout swipeRefreshLayout;
    YueFanAdapter yueFanAdapter;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_yuefan,container,false);
        yueFanPresenter=new YueFanPresenter(this);
        initview();
        yueFanPresenter.getLists();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void initview()
    {
        swipeRefreshLayout=view.findViewById(R.id.yuefan_swipe);
        recyclerView=view.findViewById(R.id.yuefan_recyclerview);
        floatingActionButton=view.findViewById(R.id.yuefan_fab);
        EventBus.getDefault().register(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                yueFanPresenter.getLists();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), PutYueFanActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(),floatingActionButton,"fab").toBundle());
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dingweiChange(DingWeiEvent dingWeiEvent)
    {
        if(dingWeiEvent!=null)
        yueFanAdapter.getEvent(dingWeiEvent);
    }

    @Override
    public void getYueFan(List<AVObject> list) {
        yueFanAdapter=new YueFanAdapter(list,getContext());
        recyclerView.setAdapter(yueFanAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
