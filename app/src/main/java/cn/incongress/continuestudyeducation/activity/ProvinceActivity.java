package cn.incongress.continuestudyeducation.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.adapter.ProvinceAdapter;
import cn.incongress.continuestudyeducation.base.BaseActivity;
import cn.incongress.continuestudyeducation.bean.NotifyArrayBean;
import cn.incongress.continuestudyeducation.bean.ProvinceBean;
import cn.incongress.continuestudyeducation.service.CMEHttpClientUsage;
import cn.incongress.continuestudyeducation.utils.LogUtils;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Jacky on 2016/1/4.
 */
public class ProvinceActivity extends BaseActivity {
    private static final String TAG = "ProvinceActivity";

    private ListView mLvProvince;
    private List<ProvinceBean> mProvinceBeans;
    private ProvinceAdapter mAdapter;

    public static ProvinceActivity mInstance;


    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_province);

        ((TextView)getViewById(R.id.home_title)).setText(R.string.person_province_city);

        ((ImageView)getViewById(R.id.home_title_back)).setVisibility(View.VISIBLE);
        mInstance = this;
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mLvProvince = getViewById(R.id.lv_province);
    }

    @Override
    protected void initializeEvents() {

    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
        CMEHttpClientUsage.getInstanse().doGetProvinceList(new JsonHttpResponseHandler(){

            @Override
            public void onStart() {
                mProgressDialog = ProgressDialog.show(mContext, null, "loading...");
            }

            @Override
            public void onFinish() {
                if(mProgressDialog!=null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogUtils.i(TAG, response.toString());
                try {
                    int state = response.getInt("state");
                    if(state == 1) {
                        JSONArray provinceArray = response.getJSONArray("provinceArray");
                        Gson gson = new Gson();
                        mProvinceBeans = gson.fromJson(provinceArray.toString(), new TypeToken<List<ProvinceBean>>() {}.getType());
                        if(mProvinceBeans.size()>0) {
                            mHandler.sendEmptyMessage(LOAD_DATA_COMPLETE);
                        }
                    }else if(state == 0) {
                        showShortToast(getString(R.string.get_date_tips));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                LogUtils.e(TAG, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if(statusCode == 0) {
                    showShortToast(R.string.connect_timeout);
                }
            }
        });
    }

    public void back(View view){
        this.finish();
    }
    @Override
    protected void handleDetailMsg(Message msg) {
        int target = msg.what;

        if(target == LOAD_DATA_COMPLETE) {
            mAdapter = new ProvinceAdapter(mProvinceBeans, mContext);
            mLvProvince.setAdapter(mAdapter);

            mLvProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CityActivity.startCityActivityByProvinceId(ProvinceActivity.this, mProvinceBeans.get(position).getProvinceId(), mProvinceBeans.get(position).getProvinceName());
                }
            });
        }
    }
}
