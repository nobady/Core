package com.android.core.base;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by tengfei.lv on 2016/11/25.
 */

public class BaseCorePresenter<V extends BaseCoreView> extends MvpBasePresenter<V> {
    public CompositeSubscription mCompositeSubscription;

    @Override public void attachView (V view) {
        super.attachView (view);
        if (mCompositeSubscription==null) {
            mCompositeSubscription = new CompositeSubscription ();
        }
    }

    @Override public void detachView (boolean retainInstance) {
        super.detachView (retainInstance);
        if (mCompositeSubscription!=null) {
            mCompositeSubscription.unsubscribe ();
            mCompositeSubscription = null;
        }
    }
}
