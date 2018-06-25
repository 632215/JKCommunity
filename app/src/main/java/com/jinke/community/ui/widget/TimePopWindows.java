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

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/10/19.
 */

public class TimePopWindows extends PopupWindow implements View.OnClickListener,
        PickDateView.onSelectedChangeListener, PickTimeView.onTimeSelectedChangeListener {
    private Context mContext;
    SimpleDateFormat sdfTime;
    SimpleDateFormat sdfDate;

    String tempDate = "";
    String tempTime = "23:59";
    private OnSureClickListense listense;

    public void setListense(OnSureClickListense listense) {
        this.listense = listense;
    }

    public TimePopWindows(Context context, OnSureClickListense listense) {
        super(context);
        this.mContext = context;
        this.listense = listense;
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
            initData();
            addKeyListener(contentView);
        }
    }

    /**
     * 初始化时间picker
     */
    private void initData() {
        sdfTime = new SimpleDateFormat("HH:mm");
        sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        datePicker.setViewType(PickDateView.TYPE_PICK_DATE);
        timePicker.setViewType(PickDateView.TYPE_PICK_TIME);
        datePicker.setOnSelectedChangeListener(this);
        timePicker.setOnSelectedChangeListener(this);
    }

    /**
     * 控件初始化
     *
     * @param contentView
     */
    private PickDateView datePicker;
    private PickTimeView timePicker;
    private TextView txCancle;
    private TextView txSure;
    private RelativeLayout rlRootView;

    public void initView(final View contentView) {
        datePicker = (PickDateView) contentView.findViewById(R.id.pick_date);
        timePicker = (PickTimeView) contentView.findViewById(R.id.pick_time);
        txCancle = (TextView) contentView.findViewById(R.id.tx_cancle);
        txSure = (TextView) contentView.findViewById(R.id.tx_sure);
        rlRootView = (RelativeLayout) contentView.findViewById(R.id.rl_root_view);
        txCancle.setOnClickListener(this);
        txSure.setOnClickListener(this);
        initData();
        initListView();
    }

    private void initListView() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_cancle:
                dismiss();
                break;
            case R.id.tx_sure:
                datePicker.getCurrentTime();
                break;
//                datePicker.getCurrentTime();
//                timePicker.getCurrentTime();
////                Date date = new Date();
////                SimpleDateFormat sdf;
////                if (listense != null) {
////                    if (tempTime.equals("")) {
////                        sdf = new SimpleDateFormat("HH:mm");
////                        tempTime = sdf.format(date);
////                    }
////                    if (tempDate.equals("")) {
////                        sdf = new SimpleDateFormat("yyyy-MM-dd");
////                        tempDate = sdf.format(date);
////                    }
////                    listense.onSureClick(tempDate + " " + tempTime);
////                }
//                SimpleDateFormat sdf;
//                if (listense != null) {
//                    listense.onSureClick(tempDate + " " + tempTime);
//                }
//                dismiss();
//                break;
        }
    }

    //年月日选择结果
    @Override
    public void onSelected(PickDateView view, long timeMillis) {
        tempDate = sdfDate.format(timeMillis);
        if (listense != null) {
            listense.onSureClick(tempDate + " " + tempTime);
        }
        dismiss();
    }

    //时间选择结果
    @Override
    public void onTimeSelected(PickTimeView view, long timeMillis) {
//        tempTime = sdfTime.format(timeMillis);
        //都设置为23：59
        tempTime = "23:59";
    }

    public interface OnSureClickListense {
        void onSureClick(String time);
    }


}
