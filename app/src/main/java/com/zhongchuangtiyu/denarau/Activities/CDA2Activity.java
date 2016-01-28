package com.zhongchuangtiyu.denarau.Activities;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhongchuangtiyu.denarau.Entities.MyCourses;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.DateUtils;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.RatingBar;
import com.zhongchuangtiyu.denarau.Utils.StatusBarCompat;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.HashMap;
import java.util.Map;

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
    @Bind(R.id.cda2RatingBar)
    RatingBar cda2RatingBar;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private int ratingCount;

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
        annoucementsDetailTitleLeft.setOnClickListener(this);
    }


    private void initView()
    {
        imageLoader.init(ImageLoaderConfiguration.createDefault(CDA2Activity.this));
        final MyCourses myCourses = (MyCourses) getIntent().getSerializableExtra("myCourses");
        courseDate.setText(DateUtils.getDateToString4(Long.valueOf(myCourses.getLesson().getStarted_at()) * 1000));
        courseTime.setText(DateUtils.getDateToString2(Long.valueOf(myCourses.getLesson().getStarted_at())) + "-" + DateUtils.getDateToString2(Long.valueOf(myCourses.getLesson().getFinished_at())));
        switch (myCourses.getState())
        {
            case "progressing":
                courseState.setText("等待上课");
                break;
            case "cancelled":
                courseState.setText("已取消");
                break;
            case "confirming":
                courseState.setText("等待评分");
                break;
            case "finished":
                courseState.setText("已完成");
                break;
            default:
                break;
        }
        imageLoader.displayImage(myCourses.getLesson().getCourse().getCoach().getPortrait(), courseCoachImg);
        courseCoachName.setText(myCourses.getLesson().getCourse().getCoach().getName());
        courseCoachType.setText(myCourses.getLesson().getCourse().getCoach().getTitle());
        webView2.loadData(myCourses.getLesson().getCourse().getDescription(), "text/html", "UTF-8");
        Xlog.d("Rated?Rated?Rated?Rated?Rated?Rated?Rated?" + myCourses.getRating());
        if (myCourses.getRating() != null)
        {
            cda2RatingBar.setStar((Float) myCourses.getRating());
            cda2RatingBar.setmClickable(false);
            btnRating.setBackgroundResource(R.mipmap.yiwancheng_anniu);
            btnRating.setClickable(false);
            btnRating.setText("已完成");
        }else if (myCourses.getRating() == null && !myCourses.getState().equals("confirming"))
        {
            btnRating.setBackgroundResource(R.mipmap.yiwancheng_anniu);
            btnRating.setClickable(false);
            cda2RatingBar.setmClickable(false);
            btnRating.setText("评价");
        }
        else if (myCourses.getRating() == null && myCourses.getState().equals("confirming"))
        {
            btnRating.setBackgroundResource(R.mipmap.yiwancheng_anniu);
            btnRating.setClickable(false);
            btnRating.setText("评价");
            cda2RatingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener()
            {
                @Override
                public void onRatingChange(int RatingCount)
                {
                    ratingCount = RatingCount;
                    CustomToast.showToast(CDA2Activity.this, String.valueOf(ratingCount));
                    btnRating.setClickable(true);
                    btnRating.setBackgroundResource(R.mipmap.pingjia_anniu);
                    btnRating.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            Map<String, String> map = new HashMap<String, String>();
                            String token = CacheUtils.getString(CDA2Activity.this, "token", null);
                            String uuid = myCourses.getUuid();
                            int score = ratingCount;
                            Xlog.d("token" + token + "uuid" + uuid + "score" + score);
                            MyApplication.volleyGET(APIUrls.CURRICULUMS_RATING + "token=" + token + "&" + "uuid=" + uuid + "&" + "score=" + score, map, new MyApplication.VolleyCallBack()
                            {
                                @Override
                                public void netSuccess(String response)
                                {
                                    CustomToast.showToast(CDA2Activity.this, "评价成功");
                                    btnRating.setBackgroundResource(R.mipmap.yiwancheng_anniu);
                                    btnRating.setClickable(false);
                                    btnRating.setText("已完成");
                                    CustomToast.showToast(CDA2Activity.this, String.valueOf(ratingCount));
                                }

                                @Override
                                public void netFail(VolleyError error)
                                {
                                    CustomToast.showToast(CDA2Activity.this, "评价失败请稍后再试");
                                    Xlog.d("error" + error.toString());
                                }
                            });
                        }
                    });
                }
            });

        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.annoucementsDetailTitleLeft:
                finish();
                break;
            default:
                break;
        }
    }
}
