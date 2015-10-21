package com.zhongchuangtiyu.denarau.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Entities.Weathers;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PositionOrderActivity extends AppCompatActivity implements View.OnClickListener
{

    @Bind(R.id.positionOrderTitleLeft)
    ImageButton positionOrderTitleLeft;
    @Bind(R.id.positionOrderToolBar)
    Toolbar positionOrderToolBar;
    @Bind(R.id.positionOrderDate)
    TextView positionOrderDate;
    @Bind(R.id.weatherImageView)
    ImageView weatherImageView;
    @Bind(R.id.positionOrderWeatherTv)
    TextView positionOrderWeatherTv;
    @Bind(R.id.positionOrderTemperature)
    TextView positionOrderTemperature;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.btnToday)
    Button btnToday;
    @Bind(R.id.btnTomorrow)
    Button btnTomorrow;
    @Bind(R.id.btnTheDayAfterTomorrow)
    Button btnTheDayAfterTomorrow;
    @Bind(R.id.btnOrder)
    Button btnOrder;
    @Bind(R.id.positionOrderButtonRl)
    RelativeLayout positionOrderButtonRl;
    @Bind(R.id.positionOrderWind)
    TextView positionOrderWind;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_order);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.positionOrderToolBar);
        setSupportActionBar(toolbar);
        requestData();
        setListeners();
    }

    private void setListeners()
    {
        btnToday.setOnClickListener(this);
        btnTomorrow.setOnClickListener(this);
        btnTheDayAfterTomorrow.setOnClickListener(this);
    }

    private void requestData()
    {
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(PositionOrderActivity.this, "token", "aa");
        String club_uuid = CacheUtils.getString(PositionOrderActivity.this, "clubuuid", "aa");
        MyApplication.volleyGET(APIUrls.WEATHER_URL + "token=" + token + "&" + "club_uuid=" + club_uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                List<Weathers> data = Weathers.instance(response);
                int date = data.get(i).getDate();
                int day_of_week = data.get(i).getDay_of_week();
                String content = data.get(i).getContent();
                int day_code = data.get(i).getDay_code();
                int maximum_temperature = data.get(i).getMaximum_temperature();
                String probability_of_precipitation = data.get(i).getProbability_of_precipitation();
                String wind = data.get(i).getWind();
                String formatDate = String.valueOf(date);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formatedDate = simpleDateFormat.format(new Date(Long.parseLong(formatDate)));
                positionOrderDate.setText(formatedDate);
                positionOrderTemperature.setText(String.valueOf(maximum_temperature));
                positionOrderWeatherTv.setText(content);
                positionOrderWind.setText(wind);
            }

            @Override
            public void netFail(VolleyError error)
            {
                CustomToast.toast(PositionOrderActivity.this,"无法获取天气信息");
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnToday:
                i = 0;
                requestData();
                break;
            case R.id.btnTomorrow:
                i = 1;
                requestData();
                break;
            case R.id.btnTheDayAfterTomorrow:
                i = 2;
                requestData();
                break;
            default:
                break;
        }
    }
}
