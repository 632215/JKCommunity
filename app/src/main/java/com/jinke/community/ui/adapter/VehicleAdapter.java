package com.jinke.community.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.UserCarBean;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 */

public class VehicleAdapter extends CommonAdapter<UserCarBean.ListBean> {
    private BanVehicleListener listener;

    public VehicleAdapter(@NonNull Context context, @LayoutRes int layoutResId, @NonNull List<UserCarBean.ListBean> dataList, BanVehicleListener listener) {
        super(context, layoutResId, dataList);
        this.listener = listener;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final UserCarBean.ListBean dataBean, final int position) {
        TextView verhicleNum = (TextView) baseViewHolder.getViewByViewId(R.id.tx_verhicle_num);
        TextView verhicleState = (TextView) baseViewHolder.getViewByViewId(R.id.tx_verhicle_state);
        ImageView imageBan = (ImageView) baseViewHolder.getViewByViewId(R.id.image_ban);
        verhicleNum.setText(dataBean.getCar_CarNo());
        switch (dataBean.getParkOrder_Lock()) {
            case "0"://0 未锁定 1锁定 2未入场
                verhicleState.setVisibility(View.GONE);
                imageBan.setVisibility(View.VISIBLE);
                imageBan.setImageResource(R.drawable.icon_verhicle_un_ban);
                break;

            case "1":
                verhicleState.setVisibility(View.GONE);
                imageBan.setVisibility(View.VISIBLE);
                imageBan.setImageResource(R.drawable.icon_verhicle_ban);
                break;

            case "2":
                verhicleState.setVisibility(View.VISIBLE);
                imageBan.setVisibility(View.GONE);
                break;
        }
        imageBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (dataBean.getParkOrder_Lock()) {
                    case "0"://0 未锁定 1锁定 2未入场
                    case "2":
                        listener.onBanClick(position);
                        break;
                    case "1":
                        listener.onUnBanClick(position);
                        break;
                }
            }
        });
    }

    public interface BanVehicleListener {
        void onBanClick(int positon);

        void onUnBanClick(int position);
    }
}
