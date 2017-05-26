package com.android.core.sample.net;

import com.android.core.net.RxHttpUtils;
import com.android.core.sample.bean.JsonResult;
import com.android.core.sample.bean.MeiziBean;
import com.android.core.sample.bean.TestBean;
import com.android.core.sample.util.RxUtil;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by tengfei.lv on 2016/12/29.
 */

public class InterfaceManager {

    public static Observable<List<TestBean>> getData () {
        Observable<JsonResult<List<TestBean>>> lastDaily = RxHttpUtils.getInstance ()
            .setUrl ("http://news-at.zhihu.com")
            .createApi (Api.class)
            .getLastDaily ();
        return RxUtil.call (lastDaily);
    }

    public static Observable<List<MeiziBean>> getMeiZi(int index){
        Observable<JsonResult<List<MeiziBean>>> meiziData =
            RxHttpUtils.getInstance ().createApi (Api.class).getMeiziData (index);
        return RxUtil.call (meiziData);
    }
}
