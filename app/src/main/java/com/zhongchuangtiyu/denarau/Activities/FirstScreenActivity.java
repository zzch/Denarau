package com.zhongchuangtiyu.denarau.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Jpush.ExampleUtil;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;

public class FirstScreenActivity extends InstrumentedActivity
{

    private int j = 0;
    private Timer timer;
    final Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 1:
                    if (JPushInterface.getConnectionState(getApplicationContext()))
                    {
//                        if (j < 1000)
//                        {
//                            j++;
//                            Log.d("Tag",String.valueOf(j) + "j--------------------------------------");
//                            break;
//                        }
                        String  registration_id = JPushInterface.getRegistrationID(getApplicationContext());
                        String token = CacheUtils.getString(getApplicationContext(), "token", null);
                        Xlog.d("registration_idregistration_idregistration_idregistration_idregistration_id"+"----"+registration_id);
                        Xlog.d("tokentokentokentokentokentokentokentokentokentoken"+"----"+token);
                        if (!registration_id.equals(CacheUtils.getString(getApplicationContext(), "registration_id", null)) && !registration_id.equals("") && !token.equals(""))
                        {
                            CacheUtils.putString(getApplicationContext(),"registration_id", registration_id);
                            Map<String, String> map = new HashMap<>();
//        String token = CacheUtils.getString(SignInActivity.this, "token", null);
//        String registration_id = CacheUtils.getString(SignInActivity.this, "registration_id", null);
                            MyApplication.volleyPUT(APIUrls.REGISTRATION_ID + "token=" + token + "&" + "registration_id=" + registration_id, map, new MyApplication.VolleyCallBack()
                            {
                                @Override
                                public void netSuccess(String response)
                                {
                                    Xlog.d("responseresponseresponseresponseresponseresponse" + response);
                                }

                                @Override
                                public void netFail(VolleyError error)
                                {

                                }
                            });
                        }
                    }
                    super.handleMessage(msg);
            }
        }
    };
    TimerTask task = new TimerTask()
    {
        public void run()
        {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        timer = new Timer(true);
        timer.schedule(task, 1000, 60000);

        // 延迟几秒，然后跳转到登录页面

        new Handler().postDelayed(r, 2000);
    }



    Runnable r = new Runnable()
    {

        @Override

        public void run() {

            Intent intent = new Intent();
            String token = CacheUtils.getString(FirstScreenActivity.this, "token", null);
            if (token != null)
            {
                intent.setClass(FirstScreenActivity.this, MembershipCardMainActivity.class);
            }else
            {
                intent.setClass(FirstScreenActivity.this, SignInActivity.class);
            }
            startActivity(intent);
            finish();
        }
    };
}
