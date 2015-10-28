package com.zhongchuangtiyu.denarau.CustomComponents;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by wangm on 2015/10/28.
 */
public class ProvButton extends TextView
{
    public ProvButton(Context context)
    {
        super(context);
    }

    private ViewPager viewPager;

    public ViewPager getViewPager()
    {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager)
    {
        this.viewPager = viewPager;
    }
}
