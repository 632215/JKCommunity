package com.jinke.community.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by root on 17-7-31.
 */

public class FillRecyclerView extends RecyclerView {
    public FillRecyclerView(Context context) {
        super(context);
    }

    public FillRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FillRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
