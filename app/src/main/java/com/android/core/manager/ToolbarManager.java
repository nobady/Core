package com.android.core.manager;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.core.R;

/**
 * 默认的标题栏管理
 * <p>
 * 不要主动调用initToolbar(Toolbar)方法，否则会报错
 * 如果需要设置全局的文字颜色和布局，调用ToolbarManager#getConfig()里面的方法即可设置
 * 如果是自定义标题栏布局，那么此类的方法都不可用
 * 如果是局部设置文字颜色或者大小，调用setXXX()方法即可
 * Created by tengfei.lv on 2017/1/10.
 */
public class ToolbarManager {

    public static int rightTextDefaultColor = R.color.right_toolbar_text_default;
    public static float rightTextDefaultSize = 12;

    public static int centerTextDefaultColor = R.color.center_toolbar_text_default;
    public static float centerTextDefaultSize = 14;

    public static int leftTextDefaultColor = R.color.center_toolbar_text_default;
    public static float leftTextDefaultSize = 12;

    public static int leftImageDefault = R.drawable.ic_arrow_back_white_18dp;

    private static Config mConfig = new Config ();

    private Toolbar mToolbar;
    private LinearLayout mRightLayout;
    private LinearLayout mLeftLayout;
    private ImageView mLeftImageView;
    private TextView mLeftTextView;
    private TextView mCenterTextView;

    private static class Singleton {
        private static ToolbarManager INSTANCE = new ToolbarManager ();
    }

    private ToolbarManager () {
    }

    public static ToolbarManager getInstance () {
        return Singleton.INSTANCE;
    }

    /**
     * 获取全局配置的Class
     *
     * @return
     */
    public static Config getConfig () {
        return mConfig;
    }

    /**
     * 全局配置的Class，对所有使用到的地方有效
     * 暂提供设置左中右文字颜色、文字大小和左边图片的方法
     */
    private static class Config {
        public Config setRightTextColor (int color) {
            rightTextDefaultColor = color;
            return mConfig;
        }

        public Config setRightTextSize (float size) {
            rightTextDefaultSize = size;
            return mConfig;
        }

        public Config setCenterRightTextColor (int color) {
            centerTextDefaultColor = color;
            return mConfig;
        }

        public Config setCenterRightTextSize (float size) {
            centerTextDefaultSize = size;
            return mConfig;
        }

        public Config setLeftRightTextColor (int color) {
            leftTextDefaultColor = color;
            return mConfig;
        }

        public Config setLeftRightTextSize (float size) {
            leftTextDefaultSize = size;
            return mConfig;
        }

        public Config setLeftImage (@IdRes int resId) {
            leftImageDefault = resId;
            return mConfig;
        }
    }

    /*此方法*/
    @Deprecated protected void initToolbar (Toolbar toolbar) {
        mToolbar = toolbar;

        mRightLayout = (LinearLayout) toolbar.findViewById (R.id.title_right_layout);
        mLeftLayout = (LinearLayout) toolbar.findViewById (R.id.title_left_layout);
        mLeftImageView = (ImageView) toolbar.findViewById (R.id.title_back_image);
        mLeftTextView = (TextView) toolbar.findViewById (R.id.title_text);
        mCenterTextView = (TextView) toolbar.findViewById (R.id.center_title);

        /*-----------------设置默认的大小和颜色-------------------*/

        mLeftImageView.setImageResource (leftImageDefault);
        mCenterTextView.setTextSize (centerTextDefaultSize);
        mCenterTextView.setTextColor (mToolbar.getContext ().getResources ().getColor (centerTextDefaultColor));
        mLeftTextView.setTextColor (mToolbar.getContext ().getResources ().getColor (leftTextDefaultColor));
        mLeftTextView.setTextSize (leftTextDefaultSize);
    }

    private void isEnabled () {
        if (mToolbar == null) throw new RuntimeException ("当前页面不支持使用默认标题栏");
    }

