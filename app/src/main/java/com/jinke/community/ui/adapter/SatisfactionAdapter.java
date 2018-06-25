package com.jinke.community.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.jinke.community.R;
import com.jinke.community.ui.adapter.base.BaseViewHolder;
import com.jinke.community.ui.adapter.base.CommonAdapter;

import java.util.List;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2017/12/6.
 */

public class SatisfactionAdapter extends CommonAdapter<String> {
    private int sizeFlag = 0;//0 小星星  1大

    public void setSizeFlag(int sizeFlag) {
        this.sizeFlag = sizeFlag;
    }

    public SatisfactionAdapter(@NonNull Context context, @LayoutRes int layoutResId, @NonNull List<String> dataList) {
        super(context, layoutResId, dataList);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s, int position) {
        ImageView imageView = (ImageView) baseViewHolder.getViewByViewId(R.id.image_star);
        imageView.setImageResource(StringUtils.equals("1", s) ? (sizeFlag == 0 ? R.mipmap.icon_satisfaction_red : R.mipmap.icon_satisfaction_red_big)
                : (sizeFlag == 0 ? R.mipmap.icon_satisfaction_gray : R.mipmap.icon_satisfaction_gray_big));
    }
}
