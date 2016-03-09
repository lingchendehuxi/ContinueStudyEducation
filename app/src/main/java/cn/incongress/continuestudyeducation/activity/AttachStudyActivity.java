package cn.incongress.continuestudyeducation.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sina.sinavideo.coreplayer.util.LogS;
import com.sina.sinavideo.sdk.VDVideoExtListeners;
import com.sina.sinavideo.sdk.VDVideoView;
import com.sina.sinavideo.sdk.data.VDVideoInfo;
import com.sina.sinavideo.sdk.data.VDVideoListInfo;
import com.sina.sinavideo.sdk.utils.VDVideoFullModeController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.base.BaseActivity;
import cn.incongress.continuestudyeducation.bean.AttachStudyBean;
import cn.incongress.continuestudyeducation.bean.Constant;
import cn.incongress.continuestudyeducation.bean.PlateDetailBean;
import cn.incongress.continuestudyeducation.bean.VideoRecordBean;
import cn.incongress.continuestudyeducation.db.VideoDBService;
import cn.incongress.continuestudyeducation.service.CMEHttpClientUsage;
import cn.incongress.continuestudyeducation.uis.CircleImageView;
import cn.incongress.continuestudyeducation.utils.LogUtils;
import cn.incongress.continuestudyeducation.utils.StringUtils;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Jacky on 2015/12/21.
 */
public class AttachStudyActivity extends BaseActivity implements VDVideoExtListeners.OnVDVideoCompletionListener {
    private String mCwUUID;
    private String mUserUuid;
    private int mIsFinish;
    private static final String COURSEWAREUUID = "cwuuId";
    private static final String ISFINISH = "isFinish";
    private static final String TAG = "AttachStudyActivity";

    private static final int LOAD_DATA_COMPLETE = 0x0001;
    private static final int LOAD_DATA_NO_DATA = 0x0002;
    private static final int LOAD_DATA_ERROR = 0x0003;

    private VDVideoView mVDVideoView = null;
    private TextView mTvVideoName,mTvVideoTime,mTvTeacherName,mTvNextVideoName, mTvNextVideoTime, mTvNextTeacherName;
    private CircleImageView mImgTeacherLog;
    private LinearLayout mLlNextStudyInfo;

    private AttachStudyBean mAttachStudyBean;

    private VideoDBService mDBService;

