package com.zhongchuangtiyu.denarau.Demos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iwhys.library.NumberPicker;
import com.iwhys.library.UnitNumberPicker;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wangm on 2015/11/2.
 */
public class Test extends Activity
{
    @Bind(R.id.view7)
    CircleImageView view7;
    @Bind(R.id.textView9)
    TextView textView9;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.button2)
    Button button2;
    NumberPicker clock = (NumberPicker) findViewById(R.id.clock);
    private String selectedValue = "09:00";
    private String[] data = {"09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayout);
        ButterKnife.bind(this);
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(Test.this);
//                builder.setView(R.layout.position_time_picker_layout);
//                final UnitNumberPicker clock = (UnitNumberPicker) v.findViewById(R.id.clock);
//                clock.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
//                {
//                    @Override
//                    public void onValueChange(NumberPicker picker, int oldVal, int newVal)
//                    {
//                        String selectedValue = "09:00";
//                        String[] data = {"09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00"};
//                        clock.setDisplayedValues(data);
//                        clock.setMinValue(0);
//                        clock.setMaxValue(data.length - 1);
//                        selectedValue = data[newVal];
//                        Xlog.d(selectedValue + "selectedValue------------------------------------------");
//                    }
//                });
//                builder.setTitle("请选择预约时间");
//                builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which)
//                    {
//
//                    }
//                });
//                builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which)
//                    {
//                        dialog.dismiss();
//                    }
//                });
//
//                builder.create();
//                builder.show();
                customDialog();
                customDialog();
            }
        });
    }

    private void customDialog()
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.position_time_picker_layout, null);
        final Dialog dialog = new AlertDialog.Builder(Test.this).create();
        initClock();
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setContentView(layout);

    }

    private void initClock()
    {
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
    }
}
