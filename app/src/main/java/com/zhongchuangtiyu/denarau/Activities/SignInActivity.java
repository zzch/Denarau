package com.zhongchuangtiyu.denarau.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zhongchuangtiyu.denarau.Entities.Sign_In;
import com.zhongchuangtiyu.denarau.Entities.Welcome;
import com.zhongchuangtiyu.denarau.Jpush.ExampleUtil;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;
import com.zhongchuangtiyu.denarau.Utils.BaseActivity;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.NetworkState;
import com.zhongchuangtiyu.denarau.Utils.StatusBarCompat;
import com.zhongchuangtiyu.denarau.Utils.ValidatePhoneNum;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

public class SignInActivity extends BaseActivity implements TextWatcher, View.OnClickListener
{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.loginPhoneNum)
    EditText loginPhoneNum;
    @Bind(R.id.welcomeTextView)
    TextView welcomeTextView;
    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.loginVerificationCode)
    EditText loginVerificationCode;
    @Bind(R.id.validateRlContainer)
    RelativeLayout validateRlContainer;
    @Bind(R.id.welcomeCourseTextView)
    TextView welcomeCourseTextView;
    @Bind(R.id.signInPhoneNumberRl)
    RelativeLayout signInPhoneNumberRl;
    @Bind(R.id.signInWelcomeRl)
    RelativeLayout signInWelcomeRl;
    @Bind(R.id.signInWelcomeCourseRl)
    RelativeLayout signInWelcomeCourseRl;
    @Bind(R.id.dividerBelowWelcomRl)
    ImageView dividerBelowWelcomRl;
    @Bind(R.id.dividerBelowWelcomeCourserRl)
    ImageView dividerBelowWelcomeCourserRl;
    @Bind(R.id.resendValidateCode)
    Button resendValidateCode;
    private CharSequence temp;//监听前的文本
    private int editStart;//光标开始位置
    private int editEnd;//光标结束位置
    private final int charMaxNum = 10;
    private TimeCount time = new TimeCount(60000,1000);
    private LocationClient mLocationClient;
    private LocationClientOption mOption;
    private String registration_id;
    private String  token;
    public double latitude, longitude;

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        StatusBarCompat.compat(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        signInWelcomeRl.setVisibility(View.GONE);
        dividerBelowWelcomRl.setVisibility(View.GONE);
        signInWelcomeCourseRl.setVisibility(View.GONE);
        dividerBelowWelcomeCourserRl.setVisibility(View.GONE);
        validateRlContainer.setVisibility(View.GONE);
        btnLogin.setVisibility(View.GONE);
        setListeners();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (NetworkState.isNetworkAvailable(SignInActivity.this))
        {
            initLocation();
            registerMessageReceiver();
            JPushInterface.init(getApplicationContext());
            registration_id = JPushInterface.getRegistrationID(SignInActivity.this);
            CacheUtils.putString(SignInActivity.this, "registration_id",registration_id);
            Log.d("REGId", registration_id + "regIdregIdregIdregIdregIdregIdregIdregId");
        }else
        {
            CustomToast.showToast(SignInActivity.this, "网络连接不可用");
        }
    }

    private void putRegistrationId()
    {
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
    private void registerMessageReceiver()
    {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }
    public class MessageReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction()))
            {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras))
                {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
            }
        }
    }
    private void setListeners()
    {
        loginPhoneNum.addTextChangedListener(this);
        loginVerificationCode.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (loginVerificationCode.getText().length() == 4)
                {

                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);

                }
            }
        });
        btnLogin.setOnClickListener(this);
        resendValidateCode.setOnClickListener(this);
    }
    private void initLocation()
    {
        mLocationClient = new LocationClient(this);
        mOption = new LocationClientOption();
    /* 设置选项 */
        mOption.setOpenGps(true);
        mOption.setCoorType("bd09ll");
        mOption.setScanSpan(100);		   //每隔0.1s, 扫描一次 (应该就是卫星定位的意思)
    /* 本地取址Client 端设置 Option选项 */
        mLocationClient.setLocOption(mOption);
    /* 设置监听器，监听服务器发送过来的地址信息 */
        mLocationClient.registerLocationListener(new BDLocationListener()
        {
            @Override
            public void onReceiveLocation(BDLocation bdLocation)
            {
                if (bdLocation == null)
                    return;
                StringBuffer sb = new StringBuffer(256);
        /* 获取经纬度 */
                latitude = bdLocation.getLatitude();
                longitude = bdLocation.getLongitude();
                mLocationClient.stop();
            }
        });

                if(mLocationClient == null)
                    return;
                mLocationClient.start();
    }

    private void setDividerBelowWelcomRlAnimIn()
    {
        Animation dividerBelowWelcomRlAnimIn = new TranslateAnimation(500f, dividerBelowWelcomRl.getScaleX(), dividerBelowWelcomRl.getScaleY(), dividerBelowWelcomRl.getScaleY());
        dividerBelowWelcomRlAnimIn.setDuration(400);
        dividerBelowWelcomRl.startAnimation(dividerBelowWelcomRlAnimIn);
        dividerBelowWelcomRl.setVisibility(View.VISIBLE);
        dividerBelowWelcomRlAnimIn.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
                if (dividerBelowWelcomeCourserRl.getVisibility() == View.VISIBLE || signInWelcomeCourseRl.getVisibility() == View.VISIBLE || validateRlContainer.getVisibility() == View.VISIBLE)
                {
                    dividerBelowWelcomeCourserRl.setVisibility(View.GONE);
                    signInWelcomeRl.setVisibility(View.GONE);
                    signInWelcomeCourseRl.setVisibility(View.GONE);
                    validateRlContainer.setVisibility(View.GONE);
                    btnLogin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                setSignInWelcomeRlAnimIn();
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }

    private void setDividerBelowWelcomRlAnimOut()
    {
        Animation dividerBelowWelcomRlAnimOut = new TranslateAnimation(dividerBelowWelcomRl.getScaleX(), 500f, dividerBelowWelcomRl.getScaleY(), dividerBelowWelcomRl.getScaleY());
        dividerBelowWelcomRlAnimOut.setDuration(200);
        dividerBelowWelcomRl.startAnimation(dividerBelowWelcomRlAnimOut);
        dividerBelowWelcomRlAnimOut.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                dividerBelowWelcomRl.setVisibility(View.GONE);
                if (signInWelcomeCourseRl.getVisibility() == View.VISIBLE)
                {
                    setSignInWelcomeCourseRlAnimout();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }

    private void setSignInWelcomeRlAnimIn()
    {
        Animation signInWelcomeRlAnimIn = new TranslateAnimation(500f, signInWelcomeRl.getScaleX(), signInWelcomeRl.getScaleY(), signInWelcomeRl.getScaleY());
        signInWelcomeRlAnimIn.setDuration(400);
        signInWelcomeRl.startAnimation(signInWelcomeRlAnimIn);
        signInWelcomeRl.setVisibility(View.VISIBLE);
        signInWelcomeRlAnimIn.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                if (!welcomeTextView.getText().equals("用户不存在"))
                {
                    setDividerBelowWelcomeCourserRlAnimIn();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }

    private void setSignInWelcomeRlAnimOut()
    {
        Animation signInWelcomeRlAnimOut = new TranslateAnimation(signInWelcomeRl.getScaleX(), 500f, signInWelcomeRl.getScaleY(), signInWelcomeRl.getScaleY());
        signInWelcomeRlAnimOut.setDuration(200);
        signInWelcomeRl.startAnimation(signInWelcomeRlAnimOut);
        signInWelcomeRlAnimOut.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
                validateRlContainer.setVisibility(View.GONE);
                btnLogin.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                signInWelcomeRl.setVisibility(View.GONE);
                setDividerBelowWelcomRlAnimOut();
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });


    }

    private void setDividerBelowWelcomeCourserRlAnimIn()
    {
        Animation dividerBelowWelcomeCourserRlAnimIn = new TranslateAnimation(500f, dividerBelowWelcomeCourserRl.getScaleX(), dividerBelowWelcomeCourserRl.getScaleY(), dividerBelowWelcomeCourserRl.getScaleY());
        dividerBelowWelcomeCourserRlAnimIn.setDuration(400);
        dividerBelowWelcomeCourserRl.startAnimation(dividerBelowWelcomeCourserRlAnimIn);
        dividerBelowWelcomeCourserRl.setVisibility(View.VISIBLE);
        dividerBelowWelcomeCourserRlAnimIn.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                setSignInWelcomeCourseRlAnimIn();
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }

    private void setDividerBelowWelcomeCourserRlAnimOut()
    {
        Animation dividerBelowWelcomeCourserRlAnimOut = new TranslateAnimation(dividerBelowWelcomeCourserRl.getScaleX(), 500f, dividerBelowWelcomeCourserRl.getScaleY(), dividerBelowWelcomeCourserRl.getScaleY());
        dividerBelowWelcomeCourserRlAnimOut.setDuration(200);
        dividerBelowWelcomeCourserRl.startAnimation(dividerBelowWelcomeCourserRlAnimOut);
        dividerBelowWelcomeCourserRlAnimOut.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                dividerBelowWelcomeCourserRl.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }

    private void setSignInWelcomeCourseRlAnimIn()
    {
        Animation signInWelcomeCourseRlAnimIn = new TranslateAnimation(500f, signInWelcomeCourseRl.getScaleX(), signInWelcomeCourseRl.getScaleY(), signInWelcomeCourseRl.getScaleY());
        signInWelcomeCourseRlAnimIn.setDuration(400);
        signInWelcomeCourseRl.startAnimation(signInWelcomeCourseRlAnimIn);
        signInWelcomeCourseRl.setVisibility(View.VISIBLE);
        signInWelcomeCourseRlAnimIn.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                setValidateRlContainerAnimIn();
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }

    private void setSignInWelcomeCourseRlAnimout()
    {
        Animation signInWelcomeCourseRlAnimout = new TranslateAnimation(signInWelcomeCourseRl.getScaleX(), 500f, signInWelcomeCourseRl.getScaleY(), signInWelcomeCourseRl.getScaleY());
        signInWelcomeCourseRlAnimout.setDuration(200);
        signInWelcomeCourseRl.startAnimation(signInWelcomeCourseRlAnimout);
        signInWelcomeCourseRlAnimout.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                signInWelcomeCourseRl.setVisibility(View.GONE);
                setDividerBelowWelcomeCourserRlAnimOut();
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }

    private void setValidateRlContainerAnimIn()
    {
        Animation validateRlContainerAnimIn = new TranslateAnimation(500f, validateRlContainer.getScaleX(), validateRlContainer.getScaleY(), validateRlContainer.getScaleY());
        validateRlContainerAnimIn.setDuration(350);
        validateRlContainer.setVisibility(View.VISIBLE);
        validateRlContainer.startAnimation(validateRlContainerAnimIn);
        time.start();
        validateRlContainerAnimIn.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                setBtnLoginAnimIn();
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }
    private void setValidateRlContainerAnimout()
    {
        Animation validateRlContainerAnimout = new TranslateAnimation(validateRlContainer.getScaleX(), 500f, validateRlContainer.getScaleY(), validateRlContainer.getScaleY());
        validateRlContainerAnimout.setDuration(400);
        validateRlContainer.startAnimation(validateRlContainerAnimout);
        validateRlContainerAnimout.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                setBtnLoginAnimOut();
                validateRlContainer.setVisibility(View.GONE);
                time.onFinish();
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }
    private void setBtnLoginAnimIn()
    {
        Animation btnLoginAnimIn = new AlphaAnimation(0,1);
        btnLoginAnimIn.setDuration(500);
        btnLogin.startAnimation(btnLoginAnimIn);
        btnLogin.setVisibility(View.VISIBLE);
    }
    private void setBtnLoginAnimOut()
    {
        Animation btnLoginAnimOut = new TranslateAnimation(btnLogin.getScaleX(), 500f, btnLogin.getScaleY(), btnLogin.getScaleY());
        btnLoginAnimOut.setDuration(350);
        btnLogin.startAnimation(btnLoginAnimOut);
        btnLoginAnimOut.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                btnLogin.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }
    @Override

    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {

    }
    @Override
    public void afterTextChanged(Editable s)
    {
        if (ValidatePhoneNum.isMobileNO(loginPhoneNum.getText().toString()) && dividerBelowWelcomeCourserRl.getVisibility() == View.VISIBLE)
        {
            dividerBelowWelcomeCourserRl.setVisibility(View.GONE);

            sendAskForValidateCodeRequest();
        } else if (ValidatePhoneNum.isMobileNO(loginPhoneNum.getText().toString()) && dividerBelowWelcomeCourserRl.getVisibility() == View.GONE)
        {
            sendAskForValidateCodeRequest();
        } else if (loginPhoneNum.getText().toString().length() == 10 && signInWelcomeRl.getVisibility() == View.VISIBLE)
        {
            setSignInWelcomeRlAnimOut();
        }

    }


    private void sendAskForValidateCodeRequest()
    {
        Map<String, String> map = new HashMap<>();
        MyApplication.volleyGET(APIUrls.WELCOME_URL + loginPhoneNum.getText().toString(), map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                Xlog.d(response.toString());
                Welcome welcome = Welcome.instance(response);
                String exceptionMsg = response.toString();
                if (welcome != null && exceptionMsg.contains("20001"))
                {
                    welcomeTextView.setText("用户不存在");
                    setDividerBelowWelcomRlAnimIn();
                }
                else if (welcome != null && exceptionMsg.contains("20002"))
                {
                    welcomeTextView.setText("用户未激活");
                    setDividerBelowWelcomRlAnimIn();
                }
                else if (welcome != null && !exceptionMsg.contains("20001"))
                {
                    if(getCurrentFocus()!=null)
                    {
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getCurrentFocus()
                                                .getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    String welcomeMsg = welcome.getSentences().get(0);
                    String welcomeCourseMsg = welcome.getSentences().get(1);
                    welcomeTextView.setText(welcomeMsg);
                    welcomeCourseTextView.setText(welcomeCourseMsg);
                    setDividerBelowWelcomRlAnimIn();
                }


            }

            @Override
            public void netFail(VolleyError error)
            {
                if (error.toString().contains("AuthFailureError"))
                {
                    CustomToast.showToast(SignInActivity.this, "登录失效，请重新登录");
                    startActivity(new Intent(SignInActivity.this, SignInActivity.class));
                    finish();
                    ActivityCollector.finishAll();
                }else
                {
                    CustomToast.showToast(SignInActivity.this, "网络连接失败，请检查网络连接");
                }
            }
        });
    }
    private void resendValidateCodeRequest()
    {
        Map<String, String> map = new HashMap<>();
        MyApplication.volleyGET(APIUrls.WELCOME_URL + loginPhoneNum.getText().toString(), map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                CustomToast.showToast(SignInActivity.this, "验证码已发送");
            }

            @Override
            public void netFail(VolleyError error)
            {
                CustomToast.showToast(SignInActivity.this, "网络连接失败");
            }
        });
    }
    private void sendSignInRequest()
    {
        Map<String, String> validate = new HashMap<>();
        validate.put("phone", loginPhoneNum.getText().toString());
        validate.put("verification_code", loginVerificationCode.getText().toString());
        DecimalFormat df = new DecimalFormat("####0.00");
        Xlog.d(String.valueOf(latitude) + "latitude-------------------------------------");
        Xlog.d(String.valueOf(longitude) + "longitude------------------------------------");
        if (!String.valueOf(latitude).equals("4.9E-324") && !String.valueOf(latitude).equals("4.9E-324"))
        {
            validate.put("latitude", df.format(latitude));
            validate.put("longitude",df.format(longitude));
        }
        MyApplication.volleyPOST(APIUrls.SIGN_IN_URL, validate, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                Xlog.d(response + "---------------------------------------");
                Sign_In data = Sign_In.instance(response);
                if (data.getException_code() == 20003)
                {
                    CustomToast.showToast(SignInActivity.this, "验证码错误");
                } else
                {
                    String name = data.getUser().getName();
                    String gender = data.getUser().getGender();
                    token = data.getUser().getToken();
                    String club_uuid = data.getClub().getUuid();
                    String userUuid = data.getUser().getUuid();
                    String telephone = loginPhoneNum.getText().toString();
                    CacheUtils.putString(SignInActivity.this, "token", token);
                    CacheUtils.putString(SignInActivity.this, "clubuuid", club_uuid);
                    CacheUtils.putString(SignInActivity.this, "name", name);
                    CacheUtils.putString(SignInActivity.this, "gender", gender);
                    CacheUtils.putString(SignInActivity.this, "userUuid", userUuid);
                    CacheUtils.putString(SignInActivity.this, "telephone", telephone);
                    if (!registration_id.equals(""))
                    {
                        putRegistrationId();
                    }
                    startActivity(new Intent(SignInActivity.this, MembershipCardMainActivity.class));
                    finish();
                }
            }

            @Override
            public void netFail(VolleyError error)
            {

//                CustomToast.showToast(SignInActivity.this, error.toString());
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnLogin:
                if(String.valueOf(loginVerificationCode.getText()).trim().equals(""))
                {
                    CustomToast.showToast(SignInActivity.this,"验证码不能为空");
                }
                else if (loginVerificationCode.getText().length() < 4 && loginVerificationCode.getText().length() > 0)
                {
                    CustomToast.showToast(SignInActivity.this,"请输入完整验证码");
                }
                else
                {
                    sendSignInRequest();
                    Xlog.d("registration_idregistration_idregistration_idregistration_idregistration_idregistration_id" + registration_id);
                }
                break;
            case R.id.resendValidateCode:
                time.start();
                resendValidateCodeRequest();
                break;
            default:
                break;
        }

     }


    class TimeCount extends CountDownTimer
    {
        public TimeCount(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish()
        {//计时完毕时触发
            resendValidateCode.setText("重新验证");
            resendValidateCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished)
        {//计时过程显示
            resendValidateCode.setClickable(false);
            resendValidateCode.setText(millisUntilFinished / 1000 + "秒");
        }
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        if (NetworkState.isNetworkAvailable(SignInActivity.this))
        {
            unregisterReceiver(mMessageReceiver);
        }
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        ActivityCollector.removeActivity(this);
    }
}
