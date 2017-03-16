package com.android.core;

import android.app.Application;
import com.apkfuns.logutils.LogLevel;
import com.apkfuns.logutils.LogUtils;

/**
 * Created by tengfei.lv on 2016/12/14.
 */

public class BaseCoreApplication extends Application {

    @Override public void onCreate () {
        super.onCreate ();

        LogUtils.getLogConfig()
            .configAllowLog(true)
            .configTagPrefix(getPackageName ())
            .configShowBorders(false)
            .configLevel(LogLevel.TYPE_VERBOSE);

    }
}
