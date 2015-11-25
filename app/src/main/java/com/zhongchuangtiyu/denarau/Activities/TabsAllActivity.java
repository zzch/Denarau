package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Adapters.TabsAllListAdapter;
import com.zhongchuangtiyu.denarau.Adapters.TabsListAdapter1;
import com.zhongchuangtiyu.denarau.Entities.Tabs;
import com.zhongchuangtiyu.denarau.Entities.TabsAll;
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
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

public class TabsAllActivity extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.tabsAllTitleLeft)
    ImageButton tabsAllTitleLeft;
    @Bind(R.id.tabsAllListView)
    ListView tabsAllListView;
    @Bind(R.id.tabsAllPtr)
    PtrClassicFrameLayout tabsAllPtr;
    private int page = 0;
    private int lastItem;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_all);
        ButterKnife.bind(this);
        StatusBarCompat.compat(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        JPushInterface.init(getApplicationContext());
        setListeners();
        ActivityCollector.addActivity(this);
    }

    private void setListeners()
    {
        tabsAllTitleLeft.setOnClickListener(this);
        final MaterialHeader header = new MaterialHeader(TabsAllActivity.this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, PtrLocalDisplay.dp2px(10));
        header.setPtrFrameLayout(tabsAllPtr);
        tabsAllPtr.setLoadingMinTime(1000);
        tabsAllPtr.setDurationToCloseHeader(1500);
        tabsAllPtr.setHeaderView(header);
        tabsAllPtr.addPtrUIHandler(header);
        tabsAllPtr.setPtrHandler(new PtrHandler()
        {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header)
            {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout ptr)
            {
                page = 1;
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
        tabsAllPtr.setResistance(1.7f);
        tabsAllPtr.setRatioOfHeaderHeightToRefresh(1.2f);
        tabsAllPtr.setDurationToClose(200);
        tabsAllPtr.setDurationToCloseHeader(1000);
        // default is false
        tabsAllPtr.setPullToRefresh(false);
        // default is true
        tabsAllPtr.setKeepHeaderWhenRefresh(true);
        tabsAllPtr.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                tabsAllPtr.autoRefresh();
            }
        }, 100);
        tabsAllListView.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            //AbsListView view 这个view对象就是listview
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE)
                {
                    if (view.getLastVisiblePosition() == view.getCount() - 1)
                    {
                        page++;
                        sendPartRequest();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
//                CustomToast.showToast(TabsListActivity.this, String.valueOf(view.getCount()));
                lastItem = firstVisibleItem + visibleItemCount - 1;
            }
        });
    }

    private void sendPartRequest()
    {
        Map<String, String> map = new HashMap<>();
        final String token = CacheUtils.getString(TabsAllActivity.this, "token", null);
        Xlog.d(String.valueOf(page) + "page------------------------------");
        Xlog.d(token + "token------------------------------");

        MyApplication.volleyGET(APIUrls.TABS_ALL + "token=" + token + "&" + "&" + "page=" + page, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(TabsAllActivity.this, "登录失效，请重新登录");
                    CacheUtils.putString(TabsAllActivity.this, "token", null);
                    CacheUtils.putString(TabsAllActivity.this, "registration_id", null);
                    startActivity(new Intent(TabsAllActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    final List<TabsAll> data = TabsAll.instance(response);
                    TabsAllListAdapter adapter = (TabsAllListAdapter) tabsAllListView.getAdapter();
                    adapter.addData(data);
                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                if (error.toString().contains("AuthFailureError"))
                {
                    CustomToast.showToast(TabsAllActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(TabsAllActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    CustomToast.showToast(TabsAllActivity.this, "网络连接失败，请检查网络连接");
                    tabsAllPtr.refreshComplete();
                }
            }
        });
    }

    private void sendRequest()
    {
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(TabsAllActivity.this, "token", null);
        MyApplication.volleyGET(APIUrls.TABS_ALL + "token=" + token, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(TabsAllActivity.this, "登录失效，请重新登录");
                    CacheUtils.putString(TabsAllActivity.this, "token", null);
                    CacheUtils.putString(TabsAllActivity.this, "registration_id", null);
                    startActivity(new Intent(TabsAllActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    List<TabsAll> data = TabsAll.instance(response);
                    TabsAllListAdapter adapter1 = new TabsAllListAdapter(data, TabsAllActivity.this);
                    tabsAllListView.setAdapter(adapter1);
                    tabsAllPtr.refreshComplete();
                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                CustomToast.showToast(TabsAllActivity.this, "刷新失败，请检查网络连接");
                tabsAllPtr.refreshComplete();
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
            case R.id.tabsAllTitleLeft:
                finish();
                break;
            default:
                break;
        }
    }
}
