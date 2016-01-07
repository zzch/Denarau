package com.zhongchuangtiyu.denarau.Activities;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.StatusBarCompat;



public class CDA2Activity extends BaseActivity implements View.OnClickListener
{



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cda2);
        StatusBarCompat.compat(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActivityCollector.addActivity(this);
    }

    @Override
    public void onClick(View v)
    {

    }
}
