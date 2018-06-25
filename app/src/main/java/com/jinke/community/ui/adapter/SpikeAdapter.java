package com.jinke.community.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jinke.community.R;
import com.jinke.community.bean.acachebean.LifeTopBannerBean;
import com.jinke.community.ui.activity.web.LifeDetailsActivity;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;
import com.jinke.community.utils.AnalyUtils;

import java.util.List;

/**
 * Created by root on 17-8-16.
 */

public class SpikeAdapter extends CommonAdapter<LifeTopBannerBean.ListBean> {
    Context context;

    public SpikeAdapter(@NonNull Context context, int layoutResId, @NonNull List<LifeTopBannerBean.ListBean> dataList) {
        super(context, layoutResId, dataList);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final LifeTopBannerBean.ListBean listBean, int position) {
        ImageView imageView = (ImageView) baseViewHolder.getViewByViewId(R.id.item_spike);
        TextView line = (TextView) baseViewHolder.getViewByViewId(R.id.tx_line);
        line.setVisibility(listBean.isVisibility() ? View.VISIBLE : View.GONE);
        Glide.with(context).load(listBean.getCircleImageUrl())
                .error(R.drawable.icon_life_fail_spike)
                .placeholder(R.drawable.icon_life_fail_spike)
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyUtils.addLifeAnaly(10026, listBean.getId());
                Intent intent = new Intent(context, LifeDetailsActivity.class);
                intent.putExtra("url", listBean.getCircleLinkUrl());
                intent.putExtra("title", listBean.getTitle());
                context.startActivity(intent);
            }
        });
    }
}

