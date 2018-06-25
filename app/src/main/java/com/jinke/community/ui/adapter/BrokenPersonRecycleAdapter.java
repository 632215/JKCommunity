package com.jinke.community.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.BrokenPersonBean;
import com.jinke.community.bean.PropertyBean;
import com.jinke.community.ui.activity.preview.PhotoActivity;
import com.jinke.community.ui.activity.preview.ThumbViewInfo;
import com.jinke.community.ui.widget.ExpandableTextView;
import com.jinke.community.ui.widget.FillGridView;
import com.jinke.community.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/28.
 */

public class BrokenPersonRecycleAdapter extends RecyclerView.Adapter<BrokenPersonRecycleAdapter.ViewHolder> implements ExpandableTextView.OnExpandStateChangeListener {
    private Context context;
    private ArrayList<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>();
    private List<BrokenPersonBean.ListBean> list = new ArrayList<>();

    public BrokenPersonRecycleAdapter(Context context, List<BrokenPersonBean.ListBean> propertyBrokenList) {
        this.context = context;
        this.list = propertyBrokenList;
    }

    public void setDataList(List<BrokenPersonBean.ListBean> propertyBrokenList) {
        this.list = propertyBrokenList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BrokenPersonRecycleAdapter.ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_break_history, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final BrokenPersonBean.ListBean bean = list.get(position);
        if (position == list.size() - 1) {
            holder.lineView.setVisibility(View.GONE);
        } else {
            holder.lineView.setVisibility(View.VISIBLE);
        }

        BrokenDetailsAdapter adapter = new BrokenDetailsAdapter(context, R.layout.item_broken_details, bean.getPicUrl());
        if (bean.getPicUrl() != null && bean.getPicUrl().size() > 0) {
            holder.gridView.setVisibility(View.VISIBLE);
        }
        holder.gridView.setAdapter(adapter);

        holder.txContent.setText(bean.getContent());//处理状态：待处理、处理中、已完成、已关闭
        switch (bean.getStatus()) {
            case 0:
                holder.imageState.setImageResource(R.mipmap.icon_activity_break_history2);
                holder.state.setTextColor(context.getResources().getColor(R.color.activity_broken_dynamic_color5));
                holder.state.setText("待处理");
                break;
            case 1:
                holder.imageState.setImageResource(R.mipmap.icon_activity_break_history1);
                holder.state.setTextColor(context.getResources().getColor(R.color.activity_broken_dynamic_color6));
                holder.state.setText("处理中");
                break;
            case 2:
                holder.imageState.setImageResource(R.mipmap.icon_activity_break_history4);
                holder.state.setTextColor(context.getResources().getColor(R.color.activity_broken_dynamic_color7));
                holder.state.setText("已完成");
                break;
            case 99:
                holder.imageState.setImageResource(R.mipmap.icon_activity_break_history3);
                holder.state.setTextColor(context.getResources().getColor(R.color.activity_broken_dynamic_color8));
                holder.state.setText("已关闭");
                break;
        }
        String time = null;
        try {
            time = TextUtils.timeChangeBreakPerson(bean.getCreateTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (time.contains(" ")) {
            holder.txDate.setVisibility(View.GONE);
            holder.txDateTime.setVisibility(View.INVISIBLE);
            holder.txYear.setText(time.substring(0, time.lastIndexOf("/") + 3));
            holder.txYear.setVisibility(View.VISIBLE);
            holder.txYearTime.setText(time.substring(time.lastIndexOf("/") + 4, time.length()));
            holder.txYearTime.setVisibility(View.VISIBLE);
        } else {
            if (time.contains("天")) {
                holder.txDate.setText(time.substring(0, 2));
                holder.txDateTime.setText(time.substring(2));
                holder.txDate.setVisibility(View.VISIBLE);
                holder.txYear.setVisibility(View.INVISIBLE);
                holder.txYearTime.setVisibility(View.INVISIBLE);
            } else {
                holder.txDate.setVisibility(View.GONE);
                holder.txDateTime.setVisibility(View.INVISIBLE);
                holder.txYear.setText(time);
                holder.txYear.setVisibility(View.VISIBLE);
                holder.txYearTime.setVisibility(View.INVISIBLE);
            }
        }

        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                computeBoundsBackward(bean.getPicUrl(), (FillGridView) holder.gridView);
                PhotoActivity.startActivity((Activity) context, mThumbViewInfoList, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onExpandStateChanged(TextView textView, boolean isExpanded) {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txDate;
        TextView txDateTime;
        TextView txYear;
        TextView txYearTime;
        ExpandableTextView txContent;
        TextView state;
        ImageView imageState;
        GridView gridView;
        View lineView;

        public ViewHolder(View itemView) {
            super(itemView);
            txDate = (TextView) itemView.findViewById(R.id.tx_date);//今日  横着的
            txDateTime = (TextView) itemView.findViewById(R.id.tx_time);//今日时间
            txYear = (TextView) itemView.findViewById(R.id.tx_year);//日期 竖着的
            txYearTime = (TextView) itemView.findViewById(R.id.tx_year_time);//日期时间
            txContent = (ExpandableTextView) itemView.findViewById(R.id.tx_home_content);
            state = (TextView) itemView.findViewById(R.id.tx_state);
            imageState = (ImageView) itemView.findViewById(R.id.image_state);
            gridView = (GridView) itemView.findViewById(R.id.item_gridView);
            lineView = (View) itemView.findViewById(R.id.line_view);
        }
    }

    /**
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     *
     * @param picUrl
     * @param itemGridView
     */
    private void computeBoundsBackward(List<String> picUrl, FillGridView itemGridView) {
        mThumbViewInfoList.clear();
        for (int i = 0; i < picUrl.size(); i++) {
            View itemView = itemGridView.getChildAt(i);
            ThumbViewInfo thumbViewInfo = new ThumbViewInfo(picUrl.get(i));
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView.findViewById(R.id.simpleImage);
                thumbView.getGlobalVisibleRect(bounds);
                thumbViewInfo.setBounds(bounds);
            }
            mThumbViewInfoList.add(thumbViewInfo);
        }
    }
}
