package com.zhongchuangtiyu.denarau.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 作者：WangMeng on 2015/10/27 14:06
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class CustomListView extends ListView
{
    public CustomListView(Context context)
    {
        super(context);
    }

    public CustomListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
