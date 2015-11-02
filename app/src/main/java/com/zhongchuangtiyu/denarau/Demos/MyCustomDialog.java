package com.zhongchuangtiyu.denarau.Demos;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iwhys.library.NumberPicker;
import com.iwhys.library.UnitNumberPicker;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.Xlog;


/**
 * 作者：WangMeng on 2015/11/2 12:17
 * 邮箱：wangmeng.wiz@foxmail.com
 */

public class MyCustomDialog extends Dialog
{


    //定义回调事件，用于dialog的点击事件
    public interface OnCustomDialogListener
    {
        public void back(String time);
    }

    private String time;
    private OnCustomDialogListener customDialogListener;
    private String[] data = {"09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00"};
    private String selectedValue = "09:00";

    public MyCustomDialog(Context context, String time, OnCustomDialogListener customDialogListener)
    {
        super(context);
        this.time = time;
        this.customDialogListener = customDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.position_time_picker_layout);
        //设置标题
        setTitle(time);
        UnitNumberPicker clock = (UnitNumberPicker) findViewById(R.id.clock);
        clock.setDisplayedValues(data);
        clock.setMinValue(0);
        clock.setMaxValue(data.length - 1);
        clock.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
                selectedValue = data[newVal];
                Xlog.d(selectedValue + "selectedValue------------------------------------------");
            }
        });
        Button clickBtn = (Button) findViewById(R.id.btnConfirm);
        clickBtn.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            customDialogListener.back(String.valueOf(selectedValue));
            MyCustomDialog.this.dismiss();
        }
    };

}
