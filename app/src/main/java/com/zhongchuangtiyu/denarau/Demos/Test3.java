package com.zhongchuangtiyu.denarau.Demos;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import com.zhongchuangtiyu.denarau.R;

import cn.jpush.android.api.JPushInterface;

public class Test3 extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        TextView textView = (TextView) findViewById(R.id.textView27);
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString(JPushInterface.EXTRA_EXTRA);
        textView.setText("extra : " + title);

    }

}
