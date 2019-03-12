package cn.incongress.continuestudyeducation.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.adapter.LessonTeacherAdapter;
import cn.incongress.continuestudyeducation.base.BaseActivity;
import cn.incongress.continuestudyeducation.bean.Constant;
import cn.incongress.continuestudyeducation.bean.LessonInfoBean;
import cn.incongress.continuestudyeducation.bean.TeacherArrayBean;
import cn.incongress.continuestudyeducation.service.CMEHttpClientUsage;
import cn.incongress.continuestudyeducation.utils.LogUtils;
import cn.incongress.continuestudyeducation.utils.NetWorkUtils;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Jacky on 2015/12/18.
 */
public class LessonIntroActivity extends BaseActivity {
    private static final String TAG = "LessonIntroActivity";

    private static final int LOAD_DATA_COMPLETE = 0x0001;
    private static final int LOAD_DATA_NO_DATA = 0x0002;
    private static final int LOAD_DATA_ERROR = 0x0003;

    private TextView mTvLessonTitle, mTvLessonIntro, mTvLeftTime,mTvSize,mTvPlate;
    private ListView mLvTeachers;
    private LessonTeacherAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;

    private LessonInfoBean mInfoBean;


    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lesson_intro);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        ((TextView)getViewById(R.id.home_title)).setText(R.string.lession_introduction);

        ((ImageView)getViewById(R.id.home_title_back)).setVisibility(View.VISIBLE);
        mTvLessonTitle = getViewById(R.id.tv_lesson_title);
        mTvLessonIntro = getViewById(R.id.tv_lesson_info);
        mTvLeftTime = getViewById(R.id.tv_left_time);
        mTvSize = getViewById(R.id.teacherSize);
        mTvPlate = getViewById(R.id.tv_lesson_plate);
        mLvTeachers = getViewById(R.id.lv_teachers);
        mLvTeachers.setEmptyView(findViewById(R.id.tv_no_data));
        mRefreshLayout = getViewById(R.id.refresh_layout);

        mRefreshLayout.setColorSchemeResources(R.color.button_background2);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetWorkUtils.getNetworkType(mContext) == -1) {
                    SimpleDialogFragment.createBuilder(mContext, getSupportFragmentManager()).setTitle(R.string.dialog_title).setMessage(R.string.network_not_enbale).setPositiveButtonText(R.string.positive_button).show();
                } else {
                    CMEHttpClientUsage.getInstanse().doGetCourseInfo(getIntent().getStringExtra("cuuId"), new JsonHttpResponseHandler() {
                        @Override
                        public void onStart() {
                            super.onStart();
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            LogUtils.i(TAG, response.toString());
                            try {
                                int state = response.getInt("state");
                                if (state == 1) {
                                    Gson gson = new Gson();
                                    mInfoBean = gson.fromJson(response.toString(), LessonInfoBean.class);
                                    mHandler.sendEmptyMessage(LOAD_DATA_COMPLETE);
                                } else {
                                    mHandler.sendEmptyMessage(LOAD_DATA_ERROR);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                        }
                    });
                }
            }
        });

        mHandler.sendEmptyMessageDelayed(LOAD_REFRESH_SHOW, 100);
    }

    @Override
    protected void initializeEvents() {
        mLvTeachers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LessonInfoBean.TeacherArrayBean bean = mInfoBean.getTeacherArray().get(position);
                TeacherInfoActivity.startTeacherInfoActivity(mContext, bean.getTeacherName(), bean.getTeacherRemark(), bean.getTeacherUrl());
            }
        });
    }

   public void back(View view){
       LessonIntroActivity.this.finish();
   }
    @Override
    protected void initializeData(Bundle savedInstanceState) {
        if (NetWorkUtils.getNetworkType(mContext) == -1) {
            SimpleDialogFragment.createBuilder(mContext, getSupportFragmentManager()).setTitle(R.string.dialog_title).setMessage(R.string.network_not_enbale).setPositiveButtonText(R.string.positive_button).show();
        } else {
            CMEHttpClientUsage.getInstanse().doGetCourseInfo(getIntent().getStringExtra("cuuId"), new JsonHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();
                    mProgressDialog = ProgressDialog.show(mContext, null, "loading...");
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    mProgressDialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    LogUtils.i(TAG, response.toString());
                    try {
                        int state = response.getInt("state");
                        if (state == 1) {
                            Gson gson = new Gson();
                            mInfoBean = gson.fromJson(response.toString(), LessonInfoBean.class);
                            mHandler.sendEmptyMessage(LOAD_DATA_COMPLETE);
                        } else {
                            mHandler.sendEmptyMessage(LOAD_DATA_ERROR);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }
    }

    @Override
    protected void handleDetailMsg(Message msg) {
        int target = msg.what;

        if (target == LOAD_DATA_COMPLETE) {
            LogUtils.i(TAG, mInfoBean.toString());
            mTvLessonTitle.setText(mInfoBean.getCourseName());
            mTvLessonIntro.setText(mInfoBean.getCourseRemark());
            mTvLeftTime.setText(mInfoBean.getHasTimeStr());
            mTvSize.setText(mContext.getString(R.string.teacher_intro,mInfoBean.getTeacherCount()+""));
            String plate = mInfoBean.getPlateArray().get(0).getPlateName();
            for (int i = 1;i<mInfoBean.getPlateArray().size();i++){
                plate = plate+"\n"+mInfoBean.getPlateArray().get(i).getPlateName();
            }
            mTvPlate.setText(plate);
            mAdapter = new LessonTeacherAdapter(mContext, mInfoBean.getTeacherArray());
            mLvTeachers.setAdapter(mAdapter);

            //调整scrollview高度
            setListViewHeightBasedOnChildren(mLvTeachers);
        } else if (target == LOAD_DATA_ERROR) {
            showShortToast(R.string.net_error_check_net);
        }

        mRefreshLayout.setRefreshing(false);
    }

}

