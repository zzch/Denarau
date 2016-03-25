package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Adapters.ReservationsListAdapter;
import com.zhongchuangtiyu.denarau.Entities.Reservations;
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

public class ReservationsActivity extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.reservationsListView)
    ListView reservationsListView;
    @Bind(R.id.reservationsFrame)
    PtrClassicFrameLayout reservationsFrame;
    @Bind(R.id.reservationsTitleLeft)
    ImageButton reservationsTitleLeft;
    private int page = 0;
    private int lastItem;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);
        ButterKnife.bind(this);
        StatusBarCompat.compat(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        JPushInterface.init(getApplicationContext());
        setListeners();
        ActivityCollector.addActivity(this);
//        sendRequest();
    }

    private void setListeners()
    {
        reservationsTitleLeft.setOnClickListener(this);
        final MaterialHeader header = new MaterialHeader(ReservationsActivity.this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, PtrLocalDisplay.dp2px(10));
        header.setPtrFrameLayout(reservationsFrame);

        reservationsFrame.setLoadingMinTime(1000);
        reservationsFrame.setDurationToCloseHeader(1500);
        reservationsFrame.setHeaderView(header);
        reservationsFrame.addPtrUIHandler(header);
        reservationsFrame.setPtrHandler(new PtrHandler()
        {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header)
            {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout ptr)
            {
                ptr.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        page = 1;
                        sendRequest();
                    }
                }, 1000);
            }
        });
        reservationsFrame.setResistance(1.7f);
        reservationsFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        reservationsFrame.setDurationToClose(200);
        reservationsFrame.setDurationToCloseHeader(1000);
        // default is false
        reservationsFrame.setPullToRefresh(false);
        // default is true
        reservationsFrame.setKeepHeaderWhenRefresh(true);
        reservationsFrame.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                reservationsFrame.autoRefresh();
            }
        }, 100);
        reservationsListView.setOnScrollListener(new AbsListView.OnScrollListener()
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
//                CustomToast.showToast(ReservationsActivity.this, String.valueOf(view.getCount()));
                lastItem = firstVisibleItem + visibleItemCount - 1;
            }
        });
    }

    private void sendPartRequest()
    {
        Map<String, String> map = new HashMap<>();
        final String token = CacheUtils.getString(ReservationsActivity.this, "token", null);
        Xlog.d(String.valueOf(page) + "page------------------------------");
        MyApplication.volleyGET(APIUrls.PROMOTIONS + "token=" + token + "&" + "page=" + page, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(ReservationsActivity.this, "登录失效，请重新登录");
                    CacheUtils.putString(ReservationsActivity.this, "token", null);
                    CacheUtils.putString(ReservationsActivity.this, "registration_id", null);
                    startActivity(new Intent(ReservationsActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    if (!response.contains("球场未找到"))
                    {
                        final List<Reservations> data = Reservations.instance(response);
                        ReservationsListAdapter adapter = (ReservationsListAdapter) reservationsListView.getAdapter();
                        adapter.addData(data);
                    }
                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                if (error.toString().contains("AuthFailureError"))
                {
                    CustomToast.showToast(ReservationsActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(ReservationsActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                }else
                {
                    CustomToast.showToast(ReservationsActivity.this, "网络连接失败，请检查网络连接");
                }
                reservationsFrame.refreshComplete();
            }
        });
    }

    private void sendRequest()
    {
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(ReservationsActivity.this, "token", null);
        MyApplication.volleyGET(APIUrls.RESERVATIONS + "token=" + token, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(ReservationsActivity.this, "登录失效，请重新登录");
                    CacheUtils.putString(ReservationsActivity.this, "token", null);
                    CacheUtils.putString(ReservationsActivity.this, "registration_id", null);
                    startActivity(new Intent(ReservationsActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    List<Reservations> data = Reservations.instance(response);
                    ReservationsListAdapter adapter = new ReservationsListAdapter(ReservationsActivity.this, data);
                    reservationsListView.setAdapter(adapter);
                    reservationsFrame.refreshComplete();
                }
            }
            @Override
            public void netFail(VolleyError error)
            {
                CustomToast.showToast(ReservationsActivity.this, "刷新失败，请检查网络连接");
                reservationsFrame.refreshComplete();
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
            case R.id.reservationsTitleLeft:
                finish();
                ActivityCollector.removeActivity(this);
                break;
            default:
                break;
        }

    }
}
