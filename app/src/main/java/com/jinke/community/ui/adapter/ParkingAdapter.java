package com.jinke.community.ui.adapter;

import android.content.Context;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.ParkInfoSelectBean;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;
import com.jinke.community.utils.DecimalFormatUtils;

import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/10/21.
 */

public class ParkingAdapter extends CommonAdapter<ParkInfoSelectBean.ListBean> {
    private ParkingAdapterListener listener;
    private Context context;
    private int month = 0;

    public ParkingAdapter(@NonNull Context context, @LayoutRes int layoutResId, @NonNull List<ParkInfoSelectBean.ListBean> dataList) {
        super(context, layoutResId, dataList);
        this.context = context;
    }

    public ParkingAdapter(@NonNull Context context, @LayoutRes int layoutResId, @NonNull List<ParkInfoSelectBean.ListBean> dataList, ParkingAdapterListener listener) {
        super(context, layoutResId, dataList);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final ParkInfoSelectBean.ListBean bean, final int position) {

        final RelativeLayout rlRootView = (RelativeLayout) baseViewHolder.getViewByViewId(R.id.rl_root_view);

        final ImageView imageSelectState = (ImageView) baseViewHolder.getViewByViewId(R.id.image_select_state);
        TextView txParkingSpace = (TextView) baseViewHolder.getViewByViewId(R.id.tx_parking_space);
        TextView txMoney = (TextView) baseViewHolder.getViewByViewId(R.id.tx_money);
        TextView txParkingTime = (TextView) baseViewHolder.getViewByViewId(R.id.tx_parking_time);
        ImageView imageAddNum = (ImageView) baseViewHolder.getViewByViewId(R.id.image_add_num);
        ImageView imageReduceNum = (ImageView) baseViewHolder.getViewByViewId(R.id.image_reduce_num);
        final TextView txNum = (TextView) baseViewHolder.getViewByViewId(R.id.tx_num);
        imageSelectState.setImageResource(bean.getSelectFlag().equals("true") ? R.drawable.icon_verhicle_selected : R.drawable.icon_un_select);
        txParkingSpace.setText(bean.getCarSpace_No());
        if (!StringUtils.isEmpty(bean.getCarTypeChargRules_Money()))
            txMoney.setText(DecimalFormatUtils.format(Double.parseDouble(bean.getCarTypeChargRules_Money()), "0.00") + "元/月");
        rlRootView.setBackgroundColor(bean.getSelectFlag().equals("true") ? context.getResources().getColor(R.color.payment_text_color24) : context.getResources().getColor(R.color.white));

        txParkingTime.setText("当前有效期：" + bean.getCarSpace_EndTime().toString().trim().replace("-", "/"));
        if (!StringUtils.isEmpty(bean.getCarTypeChargRules_MthNum()))
            txNum.setText(bean.getCarTypeChargRules_MthNum() + "月");
        final Message msg = new Message();
        imageAddNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month = Integer.parseInt(txNum.getText().toString().trim().substring(0, txNum.getText().toString().trim().lastIndexOf("月")));
                txNum.setText((month + 1) + "月");
                listener.changeMonth(position, String.valueOf(month + 1));
            }
        });
        imageReduceNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month = Integer.parseInt(txNum.getText().toString().trim().substring(0, txNum.getText().toString().trim().lastIndexOf("月")));
                if (month == 1) {
                    ToastUtils.showShort(context, "最低月租时间为1个月");
                } else {
                    txNum.setText((month - 1) + "月");
                    listener.changeMonth(position, String.valueOf(month - 1));
                }
            }
        });

        rlRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    switch (bean.getSelectFlag()) {
                        case "true":
                            rlRootView.setBackgroundColor(context.getResources().getColor(R.color.white));
                            imageSelectState.setImageResource(R.drawable.icon_verhicle_un_selected);
                            bean.setSelectFlag("false");
                            listener.imageStateChange(position, "false");
                            break;
                        case "false":
                            rlRootView.setBackgroundColor(context.getResources().getColor(R.color.payment_text_color24));
                            imageSelectState.setImageResource(R.drawable.icon_verhicle_selected);
                            bean.setSelectFlag("true");
                            listener.imageStateChange(position, "true");
                            rlRootView.setAlpha(0.8f);
                            break;
                    }
                }
            }
        });
    }

    public interface ParkingAdapterListener {
        void imageStateChange(int position, String stateFlag);

        void changeMonth(int position, String num);
    }
}
