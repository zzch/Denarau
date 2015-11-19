package com.zhongchuangtiyu.denarau.Activities;

import android.app.ProgressDialog;
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
import com.zhongchuangtiyu.denarau.Demos.MyCustomDialog;
import com.zhongchuangtiyu.denarau.Entities.Weathers;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.StatusBarCompat;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PositionOrderActivity extends BaseActivity implements View.OnClickListener
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
    RelativeLayout btnOrder;
    @Bind(R.id.positionOrderWind)
    TextView positionOrderWind;
    @Bind(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.probabilityOfPrecipitation)
    TextView probabilityOfPrecipitation;
    @Bind(R.id.showOrderTimeTv)
    TextView showOrderTimeTv;
    @Bind(R.id.btnOrderRlDialog)
    RelativeLayout btnOrderRlDialog;
    private int i;
    private String selectedValue = "09:00";
    private AlertDialog.Builder builder;
    private String formatedDate;
    private int combinedTimeStamp;
    private List<Weathers> data;
    private int date;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_order);
        ButterKnife.bind(this);
        StatusBarCompat.compat(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.positionOrderToolBar);
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("预定中...");
        progressDialog.setCancelable(false);
        btnToday.setSelected(true);
        requestData();
        setListeners();
        ActivityCollector.addActivity(this);
    }


    private void setListeners()
    {
        btnToday.setOnClickListener(this);
        btnTomorrow.setOnClickListener(this);
        btnTheDayAfterTomorrow.setOnClickListener(this);
        btnOrder.setOnClickListener(this);
        positionOrderTitleLeft.setOnClickListener(this);
        btnOrderRlDialog.setOnClickListener(this);
    }

    private void requestData()
    {
        Xlog.d(selectedValue + "--------------------------------------------currentOne");
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(PositionOrderActivity.this, "token", "aa");
        String club_uuid = CacheUtils.getString(PositionOrderActivity.this, "clubuuid", "aa");
        map.put("token", token);
        map.put("clubuuid", club_uuid);
        MyApplication.volleyGET(APIUrls.WEATHER_URL + "token=" + token + "&" + "club_uuid=" + club_uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(PositionOrderActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(PositionOrderActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    data = Weathers.instance(response);
                    if (data.size() == 3)
                    {
                        date = data.get(i).getDate();
                        int btnTodayDate = data.get(0).getDate();
                        int btnTomorrowDate = data.get(1).getDate();
                        int btnTheDayAfterTomorrowDate = data.get(2).getDate();
                        int day_of_week = data.get(i).getDay_of_week();
                        String content = data.get(i).getContent();
                        int code = data.get(i).getCode();
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
//                        String editedSelectedValue = selectedValue.replace(":", "");
//                        String startTwo = editedSelectedValue.substring(0, 2);
//                        String endTwo = editedSelectedValue.substring(2, 4);
//                        int hour = Integer.valueOf(startTwo);
//                        int minute = Integer.valueOf(endTwo);
//                        combinedTimeStamp = date + hour * 3600 + minute * 60;
//                        Xlog.d(String.valueOf(hour) + "hour=========================");
                        positionOrderDate.setText(formatedDate);
                        positionOrderTemperature.setText(String.valueOf(maximum_temperature) + "°");
                        positionOrderWeatherTv.setText(content);
                        positionOrderWind.setText(wind);
                        probabilityOfPrecipitation.setText("降水概率" + " " + probability_of_precipitation);
                        switch (code)
                        {
                            case 1:
                                weatherImageView.setImageResource(R.mipmap.oneicon);
                                break;
                            case 2:
                                weatherImageView.setImageResource(R.mipmap.twoicon);
                                break;
                            case 3:
                                weatherImageView.setImageResource(R.mipmap.threeicon);
                                break;
                            case 4:
                                weatherImageView.setImageResource(R.mipmap.fouricon);
                                break;
                            case 5:
                                weatherImageView.setImageResource(R.mipmap.fiveicon);
                                break;
                            case 6:
                                weatherImageView.setImageResource(R.mipmap.sixicon);
                                break;
                            case 7:
                                weatherImageView.setImageResource(R.mipmap.sevenicon);
                                break;
                            case 8:
                                weatherImageView.setImageResource(R.mipmap.eighticon);
                                break;
                        }
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
            }

            @Override
            public void netFail(VolleyError error)
            {
                if (error.toString().contains("AuthFailureError"))
                {
                    CustomToast.showToast(PositionOrderActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(PositionOrderActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                }else
                {
                    CustomToast.showToast(PositionOrderActivity.this, "网络连接失败，请检查网络连接");
                }
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
        String editedSelectedValue = selectedValue.replace(":", "");
        String startTwo = editedSelectedValue.substring(0, 2);
        String endTwo = editedSelectedValue.substring(2, 4);
        int hour = Integer.valueOf(startTwo);
        int minute = Integer.valueOf(endTwo);
        Xlog.d("date" + String.valueOf(date) + "hour:" + String.valueOf(hour) + "minute" + String.valueOf(minute));
        combinedTimeStamp = date + hour * 3600 + minute * 60;
        map.put("reserve_at", String.valueOf(combinedTimeStamp));
        MyApplication.volleyPOST(APIUrls.RESERVATION_URL, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("20004"))
                {
                    progressDialog.dismiss();
                    builder = new AlertDialog.Builder(PositionOrderActivity.this);
                    builder.setTitle("重复预约");
                    builder.setMessage("您已经预约过当天的打位");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    { //设置确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss(); //关闭dialog
//                            Toast.makeText(PositionOrderActivity.this, "确认" + which, Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setCancelable(false);
                    builder.create();
                    builder.show();
                    Xlog.d(response + "response------------------------------------------");
                }else
                {
                    progressDialog.dismiss();
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

            }

            @Override
            public void netFail(VolleyError error)
            {
                progressDialog.dismiss();
                Toast.makeText(PositionOrderActivity.this, "预定失败，请检查网络连接或稍后再试", Toast.LENGTH_SHORT).show();
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
                btnToday.setSelected(true);
                btnTomorrow.setSelected(false);
                btnTheDayAfterTomorrow.setSelected(false);
                requestData();
                break;
            case R.id.btnTomorrow:
                i = 1;
                btnTomorrow.setSelected(true);
                btnTheDayAfterTomorrow.setSelected(false);
                btnToday.setSelected(false);
                requestData();
                break;
            case R.id.btnTheDayAfterTomorrow:
                i = 2;
                btnTheDayAfterTomorrow.setSelected(true);
                btnToday.setSelected(false);
                btnTomorrow.setSelected(false);
                requestData();
                break;
            case R.id.btnOrder:
                String isOrderTimeChoosen = showOrderTimeTv.getText().toString();
                if (isOrderTimeChoosen.contains("预约时间"))
                {
                    CustomToast.showToast(PositionOrderActivity.this,"请选择预约时间");
                }else if (formatedDate == null)
                {
                    CustomToast.showToast(PositionOrderActivity.this,"请选择预约日期");
                }
                else
                {
                    sendOrderRequest();
//                    progressDialog.show();
                }
                break;
            case R.id.positionOrderTitleLeft:
                finish();
                break;
            case R.id.btnOrderRlDialog:
                MyCustomDialog dialog = new MyCustomDialog(PositionOrderActivity.this, "请选择预约时间", new MyCustomDialog.OnCustomDialogListener()
                {
                    @Override
                    public void back(String time)
                    {
                        showOrderTimeTv.setText(time);
                        selectedValue = time;
                    }
                });
                dialog.show();
                break;
            default:
                break;
        }
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
