package com.zhongchuangtiyu.denarau.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhongchuangtiyu.denarau.Entities.MyCourses;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.StatusBarCompat;

import butterknife.Bind;
import butterknife.ButterKnife;


public class CDA2Activity extends BaseActivity implements View.OnClickListener
{


    @Bind(R.id.annoucementsDetailTitleLeft)
    ImageButton annoucementsDetailTitleLeft;
    @Bind(R.id.courseDate)
    TextView courseDate;
    @Bind(R.id.courseTime)
    TextView courseTime;
    @Bind(R.id.courseState)
    TextView courseState;
    @Bind(R.id.courseCoachImg)
    ImageView courseCoachImg;
    @Bind(R.id.courseCoachName)
    TextView courseCoachName;
    @Bind(R.id.courseCoachType)
    TextView courseCoachType;
    @Bind(R.id.webView2)
    WebView webView2;
    @Bind(R.id.btnRating)
    Button btnRating;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cda2);
        ButterKnife.bind(this);
        StatusBarCompat.compat(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActivityCollector.addActivity(this);
        initView();
    }

    private void initView()
    {
        imageLoader.init(ImageLoaderConfiguration.createDefault(CDA2Activity.this));
        MyCourses myCourses = (MyCourses)getIntent().getSerializableExtra("myCourses");
//        courseDate.setText(myCourses.getLesson().getStarted_at());
//        courseTime.setText(myCourses.getLesson().getStarted_at());
        courseState.setText(myCourses.getState());
        imageLoader.displayImage(myCourses.getLesson().getCourse().getCoach().getPortrait(), courseCoachImg);
        courseCoachName.setText(myCourses.getLesson().getCourse().getCoach().getName());
        courseCoachType.setText(myCourses.getLesson().getCourse().getCoach().getTitle());
        webView2.loadData(myCourses.getLesson().getCourse().getDescription(), "text/html", "UTF-8");

    }

    @Override
    public void onClick(View v)
    {

    }
}
