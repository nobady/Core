package com.android.core.manager;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.core.R;
import com.android.core.utils.ViewUtil;

public class LoadingLayout extends CoordinatorLayout {

    public final static int Success = 0;
    public final static int Empty = 1;
    public final static int Error = 2;
    public final static int No_Network = 3;
    public final static int Loading = 4;
    private int state;

    private Context mContext;
    private View loadingPage;
    private View errorPage;
    private View emptyPage;
    private View networkPage;
    private View defineLoadingPage;

    private ImageView errorImg;
    private ImageView emptyImg;
    private ImageView networkImg;

    private TextView errorText;
    private TextView emptyText;
    private TextView networkText;

    private TextView errorReloadBtn;
    private TextView networkReloadBtn;

    private View contentView;//用户布局
    private Toolbar toolbarView;//标题栏布局
    private AppBarLayout mAppBarLayout;
    private OnReloadListener listener;

    //配置
    private static Config mConfig = new Config ();
    private static String emptyStr = "暂无数据";
    private static String errorStr = "加载失败，请稍后重试···";
    private static String netwrokStr = "无网络连接，请检查网络···";
    private static String reloadBtnStr = "点击重试";
    private static int emptyImgId = R.mipmap.empty;
    private static int errorImgId = R.mipmap.error;
    private static int networkImgId = R.mipmap.no_network;
    private static int reloadBtnId = R.drawable.selector_btn_back_gray;
    private static int tipTextSize = 14;
    private static int buttonTextSize = 14;
    private static int tipTextColor = R.color.base_text_color_light;
    private static int buttonTextColor = R.color.base_text_color_light;
    private static int buttonWidth = -1;
    private static int buttonHeight = -1;
    private static int loadingLayoutId = R.layout.widget_loading_page;
    private static int backgroundColor = R.color.base_loading_background;
    private static int toolbarLayoutId = R.layout.layout_toolbar;

    private LayoutInflater mInflate;

    /*标题栏布局id*/
    private int titleResId;

    public LoadingLayout (Context context, AttributeSet attrs) {
        super (context, attrs);
        this.mContext = context;
    }

