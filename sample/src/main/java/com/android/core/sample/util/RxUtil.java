package com.android.core.sample.util;

import com.android.core.net.RetrofitException;
import com.android.core.sample.App;
import com.android.core.sample.bean.JsonResult;
import com.android.core.utils.NetUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tengfei.lv on 2016/12/29.
 */

public class RxUtil {

    public static  <T> Observable<T> call(Observable<JsonResult<T>> observable){
        return Observable.just(observable)
            //判断网络
            .doOnNext(new Consumer<Observable<JsonResult<T>>> () {
                @Override
                public void accept (@NonNull Observable<JsonResult<T>> jsonResultObservable)
                    throws Exception {
                    if(NetUtil.getNetworkState(App.mContext)==NetUtil.NETWORN_NONE){
                        RetrofitException retrofitException=new RetrofitException();
                        retrofitException.setStateCode(RetrofitException.NO_NETWORK_CONNECTED);
                        retrofitException.setMsg("无网络连接");
                        throw retrofitException;
                    }
                }
            })
            //在IO线程处理网络连接
            .observeOn(Schedulers.io())
            //可在此对获取的事件进行处理（本方法没有处理，直接返回）
            .concatMap(new Function<Observable<JsonResult<T>>, ObservableSource<JsonResult<T>>> (){

                @Override public ObservableSource<JsonResult<T>> apply (
                    @NonNull Observable<JsonResult<T>> jsonResultObservable) throws Exception {
                    return jsonResultObservable;
                }
            })
            //切换到主线程
            .observeOn(AndroidSchedulers.mainThread())
            .concatMap(new Function<JsonResult<T>, Observable<T>>() {

                @Override public Observable<T> apply (@NonNull JsonResult<T> tJsonResult)
                    throws Exception {
                    return Observable.just(tJsonResult.getResults ());
                }

            });
    }
}
