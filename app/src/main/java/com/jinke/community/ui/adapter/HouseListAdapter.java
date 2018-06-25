package com.jinke.community.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.HouseBean;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/11/13.
 */

public class HouseListAdapter extends CommonAdapter<HouseBean.HouseListBean> {
    private int selectPosition ;
    private Context mContext;

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public HouseListAdapter(@NonNull Context context, @LayoutRes int layoutResId, @NonNull List<HouseBean.HouseListBean> dataList) {
        super(context, layoutResId, dataList);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HouseBean.HouseListBean houseListBean, final int position) {
        TextView tx_house = (TextView) baseViewHolder.getViewByViewId(R.id.tx_house);
        ImageView image_check = (ImageView) baseViewHolder.getViewByViewId(R.id.image_check);
        tx_house.setText(houseListBean.getArea() + houseListBean.getCommunity() + houseListBean.getHouseName());
        image_check.setImageResource(selectPosition == position ? R.mipmap.icon_bind_house_list_selected : R.mipmap.icon_bind_house_list_un_selected);
    }
}
