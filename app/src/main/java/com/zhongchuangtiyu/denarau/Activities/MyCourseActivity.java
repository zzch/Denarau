package com.zhongchuangtiyu.denarau.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Adapters.MyCourseListAdapter;
import com.zhongchuangtiyu.denarau.Entities.MyCourses;
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

public class MyCourseActivity extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.myCoursesTitleLeft)
    ImageButton myCoursesTitleLeft;
    @Bind(R.id.myCoursesFrame)
    PtrClassicFrameLayout myCoursesFrame;
    @Bind(R.id.myCourseListView)
    ListView myCourseListView;
    private int page = 0;
    private int lastItem;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);
        ButterKnife.bind(this);
        sendRequest();
        ActivityCollector.addActivity(this);
        setListeners();
    }

    private void setListeners()
    {
        myCoursesTitleLeft.setOnClickListener(this);
        final MaterialHeader header = new MaterialHeader(MyCourseActivity.this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, PtrLocalDisplay.dp2px(10));
        header.setPtrFrameLayout(myCoursesFrame);

        myCoursesFrame.setLoadingMinTime(1000);
        myCoursesFrame.setDurationToCloseHeader(1500);
        myCoursesFrame.setHeaderView(header);
        myCoursesFrame.addPtrUIHandler(header);
        myCoursesFrame.setPtrHandler(new PtrHandler()
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
        myCoursesFrame.setResistance(1.7f);
        myCoursesFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        myCoursesFrame.setDurationToClose(200);
        myCoursesFrame.setDurationToCloseHeader(1000);
        // default is false
        myCoursesFrame.setPullToRefresh(false);
        // default is true
        myCoursesFrame.setKeepHeaderWhenRefresh(true);
        myCoursesFrame.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                myCoursesFrame.autoRefresh();
            }
        }, 100);
        myCourseListView.setOnScrollListener(new AbsListView.OnScrollListener()
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
        String token = CacheUtils.getString(MyCourseActivity.this, "token", null);
        Map<String, String> map = new HashMap<>();
        MyApplication.volleyGET(APIUrls.CURRICULUMS + token + "&" + "page=" + page, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                List<MyCourses> data = MyCourses.instance(response);
                MyCourseListAdapter adapter = (MyCourseListAdapter) myCourseListView.getAdapter();
                adapter.addData(data);

            }

            @Override
            public void netFail(VolleyError error)
            {

            }
        });
    }


    private void sendRequest()
    {
        String token = CacheUtils.getString(MyCourseActivity.this, "token", null);
        Map<String, String> map = new HashMap<>();
        MyApplication.volleyGET(APIUrls.CURRICULUMS + token, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                final List<MyCourses> data = MyCourses.instance(response);
                MyCourseListAdapter adapter = new MyCourseListAdapter(MyCourseActivity.this, data);
                myCourseListView.setAdapter(adapter);
                myCoursesFrame.refreshComplete();
                myCourseListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        MyCourses myCourses = data.get(position);
                        Intent intent = new Intent(MyCourseActivity.this, CDA2Activity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("myCourses", myCourses);
                        intent.putExtras(mBundle);
                        startActivity(intent);
                        CustomToast.showToast(MyCourseActivity.this, "Clicked");
                    }
                });
            }

            @Override
            public void netFail(VolleyError error)
            {

            }
        });
    }

    @Override
    public void onClick(View v)
    {
        
    }
}
