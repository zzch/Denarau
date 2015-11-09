package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Adapters.AnnouncementsListAdapter;
import com.zhongchuangtiyu.denarau.Entities.Announcements;
import com.zhongchuangtiyu.denarau.Entities.AnnouncementsDetail;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AnnouncementsListActivity extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.announcementsListView)
    ListView announcementsListView;
    @Bind(R.id.announcementsListTitleLeft)
    ImageButton announcementsListTitleLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sendAnnouncentsDetailRequest();
        setListeners();
        ActivityCollector.addActivity(this);
    }

    private void setListeners()
    {
        announcementsListTitleLeft.setOnClickListener(this);
    }

    private void sendAnnouncentsDetailRequest()
    {
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(AnnouncementsListActivity.this, "token", null);
        String club_uuid = CacheUtils.getString(AnnouncementsListActivity.this, "clubuuid", null);
        MyApplication.volleyGET(APIUrls.ANNOUNCEMENTS + "token=" + token + "&" + "club_uuid=" + club_uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(AnnouncementsListActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(AnnouncementsListActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    final List<Announcements> data = Announcements.instance(response);
                    AnnouncementsListAdapter adapter = new AnnouncementsListAdapter(AnnouncementsListActivity.this, data);
                    announcementsListView.setAdapter(adapter);
                    announcementsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            String uuid = data.get(position).getUuid();
                            Intent intent = new Intent(AnnouncementsListActivity.this, AnnouncementsDetailActivity.class);
                            intent.putExtra("uuid", uuid);
                            Xlog.d(uuid + "uuid=====================================");
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                if (error.toString().contains("AuthFailureError"))
                {
                    CustomToast.showToast(AnnouncementsListActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(AnnouncementsListActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                }else
                {
                    CustomToast.showToast(AnnouncementsListActivity.this, "网络连接失败，请检查网络连接");
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
            case R.id.announcementsListTitleLeft:
                finish();
                break;
            default:
                break;
        }
    }
}
