package com.hlibrary.adapter.pagerAdapter;

import android.support.annotation.IntRange;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.ArrayMap;

import com.hlibrary.adapter.IListInterface;
import com.hlibrary.adapter.SimpleList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by linwenhui on 2017/8/16.
 */

public abstract class BaseFragmentPagerAdapter<T> extends FragmentPagerAdapter {

    protected List<T> datas = new ArrayList<>();
    private IListInterface<T> dataListInterfaces;
    private Map<Integer, Fragment> fragmentMap;

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        dataListInterfaces = new SimpleList<>(datas);
        fragmentMap = new ArrayMap<>();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    public IListInterface<T> getFragmentIListInterface() {
        return dataListInterfaces;
    }

    @Override
    public Fragment getItem(@IntRange(from = 0) int position) {
        if (position < getCount()) {
            Fragment fragment = fragmentMap.get(position);
            if (fragment == null) {
                fragment = onCreateFragment(datas.get(position));
                fragmentMap.put(position, fragment);
            }
            return fragment;
        }
        return null;
    }

    protected abstract Fragment onCreateFragment(T model);

    @Override
    public CharSequence getPageTitle(@IntRange(from = 0) int position) {
        Fragment fragment = getItem(position);
        if (fragment != null)
            return fragment.toString();
        return super.getPageTitle(position);
    }

}
