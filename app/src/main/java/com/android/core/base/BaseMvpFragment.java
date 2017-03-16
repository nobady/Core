package com.android.core.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.android.core.manager.LoadingLayout;
import com.android.core.manager.ToastManager;

/**
 * Created by tengfei.lv on 2017/3/15.
 */
public abstract class BaseMvpFragment<P extends MvpPresenter> extends BaseCoreFragment
    implements BaseCoreView {

    protected P presenter;

    @Override public void onViewCreated (View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        createPresenter ();
        if (presenter == null) {
            throw new RuntimeException ("The method createPresenter return null");
        }
        presenter.attachView (this);
    }

    protected abstract P createPresenter ();

    @Override public void onDestroyView () {
        super.onDestroyView ();
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
        ToastManager.showShort (getContext (), toastMsg);
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
