package com.jinke.community.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.BrokenNewsListBean;
import com.jinke.community.bean.BrokenPersonBean;
import com.jinke.community.ui.activity.preview.PhotoActivity;
import com.jinke.community.ui.activity.preview.ThumbViewInfo;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;
import com.jinke.community.ui.widget.ExpandableTextView;
import com.jinke.community.ui.widget.FillGridView;
import com.jinke.community.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 * 个人爆料记录适配器
 */

public class BrokenPersonAdapter extends CommonAdapter<BrokenPersonBean.ListBean> {
    private Context context;
    private ArrayList<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>();

    public BrokenPersonAdapter(@NonNull Context context, int layoutResId, @NonNull List<BrokenPersonBean.ListBean> dataList) {
        super(context, layoutResId, dataList);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final BrokenPersonBean.ListBean bean, int position) {
        TextView txDate = (TextView) baseViewHolder.getViewByViewId(R.id.tx_date);//今日  横着的
        TextView txDateTime = (TextView) baseViewHolder.getViewByViewId(R.id.tx_time);//今日时间
        TextView txYear = (TextView) baseViewHolder.getViewByViewId(R.id.tx_year);//日期 竖着的
        TextView txYearTime = (TextView) baseViewHolder.getViewByViewId(R.id.tx_year_time);//日期时间
        ExpandableTextView txContent = (ExpandableTextView) baseViewHolder.getViewByViewId(R.id.tx_home_content);
        TextView state = (TextView) baseViewHolder.getViewByViewId(R.id.tx_state);
        ImageView imageState = (ImageView) baseViewHolder.getViewByViewId(R.id.image_state);
        final GridView gridView = (GridView) baseViewHolder.getViewByViewId(R.id.item_gridView);

        BrokenDetailsAdapter adapter = new BrokenDetailsAdapter(context, R.layout.item_broken_details, bean.getPicUrl());
        if (bean.getPicUrl() != null && bean.getPicUrl().size() > 0) {
            gridView.setVisibility(View.VISIBLE);
        }
        gridView.setAdapter(adapter);

        txContent.setText(bean.getContent());//处理状态：待处理、处理中、已完成、已关闭
        switch (bean.getStatus()) {
            case 0:
                imageState.setImageResource(R.mipmap.icon_activity_break_history2);
                state.setTextColor(context.getResources().getColor(R.color.activity_broken_dynamic_color5));
                state.setText("待处理");
                break;
            case 1:
                imageState.setImageResource(R.mipmap.icon_activity_break_history1);
                state.setTextColor(context.getResources().getColor(R.color.activity_broken_dynamic_color6));
                state.setText("处理中");
                break;
            case 2:
                imageState.setImageResource(R.mipmap.icon_activity_break_history4);
                state.setTextColor(context.getResources().getColor(R.color.activity_broken_dynamic_color7));
                state.setText("已完成");
                break;
            case 99:
                imageState.setImageResource(R.mipmap.icon_activity_break_history3);
                state.setTextColor(context.getResources().getColor(R.color.activity_broken_dynamic_color8));
                state.setText("已关闭");
                break;
        }
        String time = null;
        try {
            time = TextUtils.timeChangeBreakPerson(bean.getCreateTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (time.contains(" ")) {
            txDate.setVisibility(View.GONE);
            txDateTime.setVisibility(View.INVISIBLE);
            txYear.setText(time.substring(0, time.lastIndexOf("/") + 3));
            txYear.setVisibility(View.VISIBLE);
            txYearTime.setText(time.substring(time.lastIndexOf("/") + 4, time.length()));
            txYearTime.setVisibility(View.VISIBLE);
        } else {
            if (time.contains("天")) {
                txDate.setText(time.substring(0, 2));
                txDateTime.setText(time.substring(2));
                txDate.setVisibility(View.VISIBLE);
                txYear.setVisibility(View.INVISIBLE);
                txYearTime.setVisibility(View.INVISIBLE);
            } else {
                txDate.setVisibility(View.GONE);
                txDateTime.setVisibility(View.INVISIBLE);
                txYear.setText(time);
                txYear.setVisibility(View.VISIBLE);
                txYearTime.setVisibility(View.INVISIBLE);
            }
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                computeBoundsBackward(bean.getPicUrl(), (FillGridView) gridView);
                PhotoActivity.startActivity((Activity) context, mThumbViewInfoList, position);
            }
        });
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
