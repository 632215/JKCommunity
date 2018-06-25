package com.jinke.community.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.AuthorizedRecordBean;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2017/10/17.
 */

public class AuthorizeAdapter extends CommonAdapter<AuthorizedRecordBean.ListBean> {
    private AuthorizedVehicleListener listener;

    public AuthorizeAdapter(@NonNull Context context, @LayoutRes int layoutResId, @NonNull List<AuthorizedRecordBean.ListBean> dataList, AuthorizedVehicleListener listener) {
        super(context, layoutResId, dataList);
        this.listener = listener;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final AuthorizedRecordBean.ListBean bean, final int position) {
        final ImageView selectState = (ImageView) baseViewHolder.getViewByViewId(R.id.image_select_state);
        TextView verhicleNum = (TextView) baseViewHolder.getViewByViewId(R.id.tx_verhicle_num);
        TextView txTime = (TextView) baseViewHolder.getViewByViewId(R.id.tx_time);
        TextView txCommunityName = (TextView) baseViewHolder.getViewByViewId(R.id.tx_community_name);
        ImageView authorizeState = (ImageView) baseViewHolder.getViewByViewId(R.id.tx_authorize_state);
        RelativeLayout relativeLayout = (RelativeLayout) baseViewHolder.getViewByViewId(R.id.rl_verhicle_authorized);
        selectState.setImageResource(bean.getDelete_remark().equals("true") ? R.drawable.icon_verhicle_selected : R.drawable.icon_verhicle_un_selected);

        verhicleNum.setText(bean.getReserveOrder_CarNO());
        String stringTime = bean.getReserveOrder_ReserveTime().toString().trim().replace("-", "/");
        txTime.setText("授权有效期至：" + stringTime.substring(0, stringTime.lastIndexOf(":")));
        if (!StringUtils.isEmpty(bean.getCommunityName()))
            txCommunityName.setText("授权进入：" + bean.getCommunityName());
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    switch (bean.getDelete_remark()) {
                        case "true":
                            selectState.setImageResource(R.drawable.icon_verhicle_un_selected);
                            bean.setDelete_remark("false");
                            listener.onAuthorizedVehicleItemClick(position, "false");
                            break;
                        case "false":
                            selectState.setImageResource(R.drawable.icon_verhicle_selected);
                            bean.setDelete_remark("true");
                            listener.onAuthorizedVehicleItemClick(position, "true");
                            break;
                    }
                }
            }
        });
    }

    public interface AuthorizedVehicleListener {
        void onAuthorizedVehicleItemClick(int positoin, String aFalse);
    }
}