    /*--------------------------------左边图片---------------------------*/
    public ToolbarManager setLeftImage (Drawable drawable) {
        isEnabled ();
        mLeftImageView.setImageDrawable (drawable);
        return Singleton.INSTANCE;
    }

    public ToolbarManager setLeftImage (int resId) {
        isEnabled ();
        mLeftImageView.setImageResource (resId);
        return Singleton.INSTANCE;
    }

    public ToolbarManager setLeftImage (Bitmap bitmap) {
        isEnabled ();
        mLeftImageView.setImageBitmap (bitmap);
        return Singleton.INSTANCE;
    }

    public ToolbarManager setLeftImage (Uri uri) {
        isEnabled ();
        mLeftImageView.setImageURI (uri);
        return Singleton.INSTANCE;
    }

    public ToolbarManager showLeftImage (boolean isShow) {
        isEnabled ();
        mLeftImageView.setVisibility (isShow ? View.VISIBLE : View.GONE);
        return Singleton.INSTANCE;
    }


    public ToolbarManager setLeftImageBackground (int resId) {
        isEnabled ();
        mLeftImageView.setBackgroundResource (resId);
        return Singleton.INSTANCE;
    }

    public ToolbarManager setLeftImageBackground (Drawable drawable) {
        isEnabled ();
        mLeftImageView.setBackground (drawable);
        return Singleton.INSTANCE;
    }

    public ToolbarManager setLeftImageBackgroundColor (int color) {
        isEnabled ();
        mLeftImageView.setBackgroundColor (color);
        return Singleton.INSTANCE;
    }

    /*--------------------------------左边文字---------------------------*/
    public ToolbarManager setLeftText (CharSequence text) {
        isEnabled ();
        mLeftTextView.setText (text);
        return Singleton.INSTANCE;
    }

    public ToolbarManager setLeftTextSize (float size) {
        isEnabled ();
        mLeftTextView.setTextSize (size);
        return Singleton.INSTANCE;
    }

    public ToolbarManager setLeftTextColor (int color) {
        isEnabled ();
        mLeftTextView.setTextColor (color);
        return Singleton.INSTANCE;
    }

    public ToolbarManager showLeftText (boolean isShow) {
        isEnabled ();
        mLeftTextView.setVisibility (isShow ? View.VISIBLE : View.GONE);
        return Singleton.INSTANCE;
    }

    public ToolbarManager showLeftLayout (boolean isShow) {
        isEnabled ();
        mLeftLayout.setVisibility (isShow ? View.VISIBLE : View.GONE);
        return Singleton.INSTANCE;
    }

    public ToolbarManager setLeftTextBackground (int resId) {
        isEnabled ();
        mLeftTextView.setBackgroundResource (resId);
        return Singleton.INSTANCE;
    }

    public ToolbarManager setLeftTextBackground (Drawable drawable) {
        isEnabled ();
        mLeftTextView.setBackground (drawable);
        return Singleton.INSTANCE;
    }

    public ToolbarManager setLeftTextBackgroundColor (int color) {
        isEnabled ();
        mLeftTextView.setBackgroundColor (color);
        return Singleton.INSTANCE;
    }

    /*--------------------------------中间布局---------------------------*/
    public ToolbarManager showCenterLayout (boolean isShow) {
        isEnabled ();
        mCenterTextView.setVisibility (isShow ? View.VISIBLE : View.GONE);
        return Singleton.INSTANCE;
    }

    public ToolbarManager setCenterText (CharSequence text) {
        isEnabled ();
        mCenterTextView.setText (text);
        return Singleton.INSTANCE;
    }

    public ToolbarManager setCenterTextColor (int color) {
        isEnabled ();
        mCenterTextView.setTextColor (color);
        return Singleton.INSTANCE;
    }

    public ToolbarManager setCenterTextSize (float size) {
        isEnabled ();
        mCenterTextView.setTextSize (size);
        return Singleton.INSTANCE;
    }
    /*--------------------------------右边布局---------------------------*/

