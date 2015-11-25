package com.zhongchuangtiyu.denarau.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Adapters.ProvisionsFragmentAdapter;
import com.zhongchuangtiyu.denarau.CustomComponents.ProvButton;
import com.zhongchuangtiyu.denarau.Entities.Provision;
import com.zhongchuangtiyu.denarau.Entities.Provisions;
import com.zhongchuangtiyu.denarau.Fragments.ProvisionFragments;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.StatusBarCompat;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

public class ProvisionsActivity extends BaseActivity
{

    @Bind(R.id.buttonContainerLl)
    LinearLayout buttonContainerLl;
    //    @Bind(R.id.iv_bottom_line)
    ImageView ivBottomLine;
    @Bind(R.id.provisionsViewPager)
    ViewPager provisionsViewPager;
    @Bind(R.id.provitionsTitleLeft)
    ImageButton provitionsTitleLeft;
    private ArrayList<ProvisionFragments> list = new ArrayList<>();
    private Resources resources;
    private LinearLayout linearLayoutBtnIn;
    private ProvButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provisions);
        ButterKnife.bind(this);
        StatusBarCompat.compat(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sendProvisionsRequest();
        JPushInterface.init(getApplicationContext());
        setListeners();
        ActivityCollector.addActivity(this);
    }

    private void setListeners()
    {
        provitionsTitleLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    private void sendProvisionsRequest()
    {
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(ProvisionsActivity.this, "token", null);
        String club_uuid = CacheUtils.getString(ProvisionsActivity.this, "clubuuid", null);
        MyApplication.volleyGET(APIUrls.PROVISIONS_URL + "token=" + token + "&" + "club_uuid=" + club_uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(ProvisionsActivity.this, "登录失效，请重新登录");
                    CacheUtils.putString(ProvisionsActivity.this, "token", null);
                    CacheUtils.putString(ProvisionsActivity.this, "registration_id", null);
                    startActivity(new Intent(ProvisionsActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    List<Provision> data = Provisions.instance(response);
                    Map<String, List<Provision>> provGroup = new HashMap<String, List<Provision>>();
                    for (Provision prov : data)
                    {
                        if (provGroup.containsKey(prov.getType()))
                        {
                            List<Provision> tmp = provGroup.get(prov.getType());
                            tmp.add(prov);
                        } else
                        {
                            List<Provision> tmp = new ArrayList<Provision>();
                            tmp.add(prov);
                            provGroup.put(prov.getType(), tmp);
                        }
                    }

                    int index = 0;
                    for (String type : provGroup.keySet())
                    {
                        WindowManager wm = (WindowManager) ProvisionsActivity.this
                                .getSystemService(Context.WINDOW_SERVICE);
                        int width = wm.getDefaultDisplay().getWidth();
                        Xlog.d(String.valueOf(width) + "width-----------------------------------");
                        button = new ProvButton(ProvisionsActivity.this);
                        linearLayoutBtnIn = new LinearLayout(ProvisionsActivity.this);
                        button.setText(type);
                        button.setTag(index++);
                        button.setTextSize(20);
                        button.setHeight(80);

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width / 4, LinearLayout.LayoutParams.MATCH_PARENT);
                        LinearLayout.LayoutParams lpll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        lpll.setMargins(10, 3, 10, 3);
                        linearLayoutBtnIn.setLayoutParams(lpll);
                        lp.setMargins(0, 0, 0, 6);
                        button.setLayoutParams(lp);
                        button.setGravity(Gravity.CENTER);
                        button.setTextColor(Color.parseColor("#9a9b9b"));
                        button.setBackgroundColor(Color.parseColor("#292d2f"));
                        button.setViewPager(provisionsViewPager);
                        button.setLinearLayout(buttonContainerLl);
                        button.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                provisionsViewPager.setCurrentItem(Integer.valueOf(v.getTag().toString()).intValue(), true);
                                for (int i = 0; i < ((ProvButton) v).getLinearLayout().getChildCount(); i++)
                                {
                                    LinearLayout buttonContainer = ((ProvButton) v).getLinearLayout();
                                    ((ProvButton) ((LinearLayout) buttonContainer.getChildAt(i)).getChildAt(0)).setTextColor(Color.parseColor("#9a9b9b"));
                                }
                                ((ProvButton) v).setTextColor(Color.parseColor("#ffffff"));
                            }
                        });
                        buttonContainerLl.addView(linearLayoutBtnIn);
                        linearLayoutBtnIn.addView(button);
                        ProvisionFragments fragment = ProvisionFragments.newInstance(provGroup.get(type));
                        list.add(fragment);

                    }
                    buttonContainerLl.getChildAt(0).setBackgroundColor(Color.parseColor("#ffffff"));
                    ((ProvButton) ((LinearLayout) buttonContainerLl.getChildAt(0)).getChildAt(0)).setTextColor(Color.parseColor("#ffffff"));
                    provisionsViewPager.setAdapter(new ProvisionsFragmentAdapter(getSupportFragmentManager(), list));
                    provisionsViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
                    {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
                        {


                        }

                        @Override
                        public void onPageSelected(int position)
                        {
                            int count = buttonContainerLl.getChildCount();
                            for (int i = 0; i < count; i++)
                            {
                                buttonContainerLl.getChildAt(i).setBackgroundColor(Color.parseColor("#292d2f"));
                                ((ProvButton) ((LinearLayout) buttonContainerLl.getChildAt(i)).getChildAt(0)).setTextColor(Color.parseColor("#9a9b9b"));

                            }
                            buttonContainerLl.getChildAt(position).setBackgroundColor(Color.parseColor("#ffffff"));
                            ((ProvButton) ((LinearLayout) buttonContainerLl.getChildAt(position)).getChildAt(0)).setTextColor(Color.parseColor("#ffffff"));

                        }

                        @Override
                        public void onPageScrollStateChanged(int state)
                        {

                        }
                    });
                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                if (error.toString().contains("AuthFailureError"))
                {
                    CustomToast.showToast(ProvisionsActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(ProvisionsActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                }else
                {
                    CustomToast.showToast(ProvisionsActivity.this, "网络连接失败，请检查网络连接");
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
}

