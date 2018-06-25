package com.jinke.community.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jinke.community.R;
import com.jinke.community.bean.acachebean.NoticeListBean;
import com.jinke.community.ui.activity.preview.PhotoActivity;
import com.jinke.community.ui.activity.preview.ThumbViewInfo;
import com.jinke.community.ui.widget.ExpandableTextView;
import com.jinke.community.ui.widget.FillGridView;
import com.jinke.community.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2017/12/25.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements ExpandableTextView.OnExpandStateChangeListener {
    private Context context;
    private List<NoticeListBean.ListBean> list;
    private ArrayList<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>();
    private PraiseListener listener;


    public RecyclerViewAdapter(Context context, List<NoticeListBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setListener(PraiseListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewAdapter.ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_break_stage, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final NoticeListBean.ListBean bean = list.get(position);
        if (position == list.size() - 1) {
            holder.itemRootView.setBackgroundResource(R.color.white);
        }

        BrokenDetailsAdapter adapter = new BrokenDetailsAdapter(context, R.layout.item_broken_details, bean.getPicUrl());
        if (bean.getPicUrl() != null && bean.getPicUrl().size() > 0) {
            holder.itemGridView.setVisibility(View.VISIBLE);
        }
        holder.itemGridView.setAdapter(adapter);
        holder.imageHead.setImageURI(bean.getAvatar());
        holder.txName.setText(bean.getManager());
        try {
            holder.txTime.setText(TextUtils.timeChangeBreakStage(bean.getCreateTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (StringUtils.isEmpty(bean.getContent())) {
            holder.txHomeContent.setVisibility(View.GONE);
        } else {
            holder.txHomeContent.setText(bean.getContent().toString());
        }

        holder.txHomeContent.setOnExpandStateChangeListener(this);
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
                listener.praiseClick(position);
            }
        });

        holder.itemGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                computeBoundsBackward(bean.getPicUrl(), holder.itemGridView);
                PhotoActivity.startActivity((Activity) context, mThumbViewInfoList, position);
            }
        });
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
        ExpandableTextView txHomeContent;
        FillGridView itemGridView;
        RelativeLayout itemRootView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageHead = (SimpleDraweeView) itemView.findViewById(R.id.image_head);//image头像
            txName = (TextView) itemView.findViewById(R.id.tx_name);//姓名
            txTime = (TextView) itemView.findViewById(R.id.tx_time);//时间
            llPraise = (LinearLayout) itemView.findViewById(R.id.ll_praise);//rl赞
            imagePraise = (ImageView) itemView.findViewById(R.id.image_praise);//image赞
            txPraise = (TextView) itemView.findViewById(R.id.tx_praise);//赞
            txHomeContent = (ExpandableTextView) itemView.findViewById(R.id.tx_home_content);//文本内容
            itemGridView = (FillGridView) itemView.findViewById(R.id.item_gridView);//图片
            itemRootView = (RelativeLayout) itemView.findViewById(R.id.item_rootView);

        }
    }

    @Override
    public void onExpandStateChanged(TextView textView, boolean isExpanded) {
    }

    public interface PraiseListener {
        void praiseClick(int position);
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
