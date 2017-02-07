package com.android.core.net;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tengfei.lv on 2016/12/14.
 */

public class RetrofitClient {

    private static Retrofit.Builder retrofitBuilder;

    /**
     * 私有化
     */
    private RetrofitClient () {
    }

    /**
     * 自己服务的地址
     */
    private static String baseUrl = "";

    private static HttpLoggingInterceptor loggingInterceptor;

    private static OkHttpRequestInterceptor requestInterceptor;

    /**
     * 添加头部信息
     *
     * @return retrofitBuilder
     */
    public static Retrofit getInstance (Map<String, Object> headerMaps,
        Interceptor netWorkInterceptor) {
        return getInstance (baseUrl, headerMaps, netWorkInterceptor);
    }

    /**
     * @param baseUrl
     * @param headerMaps
     * @param netWorkInterceptor
     * @return
     */
    public static Retrofit getInstance (String baseUrl, Map<String, Object> headerMaps,
        Interceptor netWorkInterceptor) {
        return getInstance (baseUrl, headerMaps, netWorkInterceptor, null);
    }

    /**
     * 配置okhttp
     * @param baseUrl   服务器地址
     * @param headerMaps   头部信息
     * @param netWorkInterceptor  网络拦截器
     * @param commonInterceptor   公共参数拦截器
     * @return
     */
    public static Retrofit getInstance (String baseUrl, Map<String, Object> headerMaps,
        Interceptor netWorkInterceptor, Interceptor commonInterceptor) {
        if (retrofitBuilder == null) {
            retrofitBuilder =
                new Retrofit.Builder ().addCallAdapterFactory (RxJavaCallAdapterFactory.create ());
            loggingInterceptor = new HttpLoggingInterceptor ();
            loggingInterceptor.setLevel (HttpLoggingInterceptor.Level.BODY);
        }
        if (headerMaps != null) {
            requestInterceptor = new OkHttpRequestInterceptor (headerMaps);
        } else {
            requestInterceptor = new OkHttpRequestInterceptor ();
        }

        retrofitBuilder.baseUrl (baseUrl)
            .addConverterFactory (GsonConverterFactory.create ())
            .client (getClient (commonInterceptor, netWorkInterceptor));

        return retrofitBuilder.build ();
    }

    /**
     * 初始化okHttpClient
     *
     * @return OkHttpClient
     */
    private static OkHttpClient getClient (Interceptor commonInterceptor,
        Interceptor netWorkInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder ();
        builder.addInterceptor (requestInterceptor)
            .addInterceptor (loggingInterceptor)
            .readTimeout (10, TimeUnit.SECONDS)
            .writeTimeout (10, TimeUnit.SECONDS)
            .connectTimeout (10, TimeUnit.SECONDS);

        if (commonInterceptor != null) {
            builder.addInterceptor (commonInterceptor);
        }
        if (netWorkInterceptor != null) {
            builder.addNetworkInterceptor (netWorkInterceptor);
        }

        return builder.build ();
    }
}
