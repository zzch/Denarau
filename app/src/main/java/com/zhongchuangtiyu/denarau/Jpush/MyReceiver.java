package com.zhongchuangtiyu.denarau.Jpush;



import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.zhongchuangtiyu.denarau.Activities.TabsListActivity;
import com.zhongchuangtiyu.denarau.Demos.Test3;
import com.zhongchuangtiyu.denarau.Entities.JpushCustomMessageEntity;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.AppState;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.DensityUtil;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@Override
	public void onReceive(final Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        	processCustomMessage(context, bundle);
//			String customMsg = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			JpushCustomMessageEntity customMsg = JpushCustomMessageEntity.instance(bundle.getString(JPushInterface.EXTRA_EXTRA));
			String redirect_to = customMsg.getRedirect_to();
			String club_uuid = customMsg.getClub_uuid();
			Xlog.d("bundle.getString(JPushInterface.EXTRA_MESSAGE).toString()" + bundle.getString(JPushInterface.EXTRA_EXTRA));
			Xlog.d("club_uuidclub_uuidclub_uuidclub_uuidclub_uuidclub_uuid" + club_uuid);
			Xlog.d("redirect_toredirect_toredirect_toredirect_toredirect_toredirect_toredirect_to" + redirect_to);
			Intent intent1 = new Intent(context, Test3.class);
			intent1.putExtras(bundle);
			intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(intent1);
			Xlog.d("customMsgcustomMsgcustomMsgcustomMsgcustomMsgcustomMsgcustomMsg" + customMsg);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
			String msg = bundle.getString(JPushInterface.EXTRA_ALERT);
			JpushCustomMessageEntity customMsg = JpushCustomMessageEntity.instance(bundle.getString(JPushInterface.EXTRA_EXTRA));
			final String redirect_to = customMsg.getRedirect_to();
			final String club_uuid = customMsg.getClub_uuid();
			if (!AppState.isApplicationBroughtToBackground(context))
			{

				JPushInterface.clearAllNotifications(context);
				AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppTheme);
				builder.setMessage(msg)
						.setCancelable(false)
						.setPositiveButton("去看看", new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int id)
							{
								if (redirect_to.equals("tabs_list"))
								{
									CacheUtils.putString(context, "clubuuid", club_uuid);
									Intent i = new Intent(context, TabsListActivity.class);
									i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
									context.startActivity(i);
								}
							}
						})
						.setNegativeButton("否", new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int id)
							{
								dialog.cancel();
							}
						});
				AlertDialog alert = builder.create();
				alert.getWindow().setLayout(DensityUtil.dp2px(context,300), WindowManager.LayoutParams.WRAP_CONTENT);
				alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

				alert.show();
				CustomToast.showToast(context, "AppState.isApplicationBroughtToBackground(context)");
			}
			Xlog.d("AppState.isApplicationBroughtToBackground(context)" + AppState.isApplicationBroughtToBackground(context));

			Xlog.d("redirect_toredirect_toredirect_toredirect_toredirect_to" + redirect_to);
			Xlog.d("club_uuidclub_uuidclub_uuidclub_uuidclub_uuidclub_uuid" + club_uuid);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
			JpushCustomMessageEntity customMsg = JpushCustomMessageEntity.instance(bundle.getString(JPushInterface.EXTRA_EXTRA));
			String redirect_to = customMsg.getRedirect_to();
			String club_uuid = customMsg.getClub_uuid();
        	//打开自定义的Activity
			if (redirect_to.contains("tabs_list"))
			{
				CacheUtils.putString(context, "club_uuid", club_uuid);
				Intent i = new Intent(context, TabsListActivity.class);
				i.putExtras(bundle);
				//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
				context.startActivity(i);
			}

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        	Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
        	Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
	}


	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	
	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
		if (JpushMainActivity.isForeground) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent(JpushMainActivity.MESSAGE_RECEIVED_ACTION);
			msgIntent.putExtra(JpushMainActivity.KEY_MESSAGE, message);
			if (!ExampleUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (null != extraJson && extraJson.length() > 0) {
						msgIntent.putExtra(JpushMainActivity.KEY_EXTRAS, extras);
					}
				} catch (JSONException e) {

				}

			}
			context.sendBroadcast(msgIntent);
		}
	}
}
