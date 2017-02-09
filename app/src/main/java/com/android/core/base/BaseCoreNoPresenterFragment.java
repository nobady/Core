package com.android.core.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.core.manager.LoadingLayout;

/**
 * Created by tengfei.lv on 2017/2/9.
 */
public abstract class BaseCoreNoPresenterFragment extends Fragment {

    private LoadingLayout mLoadingLayout;

    @Nullable @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        mLoadingLayout = new LoadingLayout (getContext (),getLayoutResId (),0,0);
        return mLoadingLayout;

    }
    @LayoutRes
    public abstract int getLayoutResId();
}
