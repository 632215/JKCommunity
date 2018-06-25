package com.jinke.community.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.FillGridViewBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */

public class VisitPurposeAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context mContext;
    private List<FillGridViewBean> listReady=new ArrayList<>();
    private List<FillGridViewBean> list = new ArrayList<>();

    public VisitPurposeAdapter(Context mContext, List<FillGridViewBean> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(List<FillGridViewBean> list) {
        this.list = list;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_select_purpose, null);
            holder = new ViewHolder();
            holder.purpose = (TextView) convertView.findViewById(R.id.tx_purpose);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FillGridViewBean purposeBean = list.get(position);
        holder.purpose.setText(purposeBean.getPurpose());
        holder.purpose.setBackground(purposeBean.isSelecter() ? mContext.getResources().getDrawable(R.drawable.shape_grid_text_blue_bg)
                : mContext.getResources().getDrawable(R.drawable.shape_grid_text_gray_bg));
        holder.purpose.setTextColor(purposeBean.isSelecter() ? mContext.getResources().getColor(R.color.activity_visitor_pass_text_color3)
                : mContext.getResources().getColor(R.color.activity_visitor_pass_text_color4));
        return convertView;
    }

    ViewHolder holder;

    class ViewHolder {
        TextView purpose;
    }
}
