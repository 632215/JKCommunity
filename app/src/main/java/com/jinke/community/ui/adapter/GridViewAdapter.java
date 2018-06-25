package com.jinke.community.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jinke.community.R;
import com.jinke.community.bean.LifeRecommendBean;
import com.jinke.community.ui.activity.web.LifeDetailsActivity;
import com.jinke.community.utils.AnalyUtils;

import java.util.List;

import www.jinke.com.library.adapter.BaseAdapter;
import www.jinke.com.library.adapter.BaseHolder;
import www.jinke.com.library.utils.commont.LogUtils;

/**
 * Created by Administrator on 2017/7/31.
 */

public class GridViewAdapter extends BaseAdapter<LifeRecommendBean.ListBeanX.ListBean> {
    private Context mContext;

    public GridViewAdapter(Context context, List<LifeRecommendBean.ListBeanX.ListBean> data, int layoutId) {
        super(context, data, layoutId);
        this.mContext = context;
    }


    @Override
    public void onBind(BaseHolder baseHolder, final LifeRecommendBean.ListBeanX.ListBean listBean, int i) {
        ImageView imageView = (ImageView) baseHolder.getView(R.id.grid_image);
        LogUtils.i("金榜提名----" + listBean.getCircleImageUrl());
        Glide.with(mContext).load(listBean.getCircleImageUrl())
                .error(R.drawable.icon_life_fail_merchandise)
                .placeholder(R.drawable.icon_life_fail_merchandise)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyUtils.addLifeAnaly(10026,listBean.getId());
                Intent intent = new Intent(mContext, LifeDetailsActivity.class);
                intent.putExtra("url", listBean.getCircleLinkUrl());
                intent.putExtra("title", listBean.getTitle());
                mContext.startActivity(intent);
            }
        });
    }
}
