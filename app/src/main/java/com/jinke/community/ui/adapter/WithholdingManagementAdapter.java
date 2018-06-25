package com.jinke.community.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.HouseWithHoldBean;
import com.jinke.community.service.listener.WithholdingManagementListener;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;
import com.jinke.community.ui.toast.WithHoldWindow;
import com.jinke.community.utils.TextUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/11/22.
 */

public class WithholdingManagementAdapter extends CommonAdapter<HouseWithHoldBean.ListBean> {
    private Context mContext;
    private HouseWithHoldBean.ListBean listBean;
    private ItemWithholdingManagementListener listener;
    private WithHoldWindow withHoldWindow;

    public WithholdingManagementAdapter(@NonNull Context context, @LayoutRes int layoutResId, @NonNull List<HouseWithHoldBean.ListBean> dataList) {
        super(context, layoutResId, dataList);
        mContext = context;
    }

    public void setListener(ItemWithholdingManagementListener listener) {
        this.listener = listener;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final HouseWithHoldBean.ListBean listBean, int position) {
        TextView txHouseTitle = (TextView) baseViewHolder.getViewByViewId(R.id.tx_house_title);
        TextView txHouseOwner = (TextView) baseViewHolder.getViewByViewId(R.id.tx_house_owner);
        TextView txMethod = (TextView) baseViewHolder.getViewByViewId(R.id.tx_method);//代扣方式
        final TextView txOperateWithholding = (TextView) baseViewHolder.getViewByViewId(R.id.tx_operate_withholding);//代扣签约、解约
        TextView txRecorderDetail = (TextView) baseViewHolder.getViewByViewId(R.id.tx_recorder_detail);//代扣记录/待缴费
        RelativeLayout rlRecorderDetail = (RelativeLayout) baseViewHolder.getViewByViewId(R.id.rl_recorder_detail);//代扣记录/待缴费

        this.listBean = listBean;
        txHouseTitle.setText(listBean.getCommunity_name() + listBean.getHouse_name());
        String ownerString = "";
        for (HouseWithHoldBean.ListBean.OwnersBean ownerBean : listBean.getOwners()) {
            ownerString += ownerBean.getOwnerName() + "  " + TextUtils.changTelNum(ownerBean.getPhone()) + "   ";
        }
        txHouseOwner.setText(ownerString);

        txMethod.setText(listBean.getSign_status().getAli_sign_status() == 1 ? mContext.getResources().getString(R.string.activity_withholding_management_ali)
                : listBean.getSign_status().getWx_sign_status() == 1 ? mContext.getResources().getString(R.string.activity_withholding_management_wechat) : "");


        txRecorderDetail.setText(listBean.getSign_status().getAli_sign_status() == 1 ? mContext.getResources().getString(R.string.activity_withholding_management_recorder)
                : listBean.getSign_status().getWx_sign_status() == 1 ? mContext.getResources().getString(R.string.activity_withholding_management_recorder)
                : mContext.getResources().getString(R.string.activity_withholding_management_detail));

        switch (listBean.getSign_status().getUser_sign_status()) {
            case 0:
                if (listBean.getSign_status().getAli_sign_status() == 1 || listBean.getSign_status().getWx_sign_status() == 1) {
                    txOperateWithholding.setTextColor(mContext.getResources().getColor(R.color.activity_withholding_management_color5));
                    txOperateWithholding.setText(mContext.getResources().getString(R.string.activity_withholding_management_operate_out));
                } else {
                    txOperateWithholding.setTextColor(mContext.getResources().getColor(R.color.activity_withholding_management_color4));
                    txOperateWithholding.setText(mContext.getResources().getString(R.string.activity_withholding_management_operate_in));
                }
                txOperateWithholding.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listBean.getSign_status().getAli_sign_status() == 1 || listBean.getSign_status().getWx_sign_status() == 1) {
                            if (withHoldWindow == null) {
                                withHoldWindow = new WithHoldWindow(mContext);
                            }
                            withHoldWindow.setListener(new WithHoldWindow.OnSureListener() {
                                @Override
                                public void sureDelete() {
                                    /**
                                     * 解约
                                     */
                                    listener.closeWithHold(listBean);
                                }
                            });
                            withHoldWindow.showPopWindow(v);
                        } else {
                            /**
                             * 开通代扣
                             */
                            listener.openWithHold(listBean);
                        }
                    }
                });
                break;

            case 1:
                txOperateWithholding.setTextColor(mContext.getResources().getColor(R.color.activity_withholding_management_color5));
                txOperateWithholding.setText(mContext.getResources().getString(R.string.activity_withholding_management_operate_ing));
                break;
        }
        rlRecorderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 跳转界面代扣记录/待缴费
                 */
                listener.recorderClick(listBean);
            }
        });
    }

    public interface ItemWithholdingManagementListener {
        void recorderClick(HouseWithHoldBean.ListBean listBean);

        void openWithHold(HouseWithHoldBean.ListBean listBean);

        void closeWithHold(HouseWithHoldBean.ListBean listBean);

    }
}
