package cn.incongress.continuestudyeducation.service;

/**
 * Created by Jacky on 2017/4/26.
 */

public class ReqeustFactory {
    public static IRequestManager getInstance() {
        return RetrofitRequestManager.getInstance();
    }
}
