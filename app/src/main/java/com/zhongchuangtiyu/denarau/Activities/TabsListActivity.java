package com.zhongchuangtiyu.denarau.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Adapters.TabsListAdapter1;
import com.zhongchuangtiyu.denarau.Entities.Tabs;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TabsListActivity extends AppCompatActivity
{

    @Bind(R.id.tabListView1)
    ListView tabListView1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sendRequest();
    }

    private void sendRequest()
    {
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(TabsListActivity.this, "token",null);
        String club_uuid = CacheUtils.getString(TabsListActivity.this, "clubuuid",null);
        MyApplication.volleyGET(APIUrls.TABS + "token=" + token + "&" + "club_uuid=" + club_uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                List<Tabs> data = Tabs.instance(response);
                TabsListAdapter1 adapter1 = new TabsListAdapter1(data, TabsListActivity.this);
                tabListView1.setAdapter(adapter1);
            }

            @Override
            public void netFail(VolleyError error)
            {

            }
        });
    }


}
