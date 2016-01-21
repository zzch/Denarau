package com.zhongchuangtiyu.denarau.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

/**
 * 作者：WangMeng on 2016/1/21 18:14
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class NoScrollGridView extends GridView
{

    public NoScrollGridView(Context context, AttributeSet attrs){
        super(context, attrs);
    }
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int mExpandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
