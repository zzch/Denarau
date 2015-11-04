package com.zhongchuangtiyu.denarau.Utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：WangMeng on 2015/11/4 10:19
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class ActivityCollector
{
    public static List<Activity> activities = new ArrayList<Activity>();
    public static void addActivity(Activity activity)
    {
        activities.add(activity);
    }
    public static void removeActivity(Activity activity)
    {
        activities.remove(activity);
    }
    public static void finishAll()
    {
        for (Activity activity : activities)
        {
            if (!activity.isFinishing()) { activity.finish(); }
        }
    }

}
