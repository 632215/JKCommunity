package com.jinke.community.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jinke.community.R;
import com.jinke.community.bean.acachebean.LifeTopBannerBean;
import com.jinke.community.ui.activity.web.LifeDetailsActivity;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;
import com.jinke.community.ui.toast.UniversalDialog;
import com.jinke.community.utils.AnalyUtils;

import java.util.List;

import www.jinke.com.library.utils.commont.LogUtils;

/**
 * Created by root on 17-8-16.
 */

public class LifeTypeAdapter extends CommonAdapter<LifeTopBannerBean.ListBean> implements UniversalDialog.onUniversalDialogListener {
    private Context mContext;
    private UniversalDialog dialog;
    private LifeTopBannerBean.ListBean bean;
    private LifeTopBannerBean.ListBean tempBean;


    public LifeTypeAdapter(@NonNull Context context, int layoutResId, @NonNull List<LifeTopBannerBean.ListBean> dataList) {
        super(context, layoutResId, dataList);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final LifeTopBannerBean.ListBean listBean, int position) {
        ImageView imageView = (ImageView) baseViewHolder.getViewByViewId(R.id.image_supermarket);
        TextView title = (TextView) baseViewHolder.getViewByViewId(R.id.tx_life_type);
        title.setText(listBean.getTitle());
        LogUtils.i("广告栏图片：" + listBean.getCircleImageUrl());
        Glide.with(mContext).load(listBean.getCircleImageUrl())
                .error(R.drawable.icon_life_fail_type)
                .placeholder(R.drawable.icon_life_fail_type).into(imageView);
        this.bean = listBean;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listBean.getPoint().equals("1")) {
                    tempBean = listBean;
                    if (dialog == null) {
                        dialog = new UniversalDialog(mContext, LifeTypeAdapter.this);
                    }
                    dialog.setContent(mContext.getString(R.string.universal_tips));
                    dialog.show();
                } else {
                    AnalyUtils.addLifeAnaly(10026, listBean.getId());
                    Intent intent = new Intent(mContext, LifeDetailsActivity.class);
                    intent.putExtra("url", listBean.getCircleLinkUrl());
                    intent.putExtra("title", listBean.getTitle());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onCall(String phone) {
        Intent intent = new Intent(mContext, LifeDetailsActivity.class);
        intent.putExtra("url", tempBean.getCircleLinkUrl());
        intent.putExtra("title", tempBean.getTitle());
        dialog.dismiss();
        mContext.startActivity(intent);
    }
}
