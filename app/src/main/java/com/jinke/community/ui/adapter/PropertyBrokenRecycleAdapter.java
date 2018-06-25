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

public class PropertyBrokenRecycleAdapter extends RecyclerView.Adapter<PropertyBrokenRecycleAdapter.ViewHolder> implements ExpandableTextView.OnExpandStateChangeListener {
    private Context context;
    private AccessListener listener;
    private ArrayList<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>();
    private List<PropertyBean.ListBean> list = new ArrayList<>();

    public PropertyBrokenRecycleAdapter(Context context, List<PropertyBean.ListBean> propertyBrokenList) {
        this.context = context;
        this.list = propertyBrokenList;
    }

    public void setListener(AccessListener listener) {
        this.listener = listener;
    }

    public void setDataList(List<PropertyBean.ListBean> propertyBrokenList) {
        this.list = propertyBrokenList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PropertyBrokenRecycleAdapter.ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_property_history, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PropertyBean.ListBean bean = list.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(bean);
            }
        });
        BrokenDetailsAdapter adapter = new BrokenDetailsAdapter(context, R.layout.item_broken_details, bean.getImage());
        if (bean.getImage() != null && bean.getImage().size() > 0) {
            holder.gridView.setVisibility(View.VISIBLE);
        }
        holder.gridView.setAdapter(adapter);
        holder.txContent.setText(bean.getContent());
        holder.imageAssess.setVisibility(View.GONE);
        switch (bean.getStatus()) {
            case 1:
                holder.imageState.setImageResource(R.mipmap.icon_activity_break_history2);
                holder.state.setTextColor(context.getResources().getColor(R.color.activity_broken_dynamic_color5));
                holder.state.setText(R.string.activity_property_details_state1);
                break;

            case 2:
                holder.imageState.setImageResource(R.mipmap.icon_activity_break_history1);
                holder.state.setTextColor(context.getResources().getColor(R.color.activity_broken_dynamic_color6));
                holder.state.setText(R.string.activity_property_details_state2);
                break;

            case 3:
                holder.imageState.setImageResource(R.mipmap.icon_activity_break_history4);
                holder.imageAssess.setVisibility(View.VISIBLE);
                holder.state.setTextColor(context.getResources().getColor(R.color.activity_broken_dynamic_color7));
                holder.state.setText(R.string.activity_property_details_state3);
                holder.imageAssess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.clickAccess(bean);
                    }
                });
                break;

            case 4:
                holder.imageState.setImageResource(R.mipmap.icon_activity_break_history4);
                holder.state.setTextColor(context.getResources().getColor(R.color.activity_broken_dynamic_color7));
                holder.state.setText(R.string.activity_property_details_state4);
                break;
        }
        String time = null;
        try {
            time = TextUtils.timeChangeBreakPerson(String.valueOf(bean.getCreateTime()));
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
                computeBoundsBackward(bean.getImage(), (FillGridView) holder.gridView);
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
        RelativeLayout rlItemRoot;
        TextView txDate;
        TextView txDateTime;
        TextView txYear;
        TextView txYearTime;
        ExpandableTextView txContent;
        ImageView imageAssess;
        TextView state;
        ImageView imageState;
        GridView gridView;

        public ViewHolder(View itemView) {
            super(itemView);
            rlItemRoot = (RelativeLayout) itemView.findViewById(R.id.rl_item_root);
            txDate = (TextView) itemView.findViewById(R.id.tx_date);//今日  横着的
            txDateTime = (TextView) itemView.findViewById(R.id.tx_time);//今日时间
            txYear = (TextView) itemView.findViewById(R.id.tx_year);//日期 竖着的
            txYearTime = (TextView) itemView.findViewById(R.id.tx_year_time);//日期时间
            txContent = (ExpandableTextView) itemView.findViewById(R.id.tx_home_content);
            imageAssess = (ImageView) itemView.findViewById(R.id.image_assess);//评价按扭
            state = (TextView) itemView.findViewById(R.id.tx_state);
            imageState = (ImageView) itemView.findViewById(R.id.image_state);
            gridView = (GridView) itemView.findViewById(R.id.item_gridView);
        }
    }

    public interface AccessListener {
        void clickAccess(PropertyBean.ListBean bean);

        void itemClick(PropertyBean.ListBean bean);
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
