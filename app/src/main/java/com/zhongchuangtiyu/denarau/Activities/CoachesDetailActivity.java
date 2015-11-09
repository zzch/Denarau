package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhongchuangtiyu.denarau.Adapters.CoachDetailListAdapter;
import com.zhongchuangtiyu.denarau.Entities.CoachesDetail;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomListView;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.SetListViewHeight;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CoachesDetailActivity extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.coachDetailImage)
    ImageView coachDetailImage;
    @Bind(R.id.coachDetailName)
    TextView coachDetailName;
    @Bind(R.id.coachDetailType)
    TextView coachDetailType;
    @Bind(R.id.coachDetailListView)
    CustomListView coachDetailListView;
    @Bind(R.id.coachDetailIntro)
    WebView coachDetailIntro;
    @Bind(R.id.coachTutorialDetailTitleLeft)
    ImageButton coachTutorialDetailTitleLeft;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coaches_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(CoachesDetailActivity.this));
        setSupportActionBar(toolbar);
        sendCoachesDetailRequest();
        setListeners();
        ActivityCollector.addActivity(this);
    }

    private void setListeners()
    {
        coachTutorialDetailTitleLeft.setOnClickListener(this);
    }

    private void sendCoachesDetailRequest()
    {
        Map<String, String> map = new HashMap<>();
        Intent intent = getIntent();
        String uuid = intent.getStringExtra("uuid");
        final String token = CacheUtils.getString(CoachesDetailActivity.this, "token", null);
        final String club_uuid = CacheUtils.getString(CoachesDetailActivity.this, "clubuuid", null);
        MyApplication.volleyGET(APIUrls.COACHES_DETAIL_URL + "token=" + token + "&" + "club_uuid=" + club_uuid + "&" + "uuid=" + uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(CoachesDetailActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(CoachesDetailActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    final CoachesDetail data = CoachesDetail.instance(response);
                    if (data.getPortrait() != null)
                    {
                        imageLoader.displayImage(data.getPortrait().toString(), coachDetailImage);
                    }
                    coachDetailName.setText(data.getName());
                    coachDetailType.setText(data.getTitle());
                    coachDetailIntro.loadData(data.getDescription(), "text/html", "UTF-8");
                    CoachDetailListAdapter adapter = new CoachDetailListAdapter(data.getCourses(), CoachesDetailActivity.this);
                    Xlog.d(data.getCourses() + "data.getCourses()--------------------------------------------------");
                    coachDetailListView.setAdapter(adapter);
                    SetListViewHeight.setListViewHeightBasedOnChildren(coachDetailListView);
                    coachDetailListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            String uuid = data.getCourses().get(position).getUuid();
                            Intent intent1 = new Intent(CoachesDetailActivity.this, CoachTutorialDetailCoursesActivity.class);
                            intent1.putExtra("token", token);
                            intent1.putExtra("club_uuid", club_uuid);
                            intent1.putExtra("uuid", uuid);
                            startActivity(intent1);
                        }
                    });
                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                if (error.toString().contains("AuthFailureError"))
                {
                    CustomToast.showToast(CoachesDetailActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(CoachesDetailActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                }else
                {
                    CustomToast.showToast(CoachesDetailActivity.this, "网络连接失败，请检查网络连接");
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
            case R.id.coachTutorialDetailTitleLeft:
                finish();
                break;
            default:
                break;
        }
    }
}
