package com.zhongchuangtiyu.denarau.Utils;

import android.content.Context;

/**
 * 作者：WangMeng on 2015/11/9 15:49
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class DensityUtil
{
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
