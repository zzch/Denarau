package com.zhongchuangtiyu.denarau.Activities;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.Toolbar;

import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.StatusBarCompat;

public class PrivateCoursesActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_order);
        StatusBarCompat.compat(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.courseOrderToolbar);
        setSupportActionBar(toolbar);
        ActivityCollector.addActivity(this);
    }

}