    public LoadingLayout (Context context, AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public LoadingLayout (Context context, @LayoutRes int resId, @LayoutRes int titleResId,int less) {
        super (context);
        this.mContext = context;
        this.titleResId = titleResId;
        mInflate = LayoutInflater.from (mContext);
        build (resId);
    }

    private void build (int resId) {

        if (resId==0){
            throw new RuntimeException ("请在Activity或者Fragment中返回getLayoutResId的值");
        }
        contentView = mInflate.inflate (resId, null);

        loadingPage = mInflate.inflate (loadingLayoutId, null);
        errorPage = mInflate.inflate (R.layout.widget_error_page, null);
        emptyPage = mInflate.inflate (R.layout.widget_empty_page, null);
        networkPage = mInflate.inflate (R.layout.widget_nonetwork_page, null);
        defineLoadingPage = null;

        loadingPage.setBackgroundColor (ViewUtil.getColor (mContext, backgroundColor));
        errorPage.setBackgroundColor (ViewUtil.getColor (mContext, backgroundColor));
        emptyPage.setBackgroundColor (ViewUtil.getColor (mContext, backgroundColor));
        networkPage.setBackgroundColor (ViewUtil.getColor (mContext, backgroundColor));

        errorText = ViewUtil.findViewById (errorPage, R.id.error_text);
        emptyText = ViewUtil.findViewById (emptyPage, R.id.empty_text);
        networkText = ViewUtil.findViewById (networkPage, R.id.no_network_text);

        errorImg = ViewUtil.findViewById (errorPage, R.id.error_img);
        emptyImg = ViewUtil.findViewById (emptyPage, R.id.empty_img);
        networkImg = ViewUtil.findViewById (networkPage, R.id.no_network_img);

        errorReloadBtn = ViewUtil.findViewById (errorPage, R.id.error_reload_btn);
        networkReloadBtn = ViewUtil.findViewById (networkPage, R.id.no_network_reload_btn);

        errorReloadBtn.setOnClickListener (new OnClickListener () {
            @Override public void onClick (View v) {

                if (listener != null) {
                    listener.onReload (v);
                }
            }
        });
        networkReloadBtn.setOnClickListener (new OnClickListener () {
            @Override public void onClick (View v) {

                if (listener != null) {
                    listener.onReload (v);
                }
            }
        });

        errorText.setText (errorStr);
        emptyText.setText (emptyStr);
        networkText.setText (netwrokStr);

        errorText.setTextSize (tipTextSize);
        emptyText.setTextSize (tipTextSize);
        networkText.setTextSize (tipTextSize);

        errorText.setTextColor (ViewUtil.getColor (mContext, tipTextColor));
        emptyText.setTextColor (ViewUtil.getColor (mContext, tipTextColor));
        networkText.setTextColor (ViewUtil.getColor (mContext, tipTextColor));

        errorImg.setImageResource (errorImgId);
        emptyImg.setImageResource (emptyImgId);
        networkImg.setImageResource (networkImgId);

        errorReloadBtn.setBackgroundResource (reloadBtnId);
        networkReloadBtn.setBackgroundResource (reloadBtnId);

        errorReloadBtn.setText (reloadBtnStr);
        networkReloadBtn.setText (reloadBtnStr);

        errorReloadBtn.setTextSize (buttonTextSize);
        networkReloadBtn.setTextSize (buttonTextSize);

        errorReloadBtn.setTextColor (ViewUtil.getColor (mContext, buttonTextColor));
        networkReloadBtn.setTextColor (ViewUtil.getColor (mContext, buttonTextColor));

        if (buttonHeight != -1) {

            errorReloadBtn.setHeight (ViewUtil.dp2px (mContext, buttonHeight));
            networkReloadBtn.setHeight (ViewUtil.dp2px (mContext, buttonHeight));
        }
        if (buttonWidth != -1) {

            errorReloadBtn.setWidth (ViewUtil.dp2px (mContext, buttonWidth));
            networkReloadBtn.setWidth (ViewUtil.dp2px (mContext, buttonWidth));
        }

        CoordinatorLayout.LayoutParams layoutParams =
            new CoordinatorLayout.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        if (titleResId != 0) {
            setToolbarView ();
            layoutParams.topMargin = getToolbarViewHeight ();
        }

        this.addView (contentView, layoutParams);

        layoutParams.gravity = Gravity.CENTER;
        this.addView (networkPage, layoutParams);
        this.addView (emptyPage, layoutParams);
        this.addView (errorPage, layoutParams);
        this.addView (loadingPage, layoutParams);
    }

    private int getToolbarViewHeight () {
        int w = View.MeasureSpec.makeMeasureSpec (0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec (0, View.MeasureSpec.UNSPECIFIED);
        mAppBarLayout.measure (w, h);
        return mAppBarLayout.getMeasuredHeight ();
    }

    public void setStatus (@Flavour int status) {

        this.state = status;

        switch (status) {
            case Success:

                contentView.setVisibility (View.VISIBLE);
                emptyPage.setVisibility (View.GONE);
                errorPage.setVisibility (View.GONE);
                networkPage.setVisibility (View.GONE);
                if (defineLoadingPage != null) {

                    defineLoadingPage.setVisibility (View.GONE);
                } else {
                    loadingPage.setVisibility (View.GONE);
                }
                break;

            case Loading:

                contentView.setVisibility (View.GONE);
                emptyPage.setVisibility (View.GONE);
                errorPage.setVisibility (View.GONE);
                networkPage.setVisibility (View.GONE);
                if (defineLoadingPage != null) {
                    defineLoadingPage.setVisibility (View.VISIBLE);
                } else {
                    loadingPage.setVisibility (View.VISIBLE);
                }
                break;

            case Empty:

                contentView.setVisibility (View.GONE);
                emptyPage.setVisibility (View.VISIBLE);
                errorPage.setVisibility (View.GONE);
                networkPage.setVisibility (View.GONE);
                if (defineLoadingPage != null) {
                    defineLoadingPage.setVisibility (View.GONE);
                } else {
                    loadingPage.setVisibility (View.GONE);
                }
                break;

            case Error:

                contentView.setVisibility (View.GONE);
                loadingPage.setVisibility (View.GONE);
                emptyPage.setVisibility (View.GONE);
                errorPage.setVisibility (View.VISIBLE);
                networkPage.setVisibility (View.GONE);
                if (defineLoadingPage != null) {
                    defineLoadingPage.setVisibility (View.GONE);
                } else {
                    loadingPage.setVisibility (View.GONE);
                }
                break;

            case No_Network:

                contentView.setVisibility (View.GONE);
                loadingPage.setVisibility (View.GONE);
                emptyPage.setVisibility (View.GONE);
                errorPage.setVisibility (View.GONE);
                networkPage.setVisibility (View.VISIBLE);
                if (defineLoadingPage != null) {

                    defineLoadingPage.setVisibility (View.GONE);
                } else {
                    loadingPage.setVisibility (View.GONE);
                }
                break;

            default:
                break;
        }
    }

    /**
     * 设置标题栏
     *
     * @return
     */
    public LoadingLayout setToolbarView () {
        mAppBarLayout = (AppBarLayout) mInflate.inflate (toolbarLayoutId, this, false);
        toolbarView = (Toolbar) mAppBarLayout.findViewById (R.id.toolbar);
        /*将标题栏布局添加到toolbar中*/
        mInflate.inflate (titleResId, toolbarView);
        /*如果是默认的标题栏布局*/
        if (titleResId == R.layout.layout_default_title) {
            ToolbarManager.getInstance ().initToolbar (toolbarView);
        }
        addView (mAppBarLayout);
        return this;
    }

    public Toolbar getToolbarView () {
        return toolbarView;
    }

    /**
     * 返回当前状态{Success, Empty, Error, No_Network, Loading}
     *
     * @return
     */
    public int getStatus () {

        return state;
    }

    /**
     * 设置Empty状态提示文本，仅对当前所在的地方有效
     *
     * @return
     */
    public LoadingLayout setEmptyText (String text) {

        emptyText.setText (text);
        return this;
    }

    /**
     * 设置Error状态提示文本，仅对当前所在的地方有效
     *
     * @return
     */
    public LoadingLayout setErrorText (String text) {

        errorText.setText (text);
        return this;
    }

    /**
     * 设置No_Network状态提示文本，仅对当前所在的地方有效
     *
     * @return
     */
    public LoadingLayout setNoNetworkText (String text) {

        networkText.setText (text);
        return this;
    }

    /**
     * 设置Empty状态显示图片，仅对当前所在的地方有效
     *
     * @return
     */
    public LoadingLayout setEmptyImage (@DrawableRes int id) {

        emptyImg.setImageResource (id);
        return this;
    }

    /**
     * 设置Error状态显示图片，仅对当前所在的地方有效
     *
     * @return
     */
    public LoadingLayout setErrorImage (@DrawableRes int id) {

        errorImg.setImageResource (id);
        return this;
    }

    /**
     * 设置No_Network状态显示图片，仅对当前所在的地方有效
     *
     * @return
     */
    public LoadingLayout setNoNetworkImage (@DrawableRes int id) {

        networkImg.setImageResource (id);
        return this;
    }

    /**
     * 设置Empty状态提示文本的字体大小，仅对当前所在的地方有效
     *
     * @return
     */
    public LoadingLayout setEmptyTextSize (int sp) {

        emptyText.setTextSize (sp);
        return this;
    }

    /**
     * 设置Error状态提示文本的字体大小，仅对当前所在的地方有效
     *
     * @return
     */
    public LoadingLayout setErrorTextSize (int sp) {

        errorText.setTextSize (sp);
        return this;
    }

    /**
     * 设置No_Network状态提示文本的字体大小，仅对当前所在的地方有效
     *
     * @return
     */
    public LoadingLayout setNoNetworkTextSize (int sp) {

        networkText.setTextSize (sp);
        return this;
    }

    /**
     * 设置Empty状态图片的显示与否，仅对当前所在的地方有效
     *
     * @return
     */
    public LoadingLayout setEmptyImageVisible (boolean bool) {

        if (bool) {
            emptyImg.setVisibility (View.VISIBLE);
        } else {
            emptyImg.setVisibility (View.GONE);
        }
        return this;
    }

    /**
     * 设置Error状态图片的显示与否，仅对当前所在的地方有效
     *
     * @return
     */
    public LoadingLayout setErrorImageVisible (boolean bool) {

        if (bool) {
            errorImg.setVisibility (View.VISIBLE);
        } else {
            errorImg.setVisibility (View.GONE);
        }
        return this;
    }

    /**
     * 设置No_Network状态图片的显示与否，仅对当前所在的地方有效
     *
     * @return
     */
    public LoadingLayout setNoNetworkImageVisible (boolean bool) {

        if (bool) {
            networkImg.setVisibility (View.VISIBLE);
        } else {
            networkImg.setVisibility (View.GONE);
        }
        return this;
    }

    /**
     * 设置ReloadButton的文本，仅对当前所在的地方有效
     *
     * @return
     */
    public LoadingLayout setReloadButtonText (@NonNull String text) {

        errorReloadBtn.setText (text);
        networkReloadBtn.setText (text);
        return this;
    }

    /**
     * 设置ReloadButton的文本字体大小，仅对当前所在的地方有效
     *
     * @return
     */
    public LoadingLayout setReloadButtonTextSize (int sp) {

        errorReloadBtn.setTextSize (sp);
        networkReloadBtn.setTextSize (sp);
        return this;
    }

    /**
     * 设置ReloadButton的文本颜色，仅对当前所在的地方有效
     *
     * @return
     */
    public LoadingLayout setReloadButtonTextColor (@ColorRes int id) {

        errorReloadBtn.setTextColor (ViewUtil.getColor (mContext, id));
        networkReloadBtn.setTextSize (ViewUtil.getColor (mContext, id));
        return this;
    }

    /**
     * 设置ReloadButton的背景，仅对当前所在的地方有效
     *
     * @return
     */
    public LoadingLayout setReloadButtonBackgroundResource (@DrawableRes int id) {

        errorReloadBtn.setBackgroundResource (id);
        networkReloadBtn.setBackgroundResource (id);
        return this;
    }

    /**
     * 设置ReloadButton的监听器
     *
     * @return
     */
    public LoadingLayout setOnReloadListener (OnReloadListener listener) {

        this.listener = listener;
        return this;
    }

    /**
     * 自定义加载页面，仅对当前所在的Activity有效
     *
     * @return
     */
    public LoadingLayout setLoadingPage (View view) {

        defineLoadingPage = view;
        this.removeView (loadingPage);
        defineLoadingPage.setVisibility (View.GONE);
        this.addView (view);
        return this;
    }

    /**
     * 自定义加载页面，仅对当前所在的地方有效
     *
     * @return
     */
    public LoadingLayout setLoadingPage (@LayoutRes int id) {

        this.removeView (loadingPage);
        View view = mInflate.inflate (id, null);
        defineLoadingPage = view;
        defineLoadingPage.setVisibility (View.GONE);
        this.addView (view);
        return this;
    }

    /**
     * 获取当前自定义的loadingpage
     *
     * @return
     */
    public View getLoadingPage () {

        return defineLoadingPage;
    }

    /**
     * 获取全局使用的loadingpage
     *
     * @return
     */
    public View getGlobalLoadingPage () {

        return loadingPage;
    }

    @IntDef ({ Success, Empty, Error, No_Network, Loading }) public @interface Flavour {

    }

    public interface OnReloadListener {

        void onReload (View v);
    }

    /**
     * 获取全局配置的class
     *
     * @return
     */
    public static Config getConfig () {

        return mConfig;
    }

    /**
     * 全局配置的Class，对所有使用到的地方有效
     */
    public static class Config {

        public Config setErrorText (@NonNull String text) {

            errorStr = text;
            return mConfig;
        }

        public Config setEmptyText (@NonNull String text) {

            emptyStr = text;
            return mConfig;
        }

        public Config setNoNetworkText (@NonNull String text) {

            netwrokStr = text;
            return mConfig;
        }

        public Config setReloadButtonText (@NonNull String text) {

            reloadBtnStr = text;
            return mConfig;
        }

        /**
         * 设置所有提示文本的字体大小
         *
         * @return
         */
        public Config setAllTipTextSize (int sp) {

            tipTextSize = sp;
            return mConfig;
        }

        /**
         * 设置所有提示文本的字体颜色
         *
         * @return
         */
        public Config setAllTipTextColor (@ColorRes int color) {

            tipTextColor = color;
            return mConfig;
        }

        public Config setReloadButtonTextSize (int sp) {

            buttonTextSize = sp;
            return mConfig;
        }

        public Config setReloadButtonTextColor (@ColorRes int color) {

            buttonTextColor = color;
            return mConfig;
        }

        public Config setReloadButtonBackgroundResource (@DrawableRes int id) {

            reloadBtnId = id;
            return mConfig;
        }

        public Config setReloadButtonWidthAndHeight (int width_dp, int height_dp) {

            buttonWidth = width_dp;
            buttonHeight = height_dp;
            return mConfig;
        }

        public Config setErrorImage (@DrawableRes int id) {

            errorImgId = id;
            return mConfig;
        }

        public Config setEmptyImage (@DrawableRes int id) {

            emptyImgId = id;
            return this;
        }

        public Config setNoNetworkImage (@DrawableRes int id) {

            networkImgId = id;
            return mConfig;
        }

        public Config setLoadingPageLayout (@LayoutRes int id) {

            loadingLayoutId = id;
            return mConfig;
        }

        public Config setAllPageBackgroundColor (@ColorRes int color) {

            backgroundColor = color;
            return mConfig;
        }
    }
}
