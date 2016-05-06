package cn.incongress.continuestudyeducation.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.ISimpleDialogListener;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.activity.ChangePwdActivity;
import cn.incongress.continuestudyeducation.activity.EditPersonInfoActivity;
import cn.incongress.continuestudyeducation.activity.LoginActivity;
import cn.incongress.continuestudyeducation.activity.ProvinceActivity;
import cn.incongress.continuestudyeducation.activity.WebViewActivity;
import cn.incongress.continuestudyeducation.base.BaseFragment;
import cn.incongress.continuestudyeducation.bean.Constant;
import cn.incongress.continuestudyeducation.bean.PersonBean;
import cn.incongress.continuestudyeducation.bean.PersonInfoEvent;
import cn.incongress.continuestudyeducation.service.CMEHttpClientUsage;
import cn.incongress.continuestudyeducation.uis.CircleImageView;
import cn.incongress.continuestudyeducation.uis.IconChoosePopupWindow;
import cn.incongress.continuestudyeducation.uis.StringUtils;
import cn.incongress.continuestudyeducation.utils.ActivityUtils;
import cn.incongress.continuestudyeducation.utils.LogUtils;
import cz.msebera.android.httpclient.Header;
import de.greenrobot.event.EventBus;


public class MeFragment extends BaseFragment implements View.OnClickListener,ISimpleDialogListener {
    private static MeFragment mInstance;
    private SwipeRefreshLayout mRefreshLayout;
    private CircleImageView mIvIcon;
    private TextView mTvName,mTvSex, mTvEmail, mTvBirthday, mTvMobile,
                mTvZhiCheng, mTvZhiWu, mTvProvinceCity, mTvHospital, mTvKeshi, mTvZipCode,mTvAddress;
    private TextView mTvProvinceLocation,mTvHospitalLevel, mTvAjustKeshi, mTvPhone, mTvEducation;
    private TextView mTvFeedback;
    private IconChoosePopupWindow mIconChoosePopupWindow;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int ALBUM_IMAGE_ACTIVITY_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private Uri fileUri;

    /** 头像的本地路径  **/
    private String mLocalFilePath = "";
    /** 头像上传后的地址 **/
    private String mUploadFilePath = "";

    private PersonBean mPersonBean;

    private static final String TAG = "MeFragment";
    private static final int REQUEST_CODE = 1;

    public MeFragment(){}

