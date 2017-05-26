package com.android.core.sample;

import android.content.Context;
import com.android.core.BaseCoreApplication;
import com.android.core.net.RxHttpUtils;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by tengfei.lv on 2016/12/29.
 */

public class App extends BaseCoreApplication {

    public static Context mContext;
    @Override public void onCreate () {
        super.onCreate ();

        mContext = this;
        RxHttpUtils.getmConfig().setBaseUrl ("http://gank.io/api/data/");
        Fresco.initialize (this);
    }
}
