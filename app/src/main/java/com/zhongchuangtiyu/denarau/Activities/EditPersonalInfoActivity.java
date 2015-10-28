package com.zhongchuangtiyu.denarau.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iwhys.library.widget.DatePickerDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhongchuangtiyu.denarau.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditPersonalInfoActivity extends AppCompatActivity implements View.OnClickListener
{

    @Bind(R.id.editPersonalInfoTitleLeft)
    ImageButton editPersonalInfoTitleLeft;
    @Bind(R.id.personalInfoImageToEdit)
    RoundedImageView personalInfoImageToEdit;
    @Bind(R.id.editPersonalInfoImageRl)
    RelativeLayout editPersonalInfoImageRl;
    @Bind(R.id.editPersonalInfoNameToRequest)
    TextView editPersonalInfoNameToRequest;
    @Bind(R.id.editPersonalInfoGenderToRequest)
    TextView editPersonalInfoGenderToRequest;
    @Bind(R.id.editPersonalInfoBirthToSet)
    TextView editPersonalInfoBirthToSet;
    @Bind(R.id.editPersonalInfoBirthRl)
    RelativeLayout editPersonalInfoBirthRl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_info);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setData();
        setListeners();

    }

    private void setData()
    {

    }
    private void setListeners()
    {
        editPersonalInfoImageRl.setOnClickListener(this);
        editPersonalInfoBirthRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.editPersonalInfoBirthRl:
        }
    }
}
