package cn.incongress.continuestudyeducation.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yczbj.ycvideoplayerlib.constant.ConstantKeys;
import org.yczbj.ycvideoplayerlib.controller.VideoPlayerController;
import org.yczbj.ycvideoplayerlib.inter.listener.OnCompletedListener;
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;

import java.util.List;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.adapter.ListAdapterHolder;
import cn.incongress.continuestudyeducation.base.BaseActivity;
import cn.incongress.continuestudyeducation.bean.AttachStudyBean;
import cn.incongress.continuestudyeducation.bean.Constant;
import cn.incongress.continuestudyeducation.bean.VideoRecordBean;
import cn.incongress.continuestudyeducation.db.VideoDBService;
import cn.incongress.continuestudyeducation.service.CMEHttpClientUsage;
import cn.incongress.continuestudyeducation.uis.CircleImageView;
import cn.incongress.continuestudyeducation.utils.LogUtils;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Jacky on 2015/12/21.
 * 课件学习
 */
public class AttachStudyActivity extends BaseActivity {
    private String mCwUUID;
    private String mPuuID;
    private String mUserUuid;
    private int mCourseType;
    private int mIsFinish;
    private static final String COURSEWAREUUID = "cwuuId";
    private static final String PUUID = "puuId";
    private static final String ISFINISH = "isFinish";
    private static final String TYPE = "courseType";

    private static final String TAG = "AttachStudyActivity";

    private static final int LOAD_DATA_COMPLETE = 0x0001;
    private static final int LOAD_DATA_NO_DATA = 0x0002;
    private static final int LOAD_DATA_ERROR = 0x0003;

    private VideoPlayer mVDVideoView = null;
    private TextView mTvVideoName, mTvVideoTime, mTvTeacherName, mTvQuestion;
    private RecyclerView mStudyList;
    private ListAdapterHolder mAdapter;
    private CircleImageView mImgTeacherLog;

    private AttachStudyBean mAttachStudyBean;
    private RelativeLayout goQuestion;
    private VideoPlayerController controller;

