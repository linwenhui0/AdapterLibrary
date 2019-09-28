package com.hlibrary.adapter.pagerAdapter;

import android.support.annotation.IntRange;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.hlibrary.adapter.IListInterface;
import com.hlibrary.adapter.SimpleList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linwenhui on 2017/8/16.
 */

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    protected List<Fragment> fragments = new ArrayList<>();
    private IListInterface<Fragment> fragmentIListInterface;

    public FragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentIListInterface = new SimpleList<>(fragments);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public IListInterface<Fragment> getFragmentIListInterface() {
        return fragmentIListInterface;
    }

    @Override
    public Fragment getItem(@IntRange(from = 0) int position) {
        if (position < getCount())
            return fragments.get(position);
        return null;
    }

    @Override
    public CharSequence getPageTitle(@IntRange(from = 0) int position) {
        Fragment fragment = getItem(position);
        if (fragment != null)
            return fragment.toString();
        return super.getPageTitle(position);
    }

}
