package com.android.core.sample.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.android.core.base.BaseMvpCoreActivity;
import com.android.core.sample.R;
import com.android.core.sample.adapter.MultiItemAdapter;
import com.android.core.sample.bean.MeiziBean;
import com.android.core.sample.contract.ItemClickContract;
import com.library.adapter.BaseAdapter;
import java.util.List;

/**
 * Created by tengfei.lv on 2017/1/5.
 */

public class MultiItemActivity
    extends BaseMvpCoreActivity<ItemClickContract.Presenter>
    implements ItemClickContract.View, BaseAdapter.OnLoadMoreListener {
    @BindView (R.id.recycler) RecyclerView mRecycler;
    private MultiItemAdapter mMAdapter;

    @Override protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_itemclick);
        ButterKnife.bind (this);
        setView ();
        presenter.getData ();
    }

    @Override protected ItemClickContract.Presenter createPresenter () {
        return new ItemClickContract.Presenter ();
    }

    private void setView () {
        mRecycler.setLayoutManager (new LinearLayoutManager (this));
        mRecycler.setHasFixedSize (true);
    }

    @Override public void onReloadClick () {
        presenter.getData ();
    }



    @Override public void showLoadData (List<MeiziBean> meiziBeen) {
        if (mMAdapter == null) {
            mMAdapter = new MultiItemAdapter ();
            mMAdapter.setData (meiziBeen);
            mMAdapter.setEnableLoadMore (true);
            mMAdapter.setLoadMoreListener (this);
            mRecycler.setAdapter (mMAdapter);
        } else {
            mMAdapter.addAll (meiziBeen);
        }
    }

    @Override public void loadMoreComplete () {
        mMAdapter.loadMoreComplete ();
    }

    @Override public void loadMoreFail () {
        mMAdapter.loadMoreFail ();
    }

    @Override public void loadMoreRequest () {
        presenter.getMoreData ();
    }
}
