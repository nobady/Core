package com.android.core.sample.adapter;

import android.text.Html;
import com.android.core.sample.R;
import com.android.core.sample.bean.MeiziBean;
import com.library.adapter.BaseAdapter;
import com.library.adapter.BaseViewHolder;

/**
 * Created by tengfei.lv on 2017/1/4.
 */

public class ItemClickAdapter extends BaseAdapter<MeiziBean> {

    public ItemClickAdapter () {
        super (R.layout.adapter_item_item_click);
    }


    @Override protected void convert (BaseViewHolder holder, MeiziBean meiziBean) {
        holder.setText (R.id.authorTv, "作者：" + meiziBean.getWho ())
            .setText (R.id.publishDataTv, "发布日期：" + meiziBean.getPublishedAt ())
            .setText (R.id.descTv,"功能描述"+meiziBean.getDesc ())
            .setText (R.id.urlTv,"链接："+ Html.fromHtml (meiziBean.getUrl ()))
            .addOnClickListener (R.id.descTv);
    }
}
