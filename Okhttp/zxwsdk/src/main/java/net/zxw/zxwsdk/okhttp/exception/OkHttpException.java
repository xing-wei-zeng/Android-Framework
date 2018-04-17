package net.zxw.zxwsdk.okhttp.exception;

/**
 * @author zxw
 * @Email 18316275391@163.com
 * @describe 自定义异常类,返回ecode,emsg到业务层
 */

public class OkHttpException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * 异常码
     */
    private int ecode;

    /**
     * 服务异常信息
     */
    private Object emsg;

    public OkHttpException(int ecode, Object emsg) {
        this.ecode = ecode;
        this.emsg = emsg;
    }

    public int getEcode() {
        return ecode;
    }

    public Object getEmsg() {
        return emsg;
    }
}