    @Override
    protected void handleDetailMsg(Message msg) {
        int target = msg.what;

        if(target == LOAD_DATA_COMPLETE) {
            ImageLoader.getInstance().displayImage(mPersonBean.getImgUrl(), mIvIcon);
            mTvName.setText(mPersonBean.getTrueName());
            mTvSex.setText(mPersonBean.getSex());
            mTvEmail.setText(mPersonBean.getEmail());
            mTvBirthday.setText(mPersonBean.getBirthYear()+"-" + mPersonBean.getBirthMonth());
            mTvMobile.setText(mPersonBean.getMobilePhone());
            mTvZhiCheng.setText(mPersonBean.getZhicheng());
            mTvZhiWu.setText(mPersonBean.getZhiwu());
            mTvHospital.setText(mPersonBean.getHospital());
            mTvKeshi.setText((mPersonBean.getKeshi()));
            mTvZipCode.setText(mPersonBean.getZip());
            mTvAddress.setText(mPersonBean.getAddress());
            mTvProvinceLocation.setText(mPersonBean.getArea());
            mTvHospitalLevel.setText(mPersonBean.getUnitlevel());
            mTvAjustKeshi.setText(mPersonBean.getJzDep());
            mTvPhone.setText(mPersonBean.getTelPhone());
            mTvEducation.setText(mPersonBean.getEducation());

            mTvProvinceCity.setText(mPersonBean.getProvince() + " " + mPersonBean.getCity());
            mRefreshLayout.setRefreshing(false);

            updateSPValues();
        }else if(target == LOAD_REFRESH_SHOW) {
            setRefreshing(mRefreshLayout,true,true);
        }else if(target == UPLOAD_IMGURL_SUCCESS && !StringUtils.isEmpty(mUploadFilePath)) {
            setSPValue(Constant.SP_PERSON_LOGO, mUploadFilePath);
            ImageLoader.getInstance().displayImage(mUploadFilePath, mIvIcon);
            showShortToast(getString(R.string.upload_icon_success));

            //更新个人信息-头像
            updatePersonInfo();
        }
    }
    //将个人信息保存到本地sp文件中
    private void updateSPValues() {
        setSPValue(Constant.SP_PERSON_LOGO, mPersonBean.getImgUrl());
        setSPValue(Constant.SP_PERSON_NAME, mPersonBean.getTrueName());
        setSPValue(Constant.SP_PERSON_EMAIL, mPersonBean.getEmail());
        setSPValue(Constant.SP_PERSON_BIRTHDAY_YEAR, mPersonBean.getBirthYear());
        setSPValue(Constant.SP_PERSON_BIRTHDAY_MONTH, mPersonBean.getBirthMonth());
        setSPValue(Constant.SP_PERSON_MOBILE, mPersonBean.getMobilePhone());

        setSPValue(Constant.SP_ZHICHENG, mPersonBean.getZhicheng());
        setSPValue(Constant.SP_ZHIWU, mPersonBean.getZhiwu());
        setSPValue(Constant.SP_HOSPITAL, mPersonBean.getHospital());
        setSPValue(Constant.SP_KESHI, mPersonBean.getKeshi());
        setSPValue(Constant.SP_ZIP_CODE, mPersonBean.getZip());
        setSPValue(Constant.SP_ADDRESS, mPersonBean.getAddress());

        setSPValue(Constant.SP_PROVINCE_LOCATION, mPersonBean.getArea());
        setSPValue(Constant.SP_HOSPITAL_LEVEL, mPersonBean.getUnitlevel());
        setSPValue(Constant.SP_ADJUST_KESHI, mPersonBean.getJzDep());
        setSPValue(Constant.SP_PHONE, mPersonBean.getTelPhone());
        setSPValue(Constant.SP_EDUCATION, mPersonBean.getEducation());
    }

    public static MeFragment getInstance() {
        if(mInstance == null) {
            mInstance = new MeFragment();
        }
    	return mInstance;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);

        mHandler.sendEmptyMessageDelayed(LOAD_REFRESH_SHOW, 100);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    //接收信息,并更新个人信息
    public void onEventMainThread(PersonInfoEvent event) {
        int type = event.getType();
        String content = event.getContent();

        if(type == EditPersonInfoActivity.TYPE_ZHICHENG) {
            mTvZhiCheng.setText(content);
        } else if (type == EditPersonInfoActivity.TYPE_ZHIWU) {
            mTvZhiWu.setText(content);
        }else if(type == EditPersonInfoActivity.TYPE_HOSPITAL) {
            mTvHospital.setText(content);
        }else if(type == EditPersonInfoActivity.TYPE_KESHI) {
            mTvKeshi.setText(content);
        }else if(type == EditPersonInfoActivity.TYPE_ZIPCODE) {
            mTvZipCode.setText(content);
        }else if(type == EditPersonInfoActivity.TYPE_ADDRESS) {
            mTvAddress.setText(content);
        }else if(type == EditPersonInfoActivity.TYPE_PROVINCE_CITY) {
            mTvProvinceCity.setText(content);
        }else if(type == EditPersonInfoActivity.TYPE_PROVINCE_LOCATION) {
            mTvProvinceLocation.setText(content);
        }else if(type == EditPersonInfoActivity.TYPE_HOSPITAL_LEVEL) {
            mTvHospitalLevel.setText(content);
        }else if(type == EditPersonInfoActivity.TYPE_ADJUST_KESHI) {
            mTvAjustKeshi.setText(content);
        }else if(type == EditPersonInfoActivity.TYPE_PHONE) {
            mTvPhone.setText(content);
        }else if(type == EditPersonInfoActivity.TYPE_EDUCATION) {
            mTvEducation.setText(content);
        }

        //更新个人信息
        updatePersonInfo();
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_me, null);
        mIvIcon = (CircleImageView) view.findViewById(R.id.iv_person_logo);
        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mTvSex = (TextView) view.findViewById(R.id.tv_sex);
        mTvEmail = (TextView) view.findViewById(R.id.tv_email);
        mTvBirthday = (TextView) view.findViewById(R.id.tv_birthday);
//        mTvCertification = (TextView) view.findViewById(R.id.tv_certification);
        mTvMobile = (TextView) view.findViewById(R.id.tv_mobile);
        mTvZhiCheng = (TextView) view.findViewById(R.id.tv_zhicheng);
        mTvZhiWu = (TextView) view.findViewById(R.id.tv_zhiwu);
        mTvProvinceCity = (TextView) view.findViewById(R.id.tv_province_city);
        mTvHospital = (TextView) view.findViewById(R.id.tv_hospital);
        mTvKeshi = (TextView) view.findViewById(R.id.tv_keshi);
        mTvZipCode = (TextView) view.findViewById(R.id.tv_zip_code);
        mTvAddress = (TextView) view.findViewById(R.id.tv_address);
        mTvFeedback = (TextView) view.findViewById(R.id.tv_feed_back);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

