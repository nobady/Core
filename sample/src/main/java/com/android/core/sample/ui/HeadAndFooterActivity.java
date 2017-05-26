package com.android.core.sample.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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

/**
 * 添加头部和尾部view
 * Created by tengfei.lv on 2017/1/5.
 */

public class HeadAndFooterActivity
    extends BaseMvpCoreActivity< ItemClickContract.Presenter>
    implements ItemClickContract.View {

    @BindView (R.id.recycler) RecyclerView mRecycler;
    private ItemClickAdapter mAdapter;

    private TextView headView1;
    private TextView headView2;
    private TextView footView1;

    @Override protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_itemclick);
        ButterKnife.bind (this);
        setView ();
        presenter.getData ();
    }

    private void setView () {
        mRecycler.setLayoutManager (new GridLayoutManager (this,3));
        mRecycler.setHasFixedSize (true);

        headView1 = new TextView (this);
        headView1.setText ("头部view1");
        headView1.setBackgroundColor (Color.GREEN);
        headView1.setTextSize (15);

        headView2 = new TextView (this);
        headView2.setText ("头部view2");
        headView2.setTextSize (15);

        footView1 = new TextView (this);
        footView1.setText ("底部view1");
        footView1.setTextSize (15);
    }

    @Override public void onReloadClick () {
        presenter.getData ();
    }

     @Override protected ItemClickContract.Presenter createPresenter () {
        return new ItemClickContract.Presenter ();
    }

    @Override public void showLoadData (List<MeiziBean> meiziBeen) {
        if (mAdapter == null) {
            mAdapter = new ItemClickAdapter ();
            mAdapter.setData (meiziBeen);

            mAdapter.addHeadView (headView1);
            mAdapter.addHeadView (headView2);

            mAdapter.addFootView (footView1);

            mAdapter.setEnableLoadMore (true);
            mRecycler.setAdapter (mAdapter);
        } else {
            mAdapter.addAll (meiziBeen);
        }
    }

    @Override public void loadMoreComplete () {
        mAdapter.loadMoreComplete ();
    }

    @Override public void loadMoreFail () {
        mAdapter.loadMoreFail ();
    }
}
