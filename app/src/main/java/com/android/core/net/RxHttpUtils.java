package com.android.core.net;

import java.util.Map;
import java.util.TreeMap;
import okhttp3.Interceptor;

/**
 * 网络访问工具类
 * <p>使用方法：
 * 1.在Application中设置baseUrl{@link RxHttpUtils#initBaseUrl(String)}
 * 2.如果需要添加公共的参数，在Application中调用{@link RxHttpUtils#addCommonParamsInterceptor(Interceptor)}设置拦截器即可
 * 3.添加网络拦截器，在Application中调用{@link RxHttpUtils#addNetworkInterceptor(Interceptor)}
 * 3.如果需要特定的url，那么调用{@link RxHttpUtils#init(String)} ,否则调用
 * {@link RxHttpUtils#init()} 即可
 * 4.如果需要添加头部信息，调用{@link RxHttpUtils#addHeader(Map)}
 * </p>
 * Created by tengfei.lv on 2017/1/3.
 */
public class RxHttpUtils {

    /**
     * 全局的baseUrl，在application中设置
     */
    private static String mBaseUrl;
    /**
     * 局部的baseUrl，如果需要，必须每次设置
     */
    private String mUrl = "";
    private static Map<String, Object> mHeaderMaps = new TreeMap<> ();

    /**
     * 添加公共参数的拦截器
     */
    private static Interceptor commonInterceptor;

    /**
     * 网络拦截器
     */
    private static Interceptor netWorkInterceptor;

    /**
     * log拦截器
     */
    private static Interceptor loggingInterceptor;

    private RxHttpUtils () {
    }

    private static class SingleTon {
        private static RxHttpUtils INSTANCE = new RxHttpUtils ();
    }

    /**
     * 设置baseUrl，在请求之前设置即可，一般情况下在application中设置一次即可
     */
    public static RxHttpUtils initBaseUrl (String baseUrl) {
        mBaseUrl = baseUrl;
        return SingleTon.INSTANCE;
    }

    /**
     * 获取类实例
     *
     * @return
     */
    public static RxHttpUtils getInstance () {
        return SingleTon.INSTANCE;
    }

    public RxHttpUtils init () {
        mHeaderMaps.clear ();
        return SingleTon.INSTANCE;
    }

    /**
     * 使用动态的baseUrl
     *
     * @return
     */
    public RxHttpUtils init (String baseUrl) {
        mHeaderMaps.clear ();
        mUrl = baseUrl;
        return SingleTon.INSTANCE;
    }

    /**
     * @param isAddCommon
     *     true,表示添加公共参数，false,不添加
     * @param cls
     *     要返回的接口对象
     *
     * @return
     */
    public <T> T createApi (boolean isAddCommon, Class<T> cls) {

        String url = mBaseUrl;
        if (!"".equals (mUrl)) {
            url = mUrl;
        }
        if (isAddCommon) {
            return RetrofitClient.getInstance (url, loggingInterceptor, mHeaderMaps,
                netWorkInterceptor, commonInterceptor).create (cls);
        } else {
            return RetrofitClient.getInstance (url, loggingInterceptor, mHeaderMaps,
                netWorkInterceptor).create (cls);
        }
    }

    /**
     * @return
     */
    public <T> T createApi (Class<T> cls) {
        return createApi (false, cls);
    }

    /**
     * 添加头部参数
     *
     * @return
     */
    public static RxHttpUtils addHeader (Map<String, Object> headerMaps) {
        mHeaderMaps = headerMaps;
        return SingleTon.INSTANCE;
    }

    /**
     * 添加公共参数的拦截器
     *
     * @return
     */
    public static RxHttpUtils addCommonParamsInterceptor (Interceptor interceptor) {
        commonInterceptor = interceptor;
        return SingleTon.INSTANCE;
    }

    /**
     * 添加网络拦截器
     *
     * @return
     */
    public static RxHttpUtils addNetworkInterceptor (Interceptor interceptor) {
        netWorkInterceptor = interceptor;
        return SingleTon.INSTANCE;
    }

    /**
     * 添加自定义logging
     *
     * @return
     */
    public static RxHttpUtils addLoggingInterceptor (Interceptor interceptor) {
        loggingInterceptor = interceptor;
        return SingleTon.INSTANCE;
    }
}
