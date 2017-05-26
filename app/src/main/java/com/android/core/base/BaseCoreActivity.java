package com.android.core.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.core.R;
import com.android.core.manager.AppManager;
import com.android.core.manager.LoadingLayout;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import java.lang.reflect.Field;

/**
 * 没有mvp的基类activity
 * Created by tengfei.lv on 2016/11/30.
 */

public abstract class BaseCoreActivity extends AppCompatActivity {

    public LoadingLayout mLoadingLayout;

    @Override protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        AppManager.getAppManager ().addActivity (this);
        initSystemBarTint ();
    }

    @Override public void setContentView (@LayoutRes int layoutResID) {
        if (layoutResID == 0) {
            throw new RuntimeException ("请在Activity或者Fragment中返回getLayoutResId的值");
        } else {
            setContentView (LayoutInflater.from (this).inflate (layoutResID, null));
        }
    }

    @Override public void setContentView (View view) {
        ViewGroup rootView = (ViewGroup) findViewById (android.R.id.content);
        mLoadingLayout = new LoadingLayout (this, view, getTitleLayout (), rootView);
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
     *
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
     * 设置状态栏颜色
     */
    protected void initSystemBarTint () {
        SystemBarTintManager tintManager = new SystemBarTintManager (this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled (true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled (true);
        tintManager.setStatusBarTintColor (setStatusBarColor ());
        /*5.0以上*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow ();
            //设置透明状态栏,这样才能让 ContentView 向上
            window.addFlags (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags (WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor (setStatusBarColor ());

            ViewGroup mContentView = (ViewGroup) findViewById (Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt (0);
            if (mChildView != null) {
                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
                ViewCompat.setFitsSystemWindows (mChildView, false);
            }
        }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            /*4.4*/
            Window window = getWindow();
            ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);

            //首先使 ChildView 不预留空间
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, false);
            }

            int statusBarHeight = getStatusBarHeight(this);
            //需要设置这个 flag 才能设置状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //避免多次调用该方法时,多次移除了 View
            if (mChildView != null && mChildView.getLayoutParams() != null && mChildView.getLayoutParams().height == statusBarHeight) {
                //移除假的 View.
                mContentView.removeView(mChildView);
                mChildView = mContentView.getChildAt(0);
            }
            if (mChildView != null) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
                //清除 ChildView 的 marginTop 属性
                if (lp != null && lp.topMargin >= statusBarHeight) {
                    lp.topMargin -= statusBarHeight;
                    mChildView.setLayoutParams(lp);
                }
            }
        }
    }


    public  int getStatusBarHeight(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
    /**
     * 获取主题色
     */
    public int getColorPrimary () {
        TypedValue typedValue = new TypedValue ();
        getTheme ().resolveAttribute (R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    public void onReloadClick () {

    }

    @Override protected void onDestroy () {
        super.onDestroy ();
        AppManager.getAppManager ().finishActivity ();
    }
}
