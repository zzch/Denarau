package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Adapters.ReservationsListAdapter;
import com.zhongchuangtiyu.denarau.Entities.Reservations;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

public class ReservationsActivity extends AppCompatActivity
{

    @Bind(R.id.reservationsListView)
    ListView reservationsListView;
    @Bind(R.id.reservationsFrame)
    PtrClassicFrameLayout reservationsFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sendRequest();
    }

    private void sendRequest()
    {
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(ReservationsActivity.this, "token", null);
        MyApplication.volleyGET(APIUrls.RESERVATIONS + "token=" + token, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(ReservationsActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(ReservationsActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    List<Reservations> data = Reservations.instance(response);
                    Xlog.d(String.valueOf(data.get(0).getWill_playing_at()) + "getWill_playing_at(-------------------------");
                    ReservationsListAdapter adapter = new ReservationsListAdapter(ReservationsActivity.this, data);
                    reservationsListView.setAdapter(adapter);
                }
            }

            @Override
            public void netFail(VolleyError error)
            {

            }
        });
    }

}
