package cn.incongress.continuestudyeducation.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import cn.incongress.continuestudyeducation.adapter.CityAdapter;
import cn.incongress.continuestudyeducation.base.BaseActivity;
import cn.incongress.continuestudyeducation.bean.CityBean;
import cn.incongress.continuestudyeducation.bean.Constant;
import cn.incongress.continuestudyeducation.bean.PersonInfoEvent;
import cn.incongress.continuestudyeducation.service.CMEHttpClientUsage;
import cn.incongress.continuestudyeducation.utils.ActivityUtils;
import cn.incongress.continuestudyeducation.utils.LogUtils;
import cn.incongress.continuestudyeducation.utils.StringUtils;
import cz.msebera.android.httpclient.Header;
import de.greenrobot.event.EventBus;

/**
 * Created by Jacky on 2016/1/4.
 */
public class CityActivity extends BaseActivity {
    private static final String TAG = "CityActivity";
    public static final String INTENT_PROVINCE_ID = "provinceId";
    public static final String INTENT_PROVINCE_NAME = "provinceName";

    private ListView mLvCitys;
    private List<CityBean> mCitys;
    private CityAdapter mAdapter;
    private int mProvinceId;
    private int mChooseCityId;
    private String mChooseProvinceName = "" ;
    private Toolbar mToolbar;

    public static void startCityActivityByProvinceId(Context ctx, int provinceId, String provinceName) {
        Intent intent = new Intent();
        intent.setClass(ctx, CityActivity.class);
        intent.putExtra(INTENT_PROVINCE_ID, provinceId);
        intent.putExtra(INTENT_PROVINCE_NAME, provinceName);
        ctx.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_city);
        ((TextView)getViewById(R.id.home_title)).setText(R.string.plate_detail);
        ((TextView)getViewById(R.id.home_title)).setText(R.string.person_province_city);

        ((ImageView)getViewById(R.id.home_title_back)).setVisibility(View.VISIBLE);
        mProvinceId = getIntent().getIntExtra(INTENT_PROVINCE_ID, 1);
        mChooseProvinceName = getIntent().getStringExtra(INTENT_PROVINCE_NAME);
        if(StringUtils.isEmpty(getSPValue(Constant.SP_CITY_ID))) {
            mChooseCityId = 0;
        }else {
            mChooseCityId = Integer.parseInt(getSPValue(Constant.SP_CITY_ID));
        }
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mLvCitys = getViewById(R.id.lv_city);
    }

    @Override
    protected void initializeEvents() {

    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
        CMEHttpClientUsage.getInstanse().doGetCityList(mProvinceId, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                mProgressDialog = ProgressDialog.show(mContext, null, "loading...");
            }

            @Override
            public void onFinish() {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogUtils.i(TAG, response.toString());
                try {
                    int state = response.getInt("state");
                    if (state == 1) {
                        JSONArray provinceArray = response.getJSONArray("cityArray");
                        Gson gson = new Gson();
                        mCitys = gson.fromJson(provinceArray.toString(), new TypeToken<List<CityBean>>() {
                        }.getType());
                        if (mCitys.size() > 0) {
                            mHandler.sendEmptyMessage(LOAD_DATA_COMPLETE);
                        }
                    } else if (state == 0) {
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
                if (statusCode == 0) {
                    showShortToast(R.string.connect_timeout);
                }
            }
        });
    }

    @Override
    protected void handleDetailMsg(Message msg) {
        int target = msg.what;

        if(target == LOAD_DATA_COMPLETE) {
            mAdapter = new CityAdapter(mCitys, mContext, mChooseCityId);
            mLvCitys.setAdapter(mAdapter);

            mLvCitys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mAdapter.setChooseId(mCitys.get(position).getCityId());
                    mAdapter.notifyDataSetChanged();

                    CityActivity.this.finish();
                    ProvinceActivity.mInstance.finish();
                    ActivityUtils.removeActivity(ProvinceActivity.mInstance);
                    //更新本地的数据，保存下来
                    setSPValue(Constant.SP_CITY_ID, mCitys.get(position).getCityId() + "");
                    setSPValue(Constant.SP_PROVINCE_ID, mProvinceId + "");
                    setSPValue(Constant.SP_PROVINCE_CITY_NAME, mChooseProvinceName + " " + mCitys.get(position).getCityName());
                    EventBus.getDefault().post(new PersonInfoEvent(EditPersonInfoActivity.TYPE_PROVINCE_CITY, mChooseProvinceName + " " + mCitys.get(position).getCityName()));
                }
            });
        }
    }
    public void back(View v){
        CityActivity.this.finish();
    }
}