        mTvProvinceLocation = (TextView) view.findViewById(R.id.tv_province_location);
        mTvHospitalLevel = (TextView) view.findViewById(R.id.tv_hospital_level);
        mTvAjustKeshi = (TextView) view.findViewById(R.id.tv_adjust_keshi);
        mTvPhone = (TextView) view.findViewById(R.id.tv_phone);
        mTvEducation = (TextView) view.findViewById(R.id.tv_education);

        mRefreshLayout.setColorSchemeResources(R.color.button_background2);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initDatas();
            }
        });

        initEvents(view);
        return view;
	}


    /**
     * 监听
     * @param view
     */
    private void initEvents(View view) {
        view.findViewById(R.id.rl_icon).setOnClickListener(this);
        view.findViewById(R.id.ll_zhicheng).setOnClickListener(this);
        view.findViewById(R.id.ll_zhiwu).setOnClickListener(this);
        view.findViewById(R.id.ll_province_city).setOnClickListener(this);
        view.findViewById(R.id.ll_hospital).setOnClickListener(this);
        view.findViewById(R.id.ll_keshi).setOnClickListener(this);
        view.findViewById(R.id.ll_zip_code).setOnClickListener(this);
        view.findViewById(R.id.ll_address).setOnClickListener(this);
        view.findViewById(R.id.tv_change_pwd).setOnClickListener(this);
        view.findViewById(R.id.tv_log_out).setOnClickListener(this);
        mTvFeedback.setOnClickListener(this);
        view.findViewById(R.id.ll_province_location).setOnClickListener(this);
        view.findViewById(R.id.ll_hospital_level).setOnClickListener(this);
        view.findViewById(R.id.ll_adjust_keshi).setOnClickListener(this);
        view.findViewById(R.id.ll_phone).setOnClickListener(this);
        view.findViewById(R.id.ll_education).setOnClickListener(this);

    }

    private void initDatas() {
        CMEHttpClientUsage.getInstanse().doGetUserInfoNew(getSPValue(Constant.SP_USER_UUID), new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogUtils.i(TAG, response.toString());
                try {
                    int state = response.getInt("state");
                    if (state == 1) {
                        Gson gson = new Gson();
                        mPersonBean = gson.fromJson(response.toString(), PersonBean.class);
                        mHandler.sendEmptyMessage(LOAD_DATA_COMPLETE);
                    } else {
                        showShortToast(R.string.fail_to_get_person_ata);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_icon:
                initPopupWindow();
                mIconChoosePopupWindow.showAtLocation(mRefreshLayout, Gravity.BOTTOM, 0, 0);
                lightOff();
                break;
            case R.id.ll_zhicheng:
                EditPersonInfoActivity.startEditPersonInfoActivity(getActivity(), EditPersonInfoActivity.TYPE_ZHICHENG);
                break;
            case R.id.ll_zhiwu:
                EditPersonInfoActivity.startEditPersonInfoActivity(getActivity(), EditPersonInfoActivity.TYPE_ZHIWU);
                break;
            case R.id.ll_province_city:
                startActivity(new Intent(getActivity(), ProvinceActivity.class));
                break;
            case R.id.ll_province_location:
                //区县
                EditPersonInfoActivity.startEditPersonInfoActivity(getActivity(), EditPersonInfoActivity.TYPE_PROVINCE_LOCATION);
                break;
            case R.id.ll_hospital:
                EditPersonInfoActivity.startEditPersonInfoActivity(getActivity(), EditPersonInfoActivity.TYPE_HOSPITAL);
                break;
            case R.id.ll_hospital_level:
                //单位登记
                EditPersonInfoActivity.startEditPersonInfoActivity(getActivity(), EditPersonInfoActivity.TYPE_HOSPITAL_LEVEL);
                break;
            case R.id.ll_keshi:
                EditPersonInfoActivity.startEditPersonInfoActivity(getActivity(), EditPersonInfoActivity.TYPE_KESHI);
                break;
            case R.id.ll_adjust_keshi:
                //校正科室
                EditPersonInfoActivity.startEditPersonInfoActivity(getActivity(), EditPersonInfoActivity.TYPE_ADJUST_KESHI);
                break;
            case R.id.ll_phone:
                //电话
                EditPersonInfoActivity.startEditPersonInfoActivity(getActivity(), EditPersonInfoActivity.TYPE_PHONE);
                break;
            case R.id.ll_education:
                //学历
                EditPersonInfoActivity.startEditPersonInfoActivity(getActivity(), EditPersonInfoActivity.TYPE_EDUCATION);
                break;
            case R.id.ll_zip_code:
                EditPersonInfoActivity.startEditPersonInfoActivity(getActivity(), EditPersonInfoActivity.TYPE_ZIPCODE);
                break;
            case R.id.ll_address:
                EditPersonInfoActivity.startEditPersonInfoActivity(getActivity(), EditPersonInfoActivity.TYPE_ADDRESS);
                break;
            case R.id.tv_change_pwd:
                ChangePwdActivity.startChangePwdActivity(getActivity());
                break;
            case R.id.tv_log_out:
                SimpleDialogFragment.createBuilder(getActivity(),getFragmentManager()).setTitle(R.string.dialog_title).setMessage(R.string.sure_to_log_out).
                        setNegativeButtonText(R.string.negative_button).setPositiveButtonText(R.string.positive_button).setTargetFragment(this,REQUEST_CODE).show();
                break;
            case R.id.tv_feed_back:
                WebViewActivity.startWebViewActivity(getActivity(),getString(R.string.person_url_feed_back, getSPValue(Constant.SP_USER_UUID)), getString(R.string.person_feed_back));
                break;
        }
    }

    private void initPopupWindow() {
        mIconChoosePopupWindow = new IconChoosePopupWindow(getActivity());
        mIconChoosePopupWindow.setAnimationStyle(R.style.icon_popup_window);
        mIconChoosePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });

        mIconChoosePopupWindow.getContentView().findViewById(R.id.tv_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAlbumAction();
            }
        });

        mIconChoosePopupWindow.getContentView().findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTakePhotoAction();
            }
        });

        mIconChoosePopupWindow.getContentView().findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIconChoosePopupWindow.dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPositiveButtonClicked(int requestCode) {
        clearAllSPValue();

        ActivityUtils.finishAll();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @Override
    public void onNegativeButtonClicked(int requestCode) {
    }

    @Override
    public void onNeutralButtonClicked(int requestCode) {
    }

    /**
     * 内容区域变暗
     */
    private void lightOff() {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.3f;
        getActivity().getWindow().setAttributes(lp);
    }

    /**
     * 内容区域变量
     */
    private void lightOn() {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 1.0f;
        getActivity().getWindow().setAttributes(lp);
    }

    /** Create a file Uri for saving an image or video */
    private Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Constant.CACHE_FILE_NAME);
        }else {
            mediaStorageDir = new File(getActivity().getCacheDir(), Constant.CACHE_FILE_NAME);
        }

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.
        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d(Constant.CACHE_FILE_NAME, "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(mIconChoosePopupWindow != null && mIconChoosePopupWindow.isShowing()) {
            mIconChoosePopupWindow.dismiss();
        }

        if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if(resultCode == getActivity().RESULT_OK) {
                mLocalFilePath = getRealPathFromURI(fileUri);

                try {
                    CMEHttpClientUsage.getInstanse().doUploadUserIcon(new File(mLocalFilePath), getSPValue(Constant.SP_USER_UUID), new JsonHttpResponseHandler() {
                        @Override
                        public void onStart() {
                            super.onStart();
                            mProgressDialog = ProgressDialog.show(getActivity(), null, "正在上传头像，请稍等...");
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            mProgressDialog.dismiss();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            LogUtils.i(TAG, "onSuccess" + response.toString());
                            try {
                                int state = response.getInt("state");
                                if (state == 1) {
                                    mUploadFilePath = response.getString("fileUrl");
                                    mHandler.sendEmptyMessage(UPLOAD_IMGURL_SUCCESS);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            LogUtils.i(TAG, "onFailure");
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            LogUtils.i(TAG, "onFailure");
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            LogUtils.i(TAG, "onFailure");
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }else if(resultCode == getActivity().RESULT_CANCELED) {
                showShortToast("操作取消");
            }else {
                showShortToast("获取图片失败");
            }
        }

        if(requestCode == ALBUM_IMAGE_ACTIVITY_REQUEST_CODE) {
            if(resultCode == getActivity().RESULT_OK) {
                try{
                    Uri fileUri = data.getData();
                    mLocalFilePath = cn.incongress.continuestudyeducation.utils.StringUtils.getPath(getActivity(), fileUri);

                    CMEHttpClientUsage.getInstanse().doUploadUserIcon(new File(mLocalFilePath), getSPValue(Constant.SP_USER_UUID), new JsonHttpResponseHandler(){
                        @Override
                        public void onStart() {
                            super.onStart();
                            mProgressDialog = ProgressDialog.show(getActivity(), null, getString(R.string.loading_upload_icon));
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            mProgressDialog.dismiss();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            LogUtils.i(TAG, "onSuccess" + response.toString());
                            try {
                                int state = response.getInt("state");
                                if(state == 1) {
                                    mUploadFilePath = response.getString("fileUrl");
                                    mHandler.sendEmptyMessage(UPLOAD_IMGURL_SUCCESS);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            LogUtils.i(TAG, "onFailure");
                            showShortToast("头像上传失败");
                        }
                    });
                }catch (Exception e) {
                }
            }else if(resultCode == getActivity().RESULT_CANCELED) {
                showShortToast("操作取消");
            }else {
                showShortToast("获取图片失败");
            }
        }
    }

    /**
     * 并不建议这么做，因为这个操作需要查询数据库，另外一种就是直接获取Uri中的内容，也就是图片流
     * @param contentURI
     * @return
     */
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null ) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    //更新个人信息
    private void updatePersonInfo(){
        CMEHttpClientUsage.getInstanse().doUpdatePersonInfoNew(getSPValue(Constant.SP_USER_UUID),getSPValue(Constant.SP_PERSON_LOGO),
                getSPValue(Constant.SP_ZHICHENG), getSPValue(Constant.SP_ZHIWU), getSPValue(Constant.SP_PROVINCE_LOCATION), getSPValue(Constant.SP_HOSPITAL),
                getSPValue(Constant.SP_HOSPITAL_LEVEL), getSPValue(Constant.SP_EDUCATION),getSPValue(Constant.SP_PERSON_BIRTHDAY_YEAR), getSPValue(Constant.SP_PERSON_BIRTHDAY_MONTH),
                getSPValue(Constant.SP_KESHI), getSPValue(Constant.SP_ADJUST_KESHI), getSPValue(Constant.SP_PHONE), getSPValue(Constant.SP_ZIP_CODE), getSPValue(Constant.SP_ADDRESS),
                getSPValue(Constant.SP_PROVINCE_ID), getSPValue(Constant.SP_CITY_ID),Constant.CLIENT_TYPE,  new JsonHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mProgressDialog = ProgressDialog.show(getActivity(),null,"正在更新您的个人信息...");
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
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
    }

    private void callAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "选择"), ALBUM_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private void callTakePhotoAction() {
        Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //将拍照的图片保存到指定的地址，获取的时候就从fileUri中获取，否则因为设定了输出图片的位置，在onActivityResult()方法中，获取到的data是空的
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

}
