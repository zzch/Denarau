package com.zhongchuangtiyu.denarau.Activities;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Adapters.CardbagListAdapter;
import com.zhongchuangtiyu.denarau.Entities.ClubsMembership;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import butterknife.Bind;
        import butterknife.ButterKnife;

public class CardBagListActivity extends AppCompatActivity
{

    @Bind(R.id.cardBagListTitleLeft)
    ImageButton cardBagListTitleLeft;
    @Bind(R.id.cardBagListToolbar)
    Toolbar cardBagListToolbar;
    @Bind(R.id.cardBagListView)
    ListView cardBagListView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_bag_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.cardBagListToolbar);
        setSupportActionBar(toolbar);
        sendCardbagRequest();
    }


    private void sendCardbagRequest()
    {
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(CardBagListActivity.this,"token",null);
        MyApplication.volleyGET(APIUrls.CARDBAGLIST_URL + token, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                final List<ClubsMembership> data = ClubsMembership.instance(response);
                CardbagListAdapter adapter = new CardbagListAdapter(data,CardBagListActivity.this);
                cardBagListView.setAdapter(adapter);
                cardBagListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        ClubsMembership clubsMembership = data.get(position);
                        String club_uuid = clubsMembership.getUuid();
                        String token = CacheUtils.getString(CardBagListActivity.this,"token","aa");

                        CacheUtils.putString(CardBagListActivity.this,"clubuuid",club_uuid);
                        Xlog.d(token + " token-----------------------------------");
                        Xlog.d(club_uuid + "-----------------------------------");
                        startActivity(new Intent(CardBagListActivity.this, MembershipCardMainActivity.class));
                        finish();
                    }
                });
            }

            @Override
            public void netFail(VolleyError error)
            {
                Toast.makeText(CardBagListActivity.this,"无数据",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
