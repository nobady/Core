package com.android.core.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import com.android.core.R;
import com.android.core.manager.AppManager;
import com.android.core.manager.LoadingLayout;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * 没有mvp的基类activity
 * Created by tengfei.lv on 2016/11/30.
 */

public abstract class BaseCoreActivity extends AppCompatActivity{

    public LoadingLayout mLoadingLayout;

    @Override protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        AppManager.getAppManager ().addActivity (this);
        initSystemBarTint ();
    }

    @Override public void setContentView (@LayoutRes int layoutResID) {
        mLoadingLayout = new LoadingLayout (this, layoutResID,getTitleLayout (),0);
        setContentView (mLoadingLayout);
        setSupportActionBar (mLoadingLayout.getToolbarView ());
        mLoadingLayout.setOnReloadListener (new LoadingLayout.OnReloadListener () {
            @Override public void onReload (View v) {
                onReloadClick ();
            }
        });
    }

    /**
     * 标题栏默认布局，如果需要自定义，重写此方法即可，但是其中的点击事件就需要自己处理
     * <p>如果子布局不需要标题栏，那么将此方法返回0</p>
     * @return
     */
    @LayoutRes protected int getTitleLayout () {
        return R.layout.layout_default_title;
    }

    /**
     * 子类可以重写改变状态栏颜色
     */
    protected int setStatusBarColor () {
        return getColorPrimary ();
    }

    /**
     * 子类可以重写决定是否使用透明状态栏,默认使用透明栏
     */
    protected boolean translucentStatusBar () {
        return true;
    }

    /**
     * 设置状态栏颜色
     */
    protected void initSystemBarTint () {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setStatusBarTintColor (setStatusBarColor ());
    }

    /**
     * 获取主题色
     */
    public int getColorPrimary () {
        TypedValue typedValue = new TypedValue ();
        getTheme ().resolveAttribute (R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    public abstract void onReloadClick ();

    @Override protected void onDestroy () {
        super.onDestroy ();
        AppManager.getAppManager ().finishActivity ();
    }

}
