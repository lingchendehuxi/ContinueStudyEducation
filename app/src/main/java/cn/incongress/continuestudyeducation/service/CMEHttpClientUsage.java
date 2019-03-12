package cn.incongress.continuestudyeducation.service;

import android.graphics.Paint;
import android.util.JsonToken;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

import cn.incongress.continuestudyeducation.bean.Constant;

/**
 * Created by Jacky on 2015/12/19.
 */
public class CMEHttpClientUsage {
    private static CMEHttpClientUsage mInstance;

    private CMEHttpClientUsage(){}

    public static final CMEHttpClientUsage getInstanse() {
        if(mInstance == null) {
            synchronized (CMEHttpClientUsage.class) {
                if(mInstance == null) {
                    mInstance = new CMEHttpClientUsage();
                }
            }
        }
        return mInstance;
    }

    public void doUserLogin(int proId, String loginName, String password, int clientType, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("proId", proId);
        params.put("loginName", loginName);
        params.put("password", password);
        params.put("clientType", clientType);

        CMEHttpClient.get("userlogin",params,responseHandler);
    }

    public void doFindPassword(int proId, String loginName, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("proId", proId);
        params.put("loginName", loginName);

        CMEHttpClient.get("findPassword",params,responseHandler);
    }

    //找回密码
    public void doUpdatePassword(int proId, String sms, String loginName, String password, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("proId", proId);
        params.put("loginName", loginName);
        params.put("sms", sms);
        params.put("password", password);

        CMEHttpClient.get("updatePassword",params,responseHandler);
    }

    /**
     * 更新密码
     * @param userUuId
     * @param oldPwd
     * @param newPwd
     * @param responseHandler
     */
    public void doSaveNewPassword(String userUuId, String oldPwd, String newPwd ,JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("userUuId", userUuId);
        params.put("oldPassword", oldPwd);
        params.put("newPassword", newPwd);

        CMEHttpClient.get("saveNewPassword",params,responseHandler);
    }

    public void doGetSms(int proId, String mobilePhone, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("proId", proId);
        params.put("mobilePhone", mobilePhone);

        CMEHttpClient.get("getSms",params,responseHandler);
    }

    public void doCheckLogin(String userUuId, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("userUuId", userUuId);

        CMEHttpClient.get("checkLogin",params,responseHandler);
    }

    public void doGetCourse(int proId, String uuId, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("proId", proId);
        params.put("userUuId",uuId);

        //CMEHttpClient.get("getCourse",params,responseHandler);
        CMEHttpClient.post("getCourseListNew",params,responseHandler);
    }

    public void doGetCoursewareList(int proId,String cuuId, String userUuId,int courseType, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("proId", proId);
        params.put("cuuId",cuuId);
        params.put("userUuId", userUuId);
        params.put("courseType",courseType);

        CMEHttpClient.post("getPlateList",params,responseHandler);
    }
    public void doGetZhengShuList(int proId, String userUuId, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("proId", proId);
        params.put("userUuId", userUuId);
        CMEHttpClient.post("getZhengShuList",params,responseHandler);
    }
    public void doGetCourseware(String puuId, String cwuuId,String userId,int courseType ,JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("puuId", puuId);
        params.put("cwuuId", cwuuId);
        params.put("userUuId",userId);
        params.put("courseType",courseType );
        CMEHttpClient.post("continueToLearn",params,responseHandler);
    }

    public void doGetCourseInfo(String cuuId, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("cuuId", cuuId);

        CMEHttpClient.post("getCourseInfo", params, responseHandler);
    }

    public void doGetNotifyList(int proId, int notifyId, int count, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("proId", proId);
        params.put("notifyId", notifyId);
        params.put("count", count);

        CMEHttpClient.get("getNotifyList", params, responseHandler);
    }

    public void doGetUserInfo(String userUuid, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("userUuId", userUuid);

        CMEHttpClient.get("getUserInfo", params, responseHandler);
    }

    public void doGetUserInfoNew(String userUuid, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("userUuId", userUuid);

        CMEHttpClient.get("getUserInfoNew", params, responseHandler);
    }

    public void doUploadUserIcon(File file, String userUuId, JsonHttpResponseHandler responseHandler) throws FileNotFoundException {
        RequestParams params = new RequestParams();
        params.put("uploadFile", file);
        params.put("userUuId", userUuId);

        CMEHttpClient.post1("uploadFile", params, responseHandler);
    }

