package com.zhongchuangtiyu.denarau.Activities;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProvisionsActivity extends FragmentActivity
{

    @Bind(R.id.buttonContainerLl)
    LinearLayout buttonContainerLl;
//    @Bind(R.id.iv_bottom_line)
    ImageView ivBottomLine;
    @Bind(R.id.provisionsViewPager)
    ViewPager provisionsViewPager;
    private ArrayList<ProvisionFragments> list = new ArrayList<>();
    private Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provisions);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        sendProvisionsRequest();
    }

    private void sendProvisionsRequest()
    {
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(ProvisionsActivity.this, "token", null);
        String club_uuid = CacheUtils.getString(ProvisionsActivity.this,"clubuuid",null);
        MyApplication.volleyGET(APIUrls.PROVISIONS_URL + "token=" + token + "&" + "club_uuid=" + club_uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                List<Provision> data = Provisions.instance(response);
                Map<String,List<Provision>> provGroup = new HashMap<String, List<Provision>>();
                for (Provision prov: data
                     )
                {
                    if(provGroup.containsKey(prov.getType()))
                    {
                        List<Provision> tmp = provGroup.get(prov.getType());
                        tmp.add(prov);
                    }else
                    {
                        List<Provision> tmp = new ArrayList<Provision>();
                        tmp.add(prov);
                        provGroup.put(prov.getType(), tmp);
                    }
                }

                int index = 0;
                for (String type : provGroup.keySet())
                {
                    ProvButton button = new ProvButton(ProvisionsActivity.this);
                    button.setText(type);
                    button.setTag(index++);
                    button.setViewPager(provisionsViewPager);
                    button.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            provisionsViewPager.setCurrentItem(Integer.valueOf(v.getTag().toString()).intValue(),true);
                        }
                    });
                    buttonContainerLl.addView(button);
                    ProvisionFragments fragment = ProvisionFragments.newInstance(provGroup.get(type));
                    list.add(fragment);

                }
                provisionsViewPager.setAdapter(new ProvisionsFragmentAdapter(getSupportFragmentManager(),list));
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
                            buttonContainerLl.getChildAt(i).setBackgroundColor(Color.WHITE);
                        }
                        buttonContainerLl.getChildAt(position).setBackgroundColor(Color.RED);
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

