package com.jinke.community.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jinke.community.R;
import com.jinke.community.bean.acachebean.NoticeListBean;
import com.jinke.community.ui.activity.preview.ThumbViewInfo;
import com.jinke.community.ui.widget.ExpandableTextView;
import com.jinke.community.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2017/12/25.
 */

public class KeeperRecycleAdapter extends RecyclerView.Adapter<KeeperRecycleAdapter.ViewHolder>  {
    private Context context;
    private List<NoticeListBean.ListBean> list;
    private KeeperRecycleListener listener;


    public KeeperRecycleAdapter(Context context, List<NoticeListBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setListener(KeeperRecycleListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        KeeperRecycleAdapter.ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_keeper_break_stage, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final NoticeListBean.ListBean bean = list.get(position);
        if (position == list.size() - 1) {
            holder.itemRootView.setBackgroundResource(R.color.white);
        }

        holder.imageHead.setImageURI(bean.getAvatar());
        holder.txName.setText(bean.getManager());
        try {
            holder.txTime.setText(TextUtils.timeChangeBreakStage(bean.getCreateTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bean.getVoteTotal() != null) {
            holder.txPraise.setText(Integer.parseInt(bean.getVoteTotal()) == 0 ? "赞"
                    : Integer.parseInt(bean.getVoteTotal()) > 99 ? "赞 99+"
                    : "赞 " + Integer.parseInt(bean.getVoteTotal()));
        }
        holder.imagePraise.setImageResource(bean.getVoteIsUser().equals("true") ? R.mipmap.icon_fragment_housekeeper_praised : R.mipmap.icon_fragment_housekeeper_praise);
        holder.txPraise.setTextColor(bean.getVoteIsUser().equals("true") ? context.getResources().getColor(R.color.fragment_housekeeper_color3) : context.getResources().getColor(R.color.main_home_bottom_gray));

        holder.llPraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String voteTotal = bean.getVoteIsUser().equals("true") ? Integer.parseInt(bean.getVoteTotal()) - 1 + "" : Integer.parseInt(bean.getVoteTotal()) + 1 + "";
                holder.txPraise.setText(Integer.parseInt(voteTotal) == 0 ? "赞"
                        : Integer.parseInt(voteTotal) > 99 ? "赞 99+"
                        : "赞 " + Integer.parseInt(voteTotal));
                holder.imagePraise.setImageResource(bean.getVoteIsUser().equals("true") ? R.mipmap.icon_fragment_housekeeper_praise : R.mipmap.icon_fragment_housekeeper_praised);
                holder.txPraise.setTextColor(bean.getVoteIsUser().equals("true") ? context.getResources().getColor(R.color.main_home_bottom_gray) : context.getResources().getColor(R.color.fragment_housekeeper_color3));
                bean.setVoteTotal(voteTotal);
                bean.setVoteIsUser(bean.getVoteIsUser().equals("true") ? "false" : "true");
                listener.adapterPraiseClick(position);
            }
        });
        holder.txContext.setText(bean.getContent());
        holder.txSeeMore.setVisibility(holder.txContext.getText().toString().trim().endsWith("...") ? View.VISIBLE : View.GONE);
        holder.txSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.adapterSeeMore();
            }
        });
        holder.itemRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.adapterSeeMore();
            }
        });
        // 0       待处理——当前状态：小金妹正火速赶来……
        // 1       处理中——当前状态：爆料已收到，小金妹正在处理
        // 2       已完结——当前状态：你的爆料已处理完毕，感谢您的支持和信赖
        // 99       已关闭——当前状态：爆料已收到，感谢您的支持和信赖
        holder.txState.setText("【当前状态】");
        TextUtils.setText(holder.txState, "#151515", StringUtils.equals(bean.getStatus(), "0") ? "小金妹正火速赶来……" :
                StringUtils.equals(bean.getStatus(), "1") ? "爆料已收到，小金妹正在处理" :
                        StringUtils.equals(bean.getStatus(), "2") ? "你的爆料已处理完毕，感谢您的支持和信赖" : "爆料已收到，感谢您的支持和信赖");
        holder.txState.setBackgroundResource(StringUtils.equals(bean.getStatus(), "2") ? R.mipmap.icon_break_state_twoline_bg : R.mipmap.icon_break_state_oneline_bg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setDataList(List<NoticeListBean.ListBean> breakList) {
        list = breakList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView imageHead;
        TextView txName;
        TextView txTime;
        LinearLayout llPraise;
        ImageView imagePraise;
        TextView txPraise;
        TextView txContext;
        TextView txSeeMore;
        TextView txState;
        RelativeLayout itemRootView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageHead = itemView.findViewById(R.id.image_head);//image头像
            txName = itemView.findViewById(R.id.tx_name);//姓名
            txTime = itemView.findViewById(R.id.tx_time);//时间
            llPraise = itemView.findViewById(R.id.ll_praise);//rl赞
            imagePraise = itemView.findViewById(R.id.image_praise);//image赞
            txPraise = itemView.findViewById(R.id.tx_praise);//赞

            txContext = itemView.findViewById(R.id.tx_context);//内容
            txSeeMore = itemView.findViewById(R.id.tx_see_more);//查看详情
            txState = itemView.findViewById(R.id.tx_state);//状态

            itemRootView = itemView.findViewById(R.id.item_rootView);

        }
    }

    public interface KeeperRecycleListener {

        void adapterPraiseClick(int position);//点赞

        void adapterSeeMore();//查看更多
    }
}
