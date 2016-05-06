package cn.incongress.continuestudyeducation.activity;

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

import com.avast.android.dialogs.fragment.SimpleDialogFragment;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.base.BaseActivity;
import cn.incongress.continuestudyeducation.bean.Constant;
import cn.incongress.continuestudyeducation.bean.PersonInfoEvent;
import cn.incongress.continuestudyeducation.uis.StringUtils;
import cn.incongress.continuestudyeducation.utils.LogUtils;
import de.greenrobot.event.EventBus;

/**
 * Created by Jacky on 2015/12/25.
 */
public class EditPersonInfoActivity extends BaseActivity{
    private static final String TAG = "EditPersonInfoActivity";
    private int mCurrentType = TYPE_ZHICHENG;

    private static final String INFO_TYPE = "info_type";

    public static final int TYPE_ZHICHENG = 1;
    public static final int TYPE_ZHIWU = 2;
    public static final int TYPE_HOSPITAL = 3;
    public static final int TYPE_KESHI = 4;
    public static final int TYPE_ZIPCODE = 5;
    public static final int TYPE_ADDRESS = 6;
    public static final int TYPE_RECIEPIENT_NAME = 7;
    public static final int TYPE_RECIEPIENT_MOBILE = 8;
    public static final int TYPE_RECIEPIENT_ZIPCODE = 9;
    public static final int TYPE_RECIEPIENT_ADDRESS = 10;
    public static final int TYPE_PROVINCE_CITY = 11;
    public static final int TYPE_PROVINCE_LOCATION = 12;
    public static final int TYPE_HOSPITAL_LEVEL = 13;
    public static final int TYPE_ADJUST_KESHI = 14;
    public static final int TYPE_PHONE = 15;
    public static final int TYPE_EDUCATION = 16;

    private EditText mEtInfo;

    public static final void startEditPersonInfoActivity(Context ctx, int infoType) {
        Intent intent = new Intent();
        intent.setClass(ctx, EditPersonInfoActivity.class);
        intent.putExtra(INFO_TYPE, infoType);
        ctx.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_person_info);
        mCurrentType = getIntent().getIntExtra(INFO_TYPE, TYPE_ZHICHENG);

        LogUtils.i(TAG, "" + mCurrentType);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mEtInfo = getViewById(R.id.et_info);

        if(mCurrentType == TYPE_ZHICHENG) {
            getSupportActionBar().setTitle(R.string.person_zhicheng);
            mEtInfo.setText(getSPValue(Constant.SP_ZHICHENG));
        }else if(mCurrentType == TYPE_ZHIWU) {
            getSupportActionBar().setTitle(R.string.person_zhiwu);
            mEtInfo.setText(getSPValue(Constant.SP_ZHIWU));
        }else if(mCurrentType == TYPE_HOSPITAL) {
            getSupportActionBar().setTitle(R.string.person_hospital);
            mEtInfo.setText(getSPValue(Constant.SP_HOSPITAL));
        }else if(mCurrentType == TYPE_KESHI) {
            getSupportActionBar().setTitle(R.string.person_keshi);
            mEtInfo.setText(getSPValue(Constant.SP_KESHI));
        }else if(mCurrentType == TYPE_ZIPCODE) {
            getSupportActionBar().setTitle(R.string.person_zip_code);
            mEtInfo.setText(getSPValue(Constant.SP_ZIP_CODE));
        }else if(mCurrentType == TYPE_ADDRESS) {
            getSupportActionBar().setTitle(R.string.person_address);
            mEtInfo.setText(getSPValue(Constant.SP_ADDRESS));
        }else if(mCurrentType == TYPE_RECIEPIENT_NAME) {
            getSupportActionBar().setTitle(R.string.person_recipient_name);
            mEtInfo.setText(getSPValue(Constant.SP_RECIPIENT_NAME));
        }else if(mCurrentType == TYPE_RECIEPIENT_MOBILE) {
            getSupportActionBar().setTitle(R.string.person_recipient_mobile);
            mEtInfo.setText(getSPValue(Constant.SP_RECIPIENT_MOBILE));
        }else if(mCurrentType == TYPE_RECIEPIENT_ZIPCODE) {
            getSupportActionBar().setTitle(R.string.person_recipient_zip_code);
            mEtInfo.setText(getSPValue(Constant.SP_RECIPIENT_ZIP_CODE));
        }else if(mCurrentType == TYPE_RECIEPIENT_ADDRESS) {
            getSupportActionBar().setTitle(R.string.person_recipient_address);
            mEtInfo.setText(getSPValue(Constant.SP_RECIPIENT_ADDRESS));
        } else if(mCurrentType == TYPE_PROVINCE_LOCATION) {
            getSupportActionBar().setTitle(R.string.person_province_location);
            mEtInfo.setText(getSPValue(Constant.SP_PROVINCE_LOCATION));
        } else if(mCurrentType == TYPE_HOSPITAL_LEVEL) {
            getSupportActionBar().setTitle(R.string.person_hospital_level);
            mEtInfo.setText(getSPValue(Constant.SP_HOSPITAL_LEVEL));
        } else if(mCurrentType == TYPE_ADJUST_KESHI) {
            getSupportActionBar().setTitle(R.string.person_adjust_keshi);
            mEtInfo.setText(getSPValue(Constant.SP_ADJUST_KESHI));
        }else if(mCurrentType == TYPE_PHONE) {
            getSupportActionBar().setTitle(R.string.person_phone);
            mEtInfo.setText(getSPValue(Constant.SP_PHONE));
        }else if(mCurrentType == TYPE_EDUCATION) {
            getSupportActionBar().setTitle(R.string.person_education);
            mEtInfo.setText(getSPValue(Constant.SP_EDUCATION));
        }