    @Override
    protected void handleDetailMsg(Message msg) {
        int target = msg.what;

        if(target == LOAD_DATA_COMPLETE) {
            mTvVideoName.setText(mAttachStudyBean.getCoursewareTitle());
            mTvTeacherName.setText(mAttachStudyBean.getTeacher());
            mTvVideoTime.setText(getString(R.string.study_time, mAttachStudyBean.getLongTime()));


            ImageLoader.getInstance().displayImage(mAttachStudyBean.getTeacherUrl(), mImgTeacherLog);

            if(cn.incongress.continuestudyeducation.uis.StringUtils.isEmpty(mAttachStudyBean.getTeacherUrl())) {
                mImgTeacherLog.setImageResource(R.mipmap.teacher_info_circle_default);
            }else {
                ImageLoader.getInstance().displayImage(mAttachStudyBean.getTeacherUrl(), mImgTeacherLog, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                        mImgTeacherLog.setImageResource(R.mipmap.teacher_info_circle_default);
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {
                    }
                });
            }

            if(mAttachStudyBean.getHasNext() == 0) {
                mLlNextStudyInfo.setVisibility(View.GONE);
            }
            mTvNextVideoName.setText(mAttachStudyBean.getNextCoursewareTitle());
            mTvNextVideoTime.setText(getString(R.string.study_time, mAttachStudyBean.getNextLongTime()));
            mTvNextTeacherName.setText(getString(R.string.next_teacher_name, mAttachStudyBean.getNextTeacher()));

            VDVideoInfo info = new VDVideoInfo();

            info = new VDVideoInfo();
            info.mTitle = mAttachStudyBean.getNextCoursewareTitle();
            info.mVideoId = System.currentTimeMillis()+"";
            info.mPlayUrl = mAttachStudyBean.getVideoUrl();

            // 初始化播放器以及播放列表
            mVDVideoView.open(this, info);
            // 开始播放，直接选择序号即可
            mVDVideoView.play(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.video_question:
                QuestionActivity.startQuestionActivity(this,mCwUUID);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void startAttachStudyActivity(Context ctx, String cwuuId, int isFinish) {
        Intent intent = new Intent(ctx, AttachStudyActivity.class);
        intent.putExtra(COURSEWAREUUID, cwuuId);
        intent.putExtra(ISFINISH, isFinish);
        ctx.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        mIsFinish = getIntent().getIntExtra(ISFINISH, 0);

        if(mIsFinish == 1) {
            setContentView(R.layout.activity_attach_study_finish);
        }else {
            setContentView(R.layout.activity_attach_study);
        }

        mCwUUID = getIntent().getStringExtra(COURSEWAREUUID);
        mUserUuid = getSPValue(Constant.SP_USER_UUID);

        LogUtils.i(TAG, "cwuuId=" + mCwUUID +", userUuid=" + mUserUuid);

        String isFirstInVideo = getSPValue(Constant.SP_IS_FIRST_IN_VIDEO);

        if(StringUtils.isEmpty(isFirstInVideo)) {
            SimpleDialogFragment.createBuilder(this, getSupportFragmentManager()).
                    setTitle(R.string.dialog_title).setMessage(R.string.attach_study_tips).
                    setPositiveButtonText(R.string.positive_button).setCancelableOnTouchOutside(false).show();
            setSPValue(Constant.SP_IS_FIRST_IN_VIDEO,"no");
        }
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.attach_study);

        mTvVideoName = getViewById(R.id.tv_video_name);
        mTvVideoTime = getViewById(R.id.tv_video_time);
        mTvTeacherName = getViewById(R.id.tv_teacher_name);
        mTvNextVideoName = getViewById(R.id.tv_next_study_name);
        mTvNextVideoTime = getViewById(R.id.tv_next_study_time);
        mTvNextTeacherName = getViewById(R.id.tv_next_study_teacher);
        mImgTeacherLog = getViewById(R.id.iv_teacher_logo);
        mLlNextStudyInfo = getViewById(R.id.ll_next_study_info);

        mTvNextVideoName.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mTvNextVideoName.getPaint().setAntiAlias(true);//抗锯齿


        mVDVideoView = (VDVideoView) findViewById(R.id.study_video);
        mVDVideoView.setVDVideoViewContainer((ViewGroup) mVDVideoView.getParent());
        mVDVideoView.setCompletionListener(this);
    }

    @Override
    protected void initializeEvents() {

    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
        CMEHttpClientUsage.getInstanse().doGetCourseware(mCwUUID, mUserUuid, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                super.onStart();
                mProgressDialog = ProgressDialog.show(AttachStudyActivity.this,null,"loading");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mProgressDialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogUtils.v(TAG + "[onSuccess]", response.toString());

                try {
                    int status = response.getInt("state");
                    if (status == 1) {
                        Gson gson = new Gson();
                        mAttachStudyBean = gson.fromJson(response.toString(), AttachStudyBean.class);
                        mHandler.sendEmptyMessage(LOAD_DATA_COMPLETE);
                        LogUtils.i(TAG, mAttachStudyBean.toString());
                    } else {
                        Toast.makeText(AttachStudyActivity.this, "Get Status not equals 1", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "[exception]:" + e.toString(), null);
                    Toast.makeText(AttachStudyActivity.this, "Parse Data Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!mVDVideoView.onVDKeyDown(keyCode, event)) {
            return super.onKeyDown(keyCode, event);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        mVDVideoView.release(false);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVDVideoView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mVDVideoView.onStop();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mVDVideoView.setIsFullScreen(true);
            LogS.e(VDVideoFullModeController.TAG, "onConfigurationChanged---横屏");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mVDVideoView.setIsFullScreen(false);
            LogS.e(VDVideoFullModeController.TAG, "onConfigurationChanged---竖屏");
        }
    }

    public void goTeacherInfo(View view) {
        TeacherInfoActivity.startTeacherInfoActivity(this,mAttachStudyBean.getTeacher(), mAttachStudyBean.getTeacherRemark(), mAttachStudyBean.getTeacherUrl());
    }

    public void nextStudy(View view) {
        this.finish();
        startAttachStudyActivity(this,mAttachStudyBean.getNextCwuuId(), mAttachStudyBean.getIsFinish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.question, menu);
        return true;
    }

    @Override
    public void onVDVideoCompletion(VDVideoInfo info, int status) {
        CMEHttpClientUsage.getInstanse().doSaveCoursewareState(mCwUUID,mUserUuid, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                super.onStart();
                mProgressDialog = ProgressDialog.show(mContext,null,getString(R.string.update_video_record));
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mProgressDialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogUtils.i(TAG, "response===" + response.toString());

                try {
                    int state = response.getInt("state");
                    if(state == 1) {
                        VideoDBService service = new VideoDBService(mContext);
                        service.updateVideoRecord(new VideoRecordBean(mCwUUID,mUserUuid,1,1));
                    }else {
                        VideoDBService service = new VideoDBService(mContext);
                        service.updateVideoRecord(new VideoRecordBean(mCwUUID,mUserUuid,1,0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