    @Override
    protected void handleDetailMsg(Message msg) {
        int target = msg.what;

        if (target == LOAD_DATA_COMPLETE) {
            mTvVideoName.setText(mAttachStudyBean.getCwName());
            mTvTeacherName.setText(getString(R.string.next_teacher_name, mAttachStudyBean.getTeacherName()));
            mTvVideoTime.setText(getString(R.string.study_time, mAttachStudyBean.getCwTime()));
            mTvQuestion.setText(mContext.getResources().getString(R.string.menu_num, mAttachStudyBean.getCwArray().size() + ""));
            Log.e("GYW", mAttachStudyBean.getCwArray().size() + "");
            mAdapter = new ListAdapterHolder(mAttachStudyBean.getCwArray(), mContext);
            mStudyList.setAdapter(mAdapter);
            mStudyList.setItemAnimator(new DefaultItemAnimator());
            mAdapter.SetOnItemClickListener(new ListAdapterHolder.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    AttachStudyBean.CwArrayBean mBean = mAttachStudyBean.getCwArray().get(position);
                    if (mBean.getIsFinish() == 1) {
                        if (mBean.getType() != 1) {
                            startAttachStudyActivity(mContext, mPuuID, mBean.getCwuuId(),
                                    mBean.getIsFinish(), mCourseType);
                            finish();
                        } else {
                            Toast.makeText(mContext, "该课件正在播放", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (mBean.getCanStudy() == 1) {
                            if (mBean.getType() != 1) {
                                startAttachStudyActivity(mContext, mPuuID, mBean.getCwuuId(),
                                        mBean.getIsFinish(), mCourseType);
                                finish();
                            } else {
                                Toast.makeText(mContext, "该课件正在播放", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "请观影结束前一份课件", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            ImageLoader.getInstance().displayImage(mAttachStudyBean.getImgUrl(), mImgTeacherLog);

            if (cn.incongress.continuestudyeducation.uis.StringUtils.isEmpty(mAttachStudyBean.getImgUrl())) {
                mImgTeacherLog.setImageResource(R.mipmap.teacher_info_circle_default);
            } else {
                ImageLoader.getInstance().displayImage(mAttachStudyBean.getImgUrl(), mImgTeacherLog, new ImageLoadingListener() {
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

            /*if(mAttachStudyBean.getHasNext() == 0) {
                mLlNextStudyInfo.setVisibility(View.GONE);
            }
            mTvNextVideoName.setText(mAttachStudyBean.getNextCoursewareTitle());
            mTvNextVideoTime.setText(getString(R.string.study_time, mAttachStudyBean.getNextLongTime()));
            mTvNextTeacherName.setText(getString(R.string.next_teacher_name, mAttachStudyBean.getNextTeacher()));*/
            String videoUrl = mAttachStudyBean.getVideoUrl();
            mVDVideoView.setUp(videoUrl, null);
        /*Glid.with(this)
                .load("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-30-43.jpg")
                .placeholder(R.drawable.image_default)
                .crossFade()
                .into(controller.imageView());*/
            //是否从上一次的位置继续播放
            mVDVideoView.continueFromLastPosition(false);
            if (mIsFinish == 0) {
                //还没看完
                mVDVideoView.setClickable(false);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.dialog_title).setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mVDVideoView.start();
                    }
                }).setMessage(R.string.attach_study_tips).setCancelable(false).show();
            }else {
                mVDVideoView.setClickable(true);
                mVDVideoView.start();
            }
        } else if (target == LOAD_DATA_NO_DATA) {
            mAdapter.notifyDataSetChanged();
        }
    }


    public static void startAttachStudyActivity(Context ctx, String puuId, String cwuuId, int isFinish, int type) {
        Intent intent = new Intent(ctx, AttachStudyActivity.class);
        intent.putExtra(PUUID, puuId);
        intent.putExtra(COURSEWAREUUID, cwuuId);
        intent.putExtra(ISFINISH, isFinish);
        intent.putExtra(TYPE, type);
        ctx.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        mIsFinish = getIntent().getIntExtra(ISFINISH, 0);
        setContentView(R.layout.activity_attach_study);

        mPuuID = getIntent().getStringExtra(PUUID);
        mCwUUID = getIntent().getStringExtra(COURSEWAREUUID);
        mCourseType = getIntent().getIntExtra(TYPE, 0);
        mUserUuid = getSPValue(Constant.SP_USER_UUID);


//        mDBService = new VideoDBService(AttachStudyActivity.this);

//        //新逻辑，根据每个视频进行判断
//        if(mDBService.queryIsVideoFinish(mCwUUID, mUserUuid) == 1) {
//            //已经看完
//
//        }else {
//            //还没看完
//            SimpleDialogFragment.createBuilder(this, getSupportFragmentManager()).
//                    setTitle(R.string.dialog_title).setMessage(R.string.attach_study_tips).
//                    setPositiveButtonText(R.string.positive_button).setCancelableOnTouchOutside(false).show();
//        }

        //老逻辑，一次性判断
//        String isFirstInVideo = getSPValue(Constant.SP_IS_FIRST_IN_VIDEO);
//
//        if(StringUtils.isEmpty(isFirstInVideo)) {
//            SimpleDialogFragment.createBuilder(this, getSupportFragmentManager()).
//                    setTitle(R.string.dialog_title).setMessage(R.string.attach_study_tips).
//                    setPositiveButtonText(R.string.positive_button).setCancelableOnTouchOutside(false).show();
//            setSPValue(Constant.SP_IS_FIRST_IN_VIDEO,"no");
//        }
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {

        ((TextView) getViewById(R.id.home_title)).setText(R.string.attach_study);
        ((ImageView) getViewById(R.id.home_title_back)).setVisibility(View.VISIBLE);
        mTvVideoName = getViewById(R.id.tv_video_name);
        mTvVideoTime = getViewById(R.id.tv_video_time);
        mTvTeacherName = getViewById(R.id.tv_teacher_name);
        mStudyList = getViewById(R.id.study_details_list);
        mImgTeacherLog = getViewById(R.id.iv_teacher_logo);
        goQuestion = getViewById(R.id.video_question);
        mTvQuestion = getViewById(R.id.tw_position);

        mVDVideoView = (VideoPlayer) findViewById(R.id.study_video);
        mVDVideoView.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_NATIVE); // IjkPlayer or MediaPlayer
        //创建视频控制器
        controller = new VideoPlayerController(this);
        controller.setLoadingType(ConstantKeys.Loading.LOADING_QQ);
        controller.setBackVisible(View.INVISIBLE);
        controller.setTopVisibility(false);
        controller.setOnCompletedListener(new OnCompletedListener() {
            @Override
            public void onCompleted() {
                onVDVideoCompletion();
            }
        });
        if (mIsFinish == 0) {
            //还没看完
            mVDVideoView.setClickable(false);
        }else {
            mVDVideoView.setClickable(true);
        }
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mStudyList.setLayoutManager(mLayoutManager);
        goQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionActivity.startQuestionActivity(mContext, mCwUUID);
            }
        });
    }

    @Override
    protected void initializeEvents() {

    }

    public void back(View v) {
        this.finish();
    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
        getRefreshData(1);
        initDB();
    }

    private void getRefreshData(final int type) {

        CMEHttpClientUsage.getInstanse().doGetCourseware(mPuuID, mCwUUID, mUserUuid, mCourseType, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                mProgressDialog = ProgressDialog.show(AttachStudyActivity.this, null, "loading");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mProgressDialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogUtils.v("GYW", "--------------" + response.toString());
                Gson gson = new Gson();
                mAttachStudyBean = gson.fromJson(response.toString(), AttachStudyBean.class);
                controller.setTitle(mAttachStudyBean.getCwName());
                controller.setLength(mAttachStudyBean.getCwTime());
                controller.setBackVisible(View.INVISIBLE);
                ImageLoader.getInstance().displayImage(mAttachStudyBean.getImgUrl(), controller.imageView());
                mVDVideoView.setController(controller);
                if (type == 1) {
                    mHandler.sendEmptyMessage(LOAD_DATA_COMPLETE);
                } else {
                    mHandler.sendEmptyMessage(LOAD_DATA_NO_DATA);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        VideoPlayerManager.instance().releaseVideoPlayer();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mVDVideoView.enterVerticalScreenScreen();
            Log.e("GYW", "onConfigurationChanged---横屏");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.e("GYW", "onConfigurationChanged---竖屏");
        }
    }

    public void goTeacherInfo(View view) {
        TeacherInfoActivity.startTeacherInfoActivity(this, mAttachStudyBean.getTeacherName(), mAttachStudyBean.getTeachRemark(), mAttachStudyBean.getImgUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.question, menu);
        return true;
    }

    private void onVDVideoCompletion() {
        if (mIsFinish != 1) {
            CMEHttpClientUsage.getInstanse().doSaveCoursewareState(mCwUUID, mUserUuid, new JsonHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();
                    mProgressDialog = ProgressDialog.show(mContext, null, getString(R.string.update_video_record));
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
                        if (state == 1) {
                            getRefreshData(0);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    new AlertDialog.Builder(mContext).setTitle("更新失败")//设置对话框标题
                            .setMessage("学习资料状态更新失败！")//设置显示的内容
                            .setPositiveButton("重试", new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    // TODO Auto-generated method stub
                                    VideoRecordBean bean = new VideoRecordBean();
                                    bean.setCwUuid(mCwUUID);
                                    bean.setUserUuid(mUserUuid);
                                    videoCompletion(0, bean);
                                }
                            }).setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {//添加返回按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//响应事件
                            // TODO Auto-generated method stub
                            VideoDBService service = new VideoDBService(mContext);
                            service.addOneVideoRecord(new VideoRecordBean(mCwUUID, mUserUuid));
                        }
                    }).show();//在按键响应事件中显示此对话框
                }
            });
        }
    }

    private void videoCompletion(final int type, final VideoRecordBean mBean) {
        CMEHttpClientUsage.getInstanse().doSaveCoursewareState(mBean.getCwUuid(), mBean.getUserUuid(), new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                mProgressDialog = ProgressDialog.show(mContext, null, getString(R.string.update_video_record));
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
                    if (state == 1) {
                        if (type == 1) {
                            VideoDBService service = new VideoDBService(mContext);
                            service.deleteOneVideoRecord(mBean);
                        } else {
                            getRefreshData(0);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                new AlertDialog.Builder(mContext).setTitle("更新失败")//设置对话框标题
                        .setMessage("更新上次保存学习资料状态失败！")//设置显示的内容
                        .setPositiveButton("重试", new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                // TODO Auto-generated method stub
                                videoCompletion(1, mBean);
                            }
                        }).setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                        // TODO Auto-generated method stub
                        VideoDBService service = new VideoDBService(mContext);
                        service.addOneVideoRecord(new VideoRecordBean(mCwUUID, mUserUuid));
                    }
                }).show();//在按键响应事件中显示此对话框
            }
        });
    }

    private void initDB() {
        VideoDBService service = new VideoDBService(mContext);
        List<VideoRecordBean> mListBean = service.getAllVideoRecords();
        if (mListBean.size() != 0) {
            for (int i = 0; i < mListBean.size(); i++) {
                videoCompletion(1, mListBean.get(i));
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mVDVideoView!=null){
            mVDVideoView.pause();
        }
    }
}
