package com.zhongchuangtiyu.denarau.Activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhongchuangtiyu.denarau.Adapters.PrivateCoursesGridAdapter;
import com.zhongchuangtiyu.denarau.Entities.PrivateCourses;
import com.zhongchuangtiyu.denarau.Fragments.MyDialogFragment;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.DateUtils;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.NoScrollGridView;
import com.zhongchuangtiyu.denarau.Utils.StatusBarCompat;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PrivateCoursesActivity extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.cardBagListTitleLeft)
    ImageButton cardBagListTitleLeft;
    @Bind(R.id.courseOrderToolbar)
    Toolbar courseOrderToolbar;
    @Bind(R.id.privateCoachImage)
    ImageView privateCoachImage;
    @Bind(R.id.privateCoachName)
    TextView privateCoachName;
    @Bind(R.id.privateCoachTitle)
    TextView privateCoachTitle;
    @Bind(R.id.privateCoachWebView)
    WebView privateCoachWebView;
    @Bind(R.id.btnToday)
    Button btnToday;
    @Bind(R.id.btnTomorrow)
    Button btnTomorrow;
    @Bind(R.id.btnTheDayAfterTomorrow)
    Button btnTheDayAfterTomorrow;
    @Bind(R.id.linearLayout4)
    LinearLayout linearLayout4;
    @Bind(R.id.privateCoursesGridView)
    NoScrollGridView privateCoursesGridView;
    @Bind(R.id.btnPrivateCoursesOrder)
    RelativeLayout btnPrivateCoursesOrder;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_order);
        ButterKnife.bind(this);
        StatusBarCompat.compat(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.courseOrderToolbar);
        setSupportActionBar(toolbar);
        ActivityCollector.addActivity(this);
        privateCoursesGridView.setVerticalSpacing(2);
        btnToday.setSelected(true);
        sendRequest();
        setListeners();
    }

    private void setListeners()
    {
        btnPrivateCoursesOrder.setOnClickListener(this);
        cardBagListTitleLeft.setOnClickListener(this);
    }

    private void sendRequest()
    {
        String token = CacheUtils.getString(PrivateCoursesActivity.this, "token", null);
        String club_uuid = CacheUtils.getString(PrivateCoursesActivity.this, "clubuuid", null);
        Intent intent = getIntent();
        String uuid = intent.getStringExtra("uuid");
        CacheUtils.putString(PrivateCoursesActivity.this, "privateCourseUuid", uuid);
        Map<String, String> map = new HashMap<>();
        MyApplication.volleyGET(APIUrls.PRIVATE_COURSES + "token=" + token + "&" + "club_uuid=" + club_uuid + "&" + "uuid=" + uuid, map, new MyApplication.VolleyCallBack() {
            @Override
            public void netSuccess(String response) {
                final PrivateCourses data = PrivateCourses.instance(response);
                final List<PrivateCourses> list = data.generateListTodayInfo();
                List<PrivateCourses.RecentlyScheduleEntity.ScheduleEntity> result = list.get(0).getScheduleEntity();
                CacheUtils.putString(PrivateCoursesActivity.this, "whichDay", "0");
                PrivateCoursesGridAdapter adapter = new PrivateCoursesGridAdapter(PrivateCoursesActivity.this, result);
                privateCoursesGridView.setAdapter(adapter);
                imageLoader.displayImage(data.getCoach().getPortrait(), privateCoachImage);
                privateCoachName.setText(data.getCoach().getName());
                privateCoachTitle.setText(data.getCoach().getTitle());
//                privateCoachWebView.loadData(data.getDescription(), "text/html", "UTF-8");
                WebSettings settings = privateCoachWebView.getSettings();
                settings.setTextSize(WebSettings.TextSize.NORMAL);
                privateCoachWebView.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
                privateCoachWebView.loadData(data.getDescription(), "text/html; charset=UTF-8", null);//这种写法可以正确解码
//                privateCoachWebView.getSettings().setUseWideViewPort(true);
//                privateCoachWebView.getSettings().setLoadWithOverviewMode(true);
                btnToday.setText("今天" + DateUtils.getDateToString3(Long.valueOf(1454256000)));
                Xlog.d("今天" + data.getRecently_schedule().get(0).getDate());
                btnTomorrow.setText("明天" + DateUtils.getDateToString3(Long.valueOf(data.getRecently_schedule().get(1).getDate())));
                btnTheDayAfterTomorrow.setText("后天" + DateUtils.getDateToString3(Long.valueOf(data.getRecently_schedule().get(2).getDate())));
                btnToday.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnToday.setSelected(true);
                        btnTomorrow.setSelected(false);
                        btnTheDayAfterTomorrow.setSelected(false);
                        List<PrivateCourses.RecentlyScheduleEntity.ScheduleEntity> result = list.get(0).getScheduleEntity();
                        PrivateCoursesGridAdapter adapter = new PrivateCoursesGridAdapter(PrivateCoursesActivity.this, result);
                        privateCoursesGridView.setAdapter(adapter);
                        CacheUtils.putString(PrivateCoursesActivity.this, "whichDay", "0");
                    }
                });
                btnTomorrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnTomorrow.setSelected(true);
                        btnToday.setSelected(false);
                        btnTheDayAfterTomorrow.setSelected(false);
                        List<PrivateCourses.RecentlyScheduleEntity.ScheduleEntity> result = list.get(1).getScheduleEntity();
                        PrivateCoursesGridAdapter adapter = new PrivateCoursesGridAdapter(PrivateCoursesActivity.this, result);
                        privateCoursesGridView.setAdapter(adapter);
                        CacheUtils.putString(PrivateCoursesActivity.this, "whichDay", "1");
                    }
                });
                btnTheDayAfterTomorrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnTheDayAfterTomorrow.setSelected(true);
                        btnToday.setSelected(false);
                        btnTomorrow.setSelected(false);
                        List<PrivateCourses.RecentlyScheduleEntity.ScheduleEntity> result = list.get(2).getScheduleEntity();
                        PrivateCoursesGridAdapter adapter = new PrivateCoursesGridAdapter(PrivateCoursesActivity.this, result);
                        privateCoursesGridView.setAdapter(adapter);
                        CacheUtils.putString(PrivateCoursesActivity.this, "whichDay", "2");
                    }
                });
//                PrivateCoursesGridAdapter adapter = new PrivateCoursesGridAdapter(PrivateCoursesActivity.this,result);
//                privateCoursesGridView.setAdapter(adapter);
            }

            @Override
            public void netFail(VolleyError error) {
                CustomToast.showToast(PrivateCoursesActivity.this, "网络连接失败，请稍后再试");
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
            case R.id.btnPrivateCoursesOrder:
                showDialog(v);
                break;
            case R.id.cardBagListTitleLeft:
                finish();
                break;
            default:
                break;
        }
    }
    public void showDialog(View view)
    {

        FragmentManager manager = getFragmentManager();
        MyDialogFragment dialog = new MyDialogFragment();
        dialog.show (manager, "dialog");
    }
}
