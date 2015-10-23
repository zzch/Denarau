package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhongchuangtiyu.denarau.Adapters.CardbagListAdapter;
import com.zhongchuangtiyu.denarau.Adapters.MembershipCardViewpagerAdapter;
import com.zhongchuangtiyu.denarau.Entities.ClubsHome;
import com.zhongchuangtiyu.denarau.Entities.ClubsMembership;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import org.w3c.dom.Text;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MembershipCardMainActivity extends AppCompatActivity implements View.OnClickListener
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
//    private MembershipCardViewpagerAdapter adapter;
    private View view;
    private PagerAdapter adapter;
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
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
        setListeners();
        requestCardInfo();
    }

    private void requestCardInfo()
    {
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(MembershipCardMainActivity.this, "token","aa");
        String club_uuid = CacheUtils.getString(MembershipCardMainActivity.this, "clubuuid","aa");
        Xlog.d(club_uuid + "club_uuid----------------------------------------");

        MyApplication.volleyGET(APIUrls.CLUBS_HOME_URL + "token=" + token + "&" + "club_uuid=" + club_uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                final ClubsHome data = ClubsHome.instance(response);
                pagerViews = new ArrayList<View>();
                String size = String.valueOf(data.getMembers().size());
                Xlog.d(size + "size----------------------------------------");
                for (int i = 0; i < data.getMembers().size(); i++)
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
//                    membershipCardViewPagerRoot.setBackgroundColor(Color.parseColor("#" + data.getMembers().get(i).getCard().getBackground_color()));
                    GradientDrawable myGrad = (GradientDrawable) membershipCardViewPagerRoot.getBackground();
                    myGrad.setColor(Color.parseColor("#" + data.getMembers().get(i).getCard().getBackground_color()));
                    final int finalI = i;
                    membershipCardViewPagerRoot.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            Toast.makeText(MembershipCardMainActivity.this, data.getMembers().get(finalI).getCard().getName(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    pagerViews.add(view);
//                    adapter = new MembershipCardViewpagerAdapter(pagerViews,MembershipCardMainActivity.this);
//                    membershipCardViewPager.setAdapter(adapter);
                        adapter = new PagerAdapter()
                        {
                            @Override
                            public int getCount()
                            {
                                return pagerViews.size();
                            }

                            @Override
                            public boolean isViewFromObject(View view, Object object)
                            {
                                return view == object;
                            }

                            public Object instantiateItem(ViewGroup container, int position)
                            {
//                            View item = pagerViews.get(position);
//                            container.addView( item);
//                            return item;
                                try
                                {
                                    if (pagerViews.get(position).getParent() == null)
                                    {
                                        container.addView(pagerViews.get(position));
                                    } else
                                    {
                                        ((ViewGroup) pagerViews.get(position).getParent()).removeView(pagerViews.get(position));
                                        container.addView(pagerViews.get(position));
                                    }
                                } catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                return pagerViews.get(position);
                            }

                            @Override
                            public Parcelable saveState()
                            {
                                return super.saveState();
                            }

                            public void destroyItem(android.view.ViewGroup container, int position, Object object)
                            {
//                            container.removeView( (View)object);
                            }
                        };
                    membershipCardViewPager.setAdapter(adapter);
                }
            }

            @Override
            public void netFail(VolleyError error)
            {

            }
        });
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
                startActivity(new Intent(MembershipCardMainActivity.this,PositionOrderActivity.class));
                break;
            case R.id.btnGiveAdvice:
                startActivity(new Intent(MembershipCardMainActivity.this,FeedBackActivity.class));
        }
    }
}
