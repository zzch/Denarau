package com.zhongchuangtiyu.denarau.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by WangMeng on 2015/10/20.
 */
public class CustomToast
{
    private static Toast mToast = null;
    public static void showToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }

        mToast.show();
    }
}
