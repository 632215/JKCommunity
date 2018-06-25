package com.jinke.community.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import www.jinke.com.library.utils.commont.LogUtils;

/**
 * Created by root on 17-8-22.
 */

public class GoTopScrollView extends ScrollView {
    public GoTopScrollView(Context context) {
        super(context);
    }

    private boolean isScrolledToTop = true;// 初始化的时候设置一下值
    private boolean isScrolledToBottom = false;

    public GoTopScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (android.os.Build.VERSION.SDK_INT < 9) {
            if (getScaleY() == 0) {
                isScrolledToTop = true;
                isScrolledToBottom = false;
            } else if (getScaleY() + getHeight() - getPaddingTop() - getPaddingBottom() == getChildAt(0).getHeight()) {
                isScrolledToTop = false;
                isScrolledToBottom = true;
            } else {
                isScrolledToTop = false;
                isScrolledToBottom = false;
            }
            notifyScrollChangedListeners();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollY == 0) {
            isScrolledToTop = true;
            isScrolledToBottom = false;
        } else {
            isScrolledToTop = false;
            isScrolledToBottom = clampedY;
        }

        notifyScrollChangedListeners();
    }

    private void notifyScrollChangedListeners() {
        if (isScrolledToTop) {
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToTop();
            }
        } else if (isScrolledToBottom) {
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToBottom();
            }
        }
    }

    public boolean isScrolledToTop() {
        return isScrolledToTop;
    }

    public boolean isScrolledToBottom() {
        return isScrolledToBottom;
    }

    ISmartScrollChangedListener mSmartScrollChangedListener;
    public void setScanScrollChangedListener(ISmartScrollChangedListener smartScrollChangedListener) {
        mSmartScrollChangedListener = smartScrollChangedListener;
    }
    /**
     * 定义监听接口
     */
    public interface ISmartScrollChangedListener {
        void onScrolledToBottom();

        void onScrolledToTop();
    }
}
