package com.zhongchuangtiyu.denarau.Demos;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangm on 2015/10/20.
 */
public class DynamicCreateButttons extends Activity
{
    private List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        list.add("主食");
        list.add("酒水");
        list.add("套餐");
        for (int i = 0; i < list.size(); i++)
        {
            Button button = new Button(this);
            button.setId(i);
            button.setText(list.get(i));
            linearLayout.addView(button);
        }
        Xlog.d(CacheUtils.getString(DynamicCreateButttons.this, "token", null)+"-------------------------------");
    }
}
