package com.iwhys.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;


/**
 * Created by devil on 15/5/18.
 * 自定义NumberPicker 绘制单位
 */
public class UnitNumberPicker extends NumberPicker {

    private int textX, textY;
    private String unitText;
    private Paint paint;

    public UnitNumberPicker(Context context) {
        super(context);
    }

    public UnitNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs == null) throw new RuntimeException("DateNumberPicker只能从xml布局文件调用");
        TypedArray ta = getContext().obtainStyledAttributes(attrs, com.iwhys.library.R.styleable.UnitNumberPicker);
        unitText = ta.getString(com.iwhys.library.R.styleable.UnitNumberPicker_unitText);
        float unitTextSize = ta.getDimension(com.iwhys.library.R.styleable.UnitNumberPicker_unitTextSize, 14);
        int unitTextColor = ta.getColor(com.iwhys.library.R.styleable.UnitNumberPicker_unitTextColor, Color.GRAY);
        ta.recycle();
        paint = new Paint();
        paint.setColor(unitTextColor);
        paint.setTextSize(unitTextSize);
        paint.setAntiAlias(true);
        post(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(unitText)) return;
                Rect rect = new Rect();
                paint.getTextBounds(unitText, 0, unitText.length(), rect);
                textX = getWidth() - rect.width() - 20;
                textY = getHeight() / 3 + rect.height() + 20;
            }
        });
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(unitText)) {
            canvas.drawText(unitText, textX, textY, paint);
        }
    }

}
