package com.android.core.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.core.manager.LoadingLayout;
import com.android.core.manager.ToastManager;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

/**
 * Created by tengfei.lv on 2016/12/23.
 */

public abstract class BaseCoreFragment<V extends BaseCoreView, P extends BaseCorePresenter<V>>
    extends MvpFragment<V, P> implements BaseCoreView{

    private LoadingLayout mLoadingLayout;

    @Nullable @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        mLoadingLayout = new LoadingLayout (getContext (),getLayoutResId ());
        mLoadingLayout.setOnReloadListener (new LoadingLayout.OnReloadListener () {
            @Override public void onReload (View v) {
                onReloadClick ();
            }
        });
        return mLoadingLayout;

    }
    public abstract void onReloadClick();

    @LayoutRes
    public abstract int getLayoutResId();

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
        ToastManager.showShort (getContext (),toastMsg);
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
