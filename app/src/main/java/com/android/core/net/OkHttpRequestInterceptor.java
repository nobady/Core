package com.android.core.net;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加公共头部参数
 * Created by tengfei.lv on 2017/1/3.
 */
public class OkHttpRequestInterceptor implements Interceptor {

    private Map<String, Object> headerMaps = new TreeMap<> ();

    public OkHttpRequestInterceptor(Map<String, Object> headerMaps) {
        this.headerMaps = headerMaps;
    }

    public OkHttpRequestInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();
        if (headerMaps == null || headerMaps.size() == 0) {
        } else {
            for (Map.Entry<String, Object> entry : headerMaps.entrySet()) {
                request.addHeader(entry.getKey(), (String) entry.getValue());
            }
        }

        return chain.proceed(request.build());
    }
}
