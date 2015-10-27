package com.zhongchuangtiyu.denarau.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.zhongchuangtiyu.denarau.Entities.Sign_In;
import com.zhongchuangtiyu.denarau.Entities.Welcome;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.ValidatePhoneNum;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity implements TextWatcher,View.OnClickListener
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
    private CharSequence temp;//监听前的文本
    private int editStart;//光标开始位置
    private int editEnd;//光标结束位置
    private final int charMaxNum = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        welcomeTextView.setVisibility(View.GONE);
        welcomeCourseTextView.setVisibility(View.GONE);
        validateRlContainer.setVisibility(View.GONE);
        setListeners();

    }

    private void setListeners()
    {
        loginPhoneNum.addTextChangedListener(this);
        btnLogin.setOnClickListener(this);
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
        if (ValidatePhoneNum.isMobileNO(loginPhoneNum.getText().toString()))
        {
            sendAskForValidateCodeRequest();
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
                final Animation translateAnimation1 = new TranslateAnimation(500f, welcomeCourseTextView.getTextScaleX(),welcomeCourseTextView.getScaleY(),welcomeCourseTextView.getScaleY());
                translateAnimation1.setDuration(1000);
                Animation translateAnimation2 = new TranslateAnimation(500f, welcomeTextView.getTextScaleX(),welcomeTextView.getScaleY(),welcomeTextView.getScaleY());
                translateAnimation2.setDuration(1000);
                final Animation translateAnimation3 = new TranslateAnimation(500f, validateRlContainer.getScaleX(),validateRlContainer.getScaleY(),validateRlContainer.getScaleY());
                translateAnimation3.setDuration(1000);
                Xlog.d(response.toString());
                Welcome welcome = Welcome.instance(response);
                String welcomeMsg = welcome.getSentences().get(0);
                String welcomeCourseMsg = welcome.getSentences().get(1);
                welcomeTextView.setText(welcomeMsg);
                welcomeCourseTextView.setText(welcomeCourseMsg);
                welcomeTextView.startAnimation(translateAnimation2);
                welcomeTextView.setVisibility(View.VISIBLE);
                translateAnimation2.setAnimationListener(new Animation.AnimationListener()
                {
                    @Override
                    public void onAnimationStart(Animation animation)
                    {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation)
                    {
                        welcomeCourseTextView.startAnimation(translateAnimation1);
                        welcomeCourseTextView.setVisibility(View.VISIBLE);
                        translateAnimation1.setAnimationListener(new Animation.AnimationListener()
                        {
                            @Override
                            public void onAnimationStart(Animation animation)
                            {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation)
                            {
                                validateRlContainer.startAnimation(translateAnimation3);
                                validateRlContainer.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation)
                            {

                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation)
                    {

                    }
                });


            }

            @Override
            public void netFail(VolleyError error)
            {
                Xlog.d(error.toString() + "---------------------------------------------");
                Toast.makeText(SignInActivity.this, "用户不存在，请检查手机号码", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendSignInRequest()
    {
        Map<String, String> validate = new HashMap<>();
        validate.put("phone", loginPhoneNum.getText().toString());
        validate.put("verification_code", loginVerificationCode.getText().toString());
        MyApplication.volleyPOST(APIUrls.SIGN_IN_URL, validate, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                Xlog.d(response + "---------------------------------------");
                Sign_In data = Sign_In.instance(response);
                if (data.getException_code() == 20003)
                {
                    Toast.makeText(SignInActivity.this,"验证码错误",Toast.LENGTH_SHORT).show();
                }else
                {
                    String name = data.getUser().getName();
                    String gender = data.getUser().getGender();
                    String token = data.getUser().getToken();
                    String club_uuid = data.getClub().getUuid();
                    CacheUtils.putString(SignInActivity.this, "token", token);
                    CacheUtils.putString(SignInActivity.this, "clubuuid", club_uuid);
                    CacheUtils.putString(SignInActivity.this, "name", name);
                    CacheUtils.putString(SignInActivity.this, "gender", gender);
                    startActivity(new Intent(SignInActivity.this,MembershipCardMainActivity.class));
                    finish();
                }
            }
            @Override
            public void netFail(VolleyError error)
            {
                Xlog.d(error.toString() + "---------------------------------------------");
                Toast.makeText(SignInActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnLogin:
                sendSignInRequest();
                break;
            default:
                break;
        }

    }

}
