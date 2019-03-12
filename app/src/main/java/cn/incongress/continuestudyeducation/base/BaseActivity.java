package cn.incongress.continuestudyeducation.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.avast.android.dialogs.iface.ISimpleDialogListener;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.incongress.continuestudyeducation.activity.LoginActivity;
import cn.incongress.continuestudyeducation.bean.Constant;
import cn.incongress.continuestudyeducation.service.CMEHttpClientUsage;
import cn.incongress.continuestudyeducation.uis.StringUtils;
import cn.incongress.continuestudyeducation.utils.ActivityUtils;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Jacky on 2015/12/17.
 */
public abstract  class BaseActivity extends AppCompatActivity implements ISimpleDialogListener {
    protected Context mContext;
    protected ProgressDialog mProgressDialog;
    protected SharedPreferences mSharedPreference;

    protected static final int LOAD_DATA_COMPLETE = 0x0001;
    protected static final int LOAD_DATA_NO_DATA = 0x0002;
    protected static final int LOAD_DATA_ERROR = 0x0003;
    protected static final int LOAD_REFRESH_SHOW = 0x0004;

    private InputMethodManager manager;
    /**
     * 基类维护的Handler助手
     */
    protected Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            handleDetailMsg(msg);
        };
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = this;
        mSharedPreference = getSharedPreferences(Constant.DEFAULT_SP_NAME, MODE_PRIVATE);

        ActivityUtils.addActivity(this);
        setContentView(savedInstanceState);

        initializeViews(savedInstanceState);
        initializeData(savedInstanceState);
        initializeEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.removeActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
//        mBroadcastReceiver.stopWatch(this, getSPValue(Constant.SP_USER_UUID));
    }

//    public void stopWatch() {
//        if(mContext != null)
//            mBroadcastReceiver.stopWatch(this, getSPValue(Constant.SP_USER_UUID));
//    }

    /**
     * 存储sp值
     * @param key
     * @param value
     */
    public void setSPValue(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取sp值
     * @param key
     * @return
     */
    public String getSPValue(String key) {
        return mSharedPreference.getString(key, StringUtils.EMPTY_STRING);
    }

    /**
     * 短时间吐司
     * @param msg
     */
    public void showShortToast(String msg) {
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间吐司
     * @param msg
     */
    public void showLongToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 短时间吐司
     * @param msgId
     */
    public void showShortToast(int msgId) {
        Toast.makeText(getApplicationContext(), msgId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间吐司
     * @param msgId
     */
    public void showLongToast(int msgId) {
        Toast.makeText(getApplicationContext(), msgId, Toast.LENGTH_LONG).show();
    }

    protected abstract void setContentView(Bundle savedInstanceState);

    protected abstract void initializeViews(Bundle savedInstanceState);

    protected abstract void initializeEvents();

    protected abstract void initializeData(Bundle savedInstanceState);

    protected abstract void handleDetailMsg(android.os.Message msg);



    @SuppressWarnings("unchecked")
    protected <T extends View> T getViewById(int id) {
        return (T) findViewById(id);
    }


    /**
     * 重新计算listview的高度
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    /**
     * SwipeRefresh 自动刷新
     * @param refreshLayout
     * @param refreshing
     * @param notify
     */
    protected void setRefreshing(SwipeRefreshLayout refreshLayout,boolean refreshing, boolean notify){
        Class<? extends SwipeRefreshLayout> refreshLayoutClass = refreshLayout.getClass();
        if (refreshLayoutClass != null) {
            try {
                Method setRefreshing = refreshLayoutClass.getDeclaredMethod("setRefreshing", boolean.class, boolean.class);
                setRefreshing.setAccessible(true);
                setRefreshing.invoke(refreshLayout, refreshing, notify);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if(Constant.ISCUT){
            String uuid = getSharedPreferences(Constant.DEFAULT_SP_NAME,0 ).getString(Constant.SP_USER_UUID,"");
            CMEHttpClientUsage.getInstanse().doLoginIn(uuid,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Log.d("sgqTest", "onSuccess: 又登录了");
                    try {
                        int state = response.getInt("state");

                        if(state == 1) {
                            final String userUuid = response.getString("userUuId");
                            setSPValue(Constant.SP_USER_UUID, userUuid);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Constant.ISCUT = false;
                }
            });
        }
//        mBroadcastReceiver.startWatch(this,getSPValue(Constant.SP_USER_UUID));

//        if(cn.incongress.continuestudyeducation.utils.StringUtils.isEmpty(getSPValue(Constant.SP_USER_UUID)) && mSharedPreference.getBoolean(Constant.SP_IS_LOG_OUT, false)){
//            SimpleDialogFragment.createBuilder(this, getSupportFragmentManager()).
//                    setTitle(R.string.dialog_title).setMessage(getString(R.string.logout_tips)).
//                    setPositiveButtonText(R.string.positive_button).setCancelableOnTouchOutside(false).setRequestCode(200).show();
//        }
    }

    @Override
    public void onNegativeButtonClicked(int requestCode) {
        if(requestCode == 200) {
            ActivityUtils.finishAll();
            startActivity(new Intent(mContext, LoginActivity.class));
            mSharedPreference.edit().putBoolean(Constant.SP_IS_LOG_OUT, false).commit();
        }
    }

    @Override
    public void onNeutralButtonClicked(int requestCode) {
        if(requestCode == 200) {
            ActivityUtils.finishAll();
            startActivity(new Intent(mContext, LoginActivity.class));
            mSharedPreference.edit().putBoolean(Constant.SP_IS_LOG_OUT,false).commit();
        }
    }

    @Override
    public void onPositiveButtonClicked(int requestCode) {
        if(requestCode == 200) {
            ActivityUtils.finishAll();
            startActivity(new Intent(mContext, LoginActivity.class));
            mSharedPreference.edit().putBoolean(Constant.SP_IS_LOG_OUT,false).commit();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // TODO Auto-generated method stub
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }
}
