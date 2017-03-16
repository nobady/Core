package com.android.core.base;

import android.os.Bundle;
import com.android.core.manager.LoadingLayout;
import com.android.core.manager.ToastManager;

/**
 * Created by tengfei.lv on 2017/3/15.
 */
public abstract class BaseMvpCoreActivity<P extends MvpPresenter> extends BaseCoreActivity
    implements BaseCoreView {

    private P presenter;

    @Override protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        createPresenter ();
        if (presenter == null) {
            throw new RuntimeException ("The method createPresenter return null");
        }
        presenter.attachView (this);
    }

    protected abstract P createPresenter ();

    @Override protected void onDestroy () {
        super.onDestroy ();
        presenter.detachView ();
        presenter = null;
    }

    @Override public void showLoadProgress () {
        mLoadingLayout.setStatus (LoadingLayout.Loading);
    }

    @Override public void hideLoadProgress () {
        mLoadingLayout.setStatus (LoadingLayout.Success);
    }

    @Override public void showLoadDialog () {
    }

    @Override public void hideLoadDialog () {
    }

    @Override public void showLoadSuccess () {
        mLoadingLayout.setStatus (LoadingLayout.Success);
    }

    @Override public void showLoadFailPager () {
        mLoadingLayout.setStatus (LoadingLayout.Error);
    }

    @Override public void showEmptyPager () {
        mLoadingLayout.setStatus (LoadingLayout.Empty);
    }

    @Override public void showNoNetError () {
        mLoadingLayout.setStatus (LoadingLayout.No_Network);
    }

    @Override public void showToast (CharSequence toastMsg) {
        ToastManager.showShort (this, toastMsg);
    }

    @Override public void setErrorMsg (String toastMsg) {
        mLoadingLayout.setErrorText (toastMsg);
    }

    @Override public void setNotNetMsg (String toastMsg) {
        mLoadingLayout.setNoNetworkText (toastMsg);
    }

    @Override public void setEmptyMsg (String toastMsg) {
        mLoadingLayout.setEmptyText (toastMsg);
    }
}
