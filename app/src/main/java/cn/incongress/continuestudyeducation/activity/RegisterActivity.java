package cn.incongress.continuestudyeducation.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.base.BaseActivity;
import cn.incongress.continuestudyeducation.bean.Constant;
import cn.incongress.continuestudyeducation.service.CMEHttpClientUsage;
import cn.incongress.continuestudyeducation.utils.LogUtils;
import cn.incongress.continuestudyeducation.utils.StringUtils;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Jacky on 2015/12/22.
 */
public class RegisterActivity extends BaseActivity {
    private EditText mEtMobileEmail, mEtIdentifyCode, mEtPwd, mEtConfirmPwd;
    private Button mBtGetIdentifyCode, mBtLogin;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mEtMobileEmail = getViewById(R.id.et_name_or_email);
        mEtIdentifyCode = getViewById(R.id.et_identify_code);
        mEtPwd = getViewById(R.id.et_pwd);
        mEtConfirmPwd = getViewById(R.id.et_confirm_pwd);

        mBtGetIdentifyCode = getViewById(R.id.bt_get_identify_code);
        mBtLogin = getViewById(R.id.bt_login);
    }

    @Override
    protected void initializeEvents() {
    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {

    }

    @Override
    protected void handleDetailMsg(Message msg) {

    }

    public void getIdentifyCode(View view) {
        String context = mEtMobileEmail.getText().toString().trim();

        if(StringUtils.isEmpty(context)) {
            SimpleDialogFragment.createBuilder(this, getSupportFragmentManager()).setTitle(R.string.dialog_title).setMessage(R.string.dialog_context_empty).setPositiveButtonText(R.string.positive_button).show();
            return;
        }

        if(isMobileOrEmailFormatCorrect(context)) {
            CMEHttpClientUsage.getInstanse().doGetSms(Constant.PROJECT_ID, context, new JsonHttpResponseHandler(){
                @Override
                public void onStart() {
                    super.onStart();
                    mProgressDialog = ProgressDialog.show(mContext,null,"loading...");
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    mProgressDialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        int state = response.getInt("state");
                        LogUtils.i(TAG, "state:" + state);

                        String tips = "";
                        if(state == 0) {
                            tips = "用户不存在";
                        }else if(state == 1) {
                            tips = "30分钟不能重复提交";
                        }else if(state == 2) {
                            tips = "验证码发送成功";
                        }else if(state == 3) {
                            tips = "发送短信已达到最大上限";
                        }else if(state == 4) {
                            tips = "发生未知错误";
                        }
                        SimpleDialogFragment.createBuilder(mContext, getSupportFragmentManager()).setTitle(R.string.dialog_title).setMessage(tips).setPositiveButtonText(R.string.positive_button).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }else {
            SimpleDialogFragment.createBuilder(this, getSupportFragmentManager()).setTitle(R.string.dialog_title).setMessage(R.string.dialog_context_format_error).setPositiveButtonText(R.string.positive_button).show();
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

    public void login(View view) {
        String name = mEtMobileEmail.getText().toString().trim();
        String identifyCode = mEtIdentifyCode.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();
        String confirmPwd = mEtConfirmPwd.getText().toString().trim();

        //判空操作
        if(!StringUtils.isAllNotEmpty(name, identifyCode, pwd, confirmPwd)) {
            SimpleDialogFragment.createBuilder(mContext,getSupportFragmentManager()).setTitle(R.string.dialog_title).setMessage(R.string.dialog_context2_empty).setPositiveButtonText(R.string.positive_button).show();
            return;
        }else {
            //格式判断
            if(isMobileOrEmailFormatCorrect(name)) {
                if(StringUtils.isPassword(pwd)) {
                    if(pwd.equals(confirmPwd)) {
                        //不为空，且所有格式正确
                        CMEHttpClientUsage.getInstanse().doUpdatePassword(Constant.PROJECT_ID, identifyCode, name, pwd, new JsonHttpResponseHandler(){
                                    @Override
                                    public void onStart() {
                                        super.onStart();
                                        mProgressDialog = ProgressDialog.show(mContext,null, "loading...");
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
                                            String userUuid = response.getString("userUuId");
                                            setSPValue(Constant.SP_USER_UUID, userUuid);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        } finally {
                                            startActivity(new Intent(mContext,HomeActivity.class));
                                        }
                                    }
                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                        super.onFailure(statusCode, headers, responseString, throwable);
                                    }
                                });
                    }else {
                        SimpleDialogFragment.createBuilder(mContext,getSupportFragmentManager()).setTitle(R.string.dialog_title).setMessage(R.string.dialog_context_pwd_twice_check_error).setPositiveButtonText(R.string.positive_button).show();
                    }
                }else {
                    SimpleDialogFragment.createBuilder(mContext,getSupportFragmentManager()).setTitle(R.string.dialog_title).setMessage(R.string.dialog_context_pwd_format_error).setPositiveButtonText(R.string.positive_button).show();
                }
            }else {
                SimpleDialogFragment.createBuilder(mContext,getSupportFragmentManager()).setTitle(R.string.dialog_title).setMessage(R.string.dialog_context_empty).setPositiveButtonText(R.string.positive_button).show();
            }
        }
    }
}
