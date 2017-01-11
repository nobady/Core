package com.android.core.net;

import java.util.Map;
import java.util.TreeMap;
import okhttp3.Interceptor;

/**
 * 网络访问工具类
 * <p>使用方法：
 * 1.在Application中设置baseUrl{@link RxHttpUtils#initBaseUrl(String)}
 * 2.如果需要添加公共的参数，在Application中调用{@link RxHttpUtils#setCommonParamsInterceptor(Interceptor)}设置拦截器即可
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
    private static Interceptor mInterceptor;

    private RxHttpUtils () {
    }

    private static class SingleTon {
        private static RxHttpUtils INSTANCE = new RxHttpUtils ();
    }

    /**
     * 设置baseUrl，在请求之前设置即可，一般情况下在application中设置一次即可
     */
    public static void initBaseUrl (String baseUrl) {
        mBaseUrl = baseUrl;
    }

    /**
     * 获取类实例
     *
     * @return
     */
    public static RxHttpUtils getInstance () {
        return SingleTon.INSTANCE;
    }

    public RxHttpUtils init(){
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

    public <T> T createApi (final Class<T> cls) {

        String url = mBaseUrl;
        if (!"".equals (mUrl)) {
            url = mUrl;
        }
        return RetrofitClient.getInstance (url, mHeaderMaps).create (cls);
    }

    /**
     * 添加头部参数
     *
     * @return
     */
    public RxHttpUtils addHeader (Map<String, Object> headerMaps) {
        mHeaderMaps = headerMaps;
        return SingleTon.INSTANCE;
    }

    public static void setCommonParamsInterceptor (Interceptor interceptor) {
        interceptor = mInterceptor;
    }
}
