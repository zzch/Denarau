package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Entities.PromotionsDetail;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PromotionsDetailActivity extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.provitionsDetailTitleTv)
    TextView provitionsDetailTitleTv;
    @Bind(R.id.provitionsDetailWebView)
    WebView provitionsDetailWebView;
    @Bind(R.id.promotionsDetailTitleLeft)
    ImageButton promotionsDetailTitleLeft;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setListeners();
        sendPromotionsDetailRequest();
    }

    private void setListeners()
    {
        promotionsDetailTitleLeft.setOnClickListener(this);
    }

    private void sendPromotionsDetailRequest()
    {
        Map map = new HashMap();
        String token = CacheUtils.getString(PromotionsDetailActivity.this, "token", null);
        String club_uuid = CacheUtils.getString(PromotionsDetailActivity.this, "clubuuid", null);
        Intent intent = getIntent();
        String uuid = intent.getStringExtra("uuid");
        MyApplication.volleyGET(APIUrls.PROMOTIONS_DETAIL + "token=" + token + "&" + "club_uuid=" + club_uuid + "&" + "uuid=" + uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(PromotionsDetailActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(PromotionsDetailActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else if (response.contains("数据未找到"))
                {
                    CustomToast.showToast(PromotionsDetailActivity.this, "数据未找到");
                } else
                {
                    PromotionsDetail data = PromotionsDetail.instance(response);
                    String title = data.getTitle();
                    String content = data.getContent();
                    provitionsDetailTitleTv.setText(title);
                    provitionsDetailWebView.loadData(content, "text/html", "UTF-8");
                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                CustomToast.showToast(PromotionsDetailActivity.this, "请检查网络连接");
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
            case R.id.promotionsDetailTitleLeft:
                finish();
                ActivityCollector.removeActivity(this);
                break;
            default:
                break;
        }
    }
}
