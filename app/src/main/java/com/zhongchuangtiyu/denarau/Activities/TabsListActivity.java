package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Adapters.TabsListAdapter1;
import com.zhongchuangtiyu.denarau.Entities.Tabs;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

public class TabsListActivity extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.tabListView1)
    ListView tabListView1;
    @Bind(R.id.tabsTitleLeft)
    ImageButton tabsTitleLeft;
    @Bind(R.id.tabsPtr)
    PtrClassicFrameLayout tabsPtr;
    private int page = 0;
    private int lastItem;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setListeners();
        ActivityCollector.addActivity(this);
    }

    private void setListeners()
    {
        tabsTitleLeft.setOnClickListener(this);
        final MaterialHeader header = new MaterialHeader(TabsListActivity.this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, PtrLocalDisplay.dp2px(10));
        header.setPtrFrameLayout(tabsPtr);

        tabsPtr.setLoadingMinTime(1000);
        tabsPtr.setDurationToCloseHeader(1500);
        tabsPtr.setHeaderView(header);
        tabsPtr.addPtrUIHandler(header);
        tabsPtr.setPtrHandler(new PtrHandler()
        {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header)
            {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout ptr)
            {
//                page = 1;
                ptr.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        sendRequest();
                    }
                }, 1000);
            }
        });
        tabsPtr.setResistance(1.7f);
        tabsPtr.setRatioOfHeaderHeightToRefresh(1.2f);
        tabsPtr.setDurationToClose(200);
        tabsPtr.setDurationToCloseHeader(1000);
        // default is false
        tabsPtr.setPullToRefresh(false);
        // default is true
        tabsPtr.setKeepHeaderWhenRefresh(true);
        tabsPtr.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                tabsPtr.autoRefresh();
            }
        }, 100);
    }


        @Override
        protected void onResume ()
        {
            super.onResume();
            sendRequest();
        }

    private void sendRequest()
    {
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(TabsListActivity.this, "token", null);
        String club_uuid = CacheUtils.getString(TabsListActivity.this, "clubuuid", null);
        MyApplication.volleyGET(APIUrls.TABS + "token=" + token + "&" + "club_uuid=" + club_uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(TabsListActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(TabsListActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    List<Tabs> data = Tabs.instance(response);
                    TabsListAdapter1 adapter1 = new TabsListAdapter1(data, TabsListActivity.this);
                    tabListView1.setAdapter(adapter1);
                    tabsPtr.refreshComplete();
                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                CustomToast.showToast(TabsListActivity.this, "刷新失败，请检查网络连接");
                tabsPtr.refreshComplete();
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
            case R.id.tabsTitleLeft:
                finish();
                break;
            default:
                break;
        }
    }
}