    /**
     * String userUuId,
     * String imgUrl
     * String zhicheng
     * String zhiwu
     * String area
     * String danwei
     * String unitlevel
     * String education
     * String birthYear
     * String birthMonth
     * String keshi
     * String jzDep
     * String telPhone
     * String zip
     * String address,
     * int provinceId
     * int cityId
     * String clientType
     */

    public void doUpdatePersonInfoNew(String userUuId, String imgUrl, String zhicheng, String zhiwu,
                                      String area, String danwei, String unitLevel, String education,
                                      String birthYear, String birthMonth, String keshi, String jzDep,
                                      String telPhone, String zip, String address, String provinceId, String cityId,
                                      int clientType,  JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("userUuId", userUuId);
        params.put("imgUrl", imgUrl);
        params.put("zhicheng", zhicheng);
        params.put("zhiwu", zhiwu);
        params.put("danwei", danwei);
        params.put("keshi", keshi);
        params.put("zip", zip);
        params.put("address", address);
        params.put("provinceId", provinceId);
        params.put("cityId", cityId);
        params.put("area", area);
        params.put("unitlevel", unitLevel);
        params.put("education", education);
        params.put("birthYear", birthYear);
        params.put("birthMonth", birthMonth);
        params.put("jzDep", jzDep);
        params.put("telPhone", telPhone);
        params.put("clientType", clientType);

        CMEHttpClient.get("updateUserInfoNew", params, responseHandler);
    }
    public void doUpdatePersonInfo(String userUuId,String imgUrl, String zhicheng, String zhiwu, String hospital,
                                  String keshi, String zip, String address, String recipientsName,
                                  String recipientsMPhone, String recipientsZip, String recipientAddress, String provinceId, String cityId,
                                   JsonHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.put("userUuId", userUuId);
        params.put("imgUrl", imgUrl);
        params.put("zhicheng", zhicheng);
        params.put("zhiwu", zhiwu);
        params.put("hospital", hospital);
        params.put("keshi", keshi);
        params.put("zip", zip);
        params.put("address", address);
        params.put("provinceId", provinceId);
        params.put("cityId", cityId);
        params.put("recipients", recipientsName);
        params.put("recipientsMphone", recipientsMPhone);
        params.put("recipientsZip", recipientsZip);
        params.put("recipientsAddress", recipientAddress);

        CMEHttpClient.get("updateUserInfo", params, responseHandler);
    }

    public void doSaveCoursewareState(String cwUuId, String userUuId, JsonHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.put("cwUuId", cwUuId);
        params.put("userUuId", userUuId);

        CMEHttpClient.get1("saveCoursewareState", params, responseHandler);
    }

    //获取提问借口
    public void doGetQuestionList(int questionId, int count, String userUuId, String cwuuId, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("questionId", questionId);
        params.put("cwuuId", cwuuId);
        params.put("userUuId", userUuId);
        params.put("count", count);

        CMEHttpClient.get("getQuestionList", params, responseHandler);
    }

    //提问接口
    public void doSaveQuestion(String cwUuId, String userUuId, String content, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("cwUuId", cwUuId);
        params.put("userUuId", userUuId);
        params.put("content", content);

        CMEHttpClient.get("saveQuestion", params, responseHandler);
    }

    /**
     * 获取省
     */
    public void doGetProvinceList(JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        CMEHttpClient.get("getProvinceList", params, responseHandler);
    }

    /**
     * 获取市
     */
    public void doGetCityList(int provinceId, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("provinceId", provinceId);
        CMEHttpClient.get("getCityList", params, responseHandler);
    }

    /**
     * 退出登录调用(我下线了)
     *
     * @param userUuId
     * @param responseHandler
     */
    public void doLoginOut(String userUuId, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("userUuId", userUuId);
        CMEHttpClient.get("loginOut", params, responseHandler);
    }

    /**
     * 登录调用(我上线了)
     *
     * String userUuId,int clientType,String token
     * @param userUuId
     * @param responseHandler
     */
    public void doLoginIn(String userUuId,JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("userUuId", userUuId);
        params.put("clientType", Constant.CLIENT_TYPE);
        params.put("token","");
        CMEHttpClient.get("loginIn", params, responseHandler);
    }

}
