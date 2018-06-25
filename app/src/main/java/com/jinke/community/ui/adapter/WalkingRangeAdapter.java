package com.jinke.community.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jinke.community.R;
import com.jinke.community.bean.WorkingSortBean;
import com.jinke.community.utils.DrawableUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */

public class WalkingRangeAdapter extends BaseAdapter {
    private List<WorkingSortBean.ListBean> list;
    private Context mContext;

    @Override
    public int getCount() {
        return list.size();
    }

    public WalkingRangeAdapter(Context mContext, List<WorkingSortBean.ListBean> dataList) {
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

    public void setData(List<WorkingSortBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_walkingrange, parent, false);
            holder = new ViewHolder();
            holder.range = (TextView) convertView.findViewById(R.id.walking_range_txw);
            holder.head = (SimpleDraweeView) convertView.findViewById(R.id.walking_head_image);
            holder.name = (TextView) convertView.findViewById(R.id.walking_name_txw);
            holder.line = (TextView) convertView.findViewById(R.id.walking_line_txw);
            holder.num = (TextView) convertView.findViewById(R.id.walking_num_txw);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        WorkingSortBean.ListBean bean = list.get(position);
        if (bean.getRank() != 1 && bean.getRank() != 2 && bean.getRank() != 3) {
            holder.range.setText(bean.getRank() + "");
            holder.num.setTextColor(mContext.getResources().getColor(R.color.circle_walking_range_green));
        } else {
//            holder.range.setBackgroundResource(bean.getRank() == 1 ? R.mipmap.icon_range1 : bean.getRank() == 2 ? R.mipmap.icon_range2 : R.mipmap.icon_range3);
            DrawableUtils.setDrawableLeft(mContext,holder.range,bean.getRank() == 1 ? R.mipmap.icon_range1 : bean.getRank() == 2 ? R.mipmap.icon_range2 : R.mipmap.icon_range3);
        }
        holder.head.setImageURI(bean.getAvatar());
        holder.name.setText(bean.getNickName());
        holder.num.setText(bean.getStep() + "");
        if (position == list.size() - 1) {
            holder.line.setVisibility(View.GONE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    ViewHolder holder;

    private class ViewHolder {
        private TextView range;
        private SimpleDraweeView head;
        private TextView name;
        private TextView num;
        private TextView line;
    }
}
