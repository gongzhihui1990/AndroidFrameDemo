package com.starkrak.framedemo.adapter;


import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * @author caroline
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mList;
    private String[] tabTitleArray;

    /**
     * @param fm
     * @param context
     * @param fragmentList
     */
    public ViewPagerAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList, String[] tabTitleArray) {
        super(fm);
        this.tabTitleArray = tabTitleArray;
        this.mList = fragmentList;
    }

    public void updateTitle(int index, String data) {
        tabTitleArray[index] = data;
        notifyDataSetChanged();
    }

    /**
     * 重写与TabLayout配合
     */
    @Override
    public int getCount() {
        return mList.size();
    }

    /**
     * 重写与TabLayout配合
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitleArray[position % tabTitleArray.length];
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
