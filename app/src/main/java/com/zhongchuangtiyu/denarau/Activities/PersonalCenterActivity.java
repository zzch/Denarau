package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.zhongchuangtiyu.denarau.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonalCenterActivity extends AppCompatActivity implements View.OnClickListener
{

    @Bind(R.id.giveAdviceTitleLeft)
    ImageButton giveAdviceTitleLeft;
    @Bind(R.id.personalImage)
    RoundedImageView personalImage;
    @Bind(R.id.personalInfoName)
    TextView personalInfoName;
    @Bind(R.id.personalCenterInfoRl)
    RelativeLayout personalCenterInfoRl;
    @Bind(R.id.myRedBagRl)
    RelativeLayout myRedBagRl;
    @Bind(R.id.myConsumeRl)
    RelativeLayout myConsumeRl;
    @Bind(R.id.positionOrderRl)
    RelativeLayout positionOrderRl;
    @Bind(R.id.quitLoginRl)
    RelativeLayout quitLoginRl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initData();
        setListeners();
    }

    private void setListeners()
    {
        personalCenterInfoRl.setOnClickListener(this);
        myRedBagRl.setOnClickListener(this);
        myConsumeRl.setOnClickListener(this);
        positionOrderRl.setOnClickListener(this);
        quitLoginRl.setOnClickListener(this);
    }

    private void initData()
    {

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.personalCenterInfoRl:
                startActivity(new Intent(PersonalCenterActivity.this,EditPersonalInfoActivity.class));
                break;
        }
    }
}
