package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.StatusBarCompat;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

public class BeforePostAdvice extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.beforePostAdviceTitleLeft)
    ImageButton beforePostAdviceTitleLeft;
    @Bind(R.id.btnManager)
    RelativeLayout btnManager;
    @Bind(R.id.btnReception)
    RelativeLayout btnReception;
    @Bind(R.id.btnCatering)
    RelativeLayout btnCatering;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_post_advice);
        ButterKnife.bind(this);
        StatusBarCompat.compat(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActivityCollector.addActivity(this);
        JPushInterface.init(getApplicationContext());
        setListeners();
    }

    private void setListeners()
    {
        beforePostAdviceTitleLeft.setOnClickListener(this);
        btnManager.setOnClickListener(this);
        btnReception.setOnClickListener(this);
        btnCatering.setOnClickListener(this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.beforePostAdviceTitleLeft:
                finish();
                break;
            case R.id.btnManager:
                Intent intent1 = new Intent(BeforePostAdvice.this, FeedBackActivity.class);
                intent1.putExtra("type","manager");
                startActivity(intent1);
                break;
            case R.id.btnReception:
                Intent intent2 = new Intent(BeforePostAdvice.this, FeedBackActivity.class);
                intent2.putExtra("type","reception");
                startActivity(intent2);
                break;
            case R.id.btnCatering:
                Intent intent3 = new Intent(BeforePostAdvice.this, FeedBackActivity.class);
                intent3.putExtra("type", "restaurant");
                startActivity(intent3);
                break;
            default:
                break;
        }
    }
}
