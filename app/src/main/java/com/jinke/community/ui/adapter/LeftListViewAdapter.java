package com.jinke.community.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.CityBean;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;

import java.util.List;

/**
 * Created by root on 17-8-1.
 */

public class LeftListViewAdapter extends CommonAdapter<CityBean.ListBean> {

    private Context context;
    private List<CityBean.ListBean> dataList;

    public LeftListViewAdapter(@NonNull Context context, int layoutResId, @NonNull List<CityBean.ListBean> dataList) {
        super(context, layoutResId, dataList);
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final CityBean.ListBean bean, int position) {
        TextView cityName = (TextView) baseViewHolder.getViewByViewId(R.id.tx_city_name);
        cityName.setText(bean.getName());
        cityName.setTextColor(context.getResources().getColor(bean.isSelet() ? R.color.main_them_color : R.color.main_present_text_black));
        cityName.setBackground(context.getResources().getDrawable(bean.isSelet() ? R.drawable.shape_left_listview_tx_bg : R.drawable.shape_left_list_un_select_bg));

    }
}
