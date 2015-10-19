package com.zhongchuangtiyu.denarau.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.ListView;
import com.zhongchuangtiyu.denarau.R;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CardBagListActivity extends AppCompatActivity
{

    @Bind(R.id.positionOrderTitleLeft)
    ImageButton positionOrderTitleLeft;
    @Bind(R.id.cardBagListToolbar)
    Toolbar cardBagListToolbar;
    @Bind(R.id.cardBagListView)
    ListView cardBagListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_bag_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.cardBagListToolbar);
        setSupportActionBar(toolbar);
    }

}
