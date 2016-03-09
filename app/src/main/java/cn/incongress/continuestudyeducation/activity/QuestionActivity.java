package cn.incongress.continuestudyeducation.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.adapter.QuestionAdapter;
import cn.incongress.continuestudyeducation.base.BaseActivity;
import cn.incongress.continuestudyeducation.bean.Constant;
import cn.incongress.continuestudyeducation.bean.QuestionBean;
import cn.incongress.continuestudyeducation.service.CMEHttpClientUsage;
import cn.incongress.continuestudyeducation.utils.LogUtils;
import cn.incongress.continuestudyeducation.utils.NetWorkUtils;
import cn.incongress.continuestudyeducation.utils.StringUtils;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Jacky on 2015/12/18.
 */
public class QuestionActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "QuestionActivity";

    private static final int LOAD_DATA_COMPLETE = 0x0001;
    private static final int LOAD_DATA_NO_DATA = 0x0002;
    private static final int LOAD_DATA_ERROR = 0x0003;
    private static final int LOAD_DATA_MORE = 0x0004;
    protected static final int LOAD_DATA_NO_MORE = 0x0005;

    private QuestionAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private ListView mLvQuestions;
    private TextView mTvQuestionNums;
    private View mHeaderView;

    private String mUserUuId = "";
    private String mCwUuId = "";
    private List<QuestionBean> mQuestions;
    private int mMaxQuestionCount;

    private boolean mIsLoadMoreState = false;
    private EditText mEtQuestionContent;
    private Button mBtSendQuestion;
    private TextView mTvNodata;

    public static final String CWUUID = "cwuuId";

    public static void startQuestionActivity(Context ctx,String cwUuId) {
        Intent intent = new Intent();
        intent.setClass(ctx, QuestionActivity.class);
        intent.putExtra(CWUUID, cwUuId);
        ctx.startActivity(intent);
    }


    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_questions);

        mUserUuId = getSPValue(Constant.SP_USER_UUID);
        mCwUuId = getIntent().getStringExtra(CWUUID);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.attach_question);

        mLvQuestions = getViewById(R.id.lv_qestions);
        mRefreshLayout = getViewById(R.id.refresh_layout);
        mTvQuestionNums = getViewById(R.id.tv_question_size);
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.heade_view_question, null);
        mLvQuestions.addHeaderView(mHeaderView);
        mEtQuestionContent = getViewById(R.id.et_question);
        mBtSendQuestion = getViewById(R.id.bt_question);
        mTvNodata = getViewById(R.id.tv_no_data);
        mLvQuestions.setEmptyView(mTvNodata);

        mRefreshLayout.setColorSchemeResources(R.color.button_background2);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetWorkUtils.getNetworkType(mContext) == -1) {
                    SimpleDialogFragment.createBuilder(mContext, getSupportFragmentManager()).setTitle(R.string.dialog_title).setMessage(R.string.network_not_enbale).setPositiveButtonText(R.string.positive_button).show();
                } else {
                    getQuestions(-1);
                }
            }
        });

        mLvQuestions.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (view.getLastVisiblePosition() == mQuestions.size() && !mIsLoadMoreState && scrollState == SCROLL_STATE_IDLE) {
                    getQuestions(mQuestions.get(mQuestions.size() - 1).getQuestionId());
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    protected void initializeEvents() {
        mBtSendQuestion.setOnClickListener(this);
        mEtQuestionContent.setOnClickListener(this);
        mHandler.sendEmptyMessageDelayed(455, 100);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
    }

    /**
     * 获取问题列表
     */
    private void getQuestions(final int questionid) {
        CMEHttpClientUsage.getInstanse().doGetQuestionList(questionid,Constant.PAGE_SIZE, mUserUuId, mCwUuId, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                if(questionid != -1) {
                    mProgressDialog = ProgressDialog.show(mContext, null, "loading...");
                    mIsLoadMoreState = true;
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if(questionid!=-1 && mProgressDialog!= null) {
                    mProgressDialog.dismiss();
                    mIsLoadMoreState = false;
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogUtils.i(TAG, response.toString());
                try {
                    int state = response.getInt("state");
                    if (state == 1) {
                        JSONArray questionArray = response.getJSONArray("questionArray");
                        Gson gson = new Gson();
                        if(questionid == -1) {
                            mQuestions = gson.fromJson(questionArray.toString(), new TypeToken<List<QuestionBean>>() {}.getType());
                            if (mQuestions.size() == 0) {
                                mHandler.sendEmptyMessage(LOAD_DATA_NO_DATA);
                            } else {
                                mHandler.sendEmptyMessage(LOAD_DATA_COMPLETE);
                            }
                        }else {
                            List<QuestionBean> temps = gson.fromJson(questionArray.toString(), new TypeToken<List<QuestionBean>>() {}.getType());
                            mQuestions.addAll(temps);
                            mHandler.sendEmptyMessage(LOAD_DATA_MORE);
                        }
                        mMaxQuestionCount = response.getInt("maxCount");
                    } else {
                        mHandler.sendEmptyMessage(LOAD_DATA_NO_MORE);
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
    protected void handleDetailMsg(Message msg) {
        int target = msg.what;

        if (target == LOAD_DATA_COMPLETE) {
            mAdapter = new QuestionAdapter(this,mQuestions);
            mLvQuestions.setAdapter(mAdapter);
            ((TextView)mHeaderView.findViewById(R.id.tv_question_size)).setText(getString(R.string.question_nums, mMaxQuestionCount));
            mRefreshLayout.setRefreshing(false);
        } else if (target == LOAD_DATA_ERROR) {
            showShortToast(R.string.net_error_check_net);
        }else if(target == LOAD_DATA_MORE) {
            if(mAdapter != null)
                mAdapter.notifyDataSetChanged();
        }else if(target == LOAD_DATA_NO_MORE) {
            showShortToast(R.string.no_more_data);
        }else if(target == 455) {
            setRefreshing(mRefreshLayout,true,true);
        }
    }

    @Override
    public void onClick(View v) {
        int target = v.getId();

        if(target == R.id.bt_question) {
            String content = mEtQuestionContent.getText().toString().trim();
            if(!StringUtils.isEmpty(content)) {
                CMEHttpClientUsage.getInstanse().doSaveQuestion(mCwUuId, mUserUuId, content, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            int state = response.getInt("state");
                            if(state == 1) {
                                showShortToast(getString(R.string.question_success));
                                getQuestions(-1);
                                initialQuestion();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
            }else {
                showShortToast(getString(R.string.content_null_error));
            }
        }else if(target == R.id.et_question) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mEtQuestionContent.getWindowToken(), 1) ;
            mEtQuestionContent.setFocusable(true);
            mEtQuestionContent.setFocusableInTouchMode(true);
        }
    }

    private void initialQuestion(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEtQuestionContent.getWindowToken(), 0) ;
        mEtQuestionContent.setText(StringUtils.EMPTY_STRING);
        mEtQuestionContent.clearFocus();
        mEtQuestionContent.setFocusable(false);
        mEtQuestionContent.setFocusableInTouchMode(false);
    }

}

