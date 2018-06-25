package com.jinke.community.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.jinke.community.R;
import com.jinke.community.bean.PropertyBean;
import com.jinke.community.ui.activity.broken.NewPropertyDetailsActivity;
import com.jinke.community.ui.widget.FillGridView;
import com.jinke.community.utils.TextUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2018/04/25.
 * 新的物业报事记录
 */

public class PropertyHistoryAdapter extends RecyclerView.Adapter<PropertyHistoryAdapter.ViewHolder> {
    private Context context;
    private List<PropertyBean.ListBean> list = new ArrayList<>();
    private SatisfactionAdapter satisfactionAdapter;
    private List<String> satisfactionList;

    public PropertyHistoryAdapter(Context context, List<PropertyBean.ListBean> propertyBrokenList) {
        this.context = context;
        this.list = propertyBrokenList;
    }

    public void setDataList(List<PropertyBean.ListBean> propertyBrokenList) {
        this.list = propertyBrokenList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PropertyHistoryAdapter.ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_property_new_history, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PropertyBean.ListBean bean = list.get(position);
        if (bean != null) {
            holder.txPostId.setText(context.getString(R.string.activity_property_post_id) + bean.getKeepId());
            holder.txTime.setText(TextUtils.intToString(String.valueOf(bean.getCreateTime()), "yyyy/MM/dd HH:mm"));
            holder.txContent.setText(bean.getContent());
            holder.rlProgress.setVisibility(bean.getStatus() != 4 ? View.VISIBLE : View.GONE);
            holder.rlSatisfaction.setVisibility(bean.getStatus() != 4 ? View.GONE : View.VISIBLE);
            if (bean.getStatus() == 4) {//填写报事评价满意度
                if (bean.getPostComment() != null) {
                    satisfactionList = new ArrayList<>();
                    try {
                        ArrayList<LinkedTreeMap<String, Object>> list = (ArrayList<LinkedTreeMap<String, Object>>) bean.getPostComment();
                        for (int x = 0; x < 5; x++) {
                            satisfactionList.add(x < Integer.parseInt(new DecimalFormat("0").format(list.get(0).get("score"))) ? "1" : "0");
                        }
                        satisfactionAdapter = new SatisfactionAdapter(context, R.layout.item_satisfaction, satisfactionList);
                        holder.fillGridView.setAdapter(satisfactionAdapter);
                    } catch (Exception e) {
                        holder.rlProgress.setVisibility(View.GONE);
                        holder.rlSatisfaction.setVisibility(View.GONE);
                    }
                }
            } else {
                holder.txProgressExplain.setText(bean.getStatus() == 1 ? context.getString(R.string.activity_property_progress1) :
                        bean.getStatus() == 2 ? context.getString(R.string.activity_property_progress2) : context.getString(R.string.activity_property_progress3));
            }

            holder.rlItemRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!StringUtils.isEmpty(bean.getKeepId()))
                        context.startActivity(new Intent(context, NewPropertyDetailsActivity.class).putExtra("keepId", bean.getKeepId()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlItemRoot;
        TextView txPostId;
        TextView txTime;
        TextView txContent;
        RelativeLayout rlProgress;
        TextView txProgressExplain;
        RelativeLayout rlSatisfaction;
        FillGridView fillGridView;

        public ViewHolder(View itemView) {
            super(itemView);
            rlItemRoot = itemView.findViewById(R.id.rl_root);
            txPostId = itemView.findViewById(R.id.tx_post_id);//id
            txTime = itemView.findViewById(R.id.tx_time);//时间
            txContent = itemView.findViewById(R.id.tx_post_content);//内容
            rlProgress = itemView.findViewById(R.id.rl_progress);//进度
            txProgressExplain = itemView.findViewById(R.id.tx_progress_explain);//进度说明
            rlSatisfaction = itemView.findViewById(R.id.rl_satisfaction);//评分
            fillGridView = itemView.findViewById(R.id.fill_grid_view);//评分说明
        }
    }
}
