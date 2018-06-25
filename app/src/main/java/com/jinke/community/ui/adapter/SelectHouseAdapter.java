package com.jinke.community.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.PublicHouseBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;

import java.util.List;

/**
 * Created by root on 17-8-10.
 */

public class SelectHouseAdapter extends CommonAdapter<HouseListBean.ListBean> {
    private Context mContext;

    public SelectHouseAdapter(@NonNull Context context, int layoutResId, @NonNull List<HouseListBean.ListBean> dataList) {
        super(context, layoutResId, dataList);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HouseListBean.ListBean listBean, int position) {
        TextView name = (TextView) baseViewHolder.getViewByViewId(R.id.item_name);
        if (listBean.isSelect()){
            name.setBackgroundResource(R.mipmap.item_select_house_dialog_bg);
        }else {
            name.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        name.setTextColor(listBean.isSelect() ? mContext.getResources().getColor(R.color.activity_broke_news_color2) : mContext.getResources().getColor(R.color.activity_broke_news_color1));
        name.setText(listBean.getCommunity_name() + listBean.getHouse_name());
    }
}
