package net.zxw.zxwsdk;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import net.zxw.zxwsdk.okhttp.CommonOkHttpClient;
import net.zxw.zxwsdk.okhttp.listener.DisposeDataHandle;
import net.zxw.zxwsdk.okhttp.listener.DisposeDataListener;
import net.zxw.zxwsdk.okhttp.request.CommonRequest;
import net.zxw.zxwsdk.okhttp.response.CommonJsonCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author zxw
 * @Email 18316275391@163.com
 * @describe TODO
 */

public class test extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
    /**
     * 用okhttp发送一个最基本的请求
     */
    private void sendRequest(){

    }

    private void test() {
        CommonOkHttpClient.sendRequest(CommonRequest.createGetRequest("https://www.imooc.com", null),
                new CommonJsonCallback(new DisposeDataHandle(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {

                    }

                    @Override
                    public void onFailure(Object reasonObj) {

                    }
                })));
    }
}
