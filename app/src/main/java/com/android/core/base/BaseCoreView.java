package com.android.core.base;


/**
 * Created by tengfei.lv on 2016/11/25.
 */
public interface BaseCoreView extends MvpView {

    /*显示和隐藏加载的进度条*/
    void showLoadProgress();
    void hideLoadProgress();
    /*显示和隐藏加载对话框，一般情况下和进度条不会同时使用*/
    void showLoadDialog();
    void hideLoadDialog();

    void showLoadSuccess();
    void showLoadFailPager();
    void showEmptyPager();
    void showNoNetError();
    void showToast(CharSequence toastMsg);

    void setErrorMsg(String toastMsg);
    void setNotNetMsg(String toastMsg);
    void setEmptyMsg(String toastMsg);


}
