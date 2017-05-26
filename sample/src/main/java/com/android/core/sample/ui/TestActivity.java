package com.android.core.sample.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.android.core.base.BaseMvpCoreActivity;
import com.android.core.sample.R;
import com.android.core.sample.adapter.TestAdapter;
import com.android.core.sample.bean.TestBean;
import com.android.core.sample.contract.TestContract;
import java.util.List;

/**
 * Created by tengfei.lv on 2016/12/29.
 */

public class TestActivity extends BaseMvpCoreActivity<TestContract.Presenter>
    implements TestContract.View {

    private RecyclerView mRecyclerView;

    @Override protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_test);
        initView ();

        initData ();
    }

    private void initData () {
        presenter.getData ();
    }

    private void initView () {
        mRecyclerView = (RecyclerView) findViewById (R.id.recycler);
        mRecyclerView.setLayoutManager (new LinearLayoutManager (this));
        mRecyclerView.setHasFixedSize (true);
    }

    @Override public void onReloadClick () {
        initData ();
    }

    @NonNull @Override public TestContract.Presenter createPresenter () {
        return new TestContract.Presenter ();
    }

    @Override public void setData (List<TestBean> testBeen) {
        testBeen.addAll (testBeen);
        testBeen.addAll (testBeen);
        testBeen.addAll (testBeen);
        TestAdapter mAdapter = new TestAdapter (testBeen);
        mRecyclerView.setAdapter (mAdapter);
    }
}
