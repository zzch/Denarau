package com.zhongchuangtiyu.denarau.Utils;


import android.app.Application;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.youzan.sdk.YouzanSDK;

import java.util.Map;


/**
 * Created by wangm on 2015/7/17.
 */
public class MyApplication extends Application
{

    public static RequestQueue requestQueue;
    public static MyApplication mcontext;
    public static boolean isApplicationFround;

    public interface VolleyCallBack
    {
        void netSuccess(String response);

        void netFail(VolleyError error);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        mcontext = this;

        /**
         * 初始化
         * @param Context application Context
         * @param userAgent 用户代理, 填写UA
         */
        YouzanSDK.init(this, "5252300f8935683a9c1456191629356");
    }

    public static void volleyGET(String url, final Map<String, String> map, final VolleyCallBack listener)
    {
//        String token = CacheUtils.getString(mcontext, "token","aa");
//        String club_uuid = CacheUtils.getString(mcontext, "clubuuid","aa");
//        map.put("token", token);
//        map.put("clubuuid", club_uuid);
        final Map<String, String> finalMap = map;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Xlog.d(response);
                        listener.netSuccess(response);
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                listener.netFail(error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                return map;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
    public static void volleyPOST(String url, final Map<String, String> map, final VolleyCallBack listener)
    {
//        String token = CacheUtils.getString(mcontext, "token","aa");
//        String club_uuid = CacheUtils.getString(mcontext, "clubuuid","aa");
//        map.put("token", token);
//        map.put("clubuuid",club_uuid);
        final Map<String, String> finalMap = map;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Xlog.d(response);
                        listener.netSuccess(response);
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                listener.netFail(error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                return map;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
    public static void volleyPUT(String url, final Map<String, String> map, final VolleyCallBack listener)
    {
//        String token = CacheUtils.getString(mcontext, "token","aa");
//        String club_uuid = CacheUtils.getString(mcontext, "clubuuid","aa");
//        map.put("token", token);
//        map.put("clubuuid",club_uuid);
        final Map<String, String> finalMap = map;
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Xlog.d(response);
                        listener.netSuccess(response);
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                listener.netFail(error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                return map;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
}