        mEtInfo.setSelection(mEtInfo.getText().length());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.save,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.person_info_save:
                if(StringUtils.isBlank(mEtInfo.getText().toString())) {
                    showShortToast(getString(R.string.person_edit_info_tips));
                    break;
                }
                if(mCurrentType == TYPE_ZHICHENG) {
                    setSPValue(Constant.SP_ZHICHENG, mEtInfo.getText().toString().trim());
                }else if(mCurrentType == TYPE_ZHIWU) {
                    setSPValue(Constant.SP_ZHIWU, mEtInfo.getText().toString().trim());
                }else if(mCurrentType == TYPE_HOSPITAL) {
                    setSPValue(Constant.SP_HOSPITAL, mEtInfo.getText().toString().trim());
                }else if(mCurrentType == TYPE_KESHI) {
                    setSPValue(Constant.SP_KESHI, mEtInfo.getText().toString().trim());
                }else if(mCurrentType == TYPE_ZIPCODE) {
                    String zipCode = mEtInfo.getText().toString().trim();
                    //邮编验证
                    if(cn.incongress.continuestudyeducation.utils.StringUtils.isZipCode(zipCode)) {
                        setSPValue(Constant.SP_ZIP_CODE, zipCode);
                    }else {
                        SimpleDialogFragment.createBuilder(mContext,getSupportFragmentManager()).setTitle(R.string.dialog_title).setMessage("您输入的邮编格式有误,请重新输入").setPositiveButtonText(R.string.positive_button).show();
                        break;
                    }
                }else if(mCurrentType == TYPE_ADDRESS) {
                    setSPValue(Constant.SP_ADDRESS, mEtInfo.getText().toString().trim());
                }else if(mCurrentType == TYPE_RECIEPIENT_NAME) {
                    setSPValue(Constant.SP_RECIPIENT_NAME, mEtInfo.getText().toString().trim());
                }else if(mCurrentType == TYPE_RECIEPIENT_MOBILE) {
                    String mobile = mEtInfo.getText().toString().trim();
                    if(cn.incongress.continuestudyeducation.utils.StringUtils.isMobileNO(mobile)) {
                        setSPValue(Constant.SP_RECIPIENT_MOBILE, mobile);
                    }else {
                        SimpleDialogFragment.createBuilder(mContext,getSupportFragmentManager()).setTitle(R.string.dialog_title).setMessage("您输入的手机格式有误,请重新输入").setPositiveButtonText(R.string.positive_button).show();
                        break;
                    }
                }else if(mCurrentType == TYPE_RECIEPIENT_ZIPCODE) {
                    String zipCode = mEtInfo.getText().toString().trim();
                    //邮编验证
                    if(cn.incongress.continuestudyeducation.utils.StringUtils.isZipCode(zipCode)) {
                        setSPValue(Constant.SP_RECIPIENT_ZIP_CODE, mEtInfo.getText().toString().trim());
                    }else {
                        SimpleDialogFragment.createBuilder(mContext,getSupportFragmentManager()).setTitle(R.string.dialog_title).setMessage("您输入的邮编格式有误,请重新输入").setPositiveButtonText(R.string.positive_button).show();
                        break;
                    }
                }else if(mCurrentType == TYPE_RECIEPIENT_ADDRESS) {
                    setSPValue(Constant.SP_RECIPIENT_ADDRESS, mEtInfo.getText().toString().trim());
                }else if(mCurrentType == TYPE_PROVINCE_LOCATION) {
                    setSPValue(Constant.SP_PROVINCE_LOCATION, mEtInfo.getText().toString().trim());
                }else if(mCurrentType == TYPE_PROVINCE_LOCATION) {
                    setSPValue(Constant.SP_PROVINCE_LOCATION, mEtInfo.getText().toString().trim());
                }else if(mCurrentType == TYPE_HOSPITAL_LEVEL) {
                    setSPValue(Constant.SP_HOSPITAL_LEVEL, mEtInfo.getText().toString().trim());
                }else if(mCurrentType == TYPE_ADJUST_KESHI) {
                    setSPValue(Constant.SP_ADJUST_KESHI, mEtInfo.getText().toString().trim());
                }else if(mCurrentType == TYPE_PHONE) {
                    setSPValue(Constant.SP_PHONE, mEtInfo.getText().toString().trim());
                }else if(mCurrentType == TYPE_EDUCATION) {
                    setSPValue(Constant.SP_EDUCATION, mEtInfo.getText().toString().trim());
                }
                showShortToast("保存成功");
                this.finish();
                EventBus.getDefault().post(new PersonInfoEvent(mCurrentType, mEtInfo.getText().toString().trim()));
                break;
            case  android.R.id.home:
                this.finish();
                break;
        }

        return true;
    }
}
