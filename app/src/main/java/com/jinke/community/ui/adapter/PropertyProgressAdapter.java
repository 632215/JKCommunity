package com.jinke.community.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.PropertyProgressBean;
import com.jinke.community.ui.toast.UniversalDialog;
import com.jinke.community.ui.widget.FillGridView;
import com.jinke.community.utils.AndroidUtils;

import java.util.ArrayList;
import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2018/04/25.
 * 物业报事节点信息
 */

public class PropertyProgressAdapter extends RecyclerView.Adapter<PropertyProgressAdapter.ViewHolder> implements UniversalDialog.onUniversalDialogListener {
    private Context context;
    private List<PropertyProgressBean.ListObjBean> list = new ArrayList<>();
    private PropertyProgressBean progressBean;
    private SatisfactionAdapter satisfactionAdapter;
    private List<String> satisfactionList = new ArrayList<>();
    private ProgressAdapterListener listener;
    private UniversalDialog dialog = null;
    //为了保证格式不变，必须给日期设置相同的长度
    private String date = "";

    public PropertyProgressAdapter(Context context, PropertyProgressBean progressBean) {
        this.context = context;
        this.progressBean = progressBean;
        this.list = progressBean.getListObj();
        date = progressBean.getListObj().get(0).getCreateTime().substring(0, progressBean.getListObj().get(0).getCreateTime().indexOf(" ")).replace("-", "/");
    }

    public void setListener(ProgressAdapterListener listener) {
        this.listener = listener;
    }

    public void setDataList(PropertyProgressBean progressBean) {
        this.list = progressBean.getListObj();
        this.progressBean = progressBean;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PropertyProgressAdapter.ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_progress, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PropertyProgressBean.ListObjBean bean = list.get(position);
        if (bean != null) {
            holder.rlProgressContent.setPadding(0, position == 0 ? 0 : 40, 0, 40);
            holder.txDate.setPadding(0, position == 0 ? 0 : 40, 0, 0);
//            holder.txDate.setVisibility(position == 0 || position == 1 || position == 2 ? View.VISIBLE : View.INVISIBLE);
//            holder.txTime.setVisibility(position == 0 || position == 1 || position == 2 ? View.VISIBLE : View.INVISIBLE);
            holder.txDate.setText(bean.getCreateTime().substring(0, bean.getCreateTime().indexOf(" ")).replace("-", "/"));
            holder.txTime.setText(bean.getCreateTime().substring(bean.getCreateTime().indexOf(" ") + 1, bean.getCreateTime().lastIndexOf(":")).replace("-", "/"));
            holder.txLineBottom.setVisibility(list.size() - 1 == position ? View.INVISIBLE : View.VISIBLE);
            holder.txProgressIn.setAlpha(list.size() - 1 != position ? (float) 0.5 : 1);
            holder.rlProgressDealing.setAlpha(list.size() - 1 != position ? (float) 0.5 : 1);
            holder.txProgressFinish.setAlpha(list.size() - 1 != position ? (float) 0.5 : 1);
            holder.rlProgressSatisfaction.setAlpha(list.size() - 1 != position ? (float) 0.5 : 1);
            holder.rlScore.setAlpha(list.size() - 1 != position ? (float) 0.5 : 1);
            holder.imgState.setImageResource(position == list.size() - 1 ? R.mipmap.icon_prgress_dealing : R.mipmap.icon_prgress_dealed);

            switch (position) {
                case 0:
                    setUi(holder, View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE);
                    break;
                case 1:
                    if (!StringUtils.isEmpty(bean.getUserName())) {
                        holder.txName.setText(bean.getUserName());
                        holder.txName.setVisibility(View.VISIBLE);
                    } else {
                        holder.txName.setVisibility(View.GONE);
                    }
                    if (!StringUtils.isEmpty(bean.getTelePhone())) {
                        holder.txPhone.setText(StringUtils.isEmpty(bean.getTelePhone()) ? "" : bean.getTelePhone().length() != 11 ? "" : bean.getTelePhone());
                        holder.txPhone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AndroidUtils.callPhone(context, bean.getTelePhone());
                            }
                        });
                    } else {
                        holder.txPhone.setVisibility(View.GONE);
                    }
                    holder.imgFee.setVisibility(StringUtils.equals("收费", progressBean.getServiceType()) ? View.VISIBLE : View.GONE);
                    if (StringUtils.equals("收费", progressBean.getServiceType()))
                        holder.imgFee.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showToast();
                            }
                        });
                    setUi(holder, View.GONE, View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE);
                    break;
                case 2:
                    setUi(holder, View.GONE, View.GONE, View.VISIBLE, View.GONE, View.GONE, View.GONE);
                    break;
                case 3:
                    setUi(holder, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.VISIBLE);
                    holder.txVisitor.setText(!StringUtils.isEmpty(bean.getUserName()) ? "回访人：" + bean.getUserName() : "");
                    holder.txVisitor.setVisibility(!StringUtils.isEmpty(bean.getUserName()) ? View.VISIBLE : View.GONE);
                    holder.txVisitorPhone.setText(!StringUtils.isEmpty(bean.getTelePhone()) ? bean.getTelePhone() : "");
                    holder.txVisit.setText(!StringUtils.isEmpty(bean.getRemark()) ? bean.getRemark() : "");
                    if (!StringUtils.isEmpty(bean.getTelePhone())) {
                        holder.txVisitorPhone.setVisibility(View.VISIBLE);
                        holder.txVisitorPhone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AndroidUtils.callPhone(context, bean.getTelePhone());
                            }
                        });
                    } else {
                        holder.txVisitorPhone.setVisibility(View.GONE);
                    }
