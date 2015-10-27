package com.zhongchuangtiyu.denarau.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 作者：WangMeng on 2015/10/27 18:27
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class ProvisionsFragmentAdapter extends FragmentPagerAdapter
{
    private List<Fragment> list;
    public ProvisionsFragmentAdapter(FragmentManager fm)
    {
        super(fm);
    }

    public ProvisionsFragmentAdapter(FragmentManager fm, List<Fragment> list)
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
