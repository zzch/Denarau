package com.zhongchuangtiyu.denarau.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhongchuangtiyu.denarau.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PositionOrderActivity extends AppCompatActivity
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
    @Bind(R.id.btnOrderTime)
    Button btnOrderTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_order);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.positionOrderToolBar);
        setSupportActionBar(toolbar);
    }

}
