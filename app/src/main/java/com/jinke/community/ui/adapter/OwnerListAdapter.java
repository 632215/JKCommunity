package com.jinke.community.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;

import java.util.List;

/**
 * Created by root on 17-8-7.
 */

public class OwnerListAdapter extends CommonAdapter<HouseListBean.ListBean.OwnersBean> {

    public OwnerListAdapter(@NonNull Context context, int layoutResId, @NonNull List<HouseListBean.ListBean.OwnersBean> dataList) {
        super(context, layoutResId, dataList);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HouseListBean.ListBean.OwnersBean ownersBean, int position) {
        TextView owner_name = (TextView) baseViewHolder.getViewByViewId(R.id.owner_name);
        TextView owner_phone = (TextView) baseViewHolder.getViewByViewId(R.id.owner_phone);
        owner_name.setText(ownersBean.getOwnerName());
        owner_phone.setText(ownersBean.getPhone());
    }
}
