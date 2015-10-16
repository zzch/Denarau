package com.zhongchuangtiyu.denarau.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.ListView;

import com.zhongchuangtiyu.denarau.Adapters.CoachTutorialListAdapter;
import com.zhongchuangtiyu.denarau.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CoachTutorialListActivity extends AppCompatActivity
{

    @Bind(R.id.coachTutorialTitleLeft)
    ImageButton coachTutorialTitleLeft;
    @Bind(R.id.coachTutorialToolBar)
    Toolbar coachTutorialToolBar;
    @Bind(R.id.coachTutorialListView)
    ListView coachTutorialListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_tutorial_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.coachTutorialToolBar);
        setSupportActionBar(toolbar);
    }

}
