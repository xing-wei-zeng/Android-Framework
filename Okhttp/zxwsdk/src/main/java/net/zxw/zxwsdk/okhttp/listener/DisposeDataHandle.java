package net.zxw.zxwsdk.okhttp.listener;

/**
 * @author zxw
 * @Email 18316275391@163.com
 * @describe TODO
 */

public class DisposeDataHandle {
    public DisposeDataListener mListener = null;
    public Class<?> mClass = null;

    public DisposeDataHandle(DisposeDataListener listener){
        this.mListener = listener;
    }

    public DisposeDataHandle(DisposeDataListener listener,Class<?> clazz){
        this.mListener = listener;
        this.mClass = clazz;
    }
}
