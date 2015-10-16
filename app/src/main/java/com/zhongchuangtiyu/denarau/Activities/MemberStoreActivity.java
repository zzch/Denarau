package com.zhongchuangtiyu.denarau.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.ListView;

import com.zhongchuangtiyu.denarau.Adapters.MembershipStoreAdapter;
import com.zhongchuangtiyu.denarau.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MemberStoreActivity extends AppCompatActivity
{

    @Bind(R.id.memberStoreTitleLeft)
    ImageButton memberStoreTitleLeft;
    @Bind(R.id.memberStoreToolbar)
    Toolbar memberStoreToolbar;
    @Bind(R.id.memberStoreListView)
    ListView memberStoreListView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_store);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.memberStoreToolbar);
        setSupportActionBar(toolbar);
    }

}
