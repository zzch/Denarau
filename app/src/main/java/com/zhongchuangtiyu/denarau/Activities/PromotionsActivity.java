package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Adapters.PromotionsListAdapter;
import com.zhongchuangtiyu.denarau.Entities.Promotions;
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

public class PromotionsActivity extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.promotionsTitleLeft)
    ImageButton promotionsTitleLeft;
    @Bind(R.id.promotionsListView)
    ListView promotionsListView;
    @Bind(R.id.ptr)
    PtrClassicFrameLayout ptr;
    private int page = 0;
    private int lastItem;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions);
        ButterKnife.bind(this);
        StatusBarCompat.compat(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        sendProtionsRequest();
        JPushInterface.init(getApplicationContext());
        setListeners();
        ActivityCollector.addActivity(this);
    }

    private void setListeners()
    {
        promotionsTitleLeft.setOnClickListener(this);
        final MaterialHeader header = new MaterialHeader(PromotionsActivity.this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, PtrLocalDisplay.dp2px(10));
        header.setPtrFrameLayout(ptr);

        ptr.setLoadingMinTime(1000);
        ptr.setDurationToCloseHeader(1500);
        ptr.setHeaderView(header);
        ptr.addPtrUIHandler(header);
        ptr.setPtrHandler(new PtrHandler()
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
                        sendProtionsRequest();
                    }
                }, 1000);
            }
        });
        ptr.setResistance(1.7f);
        ptr.setRatioOfHeaderHeightToRefresh(1.2f);
        ptr.setDurationToClose(200);
        ptr.setDurationToCloseHeader(1000);
        // default is false
        ptr.setPullToRefresh(false);
        // default is true
        ptr.setKeepHeaderWhenRefresh(true);
        ptr.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                ptr.autoRefresh();
            }
        }, 100);
        promotionsListView.setOnScrollListener(new AbsListView.OnScrollListener()
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
                        sendPartProtionsRequest();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
//                CustomToast.showToast(PromotionsActivity.this, String.valueOf(view.getCount()));
                lastItem = firstVisibleItem + visibleItemCount - 1;
            }
        });
    }

    private void sendPartProtionsRequest()
    {
        Map<String, String> map = new HashMap<>();
        final String token = CacheUtils.getString(PromotionsActivity.this, "token", null);
        String club_uuid = CacheUtils.getString(PromotionsActivity.this, "clubuuid", null);
        Xlog.d(String.valueOf(page) + "page------------------------------");
        Xlog.d(token + "token------------------------------");
        Xlog.d(club_uuid + "club_uuid------------------------------");

        MyApplication.volleyGET(APIUrls.PROMOTIONS + "token=" + token + "&" + "club_uuid=" + club_uuid + "&" + "page=" + page, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(PromotionsActivity.this, "登录失效，请重新登录");
                    CacheUtils.putString(PromotionsActivity.this, "token", null);
                    CacheUtils.putString(PromotionsActivity.this, "registration_id", null);
                    startActivity(new Intent(PromotionsActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    final List<Promotions> data = Promotions.instance(response);
                    PromotionsListAdapter adapter = (PromotionsListAdapter) promotionsListView.getAdapter();
                    adapter.addData(data);
                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                if (error.toString().contains("AuthFailureError"))
                {
                    CustomToast.showToast(PromotionsActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(PromotionsActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                }else
                {
                    CustomToast.showToast(PromotionsActivity.this, "网络连接失败，请检查网络连接");
                    ptr.refreshComplete();
                }
            }
        });
    }

    private void sendProtionsRequest()
    {
        Map<String, String> map = new HashMap<>();
        final String token = CacheUtils.getString(PromotionsActivity.this, "token", null);
        String club_uuid = CacheUtils.getString(PromotionsActivity.this, "clubuuid", null);
        Xlog.d(String.valueOf(page) + "page------------------------------");
        MyApplication.volleyGET(APIUrls.PROMOTIONS + "token=" + token + "&" + "club_uuid=" + club_uuid + "&" + "page=" + page, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(PromotionsActivity.this, "登录失效，请重新登录");
                    CacheUtils.putString(PromotionsActivity.this, "token", null);
                    CacheUtils.putString(PromotionsActivity.this, "registration_id", null);
                    startActivity(new Intent(PromotionsActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    final List<Promotions> data = Promotions.instance(response);
                    final PromotionsListAdapter adapter = new PromotionsListAdapter(data, PromotionsActivity.this);
                    promotionsListView.setAdapter(adapter);
                    ptr.refreshComplete();
                    promotionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            String uuid = data.get(position).getUuid();
                            Intent intent = new Intent(PromotionsActivity.this, PromotionsDetailActivity.class);
                            intent.putExtra("uuid", uuid);
                            startActivity(intent);
                        }
                    });

                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                CustomToast.showToast(PromotionsActivity.this, "刷新失败，请检查网络连接");
                ptr.refreshComplete();
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
            case R.id.promotionsTitleLeft:
                finish();
                break;
            default:
                break;
        }
    }
}
