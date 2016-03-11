package com.zhongchuangtiyu.denarau.h5;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youzan.sdk.YouzanBridge;
import com.youzan.sdk.YouzanSDK;
import com.youzan.sdk.YouzanUser;
import com.youzan.sdk.http.engine.OnRegister;
import com.youzan.sdk.http.engine.QueryError;
import com.youzan.sdk.model.goods.GoodsShareModel;
import com.youzan.sdk.web.bridge.IBridgeEnv;
import com.youzan.sdk.web.event.ShareDataEvent;
import com.youzan.sdk.web.event.UserInfoEvent;
import com.youzan.sdk.web.event.WXAppPayEvent;
import com.youzan.sdk.web.event.WebReadyEvent;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.StatusBarCompat;
import com.zhongchuangtiyu.denarau.event.ShareEvent;
import com.zhongchuangtiyu.denarau.event.UserEvent;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * #html5交互版
 * #======
 * <p/>
 * #不必继承BaseWebActivity, 仅参考作用
 * #
 * #记住不要用短链接，短链接类似：http://kdt.im/......, 使用长连接
 * #不然会多一次跳转http://wap.koudaitong.com/v2/showcase/homepage?alias=xxxxxx
 */
public class H5Activity extends BaseActivity
{
    public static final String SIGN_URL = "URL";
    @Bind(R.id.tabsAllTitleLeft)
    ImageButton tabsAllTitleLeft;
    @Bind(R.id.webView3)
    WebView web;
    /**
     * H5和原生的桥接对象
     */
    private YouzanBridge bridge;
    /**
     * WebView
     */
//    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        StatusBarCompat.compat(this);
        iniView();
        setContentView(R.layout.activity_h5);
        StatusBarCompat.compat(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        initBridge();
        //openWebview();
        registerYouzanUser();
        ActivityCollector.addActivity(this);
        tabsAllTitleLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Runtime runtime = Runtime.getRuntime();
                try
                {
                    runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 打开有赞入口网页需先注册有赞用户
     * <p/>
     * 如果你们App的用户这个时候还没有登录, 请先跳转你们的登录页面, 然后再回来同步用户信息
     */
    private void registerYouzanUser()
    {
        YouzanUser user = new YouzanUser();
        String userUuid = CacheUtils.getString(H5Activity.this, "userUuid", null);
        user.setUserId(userUuid);
        String userPortrait = CacheUtils.getString(H5Activity.this, "userPortrait", null);
        user.setAvatar(userPortrait);
        String gender = CacheUtils.getString(H5Activity.this, "gender", null);
        if (gender.equals("male"))
        {
            user.setGender(1);
        } else if (gender.equals("female"))
        {
            user.setGender(0);
        }
//        user.setGender(1);
        String nickName = CacheUtils.getString(H5Activity.this, "name", null);
        user.setNickName(nickName);
        String telephone = CacheUtils.getString(H5Activity.this, "telephone", null);
        user.setTelephone(telephone);
//        user.setUserName("大明");


        YouzanSDK.asyncRegisterUser(user, new OnRegister()
        {
            /**
             * 注册失败, 请参考错误信息修改注册参数
             * <p/>
             * 如报非法请求, 请检查UA是否正确
             */
            @Override
            public void onFailed(QueryError queryError)
            {
                Toast.makeText(H5Activity.this, queryError.getMsg(), Toast.LENGTH_SHORT).show();
            }

            /**
             * 注册成功, 打开有赞入口网页
             */
            @Override
            public void onSuccess()
            {
                openWebview();
            }
        });
    }


    /**
     * 初始化视图
     */
    private void iniView()
    {
        LinearLayout rootView = new LinearLayout(this);
        LinearLayout configView = new LinearLayout(this);
//        Button buttonShare = new Button(this);
//        Button buttonRefresh = new Button(this);
//        TextView tvTips = new TextView(this);
//        web = new WebView(this);

        rootView.setOrientation(LinearLayout.VERTICAL);
        configView.setOrientation(LinearLayout.HORIZONTAL);
//        buttonShare.setText("分享");
//        buttonRefresh.setText("刷新");
//        tvTips.setText("DEMO演示");
//        configView.addView(buttonShare);
//        configView.addView(buttonRefresh);
//        configView.addView(tvTips);
        rootView.addView(configView);
//        rootView.addView(web, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        setContentView(rootView);

//        buttonShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (bridge != null) {//分享
//                    bridge.sharePage();
//                }
//            }
//        });
//        buttonRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (web != null) {//刷新
//                    web.reload();
//                }
//            }
//        });
    }

    /**
     * 打开链接
     */
    private void openWebview()
    {
        Intent intent = getIntent();
        String url = intent.getStringExtra(SIGN_URL);
        if (url != null)
        {
            loadPage(url);
        }
    }


    /**
     * 初始化桥接对象
     * <p/>
     * {@link YouzanBridge}是一个桥接对象, 用于打通Html5页面和原生的交互.
     * 通过{@code YouzanBridge.build(activity, webView).create(); }的方式初始化.
     * <p/>
     * 通过{@code YouzanBridge.add(event)}可以添加桥接事件, 现在支持的事件有:
     * <p/>
     * {@link ShareDataEvent} => 分享事件
     * 说明:  调用{@code YouzanBridge.sharePage()}来触发
     * <p/>
     * {@link UserInfoEvent} => 用户同步登录事件(高阶实现)
     * 说明:  如果不使用{@code YouzanSDK.asyncRegisterUser(user);}
     * <p/>
     * {@link WXAppPayEvent} => 微信APP支付事件(高阶实现)
     * 说明:  需在有赞后台已经开启微信APP支付, 事件返回支付参数, 开发者使用微信SDK
     * http://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419319167&token=&lang=zh_CN
     * 实现跳转支付.
     * <p/>
     * {@link WebReadyEvent} =>页面初始化完成(废弃)
     */
    private void initBridge()
    {
        bridge = YouzanBridge.build(this, web)
                //.setWebClient(new WebClient())client拓展示例, 不进行拓展可以删除这行代码
                .create();

        //根据需求添加相应的桥接事件
        bridge.add(new ShareEvent())//分享
                .add(new ShareDataEvent()
                {

                    @Override
                    public void call(IBridgeEnv env, GoodsShareModel data)
                    {
                        /**
                         *或者调用分享组件进行商品信息分享, 参考{@link ShareEvent}
                         */
                        new AlertDialog
                                .Builder(H5Activity.this)
                                .setTitle(data.getTitle())
                                .setMessage("分享链接:\n" + data.getLink() + "\n\n图片链接:\n" + data.getImgUrl() + "\n\n描述:\n" + data.getDesc())
                                .create()
                                .show();

                    }
                })
                .add(new UserEvent());//同步注册有赞用户, 需要先打开网页后要求用户登录的可以使用这种
    }


    /**
     * 加载链接
     *
     * @param url 链接, 推荐不要使用短链接(多一次重定向)
     */
    private void loadPage(String url)
    {
        if (web != null && !TextUtils.isEmpty(url))
        {
            web.loadUrl(url);
        }
    }

    /**
     * 页面回退
     * <p/>
     * bridge.pageGoBack()返回True表示处理的是网页的回退
     */
    @Override
    public void onBackPressed()
    {
        if (bridge == null || !bridge.pageGoBack())
        {
            super.onBackPressed();
        }
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
