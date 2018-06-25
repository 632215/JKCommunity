package com.jinke.community.ui.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.ArrearsListBean;
import com.jinke.community.bean.PendingDetailsBean;
import com.jinke.community.bean.PendingPaymentBean;
import com.jinke.community.utils.DecimalFormatUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 */

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private List<ArrearsListBean.ListBean> list = new ArrayList<>();
    private Context mContext;

    public MyExpandableListAdapter(List<ArrearsListBean.ListBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public void setData(List<ArrearsListBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getDetail().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getDetail().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_expendlist_group, parent, false);
            groupHolder = new GroupViewHolder();
            groupHolder.time = (TextView) convertView.findViewById(R.id.time_text);
            groupHolder.totalMoney = (TextView) convertView.findViewById(R.id.total_money_text);
            groupHolder.instructionsImage = (ImageView) convertView.findViewById(R.id.instructions_image);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupViewHolder) convertView.getTag();
        }
        ArrearsListBean.ListBean groupBean = list.get(groupPosition);
        if (groupBean != null) {
            groupHolder.time.setText(groupBean.getMonth());
            groupHolder.totalMoney.setText("￥" + DecimalFormatUtils.format(Double.parseDouble(groupBean.getMonthMoney()), "0.00"));
        }
        if (isExpanded) {
            groupHolder.instructionsImage.setImageResource(R.mipmap.icon_arrow_up);
        } else {
            groupHolder.instructionsImage.setImageResource(R.mipmap.icon_arrow_down);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_expendlist_child, parent, false);
            childHolder = new ChildViewHolder();
            childHolder.costName = (TextView) convertView.findViewById(R.id.cost_name_text);
            childHolder.money = (TextView) convertView.findViewById(R.id.money_text);
            childHolder.childDivider = (View) convertView.findViewById(R.id.child_divider_view);
            childHolder.image = (ImageView) convertView.findViewById(R.id.image_fee_icon);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildViewHolder) convertView.getTag();
        }
        ArrearsListBean.ListBean.DetailBean childBean = list.get(groupPosition).getDetail().get(childPosition);
        if (childBean != null) {
            childHolder.costName.setText(childBean.getItem());
            childHolder.money.setText("￥" + DecimalFormatUtils.format(Double.parseDouble(childBean.getFee()), "0.00"));
            /**
             * 图标展示对后台
             * 0=》物业费
             *  1=》水电公摊
             *  2=》车位费
             */
            if (childBean.getCostType() != null) {
                childHolder.image.setImageResource(childBean.getCostType().equals("0") ? R.mipmap.icon_property_costs
                        : childBean.getCostType().equals("1") ? R.mipmap.icon_water_and_electricity : R.mipmap.icon_parking_fee);
            }
            if (childPosition == list.get(groupPosition).getDetail().size() - 1) {
                childHolder.childDivider.setVisibility(View.GONE);
            } else {
                childHolder.childDivider.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    GroupViewHolder groupHolder;

    private class GroupViewHolder {
        TextView time;
        TextView totalMoney;
        ImageView instructionsImage;
    }


    ChildViewHolder childHolder;

    private class ChildViewHolder {
        TextView costName;
        TextView money;
        View childDivider;
        ImageView image;
    }
}
