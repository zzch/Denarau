package com.zhongchuangtiyu.denarau.Utils;

import android.app.Activity;
import android.view.WindowManager;

/**
 * 作者：WangMeng on 2015/12/29 10:49
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class ScreenUtils
{
    /**
     * 设置全屏
     *
     * @param activity
     */
    public static void setFullScreen(Activity activity) {
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 取消全屏
     *
     * @param activity
     */
    public static void cancelFullScreen(Activity activity) {
        activity.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
