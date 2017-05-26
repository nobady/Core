package com.android.core.sample.contract;

import com.android.core.base.BaseCorePresenter;
import com.android.core.base.BaseCoreView;
import com.android.core.sample.bean.TestBean;
import com.android.core.sample.net.InterfaceManager;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import java.util.List;

/**
 * Created by tengfei.lv on 2016/12/29.
 */

public interface TestContract {
    interface View extends BaseCoreView{

        void setData (List<TestBean> testBeen);
    }

    class Presenter extends BaseCorePresenter<TestContract.View>{

        public void getData () {
            if (!isViewAttach ()) {
                return;
            }
            getView ().showLoadProgress ();
            mCompositeDisposable.add (InterfaceManager.getData ().subscribe (new Consumer<List<TestBean>> () {
                @Override public void accept (@NonNull List<TestBean> testBeen) throws Exception {
                    getView ().hideLoadProgress ();
                    getView ().setData(testBeen);
                }
            }, new Consumer<Throwable> () {
                @Override public void accept (@NonNull Throwable throwable) throws Exception {
                    getView ().hideLoadProgress ();
                    getView ().showLoadFailPager ();
                }
            }));
        }
    }
}
