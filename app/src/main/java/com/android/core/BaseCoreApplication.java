package com.android.core;

import android.app.Application;
import android.content.Context;
import com.apkfuns.logutils.LogLevel;
import com.apkfuns.logutils.LogUtils;

/**
 * Created by tengfei.lv on 2016/12/14.
 */

public class BaseCoreApplication extends Application {
    public static Context context;

    @Override public void onCreate () {
        super.onCreate ();
        context = this;

        LogUtils.getLogConfig()
            .configAllowLog(true)
            .configTagPrefix(getPackageName ())
            .configShowBorders(false)
            .configLevel(LogLevel.TYPE_VERBOSE);

    }
}
