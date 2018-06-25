package com.jinke.community.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jinke.community.R;
import com.jinke.community.bean.BrokeNoticeListBean;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;
import com.jinke.community.utils.FrescoUtils;
import com.jinke.community.utils.TextUtils;

import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-8-14.
 */

public class DynamicPropertyAdapter extends CommonAdapter<BrokeNoticeListBean.ListBean> {
    private Context mContext;

    public DynamicPropertyAdapter(@NonNull Context context, int layoutResId, @NonNull List<BrokeNoticeListBean.ListBean> dataList) {
        super(context, layoutResId, dataList);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, BrokeNoticeListBean.ListBean listBean, int position) {
        SimpleDraweeView headImage = (SimpleDraweeView) baseViewHolder.getViewByViewId(R.id.image_item_head);
        TextView title = (TextView) baseViewHolder.getViewByViewId(R.id.tx_item_title);
        TextView describe = (TextView) baseViewHolder.getViewByViewId(R.id.tx_item_describe);
        TextView time = (TextView) baseViewHolder.getViewByViewId(R.id.tx_item_time);
        title.setText(listBean.getManager());
        describe.setText(listBean.getTitle());
        time.setText(StringUtils.isEmpty(listBean.getCreateTime())?"暂无": TextUtils.getStringToDate(listBean.getCreateTime()));

        if (listBean.getType() == 1) {
            FrescoUtils.loadResPic(mContext, headImage, R.drawable.icon_home_notifaction);
        } else {
            headImage.setImageURI(listBean.getAvatar());
        }
    }
}
