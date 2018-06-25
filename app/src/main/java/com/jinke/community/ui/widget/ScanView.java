package com.jinke.community.ui.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.jinke.community.R;

import www.jinke.com.library.utils.commont.LogUtils;

/**
 * Created by Administrator on 2018/3/30.
 */

public class ScanView extends View {
    public static final int style_gridding = 0;//扫描区域的样式
    public static final int style_radar = 1;
    public static final int style_hybrid = 2;

    private Rect mFrame;//最佳扫描区域的Rect
    private Paint mLinePaint;//边框画笔
    private Path mBoundaryLinePath;//边框path
    private int mBoundaryColor =Color.parseColor("#FF344D");
    private float mBoundaryStrokeWidth = 8f;//扫描区域边线样式-线宽

    private float mCornerLineLenRatio = 0.06f;//扫描边框角线占边总长的比例
    private float mCornerLineLen = 50f;//根据比例计算的边框长度，从四角定点向临近的定点画出的长度

    private Paint linePaint;
    private final int lineColor = Color.parseColor("#FF0000");
    private int lineOffsetCount = 0;

    public ScanView(Context context) {
        this(context, null);
    }

    // This constructor is used when the class is built from an XML resource.
    public ScanView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ScanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //扫描区域的四角线框的样式
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(mBoundaryColor);
        mLinePaint.setStrokeWidth(mBoundaryStrokeWidth);
        mLinePaint.setStyle(Paint.Style.STROKE);
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(lineColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int measuredHeight = this.getMeasuredHeight();
        int measuredWidth = this.getMeasuredWidth();
        int rectHeight = 5 * measuredHeight / 8;
        int rectWidth = 5 * measuredWidth / 8;
        int rectLen = rectHeight > rectWidth ? rectWidth : rectHeight;
        int rectLeft = (measuredWidth - rectLen) / 2;
        int rectTop = (measuredHeight - rectLen) / 2;
        mFrame = new Rect(rectLeft, rectTop, rectLeft + rectLen, rectTop + rectLen);
        initBoundaryAndAnimator();
    }

    private void initBoundaryAndAnimator() {
        if (mBoundaryLinePath == null) {
            mCornerLineLen = mFrame.width() * mCornerLineLenRatio;
            mBoundaryLinePath = new Path();
            mBoundaryLinePath.moveTo(mFrame.left, mFrame.top + mCornerLineLen);
            mBoundaryLinePath.lineTo(mFrame.left, mFrame.top);
            mBoundaryLinePath.lineTo(mFrame.left + mCornerLineLen, mFrame.top);
            mBoundaryLinePath.moveTo(mFrame.right - mCornerLineLen, mFrame.top);
            mBoundaryLinePath.lineTo(mFrame.right, mFrame.top);
            mBoundaryLinePath.lineTo(mFrame.right, mFrame.top + mCornerLineLen);
            mBoundaryLinePath.moveTo(mFrame.right, mFrame.bottom - mCornerLineLen);
            mBoundaryLinePath.lineTo(mFrame.right, mFrame.bottom);
            mBoundaryLinePath.lineTo(mFrame.right - mCornerLineLen, mFrame.bottom);
            mBoundaryLinePath.moveTo(mFrame.left + mCornerLineLen, mFrame.bottom);
            mBoundaryLinePath.lineTo(mFrame.left, mFrame.bottom);
            mBoundaryLinePath.lineTo(mFrame.left, mFrame.bottom - mCornerLineLen);
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        if (mFrame == null || mBoundaryLinePath == null) {
            return;
        }
        canvas.drawPath(mBoundaryLinePath, mLinePaint);
        drawLine(canvas);
    }

    private void drawLine(Canvas canvas) {
        //循环划线，从上到下
        if (lineOffsetCount > mFrame.bottom - mFrame.top - dp2px(10)) {
            lineOffsetCount = 0;
        } else {
            lineOffsetCount = lineOffsetCount + 6;
            Rect lineRect = new Rect();
            lineRect.left = mFrame.left;
            lineRect.top = mFrame.top + lineOffsetCount;
            lineRect.right = mFrame.right;
            lineRect.bottom = mFrame.top + dp2px(10) + lineOffsetCount;
            canvas.drawBitmap(((BitmapDrawable) (getResources().getDrawable(R.drawable.icon_sacn_line))).getBitmap(), null, lineRect, linePaint);
//            canvas.drawLine(mFrame.left, mFrame.top + lineOffsetCount, mFrame.right, mFrame.top + lineOffsetCount, linePaint);    //画一条红色的线

        }
        postInvalidateDelayed(10L, mFrame.left, mFrame.top, mFrame.right, mFrame.bottom);
    }

    private int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
}
