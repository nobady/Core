package com.android.core.net;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by lan.zheng on 2016/12/13.
 */
public class CommonParameterInterceptor implements Interceptor {
    public static final String TAG = "CommonParameterInterceptor.java";
    private static Map<String, String> mCommonParameter;
    private String urlStitch;

    public CommonParameterInterceptor () {
    }

    public void setCommonParameterMap (Map<String, String> commonParameter) {
        mCommonParameter = commonParameter;
        getUrlStitch ();  //获得后面的拼接串，虽然可能用不到
    }

    private void getUrlStitch () {
        urlStitch = "";
        Object[] arrays = mCommonParameter.keySet ().toArray ();
        for (int i = 0; i < mCommonParameter.size (); i++) {
            //根据key去取值
            String value = mCommonParameter.get (arrays[i].toString ());
            urlStitch += "&" + arrays[i].toString () + "=" + value;
        }
    }

    @Override public Response intercept (Chain chain) throws IOException {
        Request request = chain.request ();
        //the request method
        //        String method = request.method();
        //请求body
        RequestBody requestBody = request.body ();
        LogUtils.e ("okh = " + requestBody);
        if (requestBody != null) {
            //有body的情况
            String query = getBodyParams (request);
            FormBody formBody = getNewRequestBody (query);
            HttpUrl.Builder authorizedUrlBuilder = request.url ()
                .newBuilder ()
                .scheme (request.url ().scheme ())
                .host (request.url ().host ());
            // 新的请求
            Request newRequest = request.newBuilder ()
                .method (request.method (), formBody)
                .url (authorizedUrlBuilder.build ())
                .build ();
            Log.e ("okh", "url = " + newRequest.url ());
            Log.e ("okh", "formBody = " + formBody);
            return chain.proceed (newRequest);  //返回新的请求
        } else {
            //拼接的情况
            String url = request.url ().toString ();
            //没有生成拼接重新生成
            if (TextUtils.isEmpty (urlStitch) && mCommonParameter.size () > 0) {
                //此时说明没有生成拼接字符串，要再次生成
                getUrlStitch ();
            }
            //如果是无参的请求，无参拼接需要先加？
            if (!url.contains ("?") && mCommonParameter.size () > 0) {
                String subStitch = urlStitch.substring (1, urlStitch.length ());
                urlStitch = "?" + subStitch;
            }
            url = url + urlStitch;  //正常生成
            Log.e ("okh", "url = " + url);
            Request newRequest = request.newBuilder ().url (url).build ();
            return chain.proceed (newRequest);  //返回新的请求
        }
    }

    /**
     * 获取到请求参数
     *
     * @return
     *
     * @throws IOException
     */
    @Nullable private String getBodyParams (Request request) throws IOException {
        String query = request.url ().query ();  //如果参数在url中
        if (query != null) {
        } else {
            if (request.method ().equals ("POST")) {   //如果参数在body中
                Buffer buffer = new Buffer ();
                RequestBody body = request.body ();
                body.writeTo (buffer);
                query = buffer.readUtf8 ();
            }
        }
        return query;
    }
     /**
     * 写入的参数已经做成静态，直接用
     *
     * @param query
     *     原来的参数
     *
     * @return
     */
    @NonNull private FormBody getNewRequestBody (String query) {
        //        RequestBodyEntity body = JsonHelper.parseObject (query,RequestBodyEntity.class);
        //添加参数
        //        body.setTimestamp (date);
        //        body.setSign (sign);
        JSONObject jsonString = JSONObject.parseObject (query);
        Object[] arrays = mCommonParameter.keySet ().toArray ();
        for (int i = 0; i < mCommonParameter.size (); i++) {
            jsonString.put (arrays[i].toString (), mCommonParameter.get (arrays[i].toString ()));
        }

        Log.e ("okh",""+jsonString.toJSONString ());
        //重新生成请求对象
        FormBody.Builder builder = new FormBody.Builder ();
        Iterator it = jsonString.keySet ().iterator ();
        // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext ()) {
            String key = String.valueOf (it.next ());
            String value = (String) jsonString.get (key);
            builder.add (key, value);
        }
        return builder.build ();
    }
}
