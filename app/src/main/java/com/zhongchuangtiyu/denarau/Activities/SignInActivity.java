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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Entities.Sign_In;
import com.zhongchuangtiyu.denarau.Entities.Welcome;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.ValidatePhoneNum;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener
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
        signInWelcomeRl.setVisibility(View.GONE);
        signInWelcomeCourseRl.setVisibility(View.GONE);
        dividerBelowWelcomRl.setVisibility(View.GONE);
        dividerBelowWelcomeCourserRl.setVisibility(View.GONE);
        validateRlContainer.setVisibility(View.GONE);
        setListeners();

    }

    private void setListeners()
    {
        loginPhoneNum.addTextChangedListener(this);
        btnLogin.setOnClickListener(this);
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
                if (dividerBelowWelcomeCourserRl.getVisibility() == View.VISIBLE || signInWelcomeCourseRl.getVisibility() == View.VISIBLE)
                {
                    dividerBelowWelcomeCourserRl.setVisibility(View.GONE);
                    signInWelcomeRl.setVisibility(View.GONE);
                    signInWelcomeCourseRl.setVisibility(View.GONE);
                    validateRlContainer.setVisibility(View.GONE);
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
        dividerBelowWelcomRlAnimOut.setDuration(400);
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
        signInWelcomeRlAnimOut.setDuration(400);
        signInWelcomeRl.startAnimation(signInWelcomeRlAnimOut);
        signInWelcomeRlAnimOut.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

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
        dividerBelowWelcomeCourserRlAnimOut.setDuration(400);
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
                setValidateRlContainerAnimout();
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
        signInWelcomeCourseRlAnimout.setDuration(400);
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
        validateRlContainerAnimIn.setDuration(400);
        validateRlContainer.setVisibility(View.VISIBLE);
        validateRlContainer.startAnimation(validateRlContainerAnimIn);
    }
    @Override

    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {

    }
    private void setValidateRlContainerAnimout()
    {
        Animation validateRlContainerAnimout = new TranslateAnimation( validateRlContainer.getScaleX(), 500f,  validateRlContainer.getScaleY(),  validateRlContainer.getScaleY());
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
                validateRlContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }

    @Override
    public void afterTextChanged(Editable s)
    {
        if (ValidatePhoneNum.isMobileNO(loginPhoneNum.getText().toString()) && dividerBelowWelcomeCourserRl.getVisibility() == View.VISIBLE)
        {
            dividerBelowWelcomeCourserRl.setVisibility(View.GONE);

            sendAskForValidateCodeRequest();
        }
        else if (ValidatePhoneNum.isMobileNO(loginPhoneNum.getText().toString()) && dividerBelowWelcomeCourserRl.getVisibility() == View.GONE)
        {
            sendAskForValidateCodeRequest();
        }
        else if (loginPhoneNum.getText().toString().length() == 10 && signInWelcomeRl.getVisibility() == View.VISIBLE)
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
                final Animation translateAnimation1 = new TranslateAnimation(500f, signInWelcomeCourseRl.getScaleX(), signInWelcomeCourseRl.getScaleY(), signInWelcomeCourseRl.getScaleY());
                translateAnimation1.setDuration(400);
                Animation translateAnimation2 = new TranslateAnimation(500f, signInWelcomeRl.getScaleX(), signInWelcomeRl.getScaleY(), signInWelcomeRl.getScaleY());
                translateAnimation2.setDuration(400);
                Xlog.d(response.toString());
                Welcome welcome = Welcome.instance(response);
                String exceptionMsg = response.toString();
                if (welcome != null && exceptionMsg.contains("20001"))
                {
                    welcomeTextView.setText("用户不存在");
                    setDividerBelowWelcomRlAnimIn();
                }
                else if (welcome != null && !exceptionMsg.contains("20001"))
                {

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
                Xlog.d(error.toString() + "---------------------------------------------");
                Toast.makeText(SignInActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(SignInActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                } else
                {
                    String name = data.getUser().getName();
                    String gender = data.getUser().getGender();
                    String token = data.getUser().getToken();
                    String club_uuid = data.getClub().getUuid();
                    CacheUtils.putString(SignInActivity.this, "token", token);
                    CacheUtils.putString(SignInActivity.this, "clubuuid", club_uuid);
                    CacheUtils.putString(SignInActivity.this, "name", name);
                    CacheUtils.putString(SignInActivity.this, "gender", gender);
                    startActivity(new Intent(SignInActivity.this, MembershipCardMainActivity.class));
                    finish();
                }
            }

            @Override
            public void netFail(VolleyError error)
            {
                Xlog.d(error.toString() + "---------------------------------------------");
                Toast.makeText(SignInActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
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
