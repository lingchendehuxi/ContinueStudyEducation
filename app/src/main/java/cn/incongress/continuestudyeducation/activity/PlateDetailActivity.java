package cn.incongress.continuestudyeducation.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.adapter.CourseListAdapter;
import cn.incongress.continuestudyeducation.base.BaseActivity;
import cn.incongress.continuestudyeducation.bean.Constant;
import cn.incongress.continuestudyeducation.bean.CoursewareArrayBean;
import cn.incongress.continuestudyeducation.bean.PlateDetailBean;
import cn.incongress.continuestudyeducation.bean.VideoRecordBean;
import cn.incongress.continuestudyeducation.db.VideoDBService;
import cn.incongress.continuestudyeducation.service.CMEHttpClientUsage;
import cn.incongress.continuestudyeducation.uis.ListViewForScrollview;
import cn.incongress.continuestudyeducation.utils.LogUtils;
import cz.msebera.android.httpclient.Header;
import roundcornerprogressbar.RoundCornerProgressBar;

/**
 * 板块详情
 * Created by Jacky on 2015/12/21.
 */
public class PlateDetailActivity extends BaseActivity {
    private static final String TAG = "PlateDetailActivity";
    private static final String BUNDLE_PUUID = "puuId";
    private String mPuuId;
    private String mUserUuId;

    private TextView mTvPlateName,mTvStudyProgress,mTvEndTime,mTvCourseTitle;
    private RoundCornerProgressBar mPbStudy;
    private PlateDetailBean mPlateDetailBean;
    private ListViewForScrollview mLvCourse;
    private SwipeRefreshLayout mRefreshLayout;

    private CourseListAdapter mAdapter;
    private boolean mIsFirstIn = true;

    @Override
    protected void handleDetailMsg(Message msg) {
        int target = msg.what;

        if(target == LOAD_DATA_COMPLETE) {
            mTvPlateName.setText(mPlateDetailBean.getPlateName());
            mTvStudyProgress.setText(getString(R.string.study_progress_with_percent, mPlateDetailBean.getStudyJd()));
            mTvEndTime.setText(getString(R.string.study_end_time_with_percent, mPlateDetailBean.getHasTime()));
            mTvCourseTitle.setText(getString(R.string.plate_attach, mPlateDetailBean.getCoursewareArray().size() + ""));
            String percent = mPlateDetailBean.getStudyJd().substring(0, mPlateDetailBean.getStudyJd().length() - 1);
            mPbStudy.setProgress(Float.parseFloat(percent));

            initListData();
            initStudyJdInDB();

            mRefreshLayout.setRefreshing(false);
        }else if(target == LOAD_REFRESH_SHOW) {
            setRefreshing(mRefreshLayout,true,true);
        }
    }

    /**
     * 根据情况更新学习进度
     */
    private void initStudyJdInDB() {
        VideoDBService service = new VideoDBService(this);
        for(int i=0; i<mPlateDetailBean.getCoursewareArray().size(); i++) {
            CoursewareArrayBean bean = mPlateDetailBean.getCoursewareArray().get(i);
            if(!service.queryIsVideoRecordExist(bean.getCwuuId(), mUserUuId)) {
                service.addOneVideoRecord(new VideoRecordBean(bean.getCwuuId(), mUserUuId, bean.getIsFinish(), bean.getIsFinish()));
            }
        }
    }

    /**
     * 将获取到的数据填充
     */
    private void initListData() {
        mAdapter = new CourseListAdapter(this, mPlateDetailBean.getCoursewareArray());
        mLvCourse.setAdapter(mAdapter);

        setListViewHeightBasedOnChildren(mLvCourse);
    }

    public static void startPlateDetailActivity(Context ctx, String puuId) {
        Intent intent = new Intent(ctx, PlateDetailActivity.class);
        intent.putExtra(BUNDLE_PUUID, puuId);
        ctx.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_plate_detail);

        mPuuId = getIntent().getStringExtra(BUNDLE_PUUID);
        mUserUuId = getSPValue(Constant.SP_USER_UUID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mIsFirstIn == false) {
            initDatas();
        }
        mIsFirstIn = false;
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        ((TextView)getViewById(R.id.tv_title)).setText(R.string.plate_detail);

        mTvPlateName = getViewById(R.id.tv_plate_name);
        mTvStudyProgress = getViewById(R.id.tv_study_progress);
        mTvEndTime = getViewById(R.id.tv_end_time);
        mPbStudy = getViewById(R.id.pb_study);
        mTvCourseTitle = getViewById(R.id.tv_plate_title);
        mLvCourse = getViewById(R.id.lv_plate_courses);
        mLvCourse.setEmptyView(findViewById(R.id.empty_list_view));
        mRefreshLayout = getViewById(R.id.refresh_layout);

        mRefreshLayout.setColorSchemeResources(R.color.button_background2);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initDatas();
            }
        });

        mHandler.sendEmptyMessageDelayed(LOAD_REFRESH_SHOW,100);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
        mPuuId = getIntent().getStringExtra(BUNDLE_PUUID);
    }

    private void initDatas() {
        CMEHttpClientUsage.getInstanse().doGetCoursewareList(mPuuId, mUserUuId, new JsonHttpResponseHandler() {

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
                LogUtils.v(TAG + "[onSuccess]", response.toString());

                try {
                    int status = response.getInt("state");
                    if (status == 1) {
                        Gson gson = new Gson();
                        mPlateDetailBean = gson.fromJson(response.toString(), PlateDetailBean.class);
                        mHandler.sendEmptyMessage(LOAD_DATA_COMPLETE);

                        LogUtils.i(TAG, mPlateDetailBean.toString());
                    } else {
                        Toast.makeText(PlateDetailActivity.this, "Get Status not equals 1", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "[exception]:" + e.toString(), null);
                    Toast.makeText(PlateDetailActivity.this, "Parse Data Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    @Override
    protected void initializeEvents() {
        mLvCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AttachStudyActivity.startAttachStudyActivity(PlateDetailActivity.this, mPlateDetailBean.getCoursewareArray().get(position).getCwuuId(), mPlateDetailBean.getCoursewareArray().get(position).getIsFinish());
            }
        });
    }
}
