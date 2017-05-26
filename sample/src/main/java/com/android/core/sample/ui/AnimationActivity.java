package com.android.core.sample.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.android.core.base.BaseMvpCoreActivity;
import com.android.core.sample.R;
import com.android.core.sample.adapter.ItemClickAdapter;
import com.android.core.sample.bean.MeiziBean;
import com.android.core.sample.contract.ItemClickContract;
import java.util.List;

public class AnimationActivity
    extends BaseMvpCoreActivity<ItemClickContract.Presenter>
    implements ItemClickContract.View {

    @BindView (R.id.recycler) RecyclerView mRecycler;
    private ItemClickAdapter mAdapter;

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

        mAdapter = new ItemClickAdapter ();

        TextView headView1 = new TextView (this);
        headView1.setText ("头部view1");
        headView1.setBackgroundColor (Color.GREEN);
        headView1.setTextSize (15);

        mAdapter.setEnableLoadMore (true);
        mAdapter.addHeadView (headView1);

        mRecycler.setAdapter (mAdapter);
    }

    @Override public void onReloadClick () {
        presenter.getData ();
    }



    @Override public void showLoadData (List<MeiziBean> meiziBeen) {
        mAdapter.addAll (meiziBeen);
    }

    @Override public void loadMoreComplete () {
        mAdapter.loadMoreComplete ();
    }

    @Override public void loadMoreFail () {
        mAdapter.loadMoreFail ();
    }

}
