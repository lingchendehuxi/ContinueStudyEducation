package cn.incongress.continuestudyeducation.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
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
import cn.incongress.continuestudyeducation.utils.DensityUtil;
import cn.incongress.continuestudyeducation.utils.LogUtils;
import cz.msebera.android.httpclient.Header;
import roundcornerprogressbar.RoundCornerProgressBar;

/**
 * 板块详情
 * Created by Jacky on 2015/12/21.
 */
public class PlateDetailActivity extends BaseActivity {
    private static final String BUNDLE_PUUID = "puuId";
    private static final String BUNDLE_CUUID = "cuuId";
    private static final String BUNDLE_TITLE = "title";
    private static final String BUNDLE_TYPE="courseType";

    private String mPuuId,mCuuId,mTitle;
    private String mUserUuId;
    private int type;

    private ExpandableListView mExpandableListView;
    private TextView detail_title,plate_size;
    private View mDivider;
    private CourseListAdapter mAdapter;
    private PlateDetailBean mPlateDetailBean;
    //private SwipeRefreshLayout mRefreshLayout;
    private RelativeLayout plateIntroduction;




    private boolean mIsFirstIn = true;

    @Override
    protected void handleDetailMsg(Message msg) {
        int target = msg.what;

        if(target == LOAD_DATA_COMPLETE) {
            detail_title.setText(mTitle);
            plate_size.setText(getString(R.string.study_position2, ""+mPlateDetailBean.getPlateCount()));

            initListData();
            initStudyJdInDB();

            //mRefreshLayout.setRefreshing(false);
        }else if(target == LOAD_REFRESH_SHOW) {
            //setRefreshing(mRefreshLayout,true,true);
            initDatas();
        }
    }

    /**
     * 根据情况更新学习进度
     */
    private void initStudyJdInDB() {
        /*VideoDBService service = new VideoDBService(this);
        for(int i=0; i<mPlateDetailBean.getCoursewareArray().size(); i++) {
            CoursewareArrayBean bean = mPlateDetailBean.getCoursewareArray().get(i);
            if(!service.queryIsVideoRecordExist(bean.getCwuuId(), mUserUuId)) {
                service.addOneVideoRecord(new VideoRecordBean(bean.getCwuuId(), mUserUuId, bean.getIsFinish(), bean.getIsFinish()));
            }
        }*/
    }

    /**
     * 将获取到的数据填充
     */
    private void initListData() {
        mAdapter = new CourseListAdapter(mContext);
        mAdapter.setData(mPlateDetailBean.getPlateArray());
        mExpandableListView.setAdapter(mAdapter);

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(mPlateDetailBean.getPlateArray().get(groupPosition).getCwArray().get(childPosition).getType() != 1){
                    if(mPlateDetailBean.getPlateArray().get(groupPosition).getCwArray().get(childPosition).getCanStudy() == 1){
                        String puuId = mPlateDetailBean.getPlateArray().get(groupPosition).getPuuId();
                        String cwuuId = mPlateDetailBean.getPlateArray().get(groupPosition).getCwArray().get(childPosition).getCwuuId();
                        int type1 = mPlateDetailBean.getPlateArray().get(groupPosition).getCwArray().get(childPosition).getType();
                        AttachStudyActivity.startAttachStudyActivity(mContext, puuId, cwuuId, type1,type);
                    }else{
                        Toast.makeText(mContext,"请按提示进行学习",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    String puuId = mPlateDetailBean.getPlateArray().get(groupPosition).getPuuId();
                    String cwuuId = mPlateDetailBean.getPlateArray().get(groupPosition).getCwArray().get(childPosition).getCwuuId();
                    int type1 = mPlateDetailBean.getPlateArray().get(groupPosition).getCwArray().get(childPosition).getType();
                    AttachStudyActivity.startAttachStudyActivity(mContext, puuId, cwuuId, type1,type);
                }
                return false;
            }
        });
        for(int i = 0; i < mPlateDetailBean.getPlateArray().size(); i++){
            for (int j=0;j<mPlateDetailBean.getPlateArray().get(i).getCwArray().size();j++){
                if(mPlateDetailBean.getPlateArray().get(i).getCwArray().get(j).getType() == 1){
                    mExpandableListView.expandGroup(i);
                }else{
                    if(mPlateDetailBean.getPlateArray().get(i).getCwArray().get(j).getCanStudy() == 1){
                        mExpandableListView.expandGroup(i);
                    }
                }
            }
        }
    }

    public static void startPlateDetailActivity(Context ctx, String puuId, String cuuId, String title,int courseType) {
        Intent intent = new Intent(ctx, PlateDetailActivity.class);
        intent.putExtra(BUNDLE_PUUID, puuId);
        intent.putExtra(BUNDLE_CUUID,cuuId);
        intent.putExtra(BUNDLE_TITLE,title);
        intent.putExtra(BUNDLE_TYPE,courseType);
        ctx.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_plate_detail);
        mPuuId = getIntent().getStringExtra(BUNDLE_PUUID);
        mCuuId = getIntent().getStringExtra(BUNDLE_CUUID);
        mTitle = getIntent().getStringExtra(BUNDLE_TITLE);
        type = getIntent().getIntExtra(BUNDLE_TYPE,0);
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
        ((TextView)getViewById(R.id.home_title)).setText(R.string.plate_detail);
        ((ImageView)getViewById(R.id.home_title_back)).setVisibility(View.VISIBLE);
        //mRefreshLayout = getViewById(R.id.refresh_layout);
        mExpandableListView = getViewById(R.id.detail_listView);
        detail_title = getViewById(R.id.detail_title);
        plate_size = getViewById(R.id.plate_size);
        mDivider = getViewById(R.id.divider);
        plateIntroduction = getViewById(R.id.plate_introduction);
        mExpandableListView.setGroupIndicator(null);
        /*mRefreshLayout.setColorSchemeResources(R.color.button_background2);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initDatas();
            }
        });*/
        plateIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LessonIntroActivity.class);
                intent.putExtra("cuuId",mCuuId);
                startActivity(intent);
            }
        });
        mHandler.sendEmptyMessageDelayed(LOAD_REFRESH_SHOW,100);
    }


    @Override
    protected void initializeData(Bundle savedInstanceState) {
        ViewTreeObserver vto = plate_size.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                int width = plate_size.getMeasuredWidth();
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, DensityUtil.dip2px(PlateDetailActivity.this, 1));
                layoutParams.setMargins(0, DensityUtil.dip2px(PlateDetailActivity.this,1), 7, 7);
                mDivider.setLayoutParams(layoutParams);
                return true;
            }
        });
    }

    private void initDatas() {
        CMEHttpClientUsage.getInstanse().doGetCoursewareList(Constant.PROJECT_ID,mCuuId ,mUserUuId,type, new JsonHttpResponseHandler() {
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
                try {
                    Log.e("GYW",response.toString());
                    if(1==response.getInt("state")){
                        Gson gson = new Gson();
                        mPlateDetailBean = gson.fromJson(response.toString(), PlateDetailBean.class);
                        mHandler.sendEmptyMessage(LOAD_DATA_COMPLETE);
                    }else{

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

    @Override
    protected void initializeEvents() {

    }
    public void back(View v){
        finish();
    }
}
