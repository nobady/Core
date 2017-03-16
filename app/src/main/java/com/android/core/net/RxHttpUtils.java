package com.android.core.net;

import java.util.ArrayList;
import java.util.List;
import okhttp3.Cache;
import okhttp3.Interceptor;

/**
 * 网络访问工具类
 * <p>
 * 可以进行全局的属性设置，也可以在当前所在地方进行局部设置
 * <p>
 * 使用方法
 * <p>
 * <ul>
 * <li>
 * 全局设置：使用{@link RxHttpUtils#getmConfig()} 获取到全局配置器对象，然后调用其中的方法即可设置
 * </li>
 * <li>
 * 局部设置：使用{@link RxHttpUtils#getInstance()} 获取到RxHttpUtils对象，然后调用其中的方法即可局部设置
 * </li>
 * </ul>
 * <p>
 * 属性设置完成后，调用{@link RxHttpUtils#createApi(Class)} 即可执行
 * <p>
 * Created by tengfei.lv on 2017/1/3.
 */
public class RxHttpUtils {

    private static NetConfig mConfig = new NetConfig ();

    /**
     * 全局的baseUrl，
     */
    private static String mBaseUrl;
    /**
     * 网络拦截器
     */
    private static Interceptor mNetWorkInterceptor;
    /**
     * log拦截器
     */
    private static Interceptor mLoggingInterceptor;
    /**
     * 网络缓存配置
     */
    private static Cache mCache;
    /**
     * 链接、读写的超时时间
     */
    private static int connectTime = 10;
    /**
     * 保存全局拦截器集合
     */
    private static List<Interceptor> interceptorList = new ArrayList<> ();
    /**
     * 保存某一次的拦截器集合
     */
    private List<Interceptor> tempList = new ArrayList<> ();
    /**
     * 标记是否使用全局的拦截器还是某一次的拦截器
     */
    private boolean isTemp;

    private RetrofitClient.Builder mBuilder;

    private RxHttpUtils () {
        mBuilder = new RetrofitClient.Builder ();
        mBuilder.setBaseUrl (mBaseUrl)
            .setCache (mCache)
            .setConnectTime (connectTime)
            .setLoggingInterceptor (mLoggingInterceptor)
            .setNetworkInterceptor (mNetWorkInterceptor);
    }

    private static class SingleTon {
        private static RxHttpUtils INSTANCE = new RxHttpUtils ();
    }

    /**
     * 获取全局信息配置对象
     *
     * @return
     */
    public static NetConfig getmConfig () {
        return mConfig;
    }

    /**
     * 获取类实例
     *
     * @return
     */
    public static RxHttpUtils getInstance () {
        return SingleTon.INSTANCE;
    }

    public RxHttpUtils setUrl (String url) {
        mBuilder.setBaseUrl (url);
        return this;
    }

    public RxHttpUtils setLoggingInterceptor (Interceptor loggingInterceptor) {
        mBuilder.setLoggingInterceptor (loggingInterceptor);
        return this;
    }

    public RxHttpUtils setNetworkInterceptor (Interceptor networkInterceptor) {
        mBuilder.setNetworkInterceptor (networkInterceptor);
        return this;
    }

    public RxHttpUtils setCache (Cache cache) {
        mBuilder.setCache (cache);
        return this;
    }

    public RxHttpUtils setConnectTime (int time) {
        mBuilder.setConnectTime (time);
        return this;
    }

    public RxHttpUtils setInterceptor (Interceptor interceptor) {
        isTemp = true;
        tempList.add (interceptor);
        return this;
    }

    /**
     * @param cls
     *     要返回的接口对象
     *
     * @return
     */
    public <T> T createApi (Class<T> cls) {
        if (isTemp) {
            mBuilder.setInterceptors (tempList);
        } else {
            mBuilder.setInterceptors (interceptorList);
        }
        return mBuilder.build ().create (cls);
    }

    /**
     * 网络请求全局配置类
     */
    private static class NetConfig {

        public NetConfig setLoggingInterceptor (Interceptor loggingInterceptor) {
            mLoggingInterceptor = loggingInterceptor;
            return mConfig;
        }

        public NetConfig setNetworkInterceptor (Interceptor networkInterceptor) {
            mNetWorkInterceptor = networkInterceptor;
            return mConfig;
        }

        public NetConfig setBaseUrl (String baseUrl) {
            mBaseUrl = baseUrl;
            return mConfig;
        }

        public NetConfig setCache (Cache cache) {
            mCache = cache;
            return mConfig;
        }

        public NetConfig setConnectTime (int time) {
            connectTime = time;
            return mConfig;
        }

        public NetConfig setInterceptor (Interceptor interceptor) {
            interceptorList.add (interceptor);
            return mConfig;
        }

        public NetConfig clearInteceptor () {
            interceptorList.clear ();
            return mConfig;
        }
    }
}
