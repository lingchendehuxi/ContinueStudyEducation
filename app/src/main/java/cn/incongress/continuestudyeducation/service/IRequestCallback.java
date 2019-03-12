package cn.incongress.continuestudyeducation.service;

/**
 * Created by Jacky on 2017/4/26.
 */

public interface IRequestCallback {
    void onSuccess(String response);
    void onFailure(Throwable throwable);
}
