package com.zhongchuangtiyu.denarau.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Adapters.ProvisionsGridAdapter;
import com.zhongchuangtiyu.denarau.Entities.Provision;
import com.zhongchuangtiyu.denarau.Entities.Provisions;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：WangMeng on 2015/10/27 18:10
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class ProvisionFragments extends android.support.v4.app.Fragment
{
    private String key = null;
    private List<Provision> provs = null;

    public static ProvisionFragments newInstance(List<Provision> s){
        ProvisionFragments myFragment = new ProvisionFragments();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("key",(Serializable)s);
//        myFragment.setArguments(bundle);
        myFragment.setProvs(s);
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Bundle bundle = getArguments();
        key = bundle != null? bundle.getString("key") : null;
        super.onCreate(savedInstanceState);
    }
    private GridView provisionsGridView;
    private ProvisionsGridAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = null;
//        Map<String, String> map = new HashMap<>();
//        String token = CacheUtils.getString(getActivity(),"token",null);
//        String club_uuid = CacheUtils.getString(getActivity(),"clubuuid",null);
//        MyApplication.volleyGET(APIUrls.PROVISIONS_URL + "token=" + token + "&" + "club_uuid=" + club_uuid, map, new MyApplication.VolleyCallBack()
//        {
//            @Override
//            public void netSuccess(String response)
//            {
//                List<Provision> data = Provisions.instance(response);
//                adapter = new ProvisionsGridAdapter(data,getActivity());
//                provisionsGridView.setAdapter(adapter);
//            }
//
//            @Override
//            public void netFail(VolleyError error)
//            {
//
//            }
//        });
        view = inflater.inflate(R.layout.provisions_grid_view, container, false);
        provisionsGridView = (GridView) view.findViewById(R.id.provisionsGridView);
        adapter = new ProvisionsGridAdapter(provs,getActivity());
        provisionsGridView.setAdapter(adapter);
        return view;
    }

    public List<Provision> getProvs()
    {
        return provs;
    }

    public void setProvs(List<Provision> provs)
    {
        this.provs = provs;
    }
}
