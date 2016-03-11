package com.zhongchuangtiyu.denarau.h5;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.zhongchuangtiyu.denarau.Utils.Xlog;
import com.zhongchuangtiyu.denarau.event.ShareEvent;
import com.zhongchuangtiyu.denarau.event.UserEvent;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * #html5������
 * #======
 * <p/>
 * #���ؼ̳�BaseWebActivity, ���ο�����
 * #
 * #��ס��Ҫ�ö����ӣ����������ƣ�http://kdt.im/......, ʹ�ó�����
 * #��Ȼ���һ����תhttp://wap.koudaitong.com/v2/showcase/homepage?alias=xxxxxx
 */
public class H5Activity extends BaseActivity
{
    public static final String SIGN_URL = "URL";
    @Bind(R.id.tabsAllTitleLeft)
    ImageButton tabsAllTitleLeft;
    @Bind(R.id.webView3)
    WebView web;
    /**
     * H5��ԭ�����ŽӶ���
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
     * �����������ҳ����ע�������û�
     * <p/>
     * �������App���û����ʱ��û�е�¼, ������ת���ǵĵ�¼ҳ��, Ȼ���ٻ���ͬ���û���Ϣ
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
        user.setUserName(nickName);


        YouzanSDK.asyncRegisterUser(user, new OnRegister()
        {
            /**
             * ע��ʧ��, ��ο�������Ϣ�޸�ע�����
             * <p/>
             * �籨�Ƿ�����, ����UA�Ƿ���ȷ
             */
            @Override
            public void onFailed(QueryError queryError)
            {
                Toast.makeText(H5Activity.this, queryError.getMsg(), Toast.LENGTH_SHORT).show();
            }

            /**
             * ע��ɹ�, �����������ҳ
             */
            @Override
            public void onSuccess()
            {
                openWebview();
            }
        });
    }


    /**
     * ��ʼ����ͼ
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
//        buttonShare.setText("����");
//        buttonRefresh.setText("ˢ��");
//        tvTips.setText("DEMO��ʾ");
//        configView.addView(buttonShare);
//        configView.addView(buttonRefresh);
//        configView.addView(tvTips);
        rootView.addView(configView);
//        rootView.addView(web, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        setContentView(rootView);

//        buttonShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (bridge != null) {//����
//                    bridge.sharePage();
//                }
//            }
//        });
//        buttonRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (web != null) {//ˢ��
//                    web.reload();
//                }
//            }
//        });
    }

    /**
     * ������
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
     * ��ʼ���ŽӶ���
     * <p/>
     * {@link YouzanBridge}��һ���ŽӶ���, ���ڴ�ͨHtml5ҳ���ԭ���Ľ���.
     * ͨ��{@code YouzanBridge.build(activity, webView).create(); }�ķ�ʽ��ʼ��.
     * <p/>
     * ͨ��{@code YouzanBridge.add(event)}��������Ž��¼�, ����֧�ֵ��¼���:
     * <p/>
     * {@link ShareDataEvent} => �����¼�
     * ˵��:  ����{@code YouzanBridge.sharePage()}������
     * <p/>
     * {@link UserInfoEvent} => �û�ͬ����¼�¼�(�߽�ʵ��)
     * ˵��:  �����ʹ��{@code YouzanSDK.asyncRegisterUser(user);}
     * <p/>
     * {@link WXAppPayEvent} => ΢��APP֧���¼�(�߽�ʵ��)
     * ˵��:  �������޺�̨�Ѿ�����΢��APP֧��, �¼�����֧������, ������ʹ��΢��SDK
     * http://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419319167&token=&lang=zh_CN
     * ʵ����ת֧��.
     * <p/>
     * {@link WebReadyEvent} =>ҳ���ʼ�����(����)
     */
    private void initBridge()
    {
        bridge = YouzanBridge.build(this, web)
                //.setWebClient(new WebClient())client��չʾ��, ��������չ����ɾ�����д���
                .create();

        //�������������Ӧ���Ž��¼�
        bridge.add(new ShareEvent())//����
                .add(new ShareDataEvent()
                {

                    @Override
                    public void call(IBridgeEnv env, GoodsShareModel data)
                    {
                        /**
                         *���ߵ��÷������������Ʒ��Ϣ����, �ο�{@link ShareEvent}
                         */
                        new AlertDialog
                                .Builder(H5Activity.this)
                                .setTitle(data.getTitle())
                                .setMessage("��������:\n" + data.getLink() + "\n\nͼƬ����:\n" + data.getImgUrl() + "\n\n����:\n" + data.getDesc())
                                .create()
                                .show();

                    }
                })
                .add(new UserEvent());//ͬ��ע�������û�, ��Ҫ�ȴ���ҳ��Ҫ���û���¼�Ŀ���ʹ������
    }


    /**
     * ��������
     *
     * @param url ����, �Ƽ���Ҫʹ�ö�����(��һ���ض���)
     */
    private void loadPage(String url)
    {
        if (web != null && !TextUtils.isEmpty(url))
        {
            web.loadUrl(url);
        }
    }

    /**
     * ҳ�����
     * <p/>
     * bridge.pageGoBack()����True��ʾ���������ҳ�Ļ���
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
