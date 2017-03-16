package com.android.core.net;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tengfei.lv on 2016/12/14.
 */

public class RetrofitClient {

    private Retrofit.Builder retrofitBuilder;

    protected static class Builder {
        private Interceptor loggingInterceptor;
        private Interceptor networkInterceptor;
        private List<Interceptor> interceptorList;
        private String baseUrl;

        private int connectTime = 10;

        private Cache mCache;

        Builder () {
            interceptorList = new ArrayList<> ();
        }

        public Builder setLoggingInterceptor (Interceptor loggingInterceptor) {
            this.loggingInterceptor = loggingInterceptor;
            return this;
        }

        public Builder setNetworkInterceptor (Interceptor networkInterceptor) {
            this.networkInterceptor = networkInterceptor;
            return this;
        }

        public Builder setInterceptors (List<Interceptor> interceptorList) {
            this.interceptorList = interceptorList;
            return this;
        }

        public Builder setBaseUrl (String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setConnectTime (int connectTime) {
            this.connectTime = connectTime;
            return this;
        }

        public Builder setCache (Cache cache) {
            mCache = cache;
            return this;
        }

        public Retrofit build () {
            return new RetrofitClient (baseUrl, loggingInterceptor, networkInterceptor, interceptorList,
                connectTime, mCache).getRetrofit ();
        }
    }

    /**
     * 私有化
     */
    private RetrofitClient (String baseUrl, Interceptor loggingInterceptor,
        Interceptor networkInterceptor, List<Interceptor> interceptorList, int connectTime,
        Cache cache) {
        this.baseUrl = baseUrl;
        this.loggingInterceptor = loggingInterceptor;
        this.networkInterceptor = networkInterceptor;
        this.mInterceptorList = interceptorList;
        this.connectTime = connectTime;
        mCache = cache;
    }

    /**
     * 自己服务的地址
     */
    private String baseUrl = "";

    private Interceptor loggingInterceptor;

    private Interceptor networkInterceptor;

    private List<Interceptor> mInterceptorList;

    private int connectTime;
    private Cache mCache;

    protected Retrofit getRetrofit () {
        if (retrofitBuilder == null) {
            retrofitBuilder =
                new Retrofit.Builder ().addCallAdapterFactory (RxJava2CallAdapterFactory.create ());
        }
        if (loggingInterceptor == null) {
            loggingInterceptor = new OkHttpLoggingInterceptor ();
            ((OkHttpLoggingInterceptor) loggingInterceptor).setLevel (
                HttpLoggingInterceptor.Level.BODY);
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
    private OkHttpClient getClient () {
        OkHttpClient.Builder builder = new OkHttpClient.Builder ();
        builder.addInterceptor (loggingInterceptor)
            .retryOnConnectionFailure (true)
            .cache (mCache)
            .readTimeout (connectTime, TimeUnit.SECONDS)
            .writeTimeout (connectTime, TimeUnit.SECONDS)
            .connectTimeout (connectTime, TimeUnit.SECONDS);

        if (networkInterceptor != null) {
            builder.addNetworkInterceptor (networkInterceptor);
        }
        for (int i = 0; i < mInterceptorList.size (); i++) {
            builder.addInterceptor (mInterceptorList.get (i));
        }
        return builder.build ();
    }
}
