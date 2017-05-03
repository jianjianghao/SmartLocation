package com.jianjianghao.smartlocation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/4/27.
 */

public class TabFragmentPagerAdapter extends FragmentPagerAdapter{
    private List<Fragment> fragments;
    public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments){
        super(fm);
        this.fragments=fragments;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
