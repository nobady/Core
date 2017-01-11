package com.android.core.net;

/**
 * Created by dongke.li on 2016/8/15.
 * 自定义的异常类
 */
public class RetrofitException extends RuntimeException {
    public static final int NETWORK_BUSY_OR_ERROR=2;//网络拥堵或者网络异常
    public static final int USERNAME_OR_PASSWORD_ERROR=3;//用户名或者密码错误
    public static final int NO_PERSON=4;//查无此人
    public static final int NO_STUDENT_IN_DORM=5;//当前宿舍无学生
    public static final int NO_PERMISSION=6;//抱歉，您无此权限
    public static final int NOT_IN_BATCH=7;//您不在当前批次
    public static final int PARAMETER_VALIDATE_FAIL=99;//参数认证失败
    public static final int NO_NETWORK_CONNECTED=100;//网络没有连接
    private int stateCode;
    private String msg;

    public RetrofitException() {
    }

    public RetrofitException(int stateCode, String msg) {
        this.stateCode = stateCode;
        this.msg = msg;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
