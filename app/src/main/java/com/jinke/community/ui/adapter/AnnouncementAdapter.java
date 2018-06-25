package com.jinke.community.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jinke.community.R;
import com.jinke.community.bean.BrokeNoticeListBean;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;
import com.jinke.community.utils.TextUtils;

import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-8-14.
 */

public class AnnouncementAdapter extends CommonAdapter<BrokeNoticeListBean.ListBean> {
    public AnnouncementAdapter(@NonNull Context context, int layoutResId, @NonNull List<BrokeNoticeListBean.ListBean> dataList) {
        super(context, layoutResId, dataList);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, BrokeNoticeListBean.ListBean listBean, int position) {
        ImageView headImage = (ImageView) baseViewHolder.getViewByViewId(R.id.image_item_head);
        TextView title = (TextView) baseViewHolder.getViewByViewId(R.id.tx_item_title);
        TextView describe = (TextView) baseViewHolder.getViewByViewId(R.id.tx_item_describe);
        TextView time = (TextView) baseViewHolder.getViewByViewId(R.id.tx_item_time);
        TextView txSeeAll = (TextView) baseViewHolder.getViewByViewId(R.id.tx_see_all);
        ImageView imageNew = (ImageView) baseViewHolder.getViewByViewId(R.id.image_new);

        /**
         * 根据类型放至图片
         */
        switch (listBean.getNoticeType()) {
            case "1":
                headImage.setImageResource(R.mipmap.icon_activity_property_dynamic_electricity);
                break;

            case "2":
                headImage.setImageResource(R.mipmap.icon_activity_property_dynamic_gas);
                break;

            case "3":
                headImage.setImageResource(R.mipmap.icon_activity_property_dynamic_water);
                break;

            case "4":
                headImage.setImageResource(R.mipmap.icon_activity_property_dynamic_other);
                break;

            case "5":
                headImage.setImageResource(R.mipmap.icon_activity_property_dynamic_elevator);
                break;

            case "7":
                headImage.setImageResource(R.mipmap.icon_activity_property_dynamic_kill);
                break;

            case "6"://报事保修进展
                headImage.setImageResource(R.mipmap.icon_activity_property_dynamic_progress);
                break;
        }
        title.setText(StringUtils.isEmpty(listBean.getTitle()) ? "暂无" : listBean.getTitle());
        describe.setText(listBean.getContent());
        try {
            time.setText(StringUtils.isEmpty(listBean.getCreateTime()) ? "暂无" : TextUtils.timeChangeBreakStage(listBean.getCreateTime()));
            imageNew.setVisibility(time.getText().toString().trim().contains("-") ? View.GONE : View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        txOverFlowed(describe, txSeeAll);
    }

    public void txOverFlowed(final TextView textView, final TextView txSeeAll) {
        ViewTreeObserver vto = textView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                textView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                textView.getHeight();
                double w0 = textView.getWidth();//控件宽度
                double w1 = textView.getPaint().measureText(textView.getText().toString());//文本宽度
                if (w1 >= w0) txSeeAll.setVisibility(View.VISIBLE);//需要换行就显示该控件

            }
        });
    }
}
