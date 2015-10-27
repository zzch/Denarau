package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Entities.Courses;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CoachTutorialDetailCoursesActivity extends AppCompatActivity
{

    @Bind(R.id.coachDetailCourseName)
    TextView coachDetailCourseName;
    @Bind(R.id.coachDetailValidMonth)
    TextView coachDetailValidMonth;
    @Bind(R.id.tutorialType)
    TextView tutorialType;
    @Bind(R.id.coachTutorialDetailCourseDesc)
    TextView coachTutorialDetailCourseDesc;
    @Bind(R.id.coachTutorialDetailCourseFit)
    TextView coachTutorialDetailCourseFit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_tutorial_detail_courses);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initData();
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
                Courses data = Courses.instance(response);
                coachDetailCourseName.setText(data.getName());
                coachDetailValidMonth.setText(String.valueOf(data.getValid_months()) + "个月");
                tutorialType.setText("1对" + String.valueOf(data.getMaximum_students()));
                coachTutorialDetailCourseDesc.setText(data.getDescription());

            }

            @Override
            public void netFail(VolleyError error)
            {

            }
        });
    }

}
