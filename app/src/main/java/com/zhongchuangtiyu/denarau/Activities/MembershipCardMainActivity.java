package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhongchuangtiyu.denarau.Adapters.MembershipCardViewpagerAdapter;
import com.zhongchuangtiyu.denarau.Entities.Announcements;
import com.zhongchuangtiyu.denarau.Entities.ClubsHome;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MembershipCardMainActivity extends BaseActivity implements View.OnClickListener
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
    @Bind(R.id.btnOrderPositon)
    RelativeLayout btnOrderPositon;
    @Bind(R.id.btnCoachTurorial)
    RelativeLayout btnCoachTurorial;
    @Bind(R.id.btnMemberStore)
    RelativeLayout btnMemberStore;
    @Bind(R.id.btnCostHistory)
    RelativeLayout btnCostHistory;
    @Bind(R.id.btnFoodService)
    RelativeLayout btnFoodService;
    @Bind(R.id.btnGiveAdvice)
    RelativeLayout btnGiveAdvice;
    @Bind(R.id.indicatorLinearLayout)
    LinearLayout indicatorLinearLayout;
    private List<View> pagerViews;
    private MembershipCardViewpagerAdapter adapter;
    private View view;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Button mPreSelectedBt;
    private boolean isSelected = true;
    private final List<String> list = new ArrayList<String>();
    private int j = 0;
    private Timer timer;

    final Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 1:
                    if (j < list.size()-1)
                    {
                        j++;
                    }else if (j == list.size() -1)
                    {
                        j = 0;
                    }
                    setAnnouncementOutAnimation();
                    Xlog.d(String.valueOf(j) + "j--------------------------------------");
                    break;
            }
            super.handleMessage(msg);
        }
    };


    TimerTask task = new TimerTask(){
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        membershipCardMainToolbar = (Toolbar) findViewById(R.id.membershipCardMainToolbar);
        imageLoader.init(ImageLoaderConfiguration.createDefault(MembershipCardMainActivity.this));
        membershipCardViewPager.setPageMargin(20);
        setSupportActionBar(membershipCardMainToolbar);
        sendAnnoucementsRequest();
        timer = new Timer(true);
        timer.schedule(task, 1000, 4000);
        setListeners();
    }
    private void sendAnnoucementsRequest()
    {
        Map map = new HashMap();
        String token = CacheUtils.getString(MembershipCardMainActivity.this, "token", null);
        String club_uuid = CacheUtils.getString(MembershipCardMainActivity.this, "clubuuid", null);

        MyApplication.volleyGET(APIUrls.ANNOUNCEMENTS + "token=" + token + "&" + "club_uuid=" + club_uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                List<Announcements> data = Announcements.instance(response);
                for (int i = 0; i < data.size(); i++)
                {
                    String text = data.get(i).getTitle();
                    list.add(text);
                }
                membershipCardNoticeInfo.setText(data.get(0).getTitle());
                Xlog.d(list.toString() + "list------------------------------------");
            }

            @Override
            public void netFail(VolleyError error)
            {
                CustomToast.showToast(MembershipCardMainActivity.this, "网络连接失败，请检查网络连接");
            }
        });
    }

    private void setAnnouncementOutAnimation()
    {
        Animation announcementOutAnimation = new TranslateAnimation(membershipCardNoticeInfo.getScaleX(), membershipCardNoticeInfo.getScaleX(), membershipCardNoticeInfo.getScaleY(), -40f);
        announcementOutAnimation.setDuration(500);
        membershipCardNoticeInfo.startAnimation(announcementOutAnimation);
        announcementOutAnimation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                setAnnouncementInAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }
    private void setAnnouncementInAnimation()
    {
        Animation announcementInAnimation = new TranslateAnimation(membershipCardNoticeInfo.getScaleX(), membershipCardNoticeInfo.getScaleX(), membershipCardNoticeInfo.getScaleY() + 55f, membershipCardNoticeInfo.getScaleY());
        announcementInAnimation.setDuration(500);
        membershipCardNoticeInfo.startAnimation(announcementInAnimation);
        announcementInAnimation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
                membershipCardNoticeInfo.setText(list.get(j));
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }
    private void initIndicators()
    {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_dot_normal);
        Button indicator = new Button(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(bitmap.getWidth(),bitmap.getHeight());
        layoutParams.setMargins(0, 0, 10, 0);
        indicator.setLayoutParams(layoutParams);
        indicatorLinearLayout.addView(indicator);
        Button button1 = (Button) indicatorLinearLayout.getChildAt(0);
        if (isSelected)
        {
            button1.setBackgroundResource(R.mipmap.home_page_dot_select);
        }else
        {
            button1.setBackgroundResource(R.mipmap.icon_dot_normal);
        }
        indicator.setBackgroundResource(R.mipmap.icon_dot_normal);
        membershipCardViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                if (!isSelected)
                {
                    Button button1 = (Button) indicatorLinearLayout.getChildAt(0);
                    button1.setBackgroundResource(R.mipmap.icon_dot_normal);
                }
            }

            @Override
            public void onPageSelected(int position)
            {

                if (mPreSelectedBt != null)
                {
                    mPreSelectedBt.setBackgroundResource(R.mipmap.icon_dot_normal);
                }
                if (position != 0)
                {
                    isSelected = false;
                } else
                {
                    isSelected = true;
                }
                Button currentBt = (Button) indicatorLinearLayout.getChildAt(position);
                currentBt.setBackgroundResource(R.mipmap.home_page_dot_select);
                mPreSelectedBt = currentBt;
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
    }
    private void requestCardInfo()
    {
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(MembershipCardMainActivity.this, "token", null);
        String club_uuid = CacheUtils.getString(MembershipCardMainActivity.this, "clubuuid", null);

        MyApplication.volleyGET(APIUrls.CLUBS_HOME_URL + "token=" + token + "&" + "club_uuid=" + club_uuid, map, new MyApplication.VolleyCallBack()
        {

            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(MembershipCardMainActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(MembershipCardMainActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    final ClubsHome data = ClubsHome.instance(response);
                    pagerViews = new ArrayList<View>();
                    int dataSize = data.getMembers().size();
                    for (int i = 0; i < dataSize; i++)
                    {
                        view = LayoutInflater.from(MembershipCardMainActivity.this).inflate(R.layout.membership_viewpager_item, null);
                        ImageView membershipViewPagerCourseImage = (ImageView) view.findViewById(R.id.membershipViewPagerCourseImage);
                        TextView membershipViewPagerCourseName = (TextView) view.findViewById(R.id.membershipViewPagerCourseName);
                        TextView membershipViewPagerCardType = (TextView) view.findViewById(R.id.membershipViewPagerCardType);
                        TextView membershipViewPagerCardBalance = (TextView) view.findViewById(R.id.membershipViewPagerCardBalance);
                        TextView membershipViewPagerCardNumber = (TextView) view.findViewById(R.id.membershipViewPagerCardNumber);
                        RelativeLayout membershipCardViewPagerRoot = (RelativeLayout) view.findViewById(R.id.membershipCardViewPagerRoot);
                        imageLoader.displayImage(data.getClub().getLogo(), membershipViewPagerCourseImage);
                        membershipViewPagerCourseName.setText(data.getClub().getName());
                        membershipViewPagerCardType.setText(data.getMembers().get(i).getCard().getName());
                        membershipViewPagerCardBalance.setText(data.getMembers().get(i).getBalance());
                        membershipViewPagerCardNumber.setText(data.getMembers().get(i).getNumber());
                        GradientDrawable myGrad = (GradientDrawable) membershipCardViewPagerRoot.getBackground();
                        myGrad.setColor(Color.parseColor("#" + data.getMembers().get(i).getCard().getBackground_color()));
                        pagerViews.add(view);
                        adapter = new MembershipCardViewpagerAdapter(pagerViews, MembershipCardMainActivity.this);
                        membershipCardViewPager.setAdapter(adapter);
                        final int finalI = i;
                        membershipCardViewPagerRoot.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Toast.makeText(MembershipCardMainActivity.this, data.getMembers().get(finalI).getCard().getName(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        adapter = new MembershipCardViewpagerAdapter(pagerViews, MembershipCardMainActivity.this);
                        if (dataSize > 1)
                        {
                            initIndicators();
                        }
                    }
                    membershipCardViewPager.setAdapter(adapter);
                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                CustomToast.showToast(MembershipCardMainActivity.this, "网络连接失败，请检查网络连接");
            }
        });
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        indicatorLinearLayout.removeAllViews();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        requestCardInfo();
    }

    private void setListeners()
    {
        membershipCardMainTitleRight.setOnClickListener(this);
        btnOrderPositon.setOnClickListener(this);
        btnGiveAdvice.setOnClickListener(this);
        btnCoachTurorial.setOnClickListener(this);
        btnFoodService.setOnClickListener(this);
        membershipCardMainTitleLeft.setOnClickListener(this);
        btnMemberStore.setOnClickListener(this);
        membershipCardNoticeInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.membershipCardMainTitleRight:
                startActivity(new Intent(MembershipCardMainActivity.this, CardBagListActivity.class));
                break;
            case R.id.btnOrderPositon:
                startActivity(new Intent(MembershipCardMainActivity.this, PositionOrderActivity.class));
                break;
            case R.id.btnGiveAdvice:
                startActivity(new Intent(MembershipCardMainActivity.this, FeedBackActivity.class));
                break;
            case R.id.btnCoachTurorial:
                startActivity(new Intent(MembershipCardMainActivity.this, CoachTutorialListActivity.class));
                break;
            case R.id.btnFoodService:
                startActivity(new Intent(MembershipCardMainActivity.this, ProvisionsActivity.class));
                break;
            case  R.id.membershipCardMainTitleLeft:
                startActivity(new Intent(MembershipCardMainActivity.this, PersonalCenterActivity.class));
                break;
            case R.id.btnMemberStore:
                startActivity(new Intent(MembershipCardMainActivity.this, PromotionsActivity.class));
                break;
            case R.id.membershipCardNoticeInfo:
                startActivity(new Intent(MembershipCardMainActivity.this, AnnouncementsListActivity.class));
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
