package cn.incongress.continuestudyeducation.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.activity.LoginActivity;
import cn.incongress.continuestudyeducation.bean.Constant;
import cn.incongress.continuestudyeducation.utils.ActivityUtils;
import cn.incongress.continuestudyeducation.utils.LogUtils;
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

		if(JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			String title = bundle.getString(JPushInterface.EXTRA_TITLE);
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);

			LogUtils.i(TAG, "title=" + title + ",message=" + message);
			if(title.equals("loginOut") && message.equals("yes")) {
				//清空当前的用户数据及所有数据，达到未登录状态
				SharedPreferences sp = context.getSharedPreferences(Constant.DEFAULT_SP_NAME, context.MODE_PRIVATE);
				sp.edit().clear().commit();
				sp.edit().putBoolean(Constant.SP_IS_LOG_OUT, true).commit();

				if(isAppOnForeground(context) && sp.getBoolean(Constant.SP_IS_LOG_OUT,false)) {
					Toast.makeText(context, R.string.logout_tips, Toast.LENGTH_LONG).show();

					ActivityUtils.finishAll();
					Intent newIntent = new Intent();
					newIntent.setClass(context, LoginActivity.class);
					newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(newIntent);
					sp.edit().putBoolean(Constant.SP_IS_LOG_OUT, false).commit();
				}
			}
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

			Intent i = new Intent(context, LoginActivity.class);
			i.putExtras(bundle);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(i);
		}
	}

	//在进程中去寻找当前APP的信息，判断是否在前台运行
	private boolean isAppOnForeground(Context context) {
		ActivityManager activityManager =(ActivityManager) context.getSystemService(
				Context.ACTIVITY_SERVICE);
		String packageName = context.getPackageName();
		List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		if (appProcesses == null)
			return false;
		for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}
		return false;
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} 
			else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
}
