package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Adapters.StudentsAndCoachesAdapter;
import com.zhongchuangtiyu.denarau.Entities.StudentsAndCoaches;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.StatusBarCompat;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

public class CoachTutorialListActivity extends BaseActivity implements View.OnClickListener
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
        StatusBarCompat.compat(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.coachTutorialToolBar);
        setSupportActionBar(toolbar);
        sendCoachRequest();
        JPushInterface.init(getApplicationContext());
        setListeners();
        ActivityCollector.addActivity(this);
    }

    private void setListeners()
    {
        coachTutorialTitleLeft.setOnClickListener(this);
    }

    private void sendCoachRequest()
    {
        Map<String,String> map = new HashMap<>();
        String token = CacheUtils.getString(CoachTutorialListActivity.this,"token",null);
        String club_uuid = CacheUtils.getString(CoachTutorialListActivity.this,"clubuuid",null);
        Xlog.d("clubUuid" + club_uuid);
        MyApplication.volleyGET(APIUrls.STUDENTS_AND_COACHES + "token=" + token + "&" + "club_uuid=" + club_uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(final String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(CoachTutorialListActivity.this, "登录失效，请重新登录");
                    CacheUtils.putString(CoachTutorialListActivity.this, "token", null);
                    CacheUtils.putString(CoachTutorialListActivity.this, "registration_id", null);
                    startActivity(new Intent(CoachTutorialListActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    StudentsAndCoaches data = StudentsAndCoaches.instance(response);
                    final List<StudentsAndCoaches> result = data.generateListInfo();
                    StudentsAndCoachesAdapter adapter = new StudentsAndCoachesAdapter(result, CoachTutorialListActivity.this);
                    coachTutorialListView.setAdapter(adapter);
                    coachTutorialListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            String uuid = result.get(position).getUuid();
                            Xlog.d("result.get(position).getType()result.get(position).getType()" + result.get(position).getType());
                            if (result.get(position).getType() == null)
                            {
                                Intent intent = new Intent(CoachTutorialListActivity.this, CoachesDetailActivity.class);
                                intent.putExtra("uuid", uuid);
                                startActivity(intent);
                            }else if (result.get(position).getType().equals("open"))
                            {
                                Intent intent = new Intent(CoachTutorialListActivity.this, OpenCoursesActivity.class);
                                intent.putExtra("uuid", uuid);
                                startActivity(intent);
                            }else if (result.get(position).getType().equals("private"))
                            {
                                Intent intent = new Intent(CoachTutorialListActivity.this, PrivateCoursesActivity.class);
                                intent.putExtra("uuid", uuid);
                                startActivity(intent);
                            }
//                            CustomToast.showToast(CoachTutorialListActivity.this, String.valueOf(position));
                        }
                    });
                }
                //setAdapter
            }

            @Override
            public void netFail(VolleyError error)
            {
                if (error.toString().contains("AuthFailureError"))
                {
                    CustomToast.showToast(CoachTutorialListActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(CoachTutorialListActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                }else
                {
                    CustomToast.showToast(CoachTutorialListActivity.this, "网络连接失败，请检查网络连接");
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
            case R.id.coachTutorialTitleLeft:
                finish();
                break;
            default:
                break;
        }
    }
}
