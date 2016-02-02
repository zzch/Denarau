package com.zhongchuangtiyu.denarau.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhongchuangtiyu.denarau.Adapters.OpenCourseAdapter;
import com.zhongchuangtiyu.denarau.Entities.OpenCourses;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.SetListViewHeight;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OpenCoursesActivity extends BaseActivity implements View.OnClickListener
{


    @Bind(R.id.openCoachImage)
    ImageView openCoachImage;
    @Bind(R.id.openCoachName)
    TextView openCoachName;
    @Bind(R.id.openCoachTitle)
    TextView openCoachTitle;
    @Bind(R.id.openCoachWebView)
    WebView openCoachWebView;
    @Bind(R.id.openCoachListView)
    ListView openCoachListView;
    @Bind(R.id.openCourseTitleImageLeft)
    ImageButton openCourseTitleImageLeft;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_order2);
        ButterKnife.bind(this);
        imageLoader.init(ImageLoaderConfiguration.createDefault(OpenCoursesActivity.this));
        sendRequest();
        setListeners();
        ActivityCollector.addActivity(this);
    }

    private void setListeners()
    {
        openCourseTitleImageLeft.setOnClickListener(this);
    }

    private void sendRequest()
    {
        Map<String, String> map = new HashMap<>();
        Intent intent = getIntent();
        String uuid = intent.getStringExtra("uuid");
        String token = CacheUtils.getString(OpenCoursesActivity.this, "token", null);
        String club_uuid = CacheUtils.getString(OpenCoursesActivity.this, "clubuuid", null);
        Xlog.d("uuid" + uuid);
        Xlog.d("token" + token);
        Xlog.d("club_uuid" + club_uuid);
        MyApplication.volleyGET(APIUrls.OPEN_COURSES + "token=" + token + "&" + "club_uuid=" + club_uuid + "&" + "uuid=" + uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(OpenCoursesActivity.this, "登录失效，请重新登录");
                    CacheUtils.putString(OpenCoursesActivity.this, "token", null);
                    CacheUtils.putString(OpenCoursesActivity.this, "registration_id", null);
                    startActivity(new Intent(OpenCoursesActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    OpenCourses data = OpenCourses.instance(response);
                    Xlog.d("html" + "======" + data.getDescription());
                    final List<OpenCourses> result = data.generateListInfo();
                    Xlog.d(data.toString());
                    imageLoader.displayImage(data.getCoach().getPortrait(), openCoachImage);
                    openCoachName.setText(data.getCoach().getName());
                    openCoachTitle.setText(data.getCoach().getTitle());
//                    openCoachWebView.loadData(data.getDescription(), "text/html", "UTF-8");
                    WebSettings settings = openCoachWebView.getSettings();
                    settings.setTextSize(WebSettings.TextSize.NORMAL);
                    openCoachWebView.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
                    openCoachWebView.loadData(data.getDescription(), "text/html; charset=UTF-8", null);//这种写法可以正确解码
//                    openCoachWebView.getSettings().setUseWideViewPort(true);
//                    openCoachWebView.getSettings().setLoadWithOverviewMode(true);
                    OpenCourseAdapter adapter = new OpenCourseAdapter(OpenCoursesActivity.this, result);
                    openCoachListView.setAdapter(adapter);
                    SetListViewHeight.setListViewHeightBasedOnChildren(openCoachListView);
                }
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
        switch (v.getId())
        {
            case R.id.openCourseTitleImageLeft:
                finish();
                break;
            default:
                break;
        }
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
