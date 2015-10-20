package com.zhongchuangtiyu.denarau.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by WangMeng on 2015/10/20.
 */
public class CustomToast
{
    public static void toast(Context context,String msg)
    {
        Toast toast = new Toast(context);
        toast.setText(msg);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
