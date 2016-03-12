package cn.incongress.continuestudyeducation.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.base.BaseActivity;
import cn.incongress.continuestudyeducation.uis.StringUtils;

/**
 * Created by Jacky on 2015/12/22.
 */
public class TeacherInfoActivity extends BaseActivity {
    public static final String TEACHER_NAME = "teacherName";
    public static final String TEACHER_URL = "teacherUrl";
    public static final String TEACHER_REMARK = "teacherRemark";

    private String mTeacherName,mTeacherRemark,mTeacherUrl;

    private TextView mTvName,mTvRemark;
    private ImageView mIvTeacher;

    public static void startTeacherInfoActivity(Context ctx, String teacherName,String teacherRemark, String teacherUrl) {
        Intent intent = new Intent(ctx, TeacherInfoActivity.class);
        intent.putExtra(TEACHER_NAME, teacherName);
        intent.putExtra(TEACHER_REMARK, teacherRemark);
        intent.putExtra(TEACHER_URL, teacherUrl);
        ctx.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_teacher_info);

        mTeacherName = getIntent().getStringExtra(TEACHER_NAME);
        mTeacherRemark = getIntent().getStringExtra(TEACHER_REMARK);
        mTeacherUrl = getIntent().getStringExtra(TEACHER_URL);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        ((TextView)getViewById(R.id.tv_title)).setText(R.string.teacher_info);

        mTvName = getViewById(R.id.tv_teacher_name);
        mTvRemark = getViewById(R.id.tv_teacher_info);
        mIvTeacher = getViewById(R.id.iv_teacher);
    }

    @Override
    protected void initializeEvents() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
        mTvName.setText(mTeacherName);
        mTvRemark.setText(mTeacherRemark);
        if(StringUtils.isEmpty(mTeacherUrl)) {
            mIvTeacher.setImageResource(R.mipmap.teacher_info_recentage);
        }else {
            ImageLoader.getInstance().displayImage(mTeacherUrl, mIvTeacher, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    mIvTeacher.setImageResource(R.mipmap.teacher_info_recentage);
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                }

                @Override
                public void onLoadingCancelled(String s, View view) {
                }
            });
        }

    }

    @Override
    protected void handleDetailMsg(Message msg) {

    }
}
