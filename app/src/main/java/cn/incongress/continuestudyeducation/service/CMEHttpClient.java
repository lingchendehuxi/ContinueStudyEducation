package cn.incongress.continuestudyeducation.service;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cn.incongress.continuestudyeducation.bean.Constant;

/**
 * Created by Jacky on 2015/12/19.
 */
public class CMEHttpClient {
    //private static final String BASE_URL = "http://192.168.0.123:8080/CMEApi/";
    //   private static final String BASE_URL = "http://114.80.201.49:8090/CMEApi/";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.get(Constant.BASE_URL_MAIN +url,params,responseHandler);
    }
    public static void get1(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.get(Constant.BASE_URL_MAIN+url,params,responseHandler);
    }
    public static void post(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        String s = getAbsoluteUrl(url);
        client.post(s, params, responseHandler);
    }
    public static void post1(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.post(Constant.BASE_URL_API+url,params,responseHandler);
    }
    private static String getAbsoluteUrl(String relativeUrl) {
        return Constant.BASE_URL_API + relativeUrl;
    }
}
