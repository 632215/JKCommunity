package com.jinke.community.ui.widget;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.bean.PublicHouseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/2/21.
 */

public class CommunityPopWindows extends PopupWindow implements ScrollSelectView.onSelectListener, View.OnClickListener {
    private Context mContext;
    private OnItemSelectedListener listener;
    private List<PublicHouseBean.ListBean> list = new ArrayList<>();
    private List<String> houseNameList = new ArrayList<>();
    public CommunityPopWindows(Context context, OnItemSelectedListener listener) {
        super(context);
        this.listener = listener;
        this.mContext = context;
        initBasePopupWindow();
    }

    /**
     * 初始化BasePopupWindow的一些信息
     */
    private void initBasePopupWindow() {
        setAnimationStyle(android.R.style.Animation_Dialog);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);

    }

    @Override
    public void setContentView(View contentView) {
        super.setContentView(contentView);
        if (contentView != null) {
            initView(contentView);
            addKeyListener(contentView);
        }
    }

    /**
     * 控件初始化
     *
     * @param contentView
     */
    private TextView txTips;
    private ScrollSelectView scrollSelectView;
    private TextView txCancle;
    private TextView txSure;
    private RelativeLayout rlRootView;

    public void initView(final View contentView) {
        txTips = (TextView) contentView.findViewById(R.id.tx_tips);
        scrollSelectView = (ScrollSelectView) contentView.findViewById(R.id.scroll_select_view);
        txCancle = (TextView) contentView.findViewById(R.id.tx_cancle);
        txSure = (TextView) contentView.findViewById(R.id.tx_sure);
        rlRootView = (RelativeLayout) contentView.findViewById(R.id.rl_root_view);
        scrollSelectView.setOnSelectListener(this);
        txCancle.setOnClickListener(this);
        txSure.setOnClickListener(this);
    }

    @Override
    public void showAsDropDown(View anchor) {
        showAsDropDown(anchor, 0, 0, Gravity.CENTER);
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }


    /**
     * 为窗体添加outside点击事件
     */
    private void addKeyListener(View contentView) {
        if (contentView != null) {
            contentView.setFocusable(true);
            contentView.setFocusableInTouchMode(true);
            setBackgroundDrawable(new BitmapDrawable());
        }
    }

    //挪到中间位置
    @Override
    public void onSelect(String text) {
        listener.onItemSelected(text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tx_cancle:
                dismiss();
                break;
            case R.id.tx_sure:
                listener.onItemSelected(scrollSelectView.getCurrentText());
                dismiss();
                break;
        }
    }

    public void setListData(List<ParkInfoBean.ListBean> parkList) {
        houseNameList.clear();
        for (ParkInfoBean.ListBean bean : parkList) {
            houseNameList.add(bean.getPark_Name());
        }
        scrollSelectView.setData(houseNameList);
    }

    public interface OnItemSelectedListener {
        void onItemSelected(String text);
    }
}
