package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Adapters.PromotionsListAdapter;
import com.zhongchuangtiyu.denarau.Entities.Promotions;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class PromotionsActivity extends BaseActivity
{

    @Bind(R.id.promotionsTitleLeft)
    ImageButton promotionsTitleLeft;
    @Bind(R.id.promotionsListView)
    ListView promotionsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sendProtionsRequest();
        setListeners();
    }

    private void setListeners()
    {

    }

    private void sendProtionsRequest()
    {
        Map<String, String> map = new HashMap<>();
        final String token = CacheUtils.getString(PromotionsActivity.this, "token", null);
        String club_uuid = CacheUtils.getString(PromotionsActivity.this, "clubuuid", null);
        MyApplication.volleyGET(APIUrls.PROMOTIONS + "token=" + token + "&" + "club_uuid=" + club_uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(PromotionsActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(PromotionsActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    final List<Promotions> data = Promotions.instance(response);
                    final PromotionsListAdapter adapter = new PromotionsListAdapter(data, PromotionsActivity.this);
                    promotionsListView.setAdapter(adapter);
                    promotionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            String uuid = data.get(position).getUuid();
                            Intent intent = new Intent(PromotionsActivity.this, PromotionsDetailActivity.class);
                            intent.putExtra("uuid", uuid);
                            startActivity(intent);
                        }
                    });
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                CustomToast.showToast(PromotionsActivity.this, "刷新失败，请检查网络连接");
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
