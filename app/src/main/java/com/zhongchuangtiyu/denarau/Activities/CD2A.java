package com.zhongchuangtiyu.denarau.Activities;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.Toolbar;

import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.StatusBarCompat;

public class CD2A extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cd2);
        StatusBarCompat.compat(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.cd2Toolbar);
        setSupportActionBar(toolbar);
        ActivityCollector.addActivity(this);
    }

}
