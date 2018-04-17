package net.zxw.zxwsdk.okhttp.response;


import android.os.Handler;
import android.os.Looper;

import net.zxw.zxwsdk.adutil.ResponseEntityToModule;
import net.zxw.zxwsdk.okhttp.exception.OkHttpException;
import net.zxw.zxwsdk.okhttp.listener.DisposeDataHandle;
import net.zxw.zxwsdk.okhttp.listener.DisposeDataListener;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * @author zxw
 * @Email 18316275391@163.com
 * @describe 专门处理JSON的回调
 */

public class CommonJsonCallback implements Callback {

    //与服务器返回的字段的一个对应关系
    protected final String RESULT_CODE = "ecode"; // 有返回则对于http请求来说是成功的，但还有可能是业务逻辑上的错误
    protected final int RESULT_CODE_VALUE = 0;
    protected final String ERROR_MSG = "emsg";
    protected final String EMPTY_MSG = "";
    // can has the value of
    // set-cookie2

    /**
     * 自定义异常类型
     */
    protected final int NETWORK_ERROR = -1; // the network relative error
    protected final int JSON_ERROR = -2; // the JSON relative error
    protected final int OTHER_ERROR = -3; // the unknow error

    /**
     * 将其它线程的数据转发到UI线程
     */
    private Handler mDeliveryHandler; //进行消息的转发
    private DisposeDataListener mListener;
    private Class<?> mClass; //需要转化的字节码

    public CommonJsonCallback(DisposeDataHandle handle){
        this.mListener = handle.mListener;
        this.mClass = handle.mClass;
        this.mDeliveryHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 请求失败处理
     * @param call
     * @param e
     */
    @Override
    public void onFailure(Call call, final IOException e) {
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new OkHttpException(NETWORK_ERROR,e));
            }
        });
    }

    //真正的响应处理函数
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
            }
        });
    }

    /**
     * 处理服务器返回的响应数据
     */
    private void handleResponse(Object responseObj) {

        //为了保证代码的健壮性
        if(responseObj == null || responseObj.toString().trim().equals("")){
            mListener.onFailure(new OkHttpException(NETWORK_ERROR,EMPTY_MSG));
            return;
        }
        try{
            JSONObject result = new JSONObject(responseObj.toString());
                //开始尝试解析json
                if(result.has(RESULT_CODE)){
                    //从json对象中处出响应吗，为0则是正确的响应
                    if(result.getInt(RESULT_CODE) == RESULT_CODE_VALUE){
                        //不需要解析，直接返回应用层
                        if(mClass == null){
                            mListener.onSuccess(responseObj);
                        }else{
                            //需要将json对象转化为实体对象
                            Object obj = ResponseEntityToModule.parseJsonObjectToModule(result,mClass);
                            //正确的转为了实体对象
                            if(obj != null){
                                mListener.onSuccess(obj);
                            }else {
                                //返回的json不是合法的json
                                mListener.onFailure(new OkHttpException(JSON_ERROR,EMPTY_MSG));
                            }
                        }
                    }
                else {
                    //将服务器返回的异常回调到应用层去处理
                    mListener.onFailure(new OkHttpException(OTHER_ERROR,result.get(RESULT_CODE)));
                }
            }
        }catch (Exception e){
            mListener.onFailure(new OkHttpException(OTHER_ERROR,e.getMessage()));
        }

    }
}
