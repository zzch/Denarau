package com.zhongchuangtiyu.denarau.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.ListView;

import com.zhongchuangtiyu.denarau.Adapters.CostHistoryListAdapter;
import com.zhongchuangtiyu.denarau.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CostHistoryActivity extends AppCompatActivity
{

    @Bind(R.id.costHistoryTitleLeft)
    ImageButton costHistoryTitleLeft;
    @Bind(R.id.costHistoryToolbar)
    Toolbar costHistoryToolbar;
    @Bind(R.id.costHistoryListView)
    ListView costHistoryListView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_history);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.costHistoryToolbar);
        setSupportActionBar(toolbar);
    }

}
