package com.jinke.community.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.bean.LicenseBean;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */

public class LicenseAdapter extends CommonAdapter<LicenseBean> {
    private LicenseClickListener listener;

    public void setListener(LicenseClickListener listener) {
        this.listener = listener;
    }

    public LicenseAdapter(@NonNull Context context, @LayoutRes int layoutResId, @NonNull List<LicenseBean> dataList, LicenseClickListener listener) {
        super(context, layoutResId, dataList);
        this.listener = listener;
    }

    public LicenseAdapter(@NonNull Context context, @LayoutRes int layoutResId, @NonNull List<LicenseBean> dataList) {
        super(context, layoutResId, dataList);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final LicenseBean bean, final int position) {
        final TextView txSelected = (TextView) baseViewHolder.getViewByViewId(R.id.tx_selected);
        txSelected.setText(bean.getText());
        if (position==0){
            txSelected.setBackgroundResource(R.drawable.icon_authorized_pop_selected);
        }
        txSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onLicenseClick(bean, position);
            }
        });
    }

    public void clearDataList() {
        dataList.clear();
    }

    public interface LicenseClickListener {
        void onLicenseClick(LicenseBean s, int position);
    }


}
