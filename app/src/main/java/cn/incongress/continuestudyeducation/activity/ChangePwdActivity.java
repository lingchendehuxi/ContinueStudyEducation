package cn.incongress.continuestudyeducation.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.ISimpleDialogListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
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
 * Created by Jacky on 2015/12/25.
 */
public class ChangePwdActivity extends BaseActivity implements ISimpleDialogListener{
    private static final String TAG = "ChangePwdActivity";
    private String mUserUuid;
    private EditText mEtOldPwd,mEtNewPwd,mEtNewConfirmPwd;
    private TextView mTitle;
    private static final int REQUEST_CHANGE_PWD_SUCCESS = 0x110;

    public static final void startChangePwdActivity(Context ctx) {
        Intent intent = new Intent();
        intent.setClass(ctx, ChangePwdActivity.class);
        ctx.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_pwd);

        mUserUuid = getSPValue(Constant.SP_USER_UUID);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        ((TextView)getViewById(R.id.home_title)).setText(R.string.person_change_pwd);
        ((ImageView)getViewById(R.id.home_title_back)).setVisibility(View.VISIBLE);

        mEtOldPwd = getViewById(R.id.et_origin_pwd);
        mEtNewPwd = getViewById(R.id.et_new_pwd);
        mEtNewConfirmPwd = getViewById(R.id.et_confirm_new_pwd);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.person_info_save) {
            String oldpwd = mEtOldPwd.getText().toString().trim();
            String newPwd = mEtNewPwd.getText().toString().trim();
            String newConfirmPwd = mEtNewConfirmPwd.getText().toString().trim();

            if(oldpwd.equals(newPwd)) {
                SimpleDialogFragment.createBuilder(ChangePwdActivity.this, getSupportFragmentManager()).
                        setTitle(R.string.dialog_title).setPositiveButtonText(R.string.positive_button).
                        setMessage("新密码与旧密码不能相同").show();
                return true;
            }

            if(StringUtils.isAllNotEmpty(oldpwd, newPwd, newConfirmPwd)) {
                if(newPwd.equals(newConfirmPwd)) {
                    CMEHttpClientUsage.getInstanse().doSaveNewPassword(mUserUuid, oldpwd, newPwd, new JsonHttpResponseHandler(){
                        @Override
                        public void onStart() {
                            super.onStart();
                            mProgressDialog = ProgressDialog.show(mContext,null, "正在修改");
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
                                if(state == 0) {
                                  SimpleDialogFragment.createBuilder(ChangePwdActivity.this, getSupportFragmentManager()).setTitle(R.string.dialog_title).setPositiveButtonText(R.string.positive_button).setMessage("当前用户已经不存在，请退出登录").show();
                                }else if(state == 1) {
                                    SimpleDialogFragment.createBuilder(ChangePwdActivity.this, getSupportFragmentManager()).setTitle(R.string.dialog_title).setMessage("修改密码成功").setPositiveButtonText(R.string.positive_button).setRequestCode(REQUEST_CHANGE_PWD_SUCCESS).show();
                                }else if(state == 2) {
                                    SimpleDialogFragment.createBuilder(ChangePwdActivity.this, getSupportFragmentManager()).setTitle(R.string.dialog_title).setPositiveButtonText(R.string.positive_button).setMessage("您输入的原密码有误，请重新输入").show();
                                }else if(state == 3) {
                                    SimpleDialogFragment.createBuilder(ChangePwdActivity.this, getSupportFragmentManager()).setTitle(R.string.dialog_title).setPositiveButtonText(R.string.positive_button).setMessage("发生未知错误，修改密码失败").show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }
                    });
                }else {
                    showShortToast("两次输入的新密码不一致，请重新输入");
                }
            }else {
                showShortToast("不能有空");
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
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


    public void back(View view) {
            ChangePwdActivity.this.finish();
    }
}
