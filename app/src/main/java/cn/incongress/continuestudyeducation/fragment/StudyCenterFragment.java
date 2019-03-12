package cn.incongress.continuestudyeducation.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.activity.PlateDetailActivity;
import cn.incongress.continuestudyeducation.adapter.StudyCenterAdapter;
import cn.incongress.continuestudyeducation.base.BaseFragment;
import cn.incongress.continuestudyeducation.bean.Constant;
import cn.incongress.continuestudyeducation.bean.StudyCenterArrayBean;
import cn.incongress.continuestudyeducation.bean.StudyCenterBean;
import cn.incongress.continuestudyeducation.service.CMEHttpClientUsage;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Jacky on 2017/4/5.
 */

public class StudyCenterFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private StudyCenterAdapter mAdapter;
    private static StudyCenterFragment mInstance;
    private StudyCenterBean mBean = new StudyCenterBean();
    private List<StudyCenterArrayBean> mListBean = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private TextView size;

    public static StudyCenterFragment getInstance() {
        if(mInstance == null)
            mInstance = new StudyCenterFragment();

        return mInstance;
    }

    @Override
    protected void handleDetailMsg(Message msg) {
        if(mListBean!=null){
            mListBean.clear();
        }
        size.setText(getActivity().getResources().getString(R.string.plate_attach,mBean.getCourseCount()+""));
        for (int i = 0;i<mBean.getStudyNow().size();i++){
           StudyCenterBean.StudyNowBean nowBean= mBean.getStudyNow().get(i);
            StudyCenterArrayBean arrayBean = new StudyCenterArrayBean();
            arrayBean.setStateType(nowBean.getStateType());
            arrayBean.setCuuId(nowBean.getCuuId());
            arrayBean.setCourseName(nowBean.getCourseName());
            arrayBean.setCourseType(nowBean.getCourseType());
            arrayBean.setHasTime(nowBean.getHasTime());
            arrayBean.setPuuId(nowBean.getPuuId());
            arrayBean.setPlateName(nowBean.getPlateName());
            arrayBean.setCwuuId(nowBean.getCwuuId());
            arrayBean.setCourseWareName(nowBean.getCourseWareName());
            arrayBean.setStudyJd(nowBean.getStudyJd());
            arrayBean.setTeacherName(nowBean.getTeacherName());
            arrayBean.setRemark(nowBean.getRemark());
            arrayBean.setIsPass(nowBean.getIsPass());
            mListBean.add(arrayBean);
        }
        for (int i = 0;i<mBean.getStudyWill().size();i++){
            StudyCenterBean.StudyWillBean willBean= mBean.getStudyWill().get(i);
            StudyCenterArrayBean arrayBean = new StudyCenterArrayBean();
            arrayBean.setStateType(willBean.getStateType());
            arrayBean.setCuuId(willBean.getCuuId());
            arrayBean.setCourseName(willBean.getCourseName());
            arrayBean.setCourseType(willBean.getCourseType());
            arrayBean.setHasTime(willBean.getHasTime());
            arrayBean.setPuuId(willBean.getPuuId());
            arrayBean.setPlateName(willBean.getPlateName());
            arrayBean.setCwuuId(willBean.getCwuuId());
            arrayBean.setCourseWareName(willBean.getCourseWareName());
            arrayBean.setStudyJd(willBean.getStudyJd());
            arrayBean.setTeacherName(willBean.getTeacherName());
            arrayBean.setRemark(willBean.getRemark());
            arrayBean.setIsPass(willBean.getIsPass());
            mListBean.add(arrayBean);
        }
        for (int i = 0;i<mBean.getStudyEnd().size();i++){
            StudyCenterBean.StudyEndBean endBean= mBean.getStudyEnd().get(i);
            StudyCenterArrayBean arrayBean = new StudyCenterArrayBean();
            arrayBean.setStateType(endBean.getStateType());
            arrayBean.setCuuId(endBean.getCuuId());
            arrayBean.setCourseName(endBean.getCourseName());
            arrayBean.setCourseType(endBean.getCourseType());
            arrayBean.setHasTime(endBean.getHasTime());
            arrayBean.setPuuId(endBean.getPuuId());
            arrayBean.setPlateName(endBean.getPlateName());
            arrayBean.setCwuuId(endBean.getCwuuId());
            arrayBean.setCourseWareName(endBean.getCourseWareName());
            arrayBean.setStudyJd(endBean.getStudyJd());
            arrayBean.setTeacherName(endBean.getTeacherName());
            arrayBean.setRemark(endBean.getRemark());
            arrayBean.setIsPass(endBean.getIsPass());
            mListBean.add(arrayBean);
        }
        for (int i = 0;i<mBean.getOutTime().size();i++){
            StudyCenterBean.OutTimeBean outTimeBean= mBean.getOutTime().get(i);
            StudyCenterArrayBean arrayBean = new StudyCenterArrayBean();
            arrayBean.setCuuId(outTimeBean.getCuuId());
            arrayBean.setCourseName(outTimeBean.getCourseName());
            arrayBean.setCourseType(outTimeBean.getCourseType());
            arrayBean.setHasTime(outTimeBean.getHasTime());
            arrayBean.setPuuId(outTimeBean.getPuuId());
            arrayBean.setPlateName(outTimeBean.getPlateName());
            arrayBean.setCwuuId(outTimeBean.getCwuuId());
            arrayBean.setCourseWareName(outTimeBean.getCourseWareName());
            arrayBean.setStudyJd(outTimeBean.getStudyJd());
            arrayBean.setTeacherName(outTimeBean.getTeacherName());
            arrayBean.setRemark(outTimeBean.getRemark());
            arrayBean.setIsPass(outTimeBean.getIsPass());
            arrayBean.setStateType(outTimeBean.getStateType());
            mListBean.add(arrayBean);
        }


        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter = new StudyCenterAdapter(mListBean,getActivity());

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.SetOnItemClickListener(new StudyCenterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e("GYW","---------"+position);
                PlateDetailActivity.startPlateDetailActivity(getActivity(),mListBean.get(position).getPuuId(),mListBean.get(position).getCuuId(),mListBean.get(position).getCourseName(),mListBean.get(position).getCourseType());
            }
        });

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_studycenter, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.study_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.studycenter_swiperefresh);
        size = (TextView) view.findViewById(R.id.study_size);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        initDatas();

        mSwipeRefreshLayout.setColorSchemeResources( R.color.button_background2);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListBean.clear();
                initDatas();
            }
        });
        return view;
    }
    private void initDatas() {
        CMEHttpClientUsage.getInstanse().doGetCourse(Constant.PROJECT_ID, getSPValue(Constant.SP_USER_UUID), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("GYW",response.toString());
                Gson gson = new Gson();
                mBean = gson.fromJson(response.toString(), StudyCenterBean.class);
                mHandler.sendEmptyMessage(LOAD_DATA_NO_DATA);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                showShortToast("访问异常，请稍后重试");
            }

            @Override

            public void onFinish() {

            }
        });
    }
}
