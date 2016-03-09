package cn.incongress.continuestudyeducation.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.ISimpleDialogListener;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.activity.PlateDetailActivity;
import cn.incongress.continuestudyeducation.adapter.ListAdapterHolder;
import cn.incongress.continuestudyeducation.base.BaseFragment;
import cn.incongress.continuestudyeducation.bean.Constant;
import cn.incongress.continuestudyeducation.bean.CourseBean;
import cn.incongress.continuestudyeducation.bean.PlateArrayBean;
import cn.incongress.continuestudyeducation.service.CMEHttpClientUsage;
import cn.incongress.continuestudyeducation.utils.LogUtils;
import cz.msebera.android.httpclient.Header;


public class HomeFragment extends BaseFragment implements ISimpleDialogListener {
    private static HomeFragment mInstance;
    private RecyclerView mRecyclerView;
    private ListAdapterHolder adapter;
    private CourseBean mCourseBean;
    private TextView mTvSituationContent, mTvSituation;
    private ImageView mIvNoDataTip;
    private SwipeRefreshLayout mRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private LinearLayout mLlTopInfo;
    private Toolbar mToolbar;

    private static final int DIALOG_CODE = 0x001;

    private static final String TAG = "HomeFragment";

    public HomeFragment(){}
	
    public static HomeFragment getInstance() {
        if(mInstance == null) {
            mInstance = new HomeFragment();
        }
    	return mInstance;
    }

    @Override
    protected void handleDetailMsg(Message msg) {
        int target = msg.what;

        if(target == LOAD_DATA_COMPLETE) {
            if(mRecyclerView.getVisibility() == View.GONE) {
                mLlTopInfo.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }

            adapter = new ListAdapterHolder(mCourseBean.getPlateArray());
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setHasFixedSize(true);

            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            adapter.SetOnItemClickListener(new ListAdapterHolder.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    PlateArrayBean bean =  mCourseBean.getPlateArray().get(position);
                    if(bean.getIsFinish() == 1) {
                        SimpleDialogFragment.createBuilder(getActivity(), getFragmentManager()).
                                setTitle(R.string.dialog_title).setMessage(R.string.study_is_finish).
                                setPositiveButtonText(R.string.positive_button).setCancelableOnTouchOutside(false).show();
                    }else {
                        PlateDetailActivity.startPlateDetailActivity(getActivity(),bean.getPuuId());
                    }
                }
            });
            setSPValue(Constant.SP_CUUID, mCourseBean.getCuuId());
            mRefreshLayout.setRefreshing(false);

            String situation = "";
            if(mCourseBean.getGetCirtification() == 1) {
                situation = "课程学习";
                mTvSituationContent.setText(getString(R.string.situation1,mCourseBean.getPoint()));
            }else if(mCourseBean.getGetCirtification() == 2) {
                situation = "证书审核中";
                mTvSituationContent.setText(getString(R.string.situation2,mCourseBean.getPoint()));
            }else if(mCourseBean.getGetCirtification() == 3) {
                situation = "审核通过";
                mTvSituationContent.setText(getString(R.string.situation3,mCourseBean.getPoint()));
            }
            mTvSituation.setText(situation);

            mToolbar.setTitle(mCourseBean.getCourseName());
        }else if(target == LOAD_DATA_NO_DATA) {
            mLlTopInfo.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            mIvNoDataTip.setVisibility(View.VISIBLE);
        }else if(target == LOAD_DATA_ERROR) {
        }else if(target == LOAD_REFRESH_SHOW) {
            setRefreshing(mRefreshLayout,true,true);
        }else if(target == SERVER_ERROR) {
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, null);
        mLlTopInfo = (LinearLayout) view.findViewById(R.id.ll_top_info);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.video_list);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.video_list);
        mIvNoDataTip = (ImageView) view.findViewById(R.id.iv_no_data_tips);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        mTvSituation = (TextView) view.findViewById(R.id.tv_situation);
        mTvSituationContent = (TextView) view.findViewById(R.id.tv_situation_content);
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRefreshLayout.setColorSchemeResources(R.color.button_background2);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initDatas();
            }
        });
        mHandler.sendEmptyMessageDelayed(LOAD_REFRESH_SHOW, 100);
		return view;
	}

    @Override
    public void onResume() {
        super.onResume();
//        JPushInterface.setAlias(getActivity(), "fdafdasfjdskafj", new TagAliasCallback() {
//            @Override
//            public void gotResult(int i, String s, Set<String> set) {
//
//            }
//        });
    }

    /**
     * 初始化页面数据
     */
    private void initDatas() {
        CMEHttpClientUsage.getInstanse().doGetCourse(Constant.PROJECT_ID, getSPValue(Constant.SP_USER_UUID), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                LogUtils.v(TAG + "[onSuccess]", response.toString());
                try {
                    int status = response.getInt("state");
                    if (status == 1) {
                        Gson gson = new Gson();
                        mCourseBean = gson.fromJson(response.toString(), CourseBean.class);
                        if (mCourseBean.getPlateArray().size() == 0) {
                            mHandler.sendEmptyMessage(LOAD_DATA_NO_DATA);
                        } else {
                            mHandler.sendEmptyMessage(LOAD_DATA_COMPLETE);
                        }
                        LogUtils.i(TAG, mCourseBean.toString());
                    } else {
                        Toast.makeText(getActivity(), "Get Status not equals 1", Toast.LENGTH_SHORT).show();
                        mHandler.sendEmptyMessage(LOAD_DATA_NO_DATA);
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "[exception]:" + e.toString(), null);
                       Toast.makeText(getActivity(), "Parse Data Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                LogUtils.i(TAG,responseString);
                showShortToast("访问异常，请稍后重试");
                mHandler.sendEmptyMessage(SERVER_ERROR);
            }

            @Override
            public void onFinish() {
                LogUtils.v(TAG + "[onFinish]", "It is finished!");
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onNegativeButtonClicked(int requestCode) {
        if(requestCode == DIALOG_CODE)
           Toast.makeText(getActivity(), "Cancel clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPositiveButtonClicked(int requestCode) {
        if(requestCode == DIALOG_CODE)
            Toast.makeText(getActivity(), "Ok clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNeutralButtonClicked(int requestCode) {
        Toast.makeText(getActivity(), "Neutral clicked", Toast.LENGTH_SHORT).show();
    }



}
