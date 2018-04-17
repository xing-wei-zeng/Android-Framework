package net.zxw.zxwsdk.okhttp;

import net.zxw.zxwsdk.okhttp.https.HttpsUtils;
import net.zxw.zxwsdk.okhttp.response.CommonJsonCallback;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author zxw
 * @Email 18316275391@163.com
 * @describe 用来发送get, post请求的工具类，包括设置一些请求的共用参数
 */

public class CommonOkHttpClient {

    private static final int TIME_OUT = 30;//超时参数
    private static OkHttpClient mOkHttpClient;

    //为client去配置参数
    static {
        //创建cilent对象的构建者
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        //为构建者填充超时时间
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(TIME_OUT,TimeUnit.SECONDS);

        okHttpClientBuilder.followRedirects(true);

        //https支持
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        okHttpClientBuilder.sslSocketFactory(HttpsUtils.initSSLSocketFactory(), HttpsUtils.initTrustManager());

        //生成client对象
        mOkHttpClient = okHttpClientBuilder.build();
    }

    /**
     * 发送具体的http/https请求
     * @param request
     * @param commCallback
     * @return Call
     */
    public static Call sendRequest(Request request, CommonJsonCallback commCallback){
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(commCallback);
        return call;
    }
}
