package com.zhongchuangtiyu.denarau.Demos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Entities.Announcements;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by wangm on 2015/11/2.
 */
public class Test extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayout);
        ImageView imageView = (ImageView) findViewById(R.id.imageView41);
        imageView.setImageResource(R.mipmap.test);
        PtrClassicFrameLayout ptr = (PtrClassicFrameLayout) findViewById(R.id.ptr);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("头像上传中");
        progressDialog.show();
        ptr.setPtrHandler(new PtrHandler()
        {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header)
            {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame)
            {
                frame.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        frame.refreshComplete();
                    }
                }, 2000);
            }
        });
    }
}
