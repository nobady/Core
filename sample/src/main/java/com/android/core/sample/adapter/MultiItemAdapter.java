package com.android.core.sample.adapter;

import android.text.Html;
import com.android.core.sample.R;
import com.android.core.sample.bean.MeiziBean;
import com.library.adapter.BaseAdapter;
import com.library.adapter.BaseViewHolder;

/**
 * Created by tengfei.lv on 2017/1/4.
 */

public class MultiItemAdapter extends BaseAdapter<MeiziBean> {

    public MultiItemAdapter () {
        super ();
        addItemType (0, R.layout.adapter_item_item_click);
        addItemType (1, R.layout.adapter_muliitem);
    }

    @Override protected void convert (BaseViewHolder baseViewHolder, final MeiziBean meiziBean) {
        switch (baseViewHolder.getItemViewType ()) {
            case 0:
                baseViewHolder.setText (R.id.authorTv, "作者：" + meiziBean.getWho ())
                    .setText (R.id.publishDataTv, "发布日期：" + meiziBean.getPublishedAt ())
                    .setText (R.id.descTv, "功能描述" + meiziBean.getDesc ())
                    .setText (R.id.urlTv, "链接：" + Html.fromHtml (meiziBean.getUrl ()))
                    .addOnClickListener (R.id.descTv);
                break;
            case 1:
                break;
        }
    }

    @Override protected int getUseItemViewType (MeiziBean meiziBean) {
        return meiziBean.getItemType ();
    }
}
