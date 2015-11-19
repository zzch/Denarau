package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Entities.Courses;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.StatusBarCompat;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CoachTutorialDetailCoursesActivity extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.coachDetailCourseName)
    TextView coachDetailCourseName;
    @Bind(R.id.coachDetailValidMonth)
    TextView coachDetailValidMonth;
    @Bind(R.id.tutorialType)
    TextView tutorialType;
    @Bind(R.id.content_coach_tutorial_detail_webView)
    WebView contentCoachTutorialDetailWebView;
    @Bind(R.id.coachTutorialDetailCourseTitleLeft)
    ImageButton coachTutorialDetailCourseTitleLeft;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_tutorial_detail_courses);
        ButterKnife.bind(this);
        StatusBarCompat.compat(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setListeners();
        initData();
        ActivityCollector.addActivity(this);
    }

    private void setListeners()
    {
        coachTutorialDetailCourseTitleLeft.setOnClickListener(this);
    }

    private void initData()
    {
        Map<String, String> map = new HashMap<>();
        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        String club_uuid = intent.getStringExtra("club_uuid");
        String uuid = intent.getStringExtra("uuid");
        MyApplication.volleyGET(APIUrls.COACHES_DETAIL_COURSES_URL + "token=" + token + "&" + "club_uuid=" + club_uuid + "&" + "uuid=" + uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(CoachTutorialDetailCoursesActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(CoachTutorialDetailCoursesActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    Courses data = Courses.instance(response);
                    coachDetailCourseName.setText(data.getName());
                    coachDetailValidMonth.setText(String.valueOf(data.getValid_months()) + "个月");
                    tutorialType.setText("1对" + String.valueOf(data.getMaximum_students()));
                    contentCoachTutorialDetailWebView.loadData(data.getDescription(), "text/html", "UTF-8");
                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                if (error.toString().contains("AuthFailureError"))
                {
                    CustomToast.showToast(CoachTutorialDetailCoursesActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(CoachTutorialDetailCoursesActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                }else
                {
                    CustomToast.showToast(CoachTutorialDetailCoursesActivity.this, "网络连接失败，请检查网络连接");
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
            case R.id.coachTutorialDetailCourseTitleLeft:
                finish();
                break;
            default:
                break;
        }
    }
}
