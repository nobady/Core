package com.android.core.sample.adapter;

import android.widget.ImageView;
import com.android.core.sample.App;
import com.android.core.sample.R;
import com.android.core.sample.bean.TestBean;
import com.bumptech.glide.Glide;
import com.library.adapter.BaseAdapter;
import com.library.adapter.BaseViewHolder;
import java.util.List;

/**
 * Created by tengfei.lv on 2016/12/29.
 */

public class TestAdapter extends BaseAdapter<TestBean> {

    public TestAdapter (List<TestBean> data) {
        super (R.layout.adapter_test_item);
        setData (data);
    }

    @Override protected void convert (BaseViewHolder baseViewHolder, TestBean testBean) {
        baseViewHolder.setText (R.id.tv_test_adapter_item, testBean.getTitle ());

        //((SimpleDraweeView) baseViewHolder.getView (R.id.image_test_adapter_item)).setImageURI (
        //    Uri.parse (testBean.getImage ()));


        Glide.with (App.mContext).load (testBean.getImage ()).override (200,200).into (
            (ImageView) baseViewHolder.getView (R.id.image_test_adapter_item));
    }
}
