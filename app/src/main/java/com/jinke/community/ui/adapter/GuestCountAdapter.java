package com.jinke.community.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.GuestCountBean;

import java.util.ArrayList;
import java.util.List;

public class GuestCountAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<GuestCountBean> list = new ArrayList<>();

    public GuestCountAdapter(Context mContext, List<GuestCountBean> list) {
        super();
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.list = list;
    }

    public void setData(List<GuestCountBean> list) {
        this.list = list;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int postion) {
        return postion;
    }

    @Override
    public long getItemId(int postion) {
        return postion;
    }

    @Override
    public View getView(int postion, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_guestcount, null);
            holder = new ViewHolder();
            holder.item_tx = (TextView) view.findViewById(R.id.item_tx);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        GuestCountBean bean = list.get(postion);
        holder.item_tx.setText(bean.getName());
        holder.item_tx.setBackgroundResource(bean.isshow() ? R.drawable.btn_open_door_choice_d : R.drawable.btn_open_door_choice_u);
        holder.item_tx.setTextColor(bean.isshow() ? mContext.getResources().getColor(R.color.main_select_text_color) : mContext.getResources().getColor(R.color.text_gray));

        return view;
    }

    ViewHolder holder;

    class ViewHolder {
        TextView item_tx;
    }
}
