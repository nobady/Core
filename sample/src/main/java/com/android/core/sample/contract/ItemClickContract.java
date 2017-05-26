package com.android.core.sample.contract;

import com.android.core.base.BaseCorePresenter;
import com.android.core.base.BaseCoreView;
import com.android.core.sample.bean.MeiziBean;
import com.android.core.sample.net.InterfaceManager;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import java.util.List;

/**
 * Created by tengfei.lv on 2017/1/4.
 */

public interface ItemClickContract {
    interface View extends BaseCoreView {

        void showLoadData (List<MeiziBean> meiziBeen);

        void loadMoreComplete ();

        void loadMoreFail ();
    }

    class Presenter extends BaseCorePresenter<View> {

        private int index = 1;

        public void getData () {
            if (!isViewAttach ()) {
                return;
            }
            getView ().showLoadProgress ();
            index = 1;

            mCompositeDisposable.add (
                InterfaceManager.getMeiZi (index).subscribe (new Consumer<List<MeiziBean>> () {
                    @Override public void accept (@NonNull List<MeiziBean> meiziBeen)
                        throws Exception {
                        getView ().showLoadSuccess ();
                        getView ().showLoadData (meiziBeen);
                    }
                }, new Consumer<Throwable> () {
                    @Override public void accept (@NonNull Throwable throwable) throws Exception {
                        getView ().showLoadSuccess ();
                        getView ().setErrorMsg ("出错了");
                        getView ().showLoadFailPager ();
                    }
                }));
        }

        public void getMoreData () {
            if (!isViewAttach ()) {
                return;
            }
            index++;
            mCompositeDisposable.add ( InterfaceManager.getMeiZi (index).subscribe (new Consumer<List<MeiziBean>> () {
                @Override public void accept (@NonNull List<MeiziBean> meiziBeen) throws Exception {
                    getView ().loadMoreComplete ();
                    getView ().showLoadData (meiziBeen);
                }
            }, new Consumer<Throwable> () {
                @Override public void accept (@NonNull Throwable throwable) throws Exception {
                    getView ().loadMoreFail();
                }
            }));
        }
    }
}