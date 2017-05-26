package com.android.core.sample.net;

import com.android.core.sample.bean.JsonResult;
import com.android.core.sample.bean.MeiziBean;
import com.android.core.sample.bean.TestBean;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by tengfei.lv on 2016/12/29.
 */

public interface Api {

    @GET ("/api/4/news/latest") Observable<JsonResult<List<TestBean>>> getLastDaily ();

    @GET ("Android/10/{index}")
    Observable<JsonResult<List<MeiziBean>>> getMeiziData (@Path ("index") int index);
}
