package com.jinke.community.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.CommunityGPSBean;
import com.jinke.community.bean.CommunityListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-8-1.
 */

public class RightListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<CommunityListBean.ListBean> list = new ArrayList<>();

    public RightListAdapter(Context mContext, List<CommunityListBean.ListBean> list) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getCommunity().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    public void setData(List<CommunityListBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getCommunity().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_rigth_title, null);
            groupHolder = new GroupHolder();
            groupHolder.title = (TextView) convertView.findViewById(R.id.tx_group_title);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        groupHolder.title.setText(list.get(groupPosition).getDistrict_name());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_right_child_view, null);
            childHolder = new ChildHolder();
            childHolder.title = (TextView) convertView.findViewById(R.id.tx_child_title);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        final CommunityListBean.ListBean.CommunityBean bean = list.get(groupPosition).getCommunity().get(childPosition);
        childHolder.title.setText(bean.getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommunityGPSBean.ListBean listBean = new CommunityGPSBean.ListBean();
                listBean.setId(bean.getId());
                listBean.setName(bean.getName());
                if (listener!=null){
                    listener.onBack(listBean);
                }

            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    GroupHolder groupHolder;

    class GroupHolder {
        TextView title;
    }

    ChildHolder childHolder;

    class ChildHolder {
        TextView title;
    }

    onSelectCommunityListener listener;

    public void setOnSelectCommunityListener(onSelectCommunityListener listener) {
        this.listener = listener;
    }

    public interface onSelectCommunityListener {
        void onBack(CommunityGPSBean.ListBean listBean);
    }
}
