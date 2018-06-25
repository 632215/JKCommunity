package com.jinke.community.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.PaymentRecordBean;
import com.jinke.community.utils.DecimalFormatUtils;

import java.util.ArrayList;
import java.util.List;

import www.jinke.com.library.utils.commont.LogUtils;

/**
 * Created by Administrator on 2017/7/31.
 */

public class PaymentRecorderAdapter extends BaseAdapter {
    private List<PaymentRecordBean.ListBean> list = new ArrayList<>();
    private Context mContext;
    RecorderOnClickListener recorderOnClickListener;

    @Override
    public int getCount() {
        return list.size();
    }

    public PaymentRecorderAdapter(Context mContext, List<PaymentRecordBean.ListBean> dataList, RecorderOnClickListener recorderOnClickListener) {
        this.mContext = mContext;
        this.list = dataList;
        this.recorderOnClickListener = recorderOnClickListener;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(List<PaymentRecordBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_payment_recorder, parent, false);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.date_text);
            holder.money = (TextView) convertView.findViewById(R.id.money_text);
            holder.method = (TextView) convertView.findViewById(R.id.pay_method_text);
            holder.payment_state = (TextView) convertView.findViewById(R.id.payment_state_text);
            holder.layout = (RelativeLayout) convertView.findViewById(R.id.layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final PaymentRecordBean.ListBean bean = list.get(position);
        if (bean != null) {
            holder.date.setText(bean.getPay_time().substring(5));
            switch (bean.getPay_name()) {
                case "预存":
                    holder.payment_state.setBackgroundResource(R.mipmap.icon_pay_recorder_presave);
                    holder.money.setText("+" + DecimalFormatUtils.format(Double.parseDouble(bean.getTotal_money()), "0.00"));
                    break;

                case "车位":
                    holder.money.setText("+" + DecimalFormatUtils.format(Double.parseDouble(bean.getTotal_money()), "0.00"));
                    holder.payment_state.setBackgroundResource(R.mipmap.icon_pay_recorder_recharge);
                    break;

                case "代扣":
                case "扣费":
                case "缴费":
                    holder.money.setText("-" + DecimalFormatUtils.format(Double.parseDouble(bean.getTotal_money()), "0.00"));
                    holder.payment_state.setBackgroundResource(R.mipmap.icon_pay_recorder_pay);
                    break;
            }

            holder.payment_state.setText(bean.getPay_name());
            switch (bean.getPay_type()) {
                case "mp_wxpay":
                    holder.method.setText(R.string.payment_details_pay_method_wechat_public);
                    break;
                case "mp_alipay":
                    holder.method.setText(R.string.payment_details_pay_method_ali_public);
                    break;
                case "app_wxpay":
                    holder.method.setText(R.string.payment_details_pay_method_wechat_app);
                    break;
                case "app_alipay":
                case "alipay":
                    holder.method.setText(R.string.payment_details_pay_method_ali_app);
                    break;
            }
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recorderOnClickListener.onRecorderClick(bean);
                }
            });
        }
        return convertView;
    }

    ViewHolder holder;

    private class ViewHolder {
        private TextView date;
        private TextView money;
        private TextView method;
        private TextView payment_state;
        private RelativeLayout layout;
    }

    public static interface RecorderOnClickListener {
        void onRecorderClick(PaymentRecordBean.ListBean bean);
    }
}
