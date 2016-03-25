package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Entities.TabsDetail;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.DateUtils;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SwipeCardDetail extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.swipeCardDetailTitleLeft)
    ImageButton swipeCardDetailTitleLeft;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.sequence)
    TextView sequence;
    @Bind(R.id.reception_payment)
    TextView receptionPayment;
    @Bind(R.id.entranceDate)
    TextView entranceDate;
    @Bind(R.id.tabs_time)
    TextView tabsTime;
    @Bind(R.id.tabListView2)
    LinearLayout tabListView2;
    @Bind(R.id.tvState)
    TextView tvState;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_card_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setListeners();
        sendRequest();
        ActivityCollector.addActivity(this);
    }

    private void setListeners()
    {
        swipeCardDetailTitleLeft.setOnClickListener(this);
    }

    private void sendRequest()
    {
        String token = CacheUtils.getString(SwipeCardDetail.this, "token", null);
        Intent intent = getIntent();
        String uuid = intent.getStringExtra("uuid");
        Map<String, String> map = new HashMap<>();
        MyApplication.volleyGET(APIUrls.TABS_DETAIL + "token=" + token + "&" + "uuid=" + uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(SwipeCardDetail.this, "登录失效，请重新登录");
                    CacheUtils.putString(SwipeCardDetail.this, "token", null);
                    CacheUtils.putString(SwipeCardDetail.this, "registration_id", null);
                    startActivity(new Intent(SwipeCardDetail.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    TabsDetail data = TabsDetail.instance(response);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    if (data.getItems() != null)
                    {
                        for (int i = 0; i < data.getItems().size(); i++)
                        {
                            params.setMargins(0, 15, 0, 15);
                            LinearLayout linearLayout = new LinearLayout(SwipeCardDetail.this);
                            linearLayout.setLayoutParams(params);

                            ImageView imageView = new ImageView(SwipeCardDetail.this);
                            imageView.setMinimumWidth(100);
                            imageView.setMinimumHeight(1);
                            imageView.setBackgroundColor(Color.parseColor("#e47778"));

                            TextView name = new TextView(SwipeCardDetail.this);
                            TextView price = new TextView(SwipeCardDetail.this);
                            TextView method = new TextView(SwipeCardDetail.this);
                            name.setText(data.getItems().get(i).getName());
                            name.setGravity(Gravity.CENTER_HORIZONTAL);
                            name.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                            price.setText(data.getItems().get(i).getTotal_price());
                            price.setGravity(Gravity.CENTER_HORIZONTAL);
                            price.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                            switch (data.getItems().get(i).getPayment_method())
                            {
                                case "by_ball_member":
                                    method.setText("计球卡");
                                    break;
                                case "by_time_member":
                                    method.setText("计时卡");
                                    break;
                                case "unlimited_member":
                                    method.setText("畅打卡");
                                    break;
                                case "stored_member":
                                    method.setText("储值卡");
                                    break;
                                case "credit_card":
                                    method.setText("信用卡");
                                    break;
                                case "cash":
                                    method.setText("现金");
                                    break;
                                case "check":
                                    method.setText("支票");
                                    break;
                                case "on_account":
                                    method.setText("挂账");
                                    break;
                                case "signing":
                                    method.setText("签单");
                                    break;
                                case "coupon":
                                    method.setText("抵用卷");
                                    break;
                                default:
                                    break;
                            }
                            switch (data.getState())
                            {
                                case "finished":
                                    tvState.setText("已完成");
                                    tvState.setVisibility(View.VISIBLE);
                                    break;
                                case "progressing":
                                    tvState.setText("进行中");
                                    tvState.setVisibility(View.VISIBLE);
                                    break;
                                case "cancelled":
                                    tvState.setText("已取消");
                                    tvState.setVisibility(View.VISIBLE);
                                    break;
                                case "voided":
                                    tvState.setText("已撤销");
                                    tvState.setVisibility(View.VISIBLE);
                                    CustomToast.showToast(SwipeCardDetail.this, "Voided");
                                    break;
                            }
                            method.setGravity(Gravity.CENTER_HORIZONTAL);
                            method.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                            linearLayout.addView(name);
                            linearLayout.addView(price);
                            linearLayout.addView(method);

                            tabListView2.addView(imageView);
                            tabListView2.addView(linearLayout);
//                Resources res = context.getResources();
//                viewholder.tabListView2.setDividerDrawable(res.getDrawable(R.drawable.tabs_divider));
//                viewholder.tabListView2.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE | LinearLayout.SHOW_DIVIDER_BEGINNING);
                        }
                    }
//                TabListAdapter2 adapter2 = new TabListAdapter2(tabs.getItems(), context);
//                viewholder.tabListView2.setAdapter(adapter2);
//                SetListViewHeight.setListViewHeightBasedOnChildren(viewholder.tabListView2);
                    sequence.setText(data.getSequence());
                    receptionPayment.setText(data.getReception_payment());
                    String date = DateUtils.getDateToString1(data.getDeparture_time());
                    entranceDate.setText(date);
                    String entranceTime = DateUtils.getDateToString2(data.getEntrance_time());
                    String departureTime = DateUtils.getDateToString2(data.getDeparture_time());
                    tabsTime.setText(entranceTime + "-" + departureTime);
                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                if (error.toString().contains("AuthFailureError"))
                {
                    CustomToast.showToast(SwipeCardDetail.this, "登录失效，请重新登录");
                    startActivity(new Intent(SwipeCardDetail.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    CustomToast.showToast(SwipeCardDetail.this, "网络连接失败，请检查网络连接");
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
            case R.id.swipeCardDetailTitleLeft:
                finish();
                ActivityCollector.removeActivity(this);
                break;
            default:
                break;
        }
    }
}
