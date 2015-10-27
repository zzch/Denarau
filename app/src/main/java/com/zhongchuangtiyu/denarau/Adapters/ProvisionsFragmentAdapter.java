package com.zhongchuangtiyu.denarau.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhongchuangtiyu.denarau.Fragments.ProvisionFragments;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：WangMeng on 2015/10/27 18:27
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class ProvisionsFragmentAdapter extends FragmentPagerAdapter
{
    private ArrayList<ProvisionFragments> list;
    public ProvisionsFragmentAdapter(FragmentManager fm)
    {
        super(fm);
    }

    public ProvisionsFragmentAdapter(FragmentManager fm, ArrayList<ProvisionFragments> list)
    {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public int getCount()
    {
        return list.size();
    }
}
