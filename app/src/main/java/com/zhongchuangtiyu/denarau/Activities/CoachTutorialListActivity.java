package com.zhongchuangtiyu.denarau.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.zhongchuangtiyu.denarau.Adapters.CoachListAdapter;
import com.zhongchuangtiyu.denarau.Entities.Coaches;
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

public class CoachTutorialListActivity extends AppCompatActivity
{

    @Bind(R.id.coachTutorialTitleLeft)
    ImageButton coachTutorialTitleLeft;
    @Bind(R.id.coachTutorialToolBar)
    Toolbar coachTutorialToolBar;
    @Bind(R.id.coachTutorialListView)
    ListView coachTutorialListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_tutorial_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.coachTutorialToolBar);
        setSupportActionBar(toolbar);
        sendCoachRequest();
    }

    private void sendCoachRequest()
    {
        Map<String,String> map = new HashMap<>();
        String token = CacheUtils.getString(CoachTutorialListActivity.this,"token",null);
        String club_uuid = CacheUtils.getString(CoachTutorialListActivity.this,"clubuuid",null);
        MyApplication.volleyGET(APIUrls.COACHES_URL + "token=" + token + "&" + "club_uuid=" + club_uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                Coaches data = Coaches.instance(response);
                List<Coaches> result = data.generateListInfo();
                CoachListAdapter adapter = new CoachListAdapter(result,CoachTutorialListActivity.this);
                coachTutorialListView.setAdapter(adapter);
                //setAdapter
            }

            @Override
            public void netFail(VolleyError error)
            {
                Toast.makeText(CoachTutorialListActivity.this, "无数据", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
