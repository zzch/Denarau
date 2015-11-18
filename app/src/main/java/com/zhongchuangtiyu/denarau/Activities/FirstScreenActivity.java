package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;

import java.lang.reflect.Member;

public class FirstScreenActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        // 延迟几秒，然后跳转到登录页面

        new Handler().postDelayed(r, 2000);
    }
    Runnable r = new Runnable()
    {

        @Override

        public void run() {

            Intent intent = new Intent();
            String token = CacheUtils.getString(FirstScreenActivity.this, "token", null);
            if (token != null)
            {
                intent.setClass(FirstScreenActivity.this, MembershipCardMainActivity.class);
            }else
            {

                intent.setClass(FirstScreenActivity.this, SignInActivity.class);
            }
            startActivity(intent);
            finish();
        }
    };
}
