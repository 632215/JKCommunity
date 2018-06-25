package com.jinke.community.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.PaymentVehicleDetailsBean;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;
import com.jinke.community.utils.DecimalFormatUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */

public class PaymentVehicleDetailsAdapter extends CommonAdapter<PaymentVehicleDetailsBean.CarListBean> {

    public PaymentVehicleDetailsAdapter(@NonNull Context context, @LayoutRes int layoutResId, @NonNull List<PaymentVehicleDetailsBean.CarListBean> dataList) {
        super(context, layoutResId, dataList);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, PaymentVehicleDetailsBean.CarListBean bean, int position) {
        TextView txParkingSpace = (TextView) baseViewHolder.getViewByViewId(R.id.tx_parking_space); //车位号
        TextView txPaymentCost = (TextView) baseViewHolder.getViewByViewId(R.id.tx_payment_cost); //总额
        TextView txRecharge = (TextView) baseViewHolder.getViewByViewId(R.id.tx_recharge); //充值：
        TextView txTime = (TextView) baseViewHolder.getViewByViewId(R.id.tx_time); //有效期

        txParkingSpace.setText(bean.getPlate_no());
        txPaymentCost.setText("+" + DecimalFormatUtils.format(Double.parseDouble(bean.getPrices()),"0.00"));
        txRecharge.setText("充值：" + bean.getPay_month() + "个月（" + DecimalFormatUtils.format(Double.parseDouble(bean.getStandard_price()),"0.00") + "元/月）");

        if (bean.getPark_end_time() != null && bean.getPark_end_time() != "") {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date(Long.parseLong(bean.getPark_end_time())*1000);
            txTime.setText(sdf.format(date));
        } else {
            txTime.setText("");
        }
    }
}
