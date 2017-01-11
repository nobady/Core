package com.android.core.net;

import java.util.Map;
import java.util.concurrent.TimeUnit;
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
     * 无参数  实例化
     *
     * @return retrofitBuilder
     */
    public static Retrofit getInstance (Map<String, Object> headerMaps) {

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
            .client (getClient ());

        return retrofitBuilder.build ();
    }


    public static Retrofit getInstance (String baseUrl, Map<String, Object> headerMaps) {

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
            .client (getClient ());

        return retrofitBuilder.build ();
    }

    /**
     * 初始化okHttpClient
     *
     * @return OkHttpClient
     */
    public static OkHttpClient getClient () {

        OkHttpClient httpClient = new OkHttpClient.Builder ().addInterceptor (requestInterceptor)
            .addInterceptor (loggingInterceptor)
            .readTimeout (10, TimeUnit.SECONDS)
            .writeTimeout (10, TimeUnit.SECONDS)
            .connectTimeout (10, TimeUnit.SECONDS)
            .build ();

        return httpClient;
    }
}
