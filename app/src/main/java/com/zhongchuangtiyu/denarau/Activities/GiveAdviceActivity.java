package com.zhongchuangtiyu.denarau.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.zhongchuangtiyu.denarau.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GiveAdviceActivity extends AppCompatActivity
{

    @Bind(R.id.giveAdviceTitleLeft)
    ImageButton giveAdviceTitleLeft;
    @Bind(R.id.giveAdviceToolbar)
    Toolbar giveAdviceToolbar;
    @Bind(R.id.giveAdviceEditText)
    EditText giveAdviceEditText;
    @Bind(R.id.btnGiveAdviceSend)
    Button btnGiveAdviceSend;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_advice);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.giveAdviceToolbar);
        setSupportActionBar(toolbar);

    }

}
