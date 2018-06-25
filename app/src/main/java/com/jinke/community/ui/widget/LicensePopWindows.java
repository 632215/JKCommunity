package com.jinke.community.ui.widget;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.jinke.community.R;
import com.jinke.community.bean.LicenseBean;
import com.jinke.community.ui.adapter.LicenseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/2/21.
 */

public class LicensePopWindows extends PopupWindow implements LicenseAdapter.LicenseClickListener {
    private OnLicenseSeclectListener licenseSeclectListener;
    private Context mContext;
    private String[] provinceArray = {
            "京", "津", "沪", "冀", "豫", "云", "辽",
            "黑", "湘", "皖", "鲁", "新", "苏", "浙",
            "赣", "鄂", "桂", "甘", "晋", "蒙", "陕",
            "吉", "闽", "贵", "粤", "川", "青", "藏",
            "琼", "宁", "渝"};
    private String[] letterArray = {
            "A", "B", "C", "D", "E", "F", "G"
            , "H", "I", "J", "K", "L", "M", "N"
            , "O", "P", "Q", "R", "S", "T", "U"
            , "V", "W", "X", "Y", "Z"};
    private FillGridView fillGridView;
    private RelativeLayout rlRootView;
    private LicenseAdapter licenseAdapter;
    private List<LicenseBean> provinceList = new ArrayList<>();
    private List<LicenseBean> letterList = new ArrayList<>();
    private int popwindowsShowFlag = 0;

    public LicensePopWindows(Context context, OnLicenseSeclectListener listener) {
        super(context);
        this.licenseSeclectListener = listener;
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
        setFocusable(true);
        setOutsideTouchable(true);
    }

    @Override
    public void setContentView(View contentView) {
        super.setContentView(contentView);
        if (contentView != null) {
            initData();
            initView(contentView);
            addKeyListener(contentView);
        }
    }

    /**
     * 初始化列表
     */
    private void initData() {
        provinceList.clear();
        letterList.clear();
        for (int x = 0; x < provinceArray.length; x++) {
            if (x == 0) {
                provinceList.add(new LicenseBean(provinceArray[x], true));
            } else {
                provinceList.add(new LicenseBean(provinceArray[x], false));
            }
        }

        for (int x = 0; x < letterArray.length; x++) {
            if (x == 0) {
                letterList.add(new LicenseBean(letterArray[x], true));
            } else {
                letterList.add(new LicenseBean(letterArray[x], false));
            }
        }
    }

    /**
     * 控件初始化
     *
     * @param contentView
     */
    public void initView(final View contentView) {
        rlRootView = (RelativeLayout) contentView.findViewById(R.id.rl_root_view);
        fillGridView = (FillGridView) contentView.findViewById(R.id.fillgridview);
        rlRootView.setBackgroundResource(R.drawable.icon_authorized_pop_text_bg);
        licenseAdapter = new LicenseAdapter(mContext, R.layout.item_license, provinceList, this);
        fillGridView.setAdapter(licenseAdapter);
    }

    /**
     * 设置更换适配器数据和ui
     */
    public void setLetterList() {
        licenseAdapter.clearDataList();
        licenseAdapter.setDataList(letterList);
        rlRootView.setBackgroundResource(R.drawable.icon_authorized_pop_letter_bg);
    }


    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        popwindowsShowFlag = 0;
        licenseSeclectListener.checkLicense();
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

    @Override
    public void onLicenseClick(LicenseBean bean, int position) {
        popwindowsShowFlag++;
        switch (popwindowsShowFlag) {
            case 1:
                for (int x = 0; x < provinceList.size(); x++) {
                    provinceList.get(x).setSelected(false);
                }
                provinceList.get(position).setSelected(true);
                licenseSeclectListener.onLicenseClick(bean.getText(), 1);
                break;
            case 2:
                popwindowsShowFlag = 0;
                for (int x = 0; x < letterList.size(); x++) {
                    letterList.get(x).setSelected(false);
                }
                letterList.get(position).setSelected(true);
                licenseSeclectListener.onLicenseClick(bean.getText(), 2);
                break;
        }
    }

    public interface OnLicenseSeclectListener {
        void onLicenseClick(String text, int i);

        void checkLicense();
    }
}
