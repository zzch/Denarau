package com.zhongchuangtiyu.denarau.Utils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * 作者：WangMeng on 2015/11/4 10:57
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class BaseActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity", getClass().getSimpleName()); ActivityCollector.addActivity(this);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy(); ActivityCollector.removeActivity(this);
    }
}
