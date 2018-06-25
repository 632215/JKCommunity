package com.jinke.community.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.CommunityGPSBean;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;
import com.jinke.community.utils.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by root on 17-8-1.
 */

public class SelectCommunityAdapter extends CommonAdapter<CommunityGPSBean.ListBean> {

    public SelectCommunityAdapter(@NonNull Context context, int layoutResId, @NonNull List<CommunityGPSBean.ListBean> dataList) {
        super(context, layoutResId, dataList);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CommunityGPSBean.ListBean bean, int position) {
        TextView commnuityName = (TextView) baseViewHolder.getViewByViewId(R.id.tx_community_name);
        TextView txDistance = (TextView) baseViewHolder.getViewByViewId(R.id.tx_distance);
        commnuityName.setText(bean.getName());
        txDistance.setVisibility(bean.getDistance() == 0 ? View.GONE : View.VISIBLE);
        BigDecimal bigDecimal = new BigDecimal((double) bean.getDistance() / 1000);
        txDistance.setText(bigDecimal.setScale(2, RoundingMode.UP).doubleValue() + "km");
    }
}
