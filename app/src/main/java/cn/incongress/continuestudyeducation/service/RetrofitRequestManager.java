package cn.incongress.continuestudyeducation.service;

/**
 * Created by Jacky on 2017/4/26.
 */

public class RetrofitRequestManager implements IRequestManager {
    private static RetrofitRequestManager mInstance;

    public static RetrofitRequestManager getInstance() {
        if(mInstance == null)
            mInstance = new RetrofitRequestManager();

        return mInstance;
    }


    @Override
    public void get(String url, IRequestCallback callback) {

    }

    @Override
    public void post(String url, String responseBodyJson, IRequestCallback callback) {

    }
}
