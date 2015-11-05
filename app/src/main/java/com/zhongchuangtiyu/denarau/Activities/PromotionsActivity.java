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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        sendProtionsRequest();
        setListeners();
    }

    private void setListeners()
    {
        promotionsTitleLeft.setOnClickListener(this);
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

    }

    private void sendProtionsRequest()
    {
        Map<String, String> map = new HashMap<>();
        final String token = CacheUtils.getString(PromotionsActivity.this, "token", null);
        String club_uuid = CacheUtils.getString(PromotionsActivity.this, "clubuuid", null);
        Xlog.d(String.valueOf(page) + "page------------------------------");
        MyApplication.volleyGET(APIUrls.PROMOTIONS + "token=" + "test" + "&" + "club_uuid=" + "test" + "&" + "page=" + page, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(PromotionsActivity.this, "登录失效，请重新登录");
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
                                    sendProtionsRequest();
                                }
                            }
                        }
                        @Override
                        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
                        {
                            CustomToast.showToast(PromotionsActivity.this, String.valueOf(view.getCount()));
                            lastItem = firstVisibleItem + visibleItemCount - 1 ;
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
                ActivityCollector.removeActivity(this);
                break;
            default:
                break;
        }
    }
}
