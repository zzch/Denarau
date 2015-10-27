package com.zhongchuangtiyu.denarau.Activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Adapters.ProvisionsFragmentAdapter;
import com.zhongchuangtiyu.denarau.Entities.Provisions;
import com.zhongchuangtiyu.denarau.Fragments.ProvisionFragments;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProvisionsActivity extends FragmentActivity
{

    @Bind(R.id.buttonContainerLl)
    LinearLayout buttonContainerLl;
    @Bind(R.id.iv_bottom_line)
    ImageView ivBottomLine;
    @Bind(R.id.provisionsViewPager)
    ViewPager provisionsViewPager;
    private ArrayList<ProvisionFragments> list = null;
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
                List<Provisions> data = Provisions.instance(response);
                for (int i = 0; i < data.size(); i++)
                {
                    Button button = new Button(ProvisionsActivity.this);
                    button.setText(data.get(i).getName());
                    buttonContainerLl.addView(button);
                    Fragment fragment = ProvisionFragments.newInstance(String.valueOf(i));
                    list = new ArrayList<>();
                    list.add(fragment);
                    provisionsViewPager.setAdapter(new ProvisionsFragmentAdapter(getSupportFragmentManager(),list))

                }
            }

            @Override
            public void netFail(VolleyError error)
            {

            }
        });
    }

}
