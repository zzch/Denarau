package com.zhongchuangtiyu.denarau.Activities;

import android.content.Context;
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
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProvisionsActivity extends AppCompatActivity
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sendProvisionsRequest();
        setListeners();
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
                    button.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            provisionsViewPager.setCurrentItem(Integer.valueOf(v.getTag().toString()).intValue(), true);
                        }
                    });
                    buttonContainerLl.addView(linearLayoutBtnIn);
                    linearLayoutBtnIn.addView(button);
                    buttonContainerLl.getChildAt(0).setBackgroundColor(Color.parseColor("#ffffff"));
                    ProvisionFragments fragment = ProvisionFragments.newInstance(provGroup.get(type));
                    list.add(fragment);

                }
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

                        }
                        buttonContainerLl.getChildAt(position).setBackgroundColor(Color.parseColor("#ffffff"));

                    }

                    @Override
                    public void onPageScrollStateChanged(int state)
                    {

                    }
                });
            }

            @Override
            public void netFail(VolleyError error)
            {

            }
        });
    }

}

