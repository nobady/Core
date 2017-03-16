package com.android.core.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by tengfei.lv on 2016/11/25.
 */

public class BaseCorePresenter<V extends MvpView> extends MvpPresenter<V> {
    public CompositeDisposable mCompositeDisposable;

    @Override public void attachView (V view) {
        super.attachView (view);
        if (mCompositeDisposable==null) {
            mCompositeDisposable = new CompositeDisposable ();
        }
    }

    @Override public void detachView () {
        super.detachView ();
        if (mCompositeDisposable!=null) {
            mCompositeDisposable.dispose ();
            mCompositeDisposable = null;
        }
    }
}
