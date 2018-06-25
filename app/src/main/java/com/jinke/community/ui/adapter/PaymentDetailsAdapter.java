package com.jinke.community.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.PaymentDetailsBean;
import com.jinke.community.utils.DecimalFormatUtils;
import com.jinke.community.utils.DrawableUtils;

import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2017/7/31.
 */

public class PaymentDetailsAdapter extends BaseAdapter {
    private List<PaymentDetailsBean.PayListBean> list;
    private Context mContext;

    @Override
    public int getCount() {
        return list.size();
    }

    public PaymentDetailsAdapter(Context mContext, List<PaymentDetailsBean.PayListBean> dataList) {
        this.mContext = mContext;
        this.list = dataList;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(List<PaymentDetailsBean.PayListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_payment_details, parent, false);
            holder = new ViewHolder();
            holder.txTitle = (TextView) convertView.findViewById(R.id.tx_title);
            holder.cost = (TextView) convertView.findViewById(R.id.payment_cost_text);
            holder.rlContent = (RelativeLayout) convertView.findViewById(R.id.rl_content);
            holder.item_current_number = (TextView) convertView.findViewById(R.id.item_current_number);
            holder.item_lave_number = (TextView) convertView.findViewById(R.id.item_lave_number);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PaymentDetailsBean.PayListBean bean = list.get(position);

        if (StringUtils.isEmpty(bean.getLave()) && bean.getCurrent() == 0.0) {//缴费、代扣
            holder.cost.setText("￥" + bean.getMoney());
            holder.cost.setVisibility(View.VISIBLE);
            holder.rlContent.setVisibility(View.GONE);
            holder.txTitle.setText(bean.getMonth() + bean.getProjectname());
        } else {//预存
            holder.cost.setVisibility(View.GONE);
            holder.rlContent.setVisibility(View.VISIBLE);
            holder.item_lave_number.setText("+" + DecimalFormatUtils.format(Double.parseDouble(bean.getMoney()), "0.00") + "元");//本次充值
            holder.item_current_number.setText(DecimalFormatUtils.format(Double.parseDouble(bean.getLave()), "0.00") + "元");//当前余额
            holder.txTitle.setText(bean.getProjectname());
        }
        switch (bean.getProjectType()) {//洋房物业服务费 0 ， 公摊服务费 1，  车位服务费 2，  标识3 参数类型未找到
            case 0:
                DrawableUtils.setDrawableLeft(mContext, holder.txTitle, R.mipmap.icon_property_costs);
                break;

            case 1:
                DrawableUtils.setDrawableLeft(mContext, holder.txTitle, R.mipmap.icon_water_and_electricity);
                break;

            case 2:
                DrawableUtils.setDrawableLeft(mContext, holder.txTitle, R.mipmap.icon_parking_fee);
                break;

            case 3:
                DrawableUtils.setDrawableLeft(mContext, holder.txTitle, R.mipmap.icon_property_costs);
                break;
        }
        return convertView;
    }

    ViewHolder holder;

    private class ViewHolder {
        private ImageView image;
        private TextView txTitle;
        private TextView cost;
        private View divider;
        private TextView item_current_number;
        private TextView item_lave_number;
        private RelativeLayout rlContent;
    }
}