    /**
     * <p>
     * 按照ui图，顺序的添加资源文件，如果有的文字是动态改变，那么可以先添加空串,
     * 然后在调用{@link #setRightTextFormIndex}进行设置文本内容
     *</p>
     * @param resId
     *     资源文件的id，必须在R文件中可以找到id
     *
     * @return
     */
    public ToolbarManager setRightLayout (@IdRes int... resId) {
        isEnabled ();
        int size = resId == null ? 0 : resId.length;
        for (int i = 0; i < size; i++) {
            /*图片资源id范围*/
            if (resId[i] >= 0x7f020000 && resId[i] <= 0x7f03ffff) {
                ImageView imageView = new ImageView (mToolbar.getContext ());
                imageView.setPadding (10, 0, 10, 0);
                imageView.setImageResource (resId[i]);
                mRightLayout.addView (imageView);
            } else {
                TextView textView = new TextView (mToolbar.getContext ());
                textView.setText (resId[i]);
                textView.setTextColor (mToolbar.getContext ().getColor (rightTextDefaultColor));
                textView.setTextSize (rightTextDefaultSize);
                textView.setPadding (10, 0, 10, 0);
                textView.setGravity (Gravity.CENTER);
                mRightLayout.addView (textView);
            }
        }
        return Singleton.INSTANCE;
    }

    /**
     * 设置右边布局中子TextView的文本内容
     *
     * @param index
     *     要设置的TextView在布局中索引，和当初添加时的索引一致
     * @param text
     *     文本内容
     *
     * @return
     */
    public ToolbarManager setRightTextFormIndex (int index, CharSequence text) {
        isEnabled ();
        View view = mRightLayout.getChildAt (index);
        if (view != null && view instanceof TextView) {
            ((TextView) view).setText (text);
        }
        return Singleton.INSTANCE;
    }

    /**
     * 设置右边布局中子view是否显示
     * @param index 子view索引
     * @param isShow  true,显示，false，隐藏
     * @return
     */
    public ToolbarManager setRightChildViewIsShow(int index,boolean isShow){
        isEnabled ();
        View view = mRightLayout.getChildAt (index);
        if (view!=null) {
            view.setVisibility (isShow?View.VISIBLE:View.GONE);
        }
        return Singleton.INSTANCE;
    }

    /**
     * 设置右边布局中子view的背景
     * @param index 子view索引
     * @param drawable  背景资源
     * @return
     */
    public ToolbarManager setRightChildViewBackground(int index,Drawable drawable){
        isEnabled ();
        View view = mRightLayout.getChildAt (index);
        if (view!=null) {
            view.setBackground (drawable);
        }
        return Singleton.INSTANCE;
    }

    /**
     * 设置右边布局中子view的点击事件
     *
     * @param index
     *     子view的索引
     * @param clickListener
     *     点击事件
     *
     * @return
     */
    public ToolbarManager setRightViewClickListener (int index,
        @NonNull View.OnClickListener clickListener) {
        isEnabled ();
        View view = mRightLayout.getChildAt (index);
        if (view != null) {
            view.setOnClickListener (clickListener);
        }
        return Singleton.INSTANCE;
    }

    /**
     * 设置左边图片点击事件
     *
     * @return
     */
    public ToolbarManager setLeftImageClickListener (@NonNull View.OnClickListener clickListener) {
        isEnabled ();
        mLeftImageView.setOnClickListener (clickListener);
        return Singleton.INSTANCE;
    }

    /**
     * 设置左边布局点击事件
     *
     * @return
     */
    public ToolbarManager setLeftLayoutClickListener (@NonNull View.OnClickListener clickListener) {
        isEnabled ();
        mLeftLayout.setOnClickListener (clickListener);
        return Singleton.INSTANCE;
    }

    /**
     * 设置左边文字点击事件
     *
     * @return
     */
    public ToolbarManager setLeftTextClickListener (@NonNull View.OnClickListener clickListener) {
        isEnabled ();
        mLeftTextView.setOnClickListener (clickListener);
        return Singleton.INSTANCE;
    }
}