//                    if (!StringUtils.isEmpty(bean.getRemark())) {
//                        setUi(holder, View.GONE, View.GONE, View.GONE, View.VISIBLE, View.GONE);//评分栏
//                        holder.txSatisfaction.setText(bean.getRemark());
//                        if (!StringUtils.isEmpty(bean.getGrade())) {
//                            for (int x = 0; x < 5; x++) {
//                                satisfactionList.add(x < Integer.parseInt(bean.getGrade()) ? "1" : "0");
//                            }
//                            satisfactionAdapter = new SatisfactionAdapter(context, R.layout.item_satisfaction, satisfactionList);
//                            holder.imgState.setImageResource(R.mipmap.icon_progress_finish);
//                            holder.fillGridView.setAdapter(satisfactionAdapter);
//                        }
//                    } else {
//                        setUi(holder, View.GONE, View.GONE, View.GONE, View.GONE, View.VISIBLE);//去评分按钮
//                        holder.txToScore.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                listener.toScore();
//                            }
//                        });
//                    }
                    break;
            }
        }
    }

    private void showToast() {
        if (dialog == null) {
            dialog = new UniversalDialog(context, this);
        }
        dialog.setTxTitle("说明");
        dialog.setContent(context.getString(R.string.activity_property_please_explain));
        dialog.show();
    }

    /**
     * 统一弹框提示
     *
     * @param phone
     */
    @Override
    public void onCall(String phone) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    private void setUi(ViewHolder holder, int... vis) {
        holder.txProgressIn.setVisibility(vis[0]);
        holder.rlProgressDealing.setVisibility(vis[1]);
        holder.txProgressFinish.setVisibility(vis[2]);
        holder.rlProgressSatisfaction.setVisibility(vis[3]);
        holder.rlScore.setVisibility(vis[4]);
        holder.rlVisit.setVisibility(vis[5]);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlProgressContent;
        TextView txDate;
        TextView txTime;
        TextView txLineTop;
        ImageView imgState;
        TextView txLineBottom;
        TextView txProgressIn;
        RelativeLayout rlProgressDealing;
        TextView txName;
        TextView txPhone;
        ImageView imgFee;
        TextView txProgressFinish;
        RelativeLayout rlProgressSatisfaction;
        FillGridView fillGridView;
        TextView txSatisfaction;
        RelativeLayout rlScore;
        TextView txToScore;
        RelativeLayout rlVisit;
        TextView txVisitorPhone;
        TextView txVisit;
        TextView txVisitor;

        ViewHolder(View itemView) {
            super(itemView);
            rlProgressContent = itemView.findViewById(R.id.rl_progress_content);
            txDate = itemView.findViewById(R.id.tx_date);//日期
            txTime = itemView.findViewById(R.id.tx_time);//时间
            txLineTop = itemView.findViewById(R.id.tx_line_top);//上线
            imgState = itemView.findViewById(R.id.img_state);//处理状态图标
            txLineBottom = itemView.findViewById(R.id.tx_line_bottom);//下线
            txProgressIn = itemView.findViewById(R.id.tx_progress_in);//待处理
            rlProgressDealing = itemView.findViewById(R.id.rl_progress_dealing);//处理中
            txName = itemView.findViewById(R.id.tx_name);//处理人
            txPhone = itemView.findViewById(R.id.tx_phone);//处理人电话
            imgFee = itemView.findViewById(R.id.img_fee);//处理中费用图标
            txProgressFinish = itemView.findViewById(R.id.tx_progress_finish);//处理完成1
            rlProgressSatisfaction = itemView.findViewById(R.id.rl_progress_satisfaction);//满意度2
            fillGridView = itemView.findViewById(R.id.fill_grid_view);//满意度分数
            txSatisfaction = itemView.findViewById(R.id.tx_satisfaction);//评论
            rlScore = itemView.findViewById(R.id.rl_score);//评分
            txToScore = itemView.findViewById(R.id.tx_to_score);//去评分

            rlVisit = itemView.findViewById(R.id.rl_visit);//回访
            txVisitor = itemView.findViewById(R.id.tx_visitor);//回访人
            txVisitorPhone = itemView.findViewById(R.id.tx_visitor_phone);//回访人电话
            txVisit = itemView.findViewById(R.id.tx_visit);//回返内容
        }
    }

    public interface ProgressAdapterListener {
        void toScore();
    }
}
