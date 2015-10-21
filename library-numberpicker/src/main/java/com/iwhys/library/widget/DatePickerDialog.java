package com.iwhys.library.widget;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.iwhys.library.NumberPicker;
import com.iwhys.library.R;

import java.util.Calendar;

/**
 * Created by devil on 15/5/15.
 * 日期选择
 */
public class DatePickerDialog extends BaseDialog {

    public final static int YEAR_MAX = 2009, YEAR_MIN = 1970;
    public final static int MONTH_MAX = 12, MONTH_MIN = 1;
    public final static int DAY_MIN = 1;

    private TextView selected;
    private NumberPicker year,month,day;
    private int yearValue, monthValue, dayValue;
    private int dayMax;
    private OnSelectListener onSelectListener;

    public DatePickerDialog(Context context, int yearValue, int monthValue, int dayValue, OnSelectListener onSelectListener) {
        super(context, R.layout.date_picker);
        //验证年是否有效
        if (yearValue>YEAR_MAX||yearValue<YEAR_MIN){
            yearValue = YEAR_MIN;
        }
        this.yearValue = yearValue;
        //验证月是否有效
        if (monthValue>MONTH_MAX||monthValue<MONTH_MIN){
            monthValue = MONTH_MIN;
        }
        this.monthValue = monthValue;
        //根据年月计算最大天数
        dayMax = getMaxDay(yearValue,monthValue);
        //验证天是否有效
        if (dayValue>dayMax||dayValue<DAY_MIN){
            dayValue = DAY_MIN;
        }
        this.dayValue = dayValue;
        this.onSelectListener = onSelectListener;
        //初始化控件
        init();
        //设置年
        setYear();
        //设置月
        setMonth();
        //设置天
        setDay();
    }

    @Override
    protected int dialogAnimation() {
        return R.style.AnimationBottomDialog;
    }

    @Override
    protected int dialogGravity() {
        return Gravity.BOTTOM;
    }


    @Override
    protected int dialogWidth() {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    private void init(){
        year = (NumberPicker)findViewById(R.id.year);
        month = (NumberPicker)findViewById(R.id.month);
        day = (NumberPicker)findViewById(R.id.day);
        selected = (TextView)findViewById(R.id.selected);
        setSelect();
        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSelectListener != null) {
                    onSelectListener.onSelect(new int[]{yearValue, monthValue, dayValue}, selected.getText().toString().trim());
                }
                dismiss();
            }
        });
    }

    private void setYear(){
        year.setMaxValue(YEAR_MAX);
        year.setMinValue(YEAR_MIN);
        year.setValue(yearValue);
        year.setWrapSelectorWheel(true);
        year.setFocusableInTouchMode(true);
        year.setFocusable(true);
        year.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                yearValue = newVal;
                setSelect();
                updateDayValue();
            }
        });
    }

    private void setMonth(){
        month.setMaxValue(MONTH_MAX);
        month.setMinValue(MONTH_MIN);
        month.setValue(monthValue);
        month.setWrapSelectorWheel(true);
        month.setFocusableInTouchMode(true);
        month.setFocusable(true);
        month.setFormatter(NumberPicker.getTwoDigitFormatter());
        month.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                monthValue = newVal;
                setSelect();
                updateDayValue();
            }
        });
    }

    private void setDay(){
        day.setMinValue(DAY_MIN);
        day.setMaxValue(dayMax);
        day.setValue(dayValue);
        day.setWrapSelectorWheel(true);
        day.setFocusableInTouchMode(true);
        day.setFocusable(true);
        day.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });
        day.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dayValue = newVal;
                setSelect();
            }
        });
    }

    //年、月变化后需同时更新天
    private void updateDayValue(){
        dayMax = getMaxDay(yearValue, monthValue);
        day.setMaxValue(dayMax);
        if (dayValue > dayMax) {
            dayValue = dayMax;
        }
        day.setValue(dayValue);
    }

    //根据年月获取天数最大值
    private int getMaxDay(int yearValue, int monthValue){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, yearValue);
        c.set(Calendar.MONTH, monthValue - 1);
        return c.getActualMaximum(Calendar.DATE);
    }

    //设置已选中值
    private void setSelect(){
        selected.setText(yearValue + "年" + twoDigitFormatter(monthValue) + "月" + twoDigitFormatter(dayValue) + "日");
    }

    //显示两位数
    private String twoDigitFormatter(int value){
        return String.format("%02d", value);
    }

    public interface OnSelectListener {

        /**
         * 确认选择
         * @param values 值
         * @param displayName 显示名称
         */
        void onSelect(int[] values, String displayName);
    }
}
