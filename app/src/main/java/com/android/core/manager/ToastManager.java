package com.android.core.manager;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by tengfei.lv on 2016/11/30.
 */

public class ToastManager {

    private static Toast toast;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (null == toast) {
            toast = Toast.makeText(context.getApplicationContext (), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (null == toast) {
            toast = Toast.makeText(context.getApplicationContext (), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (null == toast) {
            toast = Toast.makeText(context.getApplicationContext (), message, Toast.LENGTH_LONG);
        } else {
            toast.setText(message);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (null == toast) {
            toast = Toast.makeText(context.getApplicationContext (), message, Toast.LENGTH_LONG);
        } else {
            toast.setText(message);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (null == toast) {
            toast = Toast.makeText(context.getApplicationContext (), message, duration);
        } else {
            toast.setText(message);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        if (null == toast) {
            toast = Toast.makeText(context.getApplicationContext (), message, duration);
        } else {
            toast.setText(message);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /** Hide the toast, if any. */
    public static void hideToast() {
        if (null != toast) {
            toast.cancel();
        }
    }

    public static void showShortTop(Context context, CharSequence message) {
        if (null == toast) {
            toast = Toast.makeText(context.getApplicationContext (), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.setGravity(Gravity.CENTER|Gravity.TOP, 0, 250);
        toast.show();
    }

    public static void showLongTop(Context context, CharSequence message) {
        if (null == toast) {
            toast = Toast.makeText(context.getApplicationContext (), message, Toast.LENGTH_LONG);
        } else {
            toast.setText(message);
        }
        toast.setGravity(Gravity.CENTER|Gravity.TOP, 0, 250);
        toast.show();
    }
}
