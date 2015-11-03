package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Adapters.PromotionsListAdapter;
import com.zhongchuangtiyu.denarau.Entities.Promotions;
import com.zhongchuangtiyu.denarau.Entities.Provision;
import com.zhongchuangtiyu.denarau.Entities.Provisions;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PromotionsActivity extends AppCompatActivity
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
    }

    private void sendProtionsRequest()
    {
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(PromotionsActivity.this, "token", null);
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
                } else
                {
                    final List<Promotions> data = Promotions.instance(response);
                    PromotionsListAdapter adapter = new PromotionsListAdapter(data, PromotionsActivity.this);
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
                }
            }

            @Override
            public void netFail(VolleyError error)
            {

            }
        });
    }

}
