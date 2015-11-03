package com.zhongchuangtiyu.denarau.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Adapters.AnnouncementsListAdapter;
import com.zhongchuangtiyu.denarau.Entities.Announcements;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AnnouncementsListActivity extends AppCompatActivity
{

    @Bind(R.id.announcementsListView)
    ListView announcementsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sendAnnouncentsDetailRequest();
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
                List<Announcements> data = Announcements.instance(response);
                AnnouncementsListAdapter adapter = new AnnouncementsListAdapter(AnnouncementsListActivity.this, data);
                announcementsListView.setAdapter(adapter);
            }

            @Override
            public void netFail(VolleyError error)
            {

            }
        });
    }

}
