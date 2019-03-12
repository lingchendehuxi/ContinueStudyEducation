package cn.incongress.continuestudyeducation.service;

/**
 * Created by Jacky on 2017/4/26.
 */

public interface IRequestManager {
    void get(String url, IRequestCallback callback);
    void post(String url, String responseBodyJson, IRequestCallback callback);
}
