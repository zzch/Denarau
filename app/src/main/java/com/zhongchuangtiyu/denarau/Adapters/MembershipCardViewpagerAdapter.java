package com.zhongchuangtiyu.denarau.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.List;

/**
 * 作者：WangMeng on 2015/10/22 17:06
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class MembershipCardViewpagerAdapter extends PagerAdapter
{
    private List<View> views;
    private Context context;

    public MembershipCardViewpagerAdapter(List<View> views, Context context)
    {
        this.views = views;
        this.context = context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position)
    {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public int getCount()
    {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }
}
