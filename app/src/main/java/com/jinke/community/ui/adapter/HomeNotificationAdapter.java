package com.jinke.community.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jinke.community.R;
import com.jinke.community.bean.acachebean.NoticeListBean;
import com.jinke.community.ui.activity.preview.PhotoActivity;
import com.jinke.community.ui.activity.preview.ThumbViewInfo;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;
import com.jinke.community.ui.widget.ExpandableTextView;
import com.jinke.community.ui.widget.FillGridView;
import com.jinke.community.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-8-8.
 */

public class HomeNotificationAdapter extends CommonAdapter<NoticeListBean.ListBean> implements ExpandableTextView.OnExpandStateChangeListener {
    Context mContext;
    private PraiseListener listener;
    private ArrayList<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>();

    public HomeNotificationAdapter(@NonNull Context context, int layoutResId, @NonNull List<NoticeListBean.ListBean> dataList) {
        super(context, layoutResId, dataList);
        this.mContext = context;
    }

    public void setListener(PraiseListener listener) {
        this.listener = listener;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final NoticeListBean.ListBean bean, final int position) {
        SimpleDraweeView imageHead = (SimpleDraweeView) baseViewHolder.getViewByViewId(R.id.image_head);//image头像
        TextView txName = (TextView) baseViewHolder.getViewByViewId(R.id.tx_name);//姓名
        TextView txTime = (TextView) baseViewHolder.getViewByViewId(R.id.tx_time);//时间
        LinearLayout llPraise = (LinearLayout) baseViewHolder.getViewByViewId(R.id.ll_praise);//rl赞
        final ImageView imagePraise = (ImageView) baseViewHolder.getViewByViewId(R.id.image_praise);//image赞
        final TextView txPraise = (TextView) baseViewHolder.getViewByViewId(R.id.tx_praise);//赞
        final ExpandableTextView txHomeContent = (ExpandableTextView) baseViewHolder.getViewByViewId(R.id.tx_home_content);//文本内容
        final FillGridView itemGridView = (FillGridView) baseViewHolder.getViewByViewId(R.id.item_gridView);//图片

        BrokenDetailsAdapter adapter = new BrokenDetailsAdapter(mContext, R.layout.item_broken_details, bean.getPicUrl());
        if (bean.getPicUrl() != null && bean.getPicUrl().size() > 0) {
            itemGridView.setVisibility(View.VISIBLE);
        }
        itemGridView.setAdapter(adapter);
        imageHead.setImageURI(bean.getAvatar());
        txName.setText(bean.getManager());
        try {
            txTime.setText(TextUtils.timeChangeBreakStage(bean.getCreateTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (StringUtils.isEmpty(bean.getContent())) {
            txHomeContent.setVisibility(View.GONE);
        } else {
            txHomeContent.setText(bean.getContent().toString());
        }

        txHomeContent.setOnExpandStateChangeListener(this);
        if (bean.getVoteTotal() != null) {
            txPraise.setText(Integer.parseInt(bean.getVoteTotal()) == 0 ? "赞"
                    : Integer.parseInt(bean.getVoteTotal()) > 99 ? "赞 99+"
                    : "赞 " + Integer.parseInt(bean.getVoteTotal()));
        }
        imagePraise.setImageResource(bean.getVoteIsUser().equals("true") ? R.mipmap.icon_fragment_housekeeper_praised : R.mipmap.icon_fragment_housekeeper_praise);
        txPraise.setTextColor(bean.getVoteIsUser().equals("true") ? mContext.getResources().getColor(R.color.fragment_housekeeper_color3) : mContext.getResources().getColor(R.color.main_home_bottom_gray));

        llPraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String voteTotal = bean.getVoteIsUser().equals("true") ? Integer.parseInt(bean.getVoteTotal()) - 1 + "" : Integer.parseInt(bean.getVoteTotal()) + 1 + "";
                txPraise.setText(Integer.parseInt(voteTotal) == 0 ? "赞"
                        : Integer.parseInt(voteTotal) > 99 ? "赞 99+"
                        : "赞 " + Integer.parseInt(voteTotal));
                imagePraise.setImageResource(bean.getVoteIsUser().equals("true") ? R.mipmap.icon_fragment_housekeeper_praise : R.mipmap.icon_fragment_housekeeper_praised);
                txPraise.setTextColor(bean.getVoteIsUser().equals("true") ? mContext.getResources().getColor(R.color.main_home_bottom_gray) : mContext.getResources().getColor(R.color.fragment_housekeeper_color3));
                bean.setVoteTotal(voteTotal);
                bean.setVoteIsUser(bean.getVoteIsUser().equals("true") ? "false" : "true");
                listener.praiseClick(position);
            }
        });

        itemGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                computeBoundsBackward(bean.getPicUrl(), itemGridView);
                PhotoActivity.startActivity((Activity) mContext, mThumbViewInfoList, position);
            }
        });
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
