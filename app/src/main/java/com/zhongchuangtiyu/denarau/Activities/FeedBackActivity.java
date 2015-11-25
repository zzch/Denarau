package com.zhongchuangtiyu.denarau.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.StatusBarCompat;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

public class FeedBackActivity extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.giveAdviceTitleLeft)
    ImageButton giveAdviceTitleLeft;
    @Bind(R.id.giveAdviceToolbar)
    Toolbar giveAdviceToolbar;
    @Bind(R.id.giveAdviceEditText)
    EditText giveAdviceEditText;
    @Bind(R.id.btnGiveAdviceSend)
    Button btnGiveAdviceSend;
    private AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_advice);
        ButterKnife.bind(this);
        StatusBarCompat.compat(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.giveAdviceToolbar);
        setSupportActionBar(toolbar);
        JPushInterface.init(getApplicationContext());
        setListeners();
        ActivityCollector.addActivity(this);
    }

    private void setListeners()
    {
        btnGiveAdviceSend.setOnClickListener(this);
        giveAdviceTitleLeft.setOnClickListener(this);
        giveAdviceTitleLeft.setOnClickListener(this);
    }

    private void sendFeedBackRequest()
    {
        String feedBackText = giveAdviceEditText.getText().toString();
        Map<String, String> map = new HashMap<>();
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        String token = CacheUtils.getString(FeedBackActivity.this, "token", "aa");
        String club_uuid = CacheUtils.getString(FeedBackActivity.this, "clubuuid", "aa");
        map.put("content",feedBackText);
        map.put("token", token);
        map.put("club_uuid", club_uuid);
        map.put("type", type);
        MyApplication.volleyPOST(APIUrls.FEEDBACKS_URL, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(FeedBackActivity.this, "登录失效，请重新登录");
                    CacheUtils.putString(FeedBackActivity.this, "token", null);
                    CacheUtils.putString(FeedBackActivity.this, "registration_id", null);
                    startActivity(new Intent(FeedBackActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    builder = new AlertDialog.Builder(FeedBackActivity.this);
                    builder.setTitle("发送成功");
                    builder.setMessage("我们已经收到您的反馈");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    { //设置确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss(); //关闭dialog
                            Toast.makeText(FeedBackActivity.this, "确认" + which, Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setCancelable(false);
                    builder.create();
                    builder.show();
                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                if (error.toString().contains("AuthFailureError"))
                {
                    CustomToast.showToast(FeedBackActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(FeedBackActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                }else
                {
                    CustomToast.showToast(FeedBackActivity.this, "网络连接失败，请检查网络连接");
                }
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btnGiveAdviceSend:
                String feedBackText = giveAdviceEditText.getText().toString();
                if (!(feedBackText == null || feedBackText.trim().length() == 0))
                {
                    sendFeedBackRequest();
                }else
                {
                    builder = new AlertDialog.Builder(FeedBackActivity.this);
                    builder.setTitle("请输入反馈内容");
                    builder.setMessage("反馈能容不能为空！");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    { //设置确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss(); //关闭dialog
                            Toast.makeText(FeedBackActivity.this, "确认" + which, Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setCancelable(false);
                    builder.create();
                    builder.show();
                }break;
            case R.id.giveAdviceTitleLeft:
                finish();
                break;
            default:
                break;
        }
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
