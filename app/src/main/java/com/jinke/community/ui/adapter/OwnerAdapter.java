package com.jinke.community.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.acachebean.HouseListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */

public class OwnerAdapter extends BaseAdapter {
    private List<HouseListBean.ListBean.OwnersBean> list = new ArrayList<>();
    private Context mContext;

    @Override
    public int getCount() {
        return list.size();
    }

    public OwnerAdapter(Context mContext, List<HouseListBean.ListBean.OwnersBean> dataList) {
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

    public void setData(List<HouseListBean.ListBean.OwnersBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ower, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name_text);
            holder.phone = (TextView) convertView.findViewById(R.id.phone_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HouseListBean.ListBean.OwnersBean bean = list.get(position);
        holder.name.setText(bean.getOwnerName());
        holder.phone.setText(bean.getPhone());
        return convertView;
    }

    ViewHolder holder;

    private class ViewHolder {
        private TextView name;
        private TextView phone;
    }
}
