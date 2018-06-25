package com.jinke.community.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * function:
 * author: hank
 * date: 2017/6/29
 */

public class ScrollViewGridView extends GridView {
    public ScrollViewGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewGridView(Context context) {
        super(context);
    }

    public ScrollViewGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
