package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhongchuangtiyu.denarau.Entities.UsersDetail;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.DateUtils;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.StatusBarCompat;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.personalCenterTitleLeft)
    ImageButton personalCenterTitleLeft;
    @Bind(R.id.personalImage)
    RoundedImageView personalImage;
    @Bind(R.id.personalInfoName)
    TextView personalInfoName;
    @Bind(R.id.personalCenterInfoRl)
    RelativeLayout personalCenterInfoRl;
//    @Bind(R.id.myRedBagRl)
//    RelativeLayout myRedBagRl;
    @Bind(R.id.myConsumeRl)
    RelativeLayout myConsumeRl;
    @Bind(R.id.positionOrderRl)
    RelativeLayout positionOrderRl;
    @Bind(R.id.quitLoginRl)
    RelativeLayout quitLoginRl;
    @Bind(R.id.myCourseRl)
    RelativeLayout myCourseRl;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StatusBarCompat.compat(this);
        JPushInterface.init(getApplicationContext());
        setListeners();
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        initData();
    }

    private void setListeners()
    {
        personalCenterInfoRl.setOnClickListener(this);
//        myRedBagRl.setOnClickListener(this);
        myConsumeRl.setOnClickListener(this);
        myCourseRl.setOnClickListener(this);
        positionOrderRl.setOnClickListener(this);
        quitLoginRl.setOnClickListener(this);
        personalCenterTitleLeft.setOnClickListener(this);
    }

    private void initData()
    {
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(PersonalCenterActivity.this, "token", null);
        Xlog.d(token + "token----------------------------------------------");
        MyApplication.volleyGET(APIUrls.USERS_DETAIL + token, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                if (response.contains("10002"))
                {
                    CustomToast.showToast(PersonalCenterActivity.this, "登录失效，请重新登录");
                    CacheUtils.putString(PersonalCenterActivity.this, "token", null);
                    CacheUtils.putString(PersonalCenterActivity.this, "registration_id", null);
                    startActivity(new Intent(PersonalCenterActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                } else
                {
                    UsersDetail data = UsersDetail.instance(response);
                    String cachedPortrait = CacheUtils.getString(PersonalCenterActivity.this, "setPortrait", null);
                    if (cachedPortrait != null)
                    {
                        Bitmap photo1 = BitmapFactory.decodeFile(cachedPortrait);
                        personalImage.setImageBitmap(photo1);
                    } else if (data.getPortrait() != null && cachedPortrait == null)
                    {
                        imageLoader.init(ImageLoaderConfiguration.createDefault(PersonalCenterActivity.this));
                        String portraitUrl = data.getPortrait();
                        imageLoader.displayImage(portraitUrl, personalImage);
                    }else if (data.getPortrait() == null && cachedPortrait == null)
                    {
                        if (data.getGender().equals("male"))
                        {
                            personalImage.setImageResource(R.mipmap.nan);
                        }else if (data.getGender().equals("female"))
                        {
                            personalImage.setImageResource(R.mipmap.nv);
                        }
                    }
                    if (data.getName() != null && !data.getName().equals(""))
                    {
                        String setName = data.getName();
                        CacheUtils.putString(PersonalCenterActivity.this, "setName", setName);
                        personalInfoName.setText(setName);
                    }
                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                if (error.toString().contains("AuthFailureError"))
                {
                    CustomToast.showToast(PersonalCenterActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(PersonalCenterActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                }else
                {
                    CustomToast.showToast(PersonalCenterActivity.this, "网络连接失败，请检查网络连接");
                }
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.personalCenterInfoRl:
                startActivity(new Intent(PersonalCenterActivity.this,EditPersonalInfoActivity.class));
                break;
            case R.id.personalCenterTitleLeft:
                finish();
                ActivityCollector.removeActivity(this);
                break;
            case R.id.positionOrderRl:
                startActivity(new Intent(PersonalCenterActivity.this, ReservationsActivity.class));
                break;
            case R.id.myConsumeRl:
                startActivity(new Intent(PersonalCenterActivity.this, TabsAllActivity.class));
                break;
            case R.id.myCourseRl:
                startActivity(new Intent(PersonalCenterActivity.this, MyCourseActivity.class));
                break;
            case R.id.quitLoginRl:
                CacheUtils.putString(PersonalCenterActivity.this, "token", null);
                CacheUtils.putString(PersonalCenterActivity.this, "clubuuid", null);
                CacheUtils.putString(PersonalCenterActivity.this, "setPortrait", null);
                CacheUtils.putString(PersonalCenterActivity.this, "registration_id",null);
                startActivity(new Intent(PersonalCenterActivity.this, SignInActivity.class));
                finish();
                ActivityCollector.finishAll();
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
