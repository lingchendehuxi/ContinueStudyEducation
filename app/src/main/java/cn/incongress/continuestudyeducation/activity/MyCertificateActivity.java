package cn.incongress.continuestudyeducation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.adapter.MyCertificateAdapter;
import cn.incongress.continuestudyeducation.base.BaseActivity;
import cn.incongress.continuestudyeducation.bean.Constant;
import cn.incongress.continuestudyeducation.bean.MyCertificateBean;
import cn.incongress.continuestudyeducation.bean.PlateDetailBean;
import cn.incongress.continuestudyeducation.bean.StudyCenterBean;
import cn.incongress.continuestudyeducation.service.CMEHttpClientUsage;
import cz.msebera.android.httpclient.Header;

public class MyCertificateActivity extends BaseActivity {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MyCertificateAdapter mAdapter;
    private MyCertificateBean mCertificateBean;
    private RecyclerView mRecyclerView;

    String[]s = {"条目1","条目2","条目3"};
    String[]a = {"按时打算大所大所大所大所多啊爱迪生啊啊的",
            "按时打算大多阿萨德按时打按时打算大多阿萨德阿萨德ad阿萨德算大多阿萨德阿萨德ad阿萨德按时打算大多阿萨德阿萨德ad阿萨德阿萨德ad阿萨德",
            "阿斯达大所大所多无大所大所多撒发"};
    String[]b = {"张三，李四","王二，麻子","狗子，二蛋"};
    String[]c = {"http://pic.58pic.com/58pic/13/79/02/44X58PICDCG_1024.jpg","http://pic.58pic.com/58pic/14/88/58/84y58PIC8bV_1024.jpg"
            ,"http://pic.58pic.com/58pic/14/88/22/30a58PICHSZ_1024.jpg"};
    int []d = {1,2,3};
    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_certificate);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mRecyclerView = getViewById(R.id.certifi_list);
        mSwipeRefreshLayout = getViewById(R.id.certificate_swiperefresh);
        ((TextView)getViewById(R.id.home_title)).setText("我的证书");
        ((ImageView)getViewById(R.id.home_title_back)).setVisibility(View.VISIBLE);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.button_background2);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        mHandler.sendEmptyMessageDelayed(LOAD_REFRESH_SHOW,100);
    }

    private void initData() {
        CMEHttpClientUsage.getInstanse().doGetZhengShuList(Constant.PROJECT_ID, getSPValue(Constant.SP_USER_UUID), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if(1==response.getInt("state")){
                        Gson gson = new Gson();
                        mCertificateBean = gson.fromJson(response.toString(), MyCertificateBean.class);
                        mHandler.sendEmptyMessage(LOAD_DATA_COMPLETE);
                    }else{
                        showShortToast(response.getInt("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showShortToast("访问异常，请稍后重试");
            }
        });
        //mHandler.sendEmptyMessage(LOAD_DATA_COMPLETE);
    }

    @Override
    protected void initializeEvents() {

    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {

    }

    @Override
    protected void handleDetailMsg(Message msg) {
        int target = msg.what;

        if(target == LOAD_DATA_COMPLETE) {
           /* final List< MyCertificateBean.CertificateArrayBean> arrayBeen = new ArrayList<>();
            for (int i = 0;i<s.length;i++){
                MyCertificateBean.CertificateArrayBean bean = new MyCertificateBean.CertificateArrayBean();
                bean.setCourseName(s[i]);
                bean.setCourseRemark(a[i]);
                bean.setImgUrl(c[i]);
                bean.setTeacherName(b[i]);
                bean.setZsState(d[i]);
                arrayBeen.add(bean);
            }*/
            mAdapter = new MyCertificateAdapter(mCertificateBean.getJsonArray(),mContext);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.SetOnItemClickListener(new MyCertificateAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    //MyCertificateBean.CertificateArrayBean bean = mCertificateBean.getJsonArray().get(position);
                    MyCertificateBean.CertificateArrayBean bean = mCertificateBean.getJsonArray().get(position);
                    int state = bean.getZsState();
                    switch (state){
                        case 1:
                            showShortToast("证书审核中，请耐心等候...");
                            break;
                        case 2:
                            Intent intent = new Intent(mContext,CertificateActivity.class);
                            intent.putExtra("title",bean.getCourseName());
                            intent.putExtra("url",bean.getImgUrl());
                            startActivity(intent);
                            break;
                        case 3:
                            showShortToast("证书审核未通过!");
                            break;
                    }
                }
            });
            mSwipeRefreshLayout.setRefreshing(false);
        }else if(target == LOAD_REFRESH_SHOW) {
            setRefreshing(mSwipeRefreshLayout,true,true);
            initData();
        }

    }
    public void back(View v){
        finish();
    }
}
