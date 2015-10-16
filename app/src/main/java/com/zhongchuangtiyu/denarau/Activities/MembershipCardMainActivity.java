package com.zhongchuangtiyu.denarau.Activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhongchuangtiyu.denarau.Adapters.MemberShipViewPagerAdapter;
import com.zhongchuangtiyu.denarau.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MembershipCardMainActivity extends AppCompatActivity
{

    @Bind(R.id.membershipCardMainTitleLeft)
    ImageButton membershipCardMainTitleLeft;
    @Bind(R.id.membershipCardMainTitleRight)
    ImageButton membershipCardMainTitleRight;
    @Bind(R.id.membershipCardMainToolbar)
    Toolbar membershipCardMainToolbar;
    @Bind(R.id.membershipCardViewPager)
    ViewPager membershipCardViewPager;
    @Bind(R.id.membershipCardNoticeInfo)
    TextView membershipCardNoticeInfo;
    @Bind(R.id.trumpetImageView)
    ImageView trumpetImageView;
    @Bind(R.id.moreInfoImageButton)
    ImageButton moreInfoImageButton;
    @Bind(R.id.btnOrderPositon)
    RelativeLayout btnOrderPositon;
    @Bind(R.id.btnCoachTurorial)
    RelativeLayout btnCoachTurorial;
    @Bind(R.id.btnMeberStore)
    RelativeLayout btnMeberStore;
    @Bind(R.id.btnCostHistory)
    RelativeLayout btnCostHistory;
    @Bind(R.id.btnFoodService)
    RelativeLayout btnFoodService;
    @Bind(R.id.btnGiveAdvice)
    RelativeLayout btnGiveAdvice;
    private List<View> pagerViews;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        membershipCardMainToolbar = (Toolbar) findViewById(R.id.membershipCardMainToolbar);
        setSupportActionBar(membershipCardMainToolbar);

    }

}
