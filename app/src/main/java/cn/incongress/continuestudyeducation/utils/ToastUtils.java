package cn.incongress.continuestudyeducation.utils;

import android.widget.Toast;

import cn.incongress.continuestudyeducation.base.BaseApplication;

/**
 * Created by Jacky on 2016/1/15.
 */
public class ToastUtils {
    public static void showShorToast(String msg) {
        Toast.makeText(BaseApplication.context(),msg,Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(String msg) {
        Toast.makeText(BaseApplication.context(),msg,Toast.LENGTH_LONG).show();
    }
}
