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
 * 没有mvp的基类fragment
 * Created by tengfei.lv on 2016/12/23.
 */

public abstract class BaseCoreFragment extends Fragment{

    protected LoadingLayout mLoadingLayout;
    /**
     * 页面是否可见
     */
    private boolean isVisible;
    /**
     * view是否初始化完成
     */
    private boolean isCreated;

    @Override public void setUserVisibleHint (boolean isVisibleToUser) {
        super.setUserVisibleHint (isVisibleToUser);
        if (getUserVisibleHint ()){
            isVisible = true;
            lazyLoad();
        }else {
            isVisible = false;
            doUnVisibleThing();
        }
    }

    /**
     * 页面不可见时的操作，比如取消请求等，子类根据自身业务去实现
     */
    protected void doUnVisibleThing () {
    }

    /**
     * 加载数据的具体方法，需要子类去实现
     */
    protected void loadData () {
    }

    /**
     * 如果isVisible&&isCreated为true时，方可加载数据
     */
    private void lazyLoad () {
        if(isVisible&&isCreated){
            loadData();
        }
    }

    @Nullable @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from (getContext ()).inflate (getLayoutResId (), null);
        mLoadingLayout = new LoadingLayout (getContext (),view,0,null);
        mLoadingLayout.setOnReloadListener (new LoadingLayout.OnReloadListener () {
            @Override public void onReload (View v) {
                onReloadClick ();
            }
        });
        return mLoadingLayout.getRootView ();
    }

    @Override public void onViewCreated (View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);

        isCreated = true;
        lazyLoad ();
    }

    public void onReloadClick(){};

    @LayoutRes
    public abstract int getLayoutResId();

}
