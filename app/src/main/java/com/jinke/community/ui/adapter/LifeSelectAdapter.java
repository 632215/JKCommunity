package com.jinke.community.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jinke.community.R;
import com.jinke.community.bean.acachebean.LifeTopBannerBean;
import com.jinke.community.ui.activity.web.LifeDetailsActivity;
import com.jinke.community.utils.AnalyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */

public class LifeSelectAdapter extends BaseAdapter {
    private List<LifeTopBannerBean.ListBean> list = new ArrayList<>();
    private Context mContext;

    @Override
    public int getCount() {
        return list.size();
    }

    public LifeSelectAdapter(Context mContext, List<LifeTopBannerBean.ListBean> dataList) {
        this.mContext = mContext;
        this.list = dataList;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(List<LifeTopBannerBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_select, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.life_select_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final LifeTopBannerBean.ListBean bean = list.get(position);

        Glide.with(mContext).load(bean.getCircleImageUrl())
                .error(R.drawable.icon_life_fail_bottom)
                .placeholder(R.drawable.icon_life_fail_bottom)
                .into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyUtils.addLifeAnaly(10026, bean.getId());
                Intent intent = new Intent(mContext, LifeDetailsActivity.class);
                intent.putExtra("url", bean.getCircleLinkUrl());
                intent.putExtra("title", bean.getTitle());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    ViewHolder holder;

    private class ViewHolder {
        private ImageView image;
    }
}
