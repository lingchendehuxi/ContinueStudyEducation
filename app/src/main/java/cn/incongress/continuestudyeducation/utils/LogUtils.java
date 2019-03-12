package cn.incongress.continuestudyeducation.utils;

import android.util.Log;

import cn.incongress.continuestudyeducation.bean.Constant;

/**
 * @author great.maronghua@gmail.com
 * @since 2012-8-15
 * TODO 发布的时候清空这些方法
 */
public final class
LogUtils {
	
	public static void println(String msg) {
		if (Constant.DEBUG) {
			Log.i("LogUtils", msg);
		}
	}

	public static void printStackTrace(Exception e) {
		if (Constant.DEBUG) {
			e.printStackTrace();
		}
	}
	
	public static void printStackTrace(Error e) {
		if (Constant.DEBUG) {
			e.printStackTrace();
		}
	}
	
	public static void v(String tag, String msg) {
		if (Constant.DEBUG) {
			Log.v(tag, msg);
		}
	}
	
	public static void i(String tag, String msg) {
		if (Constant.DEBUG) {
			Log.i(tag, msg);
		}
	}
	
	public static void e(String tag, String msg) {
		if (Constant.DEBUG) {
			Log.e(tag, msg);
		}
	}
	
	public static void e(String tag, String msg, Throwable thr) {
		if (Constant.DEBUG) {
			Log.e(tag, msg, thr);
		}
	}
}
