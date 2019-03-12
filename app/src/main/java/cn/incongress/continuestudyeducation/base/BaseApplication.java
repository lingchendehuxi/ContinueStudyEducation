package cn.incongress.continuestudyeducation.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import org.json.JSONObject;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.bean.Constant;
import cn.incongress.continuestudyeducation.service.CMEHttpClientUsage;
import cn.incongress.continuestudyeducation.uis.StringUtils;
import cn.incongress.continuestudyeducation.utils.LogUtils;
import cn.jpush.android.api.JPushInterface;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Jacky on 2015/12/18.
 */
public class BaseApplication extends Application {
    static Context _context;

    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_load_bg)
                .showImageForEmptyUri(R.mipmap.default_load_bg)
                .showImageOnFail(R.mipmap.default_load_bg)
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(100))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(options)
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);//全局初始化此配置

        // Sina 播放器初始化，要在app启动前进行初始化，才能解压出相应的解码器

        //初始化JPush相关的东西
        JPushInterface.init(this);
        JPushInterface.setDebugMode(Constant.DEBUG);
    }

    public static synchronized BaseApplication context() {
        return (BaseApplication) _context;
    }

    public boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(getPackageName())) {
            return true;
        }

        return false;

    }

        @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        boolean a = isRunningForeground(getApplicationContext());
        String uuid = getSharedPreferences(Constant.DEFAULT_SP_NAME,0 ).getString(Constant.SP_USER_UUID,"");
        if(!isRunningForeground(getApplicationContext())&&uuid.length()>0){
            CMEHttpClientUsage.getInstanse().doLoginOut(uuid,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Log.d("sgqTest", "application onSuccess: 退出了");
                    LogUtils.println("stopWatch:" + response.toString());
                }
            });
        }
            Constant.ISCUT = true;
    }
    public static String user_is_login(){
        SharedPreferences sharedPreferences = _context.getSharedPreferences(Constant.DEFAULT_SP_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(Constant.SP_USER_UUID, StringUtils.EMPTY_STRING);
    }

}