package com.zhongchuangtiyu.denarau.Demos;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Entities.Announcements;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wangm on 2015/11/2.
 */
public class Test extends Activity
{

    Button button;
    TextView textView;
    private final List<String> list = new ArrayList<String>();
    private int j = 0;
    private Timer timer;


    final Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 1:
                    if (j < list.size()-1)
                    {
                        j++;
                    }else if (j == list.size() -1)
                    {
                        j = 0;
                    }
                    setAnnouncementOutAnimation();
                    Xlog.d(String.valueOf(j) + "j--------------------------------------");
                    break;
            }
            super.handleMessage(msg);
        }
    };


    TimerTask task = new TimerTask(){
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayout);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView50);
        sendRequest();
        timer = new Timer(true);
        timer.schedule(task,1000, 4000);

    }




    private void sendRequest()
    {
        Map map = new HashMap();

        MyApplication.volleyGET(APIUrls.ANNOUNCEMENTS + "token=" + "test" + "&" + "club_uuid=" + "test", map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                List<Announcements> data = Announcements.instance(response);
                for (int i = 0; i < data.size(); i++)
                {
                    String text = data.get(i).getTitle();
                    list.add(text);
                }
                textView.setText(data.get(0).getTitle());
                Xlog.d(list.toString() + "list------------------------------------");
            }

            @Override
            public void netFail(VolleyError error)
            {

            }
        });
    }

    private void setAnnouncementOutAnimation()
    {
        Animation announcementOutAnimation = new TranslateAnimation(textView.getScaleX(), textView.getScaleX(), textView.getScaleY(), -50f);
        announcementOutAnimation.setDuration(500);
        textView.startAnimation(announcementOutAnimation);
        announcementOutAnimation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                setAnnouncementInAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }
    private void setAnnouncementInAnimation()
    {
        Animation announcementInAnimation = new TranslateAnimation(textView.getScaleX(), textView.getScaleX(), textView.getScaleY()+50f, textView.getScaleY());
        announcementInAnimation.setDuration(500);
        textView.startAnimation(announcementInAnimation);
        announcementInAnimation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
                textView.setText(list.get(j));
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }

}
