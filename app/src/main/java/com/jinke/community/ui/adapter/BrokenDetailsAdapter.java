package com.jinke.community.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jinke.community.R;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;

import java.util.List;

/**
 * Created by root on 17-8-11.
 */

public class BrokenDetailsAdapter extends CommonAdapter<String> {
    private Context mContext;

    public BrokenDetailsAdapter(@NonNull Context context, int layoutResId, @NonNull List<String> dataList) {
        super(context, layoutResId, dataList);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s, int position) {
        ImageView image = (ImageView) baseViewHolder.getViewByViewId(R.id.simpleImage);
//        LogUtils.i("图片地址"+s);
//        image.setImageURI(s);
        Glide.with(mContext).load(s).error(mContext.getResources().getDrawable(R.drawable.icon_fail_pic))
                .placeholder(mContext.getResources().getDrawable(R.drawable.icon_fail_pic)).crossFade(1000).
                into(image);
    }

}
