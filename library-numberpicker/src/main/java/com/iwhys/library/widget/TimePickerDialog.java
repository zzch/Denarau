package com.iwhys.library.widget;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.iwhys.library.NumberPicker;
import com.iwhys.library.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by devil on 15/5/18.
 * 时间选择
 */
public class TimePickerDialog extends BaseDialog {

    private final static int H_MAX = 23, H_MIN = 0;
    private final static int M_MAX = 59, M_MIN = 0;
    private final static int MONTH_DELTA = 2;//设置天数，单位为月份，当前设置可选择2个月内的日期

    private TextView selected;
    private NumberPicker day, hour, minute;
    private int dValue, hValue, mValue;
    private Date current = new Date();
    private int minDay, maxDay;
    private OnSelectListener onSelectListener;

    public TimePickerDialog(Context context, Date date, OnSelectListener onSelectListener) {
        super(context, R.layout.time_picker);
        this.onSelectListener = onSelectListener;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.hValue = calendar.get(Calendar.HOUR_OF_DAY);
        this.mValue = calendar.get(Calendar.MINUTE);
        calendar.setTime(current);
        minDay = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.add(Calendar.MONTH, MONTH_DELTA);
        maxDay = minDay + daysBetween(current, calendar.getTime());
        this.dValue = minDay + daysBetween(current, date);
        init();
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

    private void init() {
        day = (NumberPicker) findViewById(R.id.day);
        hour = (NumberPicker) findViewById(R.id.hour);
        minute = (NumberPicker) findViewById(R.id.minute);
        selected = (TextView) findViewById(R.id.selected);
        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String timeStr = selected.getText().toString();
                final Date date = getDateFromString(timeStr, "yyyy-MM-dd HH:mm");
                if (onSelectListener != null) {
                    onSelectListener.onSelect(date, selected.getText().toString().trim());
                }
                dismiss();
            }
        });
        //设置天
        setDay();
        //设置小时
        setHour();
        //设置分
        setMinute();
        //设置选中的时间
        setSelect();
    }

    private void setDay() {
        day.setMinValue(minDay);
        day.setMaxValue(maxDay);
        day.setValue(dValue);
        day.setDisplayedValues(getDayDisplayValue());
        setDayValue();
        day.setWrapSelectorWheel(true);
        day.setFocusableInTouchMode(true);
        day.setFocusable(true);
        day.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dValue = newVal;
                setDayValue();
                setSelect();
            }
        });
    }

    //设置Day的日期到day控件的tag
    private void setDayValue() {
        Calendar c = Calendar.getInstance();
        c.setTime(current);
        c.add(Calendar.DATE, dValue - minDay);
        day.setTag(c.getTime());
    }

    private void setHour() {
        hour.setMaxValue(H_MAX);
        hour.setMinValue(H_MIN);
        hour.setValue(hValue);
        hour.setFormatter(NumberPicker.getTwoDigitFormatter());
        hour.setWrapSelectorWheel(true);
        hour.setFocusableInTouchMode(true);
        hour.setFocusable(true);
        hour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hValue = newVal;
                setSelect();
            }
        });
    }

    private String[] getDayDisplayValue() {
        String[] days = new String[maxDay - minDay + 1];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        for (int i = 0; i < days.length; i++) {
            final Date date = calendar.getTime();
            days[i] = getTimeFormat("MM月dd日 EEE", date);
            calendar.add(Calendar.DATE, 1);
        }
        return days;
    }

    private void setMinute() {
        minute.setMinValue(M_MIN);
        minute.setMaxValue(M_MAX);
        minute.setValue(mValue);
        minute.setFormatter(NumberPicker.getTwoDigitFormatter());
        minute.setWrapSelectorWheel(true);
        minute.setFocusableInTouchMode(true);
        minute.setFocusable(true);
        minute.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mValue = newVal;
                setSelect();
            }
        });
    }

    //设置已选中值
    private void setSelect() {
        final Date date = (Date) day.getTag();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getTimeFormat("yyyy-MM-dd", date));
        stringBuilder.append(" ");
        stringBuilder.append(twoDigitFormatter(hValue));
        stringBuilder.append(":");
        stringBuilder.append(twoDigitFormatter(mValue));
        selected.setText(stringBuilder.toString());
    }

    //显示两位数
    private String twoDigitFormatter(int value) {
        return String.format("%02d", value);
    }

    /**
     * 计算两个日期之间相差天数
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return
     * @throws ParseException
     */
    private int daysBetween(Date beginDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            beginDate = sdf.parse(sdf.format(beginDate));
            endDate = sdf.parse(sdf.format(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    private String getTimeFormat(String format, Date date) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(date);
    }

    private Date getDateFromString(String timeStr, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface OnSelectListener {

        /**
         * 确认选择
         * @param values 值
         * @param displayName 显示名称
         */
        void onSelect(Date values, String displayName);
    }
}