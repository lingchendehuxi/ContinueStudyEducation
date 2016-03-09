package cn.incongress.continuestudyeducation.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.base.BaseActivity;
import cn.incongress.continuestudyeducation.bean.Constant;
import cn.incongress.continuestudyeducation.service.CMEHttpClientUsage;
import cn.incongress.continuestudyeducation.utils.ActivityUtils;
import cn.incongress.continuestudyeducation.utils.LogUtils;
import cn.incongress.continuestudyeducation.utils.NetWorkUtils;
import cn.incongress.continuestudyeducation.utils.StringUtils;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Jacky on 2015/12/22.
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    private EditText mEtMobileOrEmail,mEtPwd;
    private Button mBtForgetPwd,mBtLogin;
    private TextView mTvFirstLogin;

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mEtMobileOrEmail = getViewById(R.id.et_name_or_email);
        mEtPwd = getViewById(R.id.et_pwd);
        mBtForgetPwd = getViewById(R.id.bt_forget_pwd);
        mBtLogin = getViewById(R.id.bt_login);
        mTvFirstLogin = getViewById(R.id.tv_first_login);

        mTvFirstLogin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );
        mTvFirstLogin.getPaint().setAntiAlias(true);
    }

    @Override
    protected void initializeEvents() {

    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
        //判断是否已经登陆了
        if(StringUtils.isEmpty(getSPValue(Constant.SP_USER_UUID))) {
        }else {
            ActivityUtils.finishAll();
            startActivity(new Intent(mContext, HomeActivity.class));
        }
    }

    @Override
    protected void handleDetailMsg(Message msg) {

    }



    public void login(View view) {
        String name = mEtMobileOrEmail.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();

        //先做判空处理
        if(!StringUtils.isAllNotEmpty(name,pwd)) {
            SimpleDialogFragment.createBuilder(mContext, getSupportFragmentManager()).setTitle(R.string.dialog_title).setMessage(R.string.dialog_context2_empty).setPositiveButtonText(R.string.positive_button).show();
            return;
        }else {
            //格式判断
            if(isMobileOrEmailFormatCorrect(name)) {
                if(StringUtils.isPassword(pwd)) {
                    if(NetWorkUtils.getNetworkType(mContext) == -1) {
                        SimpleDialogFragment.createBuilder(mContext, getSupportFragmentManager()).setTitle(R.string.dialog_title).setMessage(R.string.network_not_enbale).setPositiveButtonText(R.string.positive_button).show();
                        return;
                    }
                    CMEHttpClientUsage.getInstanse().doUserLogin(Constant.PROJECT_ID, name, pwd, Constant.CLIENT_TYPE, new JsonHttpResponseHandler(){
                                @Override
                                public void onStart() {
                                    super.onStart();
                                    mProgressDialog = ProgressDialog.show(mContext, null, getString(R.string.tips_login));
                                }

                                @Override
                                public void onFinish() {
                                    super.onFinish();
                                    mProgressDialog.dismiss();
                                }

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                                    LogUtils.i(TAG, response.toString());

                                    try {
                                        int state = response.getInt("state");

                                        if(state == 1) {
                                            String userUuid = response.getString("userUuId");
                                            setSPValue(Constant.SP_USER_UUID, userUuid);
                                            ActivityUtils.finishAll();
                                            startActivity(new Intent(mContext, HomeActivity.class));
                                        }else {
                                            showShortToast("登录失败："+ response.getString("remark"));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onRetry(int retryNo) {
                                    showShortToast(getString(R.string.retry_post,retryNo));
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    LogUtils.v(TAG, "[statusCode]:" + statusCode +"[Trowable]:" + throwable.toString());
                                    if(statusCode == 0) {
                                        showShortToast(R.string.connect_timeout);
                                    }
                                }
                            });
                }else {
                    SimpleDialogFragment.createBuilder(mContext,getSupportFragmentManager()).setTitle(R.string.dialog_title).setMessage(R.string.dialog_context_pwd_format_error).setPositiveButtonText(R.string.positive_button).show();
                }
            }else {
                SimpleDialogFragment.createBuilder(mContext,getSupportFragmentManager()).setTitle(R.string.dialog_title).setMessage(R.string.dialog_context_empty).setPositiveButtonText(R.string.positive_button).show();
            }
        }
    }

    /**
     * 判断格式或者正确
     * @return
     */
    private boolean isMobileOrEmailFormatCorrect(String checkText) {
        boolean isFormatCorrect = false;
        if(StringUtils.isMobileNO(checkText)) {
            isFormatCorrect = true;
        }else {
            if(StringUtils.isEmail(checkText)) {
                isFormatCorrect = true;
            }
        }

        return isFormatCorrect;
    }

    public void firstLogin(View view) {
        startActivity(new Intent(mContext, RegisterActivity.class));
    }

    public void forgetPwd(View view) {
        startActivity(new Intent(mContext, RegisterActivity.class));
    }
}
