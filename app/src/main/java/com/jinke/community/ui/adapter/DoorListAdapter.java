package com.jinke.community.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.HouseListInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 19/11/16.
 */

public class DoorListAdapter extends BaseAdapter {
    private Context mContext;
    private List<HouseListInfo.ListDateBean> list;

    public DoorListAdapter(Context mContext, List<HouseListInfo.ListDateBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void setData(List<HouseListInfo.ListDateBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_door_list, null);
            viewHolder = new ViewHolder();
            viewHolder.housingEstate = (TextView) view.findViewById(R.id.housing_estate);
            viewHolder.province = (TextView) view.findViewById(R.id.housing_estate_province);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.housingEstate.setText(list.get(i).getTqcommunityName()+list.get(i).getHouseName());
        viewHolder.province.setText(list.get(i).getTqcommunityAddr());
        return view;
    }

    ViewHolder viewHolder;

    class ViewHolder {
        TextView housingEstate;
        TextView province;
    }
}

