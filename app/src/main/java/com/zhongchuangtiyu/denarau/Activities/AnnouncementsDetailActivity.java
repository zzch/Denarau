package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Entities.AnnouncementsDetail;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.StatusBarCompat;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

public class AnnouncementsDetailActivity extends BaseActivity implements View.OnClickListener
{


    @Bind(R.id.annoucementsDetailTitleLeft)
    ImageButton annoucementsDetailTitleLeft;
    @Bind(R.id.announcementsDetailTitleTv)
    TextView announcementsDetailTitleTv;
    @Bind(R.id.announcementsDetailWebView)
    WebView announcementsDetailWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_detail);
        ButterKnife.bind(this);
        StatusBarCompat.compat(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sendAnnouncementsDetailRequest();
        setListeners();
        JPushInterface.init(getApplicationContext());
        ActivityCollector.addActivity(this);
    }

    private void setListeners()
    {
        annoucementsDetailTitleLeft.setOnClickListener(this);
    }

    private void sendAnnouncementsDetailRequest()
    {
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(AnnouncementsDetailActivity.this, "token", null);
        String club_uuid = CacheUtils.getString(AnnouncementsDetailActivity.this, "clubuuid", null);
        Intent intent = getIntent();
        String uuid = intent.getStringExtra("uuid");
        MyApplication.volleyGET(APIUrls.ANNOUNCEMENTS_DETAIL_URL + "token=" + token + "&" + "club_uuid=" + club_uuid + "&" + "uuid=" + uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                AnnouncementsDetail data = AnnouncementsDetail.instance(response);
                if (response.contains("10002"))
                {
                    CustomToast.showToast(AnnouncementsDetailActivity.this, "登录失效，请重新登录");
                    CacheUtils.putString(AnnouncementsDetailActivity.this, "token", null);
                    CacheUtils.putString(AnnouncementsDetailActivity.this, "registration_id", null);
                    startActivity(new Intent(AnnouncementsDetailActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else if (response.contains("数据未找到"))
                {
                    CustomToast.showToast(AnnouncementsDetailActivity.this, "数据未找到");
                } else
                {
                    announcementsDetailTitleTv.setText(data.getTitle());

                    Display display = getWindowManager().getDefaultDisplay();
                    int width=display.getWidth();
                    String head = "<html><head><title>Example</title><meta name=\"viewport\" content=\"width="+width+", initial-scale=0.25 \" /><style>img { width: 100% }</style></head>";
                    head = head + "<body>" + data.getContent() + "</body>" + "</html>";


//                    announcementsDetailWebView.getSettings().setUseWideViewPort(true);
//                    announcementsDetailWebView.getSettings().setLoadWithOverviewMode(true);
                    announcementsDetailWebView.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
                    announcementsDetailWebView.loadData(head, "text/html; charset=UTF-8", null);//这种写法可以正确解码

                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                if (error.toString().contains("AuthFailureError"))
                {
                    CustomToast.showToast(AnnouncementsDetailActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(AnnouncementsDetailActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                }else
                {
                    CustomToast.showToast(AnnouncementsDetailActivity.this, "网络连接失败，请检查网络连接");
                }
            }
        });
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
            case R.id.annoucementsDetailTitleLeft:
                finish();
                break;
            default:
                break;
        }
    }
}
