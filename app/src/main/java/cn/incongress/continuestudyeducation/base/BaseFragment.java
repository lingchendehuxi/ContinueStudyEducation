package cn.incongress.continuestudyeducation.base;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.incongress.continuestudyeducation.bean.Constant;
import cn.incongress.continuestudyeducation.uis.StringUtils;

/**
 * Created by Jacky on 2015/12/23.
 */
public abstract class BaseFragment extends Fragment {
    protected static final int LOAD_DATA_COMPLETE = 0x0001;
    protected static final int LOAD_DATA_NO_DATA = 0x0002;
    protected static final int LOAD_DATA_ERROR = 0x0003;
    protected static final int LOAD_REFRESH_SHOW = 0x0004;
    protected static final int LOAD_DATA_MORE = 0x0005;
    protected static final int LOAD_DATA_NO_MORE = 0x0006;
    protected static final int SERVER_ERROR = 0x0007;

    protected static final int UPLOAD_IMGURL_SUCCESS = 0x0008;


    public SharedPreferences mSharedPreference;

    protected ProgressDialog mProgressDialog;

    /**
     * 基类维护的Handler助手
     */
    protected Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            handleDetailMsg(msg);
        };
    };

    protected abstract void handleDetailMsg(android.os.Message msg);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreference = getActivity().getSharedPreferences(Constant.DEFAULT_SP_NAME, getActivity().MODE_PRIVATE);

    }

    /**
     * 清楚保存的所有内容
     */
    public void clearAllSPValue(){
        mSharedPreference.edit().clear().commit();
    }

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
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间吐司
     * @param msg
     */
    public void showLongToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 短时间吐司
     * @param msgId
     */
    public void showShortToast(int msgId) {
        Toast.makeText(getActivity(), msgId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间吐司
     * @param msgId
     */
    public void showLongToast(int msgId) {
        Toast.makeText(getActivity(), msgId, Toast.LENGTH_LONG).show();
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
}
