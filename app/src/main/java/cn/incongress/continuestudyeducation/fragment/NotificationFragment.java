package cn.incongress.continuestudyeducation.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.adapter.NotifyListAdapter;
import cn.incongress.continuestudyeducation.base.BaseFragment;
import cn.incongress.continuestudyeducation.bean.Constant;
import cn.incongress.continuestudyeducation.bean.NotifyArrayBean;
import cn.incongress.continuestudyeducation.service.CMEHttpClientUsage;
import cn.incongress.continuestudyeducation.utils.LogUtils;
import cz.msebera.android.httpclient.Header;


public class NotificationFragment extends BaseFragment {
    private ListView mNotifyList;
    private NotifyListAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;

    private static final String TAG = "NotificationFragment";

    private int mNotifyId = -1;
    private boolean mIsLoadMoreState = false;
    private View footerLayout;
    private TextView textMore;

    private static NotificationFragment mInstance;
    private ArrayList<NotifyArrayBean> mNotifys;
    public NotificationFragment(){}

    public static NotificationFragment getInstance() {
        if(mInstance == null) {
            mInstance = new NotificationFragment();
        }
    	return mInstance;
    }

    @Override
    protected void handleDetailMsg(Message msg) {
        int target = msg.what;

        if(target == LOAD_DATA_COMPLETE) {
            mAdapter = new NotifyListAdapter();
            mAdapter.setData(mNotifys);
            mNotifyList.setAdapter(mAdapter);
            mRefreshLayout.setRefreshing(false);
        }else if(target == LOAD_DATA_NO_DATA) {
        }else if(target == LOAD_DATA_ERROR) {
        }else if(target == LOAD_REFRESH_SHOW) {
            setRefreshing(mRefreshLayout,true,true);
        }else if(target == LOAD_DATA_MORE) {
            mAdapter.notifyDataSetChanged();
        }else if(target == LOAD_DATA_NO_MORE) {
            showShortToast(R.string.no_more_data);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_notification, null);
        mNotifyList = (ListView) view.findViewById(R.id.lv_notify);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        mNotifyList.setEmptyView(view.findViewById(R.id.tv_no_data));

        mNotifyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        mNotifyList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                LogUtils.i(TAG, "[scrollState]:"+scrollState);
                if(view.getLastVisiblePosition() == mNotifys.size()-1 && mIsLoadMoreState == false && scrollState == SCROLL_STATE_IDLE) {
                    initDatas(mNotifys.get(mNotifys.size()-1).getNotifyId());
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                 LogUtils.i(TAG, "[firstVisibleItem]:"+firstVisibleItem+",[visibleItemCount]:"+ visibleItemCount + ",[totalItemCount]:"+totalItemCount +",[lastVisiblePosition]:"+ view.getLastVisiblePosition());
            }
        });

        footerLayout = LayoutInflater.from(getActivity()).inflate(R.layout.listview_footer, null);
        textMore = (TextView) footerLayout.findViewById(R.id.text_more);
        textMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatas(mNotifys.get(mNotifys.size()-1).getNotifyId());
            }
        });

        mRefreshLayout.setColorSchemeResources(R.color.button_background2);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mNotifyId = -1;
                initDatas(mNotifyId);
            }
        });

        mHandler.sendEmptyMessageDelayed(LOAD_REFRESH_SHOW, 100);
        return view;
	}

    private void initDatas(final int notifyId) {
        CMEHttpClientUsage.getInstanse().doGetNotifyList(Constant.PROJECT_ID, notifyId, Constant.PAGE_SIZE, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                super.onStart();
                mIsLoadMoreState = true;
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mIsLoadMoreState = false;
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogUtils.i(TAG, response.toString());
                try {
                    int status = response.getInt("state");
                    JSONArray notifyArray = response.getJSONArray("notifyArray");

                    if (status == 1) {
                        if(notifyId == -1) {
                            Gson gson = new Gson();
                            mNotifys = gson.fromJson(notifyArray.toString(), new TypeToken<List<NotifyArrayBean>>() {}.getType());
                            if (mNotifys.size() == 0) {
                                mHandler.sendEmptyMessage(LOAD_DATA_NO_DATA);
                            } else {
                                mHandler.sendEmptyMessage(LOAD_DATA_COMPLETE);
                            }
                            LogUtils.i(TAG, mNotifys.size()+"");
                        }else {
                            Gson gson = new Gson();
                            List<NotifyArrayBean> temps = gson.fromJson(notifyArray.toString(), new TypeToken<List<NotifyArrayBean>>() {}.getType());
                            mNotifys.addAll(temps);
                            mHandler.sendEmptyMessage(LOAD_DATA_MORE);
                        }
                    } else {
                        mHandler.sendEmptyMessage(LOAD_DATA_NO_MORE);
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "[exception]:" + e.toString(), null);
                    Toast.makeText(getActivity(), "Parse Data Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

}
