package com.example.yuefan.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.yuefan.view.fragment.LIaoTianFragment;
import com.example.yuefan.view.fragment.MyFragment;
import com.example.yuefan.view.fragment.YueFanFragment;

import java.util.HashMap;

/**
 * Created by 67698 on 2018/8/21.
 */

public class MainAdapter  extends FragmentPagerAdapter{

    private HashMap<Integer,Fragment> fragmentHashMap=new HashMap<>();
    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return creatFragment(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
    private Fragment creatFragment(int position)
    {

        Fragment fragment=fragmentHashMap.get(position);
        if(fragment==null)
        {
            switch (position)
            {
                case 0:fragment=new YueFanFragment();
                    break;
                case 1:fragment=new LIaoTianFragment();
                    break;
                case 2:fragment=new MyFragment();
            }
            fragmentHashMap.put(position,fragment);
        }
        return fragment;
    }
}
