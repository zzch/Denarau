package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Adapters.MembersListAdapter;
import com.zhongchuangtiyu.denarau.Adapters.TabsAllListAdapter;
import com.zhongchuangtiyu.denarau.Entities.Members;
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
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

public class SwipeCardHistory extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.reservationsTitleLeft)
    ImageButton reservationsTitleLeft;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.swipeCardListView)
    ListView swipeCardListView;
    @Bind(R.id.swipeCardFrame)
    PtrClassicFrameLayout swipeCardFrame;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private int page = 0;
    private int lastItem;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_card_history);
        StatusBarCompat.compat(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setListeners();
        ActivityCollector.addActivity(this);
    }

    private void setListeners()
    {
        reservationsTitleLeft.setOnClickListener(this);
        final MaterialHeader header = new MaterialHeader(SwipeCardHistory.this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, PtrLocalDisplay.dp2px(10));
        header.setPtrFrameLayout(swipeCardFrame);
        swipeCardFrame.setLoadingMinTime(1000);
        swipeCardFrame.setDurationToCloseHeader(1500);
        swipeCardFrame.setHeaderView(header);
        swipeCardFrame.addPtrUIHandler(header);
        swipeCardFrame.setPtrHandler(new PtrHandler()
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
        swipeCardFrame.setResistance(1.7f);
        swipeCardFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        swipeCardFrame.setDurationToClose(200);
        swipeCardFrame.setDurationToCloseHeader(1000);
        // default is false
        swipeCardFrame.setPullToRefresh(false);
        // default is true
        swipeCardFrame.setKeepHeaderWhenRefresh(true);
        swipeCardFrame.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                swipeCardFrame.autoRefresh();
            }
        }, 100);
        swipeCardListView.setOnScrollListener(new AbsListView.OnScrollListener()
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

    private void sendRequest()
    {
        final Intent intent = getIntent();
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(SwipeCardHistory.this, "token", null);
        String member_uuid = intent.getStringExtra("member_uuid");
        Xlog.d("member_uuidmember_uuidmember_uuid===" + member_uuid);
        MyApplication.volleyGET(APIUrls.MEMBERS + "token=" + token + "&" + "member_uuid=" + member_uuid , map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(SwipeCardHistory.this, "登录失效，请重新登录");
                    CacheUtils.putString(SwipeCardHistory.this, "token", null);
                    CacheUtils.putString(SwipeCardHistory.this, "registration_id", null);
                    startActivity(new Intent(SwipeCardHistory.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    final List<Members> data = Members.instance(response);
                    MembersListAdapter adapter1 = new MembersListAdapter(data, SwipeCardHistory.this);
                    swipeCardListView.setAdapter(adapter1);
                    swipeCardFrame.refreshComplete();
                    swipeCardListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            Intent intent1 = new Intent(SwipeCardHistory.this, SwipeCardDetail.class);
                            if (data.get(position).getTab() != null)
                            {
                                String uuid = data.get(position).getTab().getUuid();
                                intent1.putExtra("uuid", uuid);
                                startActivity(intent1);
                            }
                        }
                    });
                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                CustomToast.showToast(SwipeCardHistory.this, "刷新失败，请检查网络连接");
                swipeCardFrame.refreshComplete();
            }
        });
    }

    private void sendPartRequest()
    {
        Intent intent = getIntent();
        Map<String, String> map = new HashMap<>();
        final String token = CacheUtils.getString(SwipeCardHistory.this, "token", null);
        String member_uuid = intent.getStringExtra("member_uuid");
        Xlog.d(String.valueOf(page) + "page------------------------------");
        Xlog.d(token + "token------------------------------");

        MyApplication.volleyGET(APIUrls.MEMBERS + "token=" + token + "&" + "member_uuid=" + member_uuid + "&" + "page=" + page, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(SwipeCardHistory.this, "登录失效，请重新登录");
                    CacheUtils.putString(SwipeCardHistory.this, "token", null);
                    CacheUtils.putString(SwipeCardHistory.this, "registration_id", null);
                    startActivity(new Intent(SwipeCardHistory.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    final List<Members> data = Members.instance(response);
                    MembersListAdapter adapter = (MembersListAdapter) swipeCardListView.getAdapter();
                    adapter.addData(data);
                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                if (error.toString().contains("AuthFailureError"))
                {
                    CustomToast.showToast(SwipeCardHistory.this, "登录失效，请重新登录");
                    startActivity(new Intent(SwipeCardHistory.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    CustomToast.showToast(SwipeCardHistory.this, "网络连接失败，请检查网络连接");
                    swipeCardFrame.refreshComplete();
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
            case R.id.reservationsTitleLeft:
                finish();
                ActivityCollector.removeActivity(this);
                break;
            default:
                break;
        }
    }
}
