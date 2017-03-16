package com.android.core.base;

import java.lang.ref.WeakReference;

/**
 * Created by tengfei.lv on 2017/3/15.
 */
public class MvpPresenter<V extends MvpView> {

    private WeakReference<V> viewWeak;

    public V getView () {
        return viewWeak == null ? null : viewWeak.get ();
    }

    public boolean isViewAttach () {
        return viewWeak != null && viewWeak.get () != null;
    }

    public void attachView (V view) {
        viewWeak = new WeakReference<> (view);
    }

    public void detachView () {
        if (viewWeak != null) {
            viewWeak.clear ();
            viewWeak = null;
        }
    }
}
