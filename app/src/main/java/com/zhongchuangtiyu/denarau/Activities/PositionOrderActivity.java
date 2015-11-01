package com.zhongchuangtiyu.denarau.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.iwhys.library.NumberPicker;
import com.iwhys.library.UnitNumberPicker;
import com.zhongchuangtiyu.denarau.Entities.Weathers;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
    @Bind(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.clock)
    UnitNumberPicker clock;
    @Bind(R.id.probabilityOfPrecipitation)
    TextView probabilityOfPrecipitation;
    private int i;
    private String[] data = {"09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00"};
    private String selectedValue = "09:00";
    private AlertDialog.Builder builder;
    private String formatedDate;
    private int combinedTimeStamp;

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
        initCustomTimePicker();
    }


    private void initCustomTimePicker()
    {

        clock.setDisplayedValues(data);
        clock.setMinValue(0);
        clock.setMaxValue(data.length - 1);
        clock.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
                selectedValue = data[newVal];
                Xlog.d(selectedValue + "selectedValue------------------------------------------");
            }
        });
    }

    private void setListeners()
    {
        btnToday.setOnClickListener(this);
        btnTomorrow.setOnClickListener(this);
        btnTheDayAfterTomorrow.setOnClickListener(this);
        btnOrder.setOnClickListener(this);
        positionOrderTitleLeft.setOnClickListener(this);
    }

    private void requestData()
    {
        initCustomTimePicker();
        Xlog.d(selectedValue + "--------------------------------------------currentOne");
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(PositionOrderActivity.this, "token", "aa");
        String club_uuid = CacheUtils.getString(PositionOrderActivity.this, "clubuuid", "aa");
        map.put("token", token);
        map.put("clubuuid",club_uuid);
        MyApplication.volleyGET(APIUrls.WEATHER_URL + "token=" + token + "&" + "club_uuid=" + club_uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(PositionOrderActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(PositionOrderActivity.this,SignInActivity.class));
                    finish();
                }else
                {
                    List<Weathers> data = Weathers.instance(response);
                    int date = data.get(i).getDate();
                    int btnTodayDate = data.get(0).getDate();
                    int btnTomorrowDate = data.get(1).getDate();
                    int btnTheDayAfterTomorrowDate = data.get(2).getDate();
                    int day_of_week = data.get(i).getDay_of_week();
                    String content = data.get(i).getContent();
                    int day_code = data.get(i).getDay_code();
                    int maximum_temperature = data.get(i).getMaximum_temperature();
                    String probability_of_precipitation = data.get(i).getProbability_of_precipitation();
                    String wind = data.get(i).getWind();
                    String formatDate = String.valueOf(date);
                    String day1Date = String.valueOf(btnTodayDate);
                    String day2Date = String.valueOf(btnTomorrowDate);
                    String day3Date = String.valueOf(btnTheDayAfterTomorrowDate);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formatMMdd = new SimpleDateFormat("MM-dd");
                    formatMMdd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                    String btnDay1 = formatMMdd.format(new Date(Long.parseLong(day1Date) * 1000));
                    String btnDay2 = formatMMdd.format(new Date(Long.parseLong(day2Date) * 1000));
                    String btnDay3 = formatMMdd.format(new Date(Long.parseLong(day3Date) * 1000));
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                    formatedDate = simpleDateFormat.format(new Date(Long.parseLong(formatDate) * 1000));
                    String editedSelectedValue = selectedValue.replace(":", "");
                    String startTwo = editedSelectedValue.substring(0, 2);
                    String endTwo = editedSelectedValue.substring(2, 4);
                    int hour = Integer.valueOf(startTwo);
                    int minute = Integer.valueOf(endTwo);
                    combinedTimeStamp = date + hour * 3600 + minute * 60;

                    positionOrderDate.setText(formatedDate);
                    positionOrderTemperature.setText(String.valueOf(maximum_temperature) + "℃");
                    positionOrderWeatherTv.setText(content);
                    positionOrderWind.setText(wind);
                    probabilityOfPrecipitation.setText("降水概率" + " " + probability_of_precipitation);

                    Date now = new Date();
                    SimpleDateFormat nowFormatter = new SimpleDateFormat("MM-dd");
                    String nowDate = nowFormatter.format(now);
                    btnTheDayAfterTomorrow.setSingleLine(true);
                    if (nowDate.equals(btnDay1))
                    {
                        btnToday.setText("今天" + btnDay1);
                        btnTomorrow.setText("明天" + btnDay2);
                        btnTheDayAfterTomorrow.setText("后天" + btnDay3);
                    } else
                    {
                        btnToday.setText("明天" + btnDay1);
                        btnTomorrow.setText("后天" + btnDay2);
                        btnTheDayAfterTomorrow.setText("大后天" + btnDay3);
                    }
                }
            }

            @Override
            public void netFail(VolleyError error)
            {

            }
        });
    }

    private void sendOrderRequest()
    {
        final Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(PositionOrderActivity.this, "token", "aa");
        String club_uuid = CacheUtils.getString(PositionOrderActivity.this, "clubuuid", "aa");
        map.put("token", token);
        map.put("club_uuid", club_uuid);
        map.put("reserve_at", String.valueOf(combinedTimeStamp));
        MyApplication.volleyPOST(APIUrls.RESERVATION_URL, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                builder = new AlertDialog.Builder(PositionOrderActivity.this);
                builder.setTitle("预定成功");
                builder.setMessage("你已经成功预定" + formatedDate + " " + selectedValue + "的打位，可以在“个人中心”>“打位预约”中查看");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                { //设置确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss(); //关闭dialog
                        Toast.makeText(PositionOrderActivity.this, "确认" + which, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setCancelable(false);
                builder.create();
                builder.show();
                Xlog.d(response + "response------------------------------------------");
            }

            @Override
            public void netFail(VolleyError error)
            {
                Toast.makeText(PositionOrderActivity.this,"预定失败，请检查网络连接或稍后再试",Toast.LENGTH_SHORT).show();
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
            case R.id.btnOrder:
                sendOrderRequest();
                break;
            case R.id.positionOrderTitleLeft:
                finish();
                break;
            default:
                break;
        }
    }


}
