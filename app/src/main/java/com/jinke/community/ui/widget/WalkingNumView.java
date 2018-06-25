package com.jinke.community.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.jinke.community.R;

/**
 * Created by Administrator on 2017/7/31.
 */

public class WalkingNumView extends View {
    private Paint mBackPaint;//白色圈
    private Paint mFrontPaint;//橙色圈
    private Paint mNumPaint;//步數
    private Paint mTextPaint;//文字

    private float mStrokeWidth = 15;
    private float mHalfStrokeWidth = mStrokeWidth / 2;
    private float mRadius = 200;//半径
    private RectF mRect;
    private int mProgress = 0;
    //最终步数
    private int mTargetProgress;
    private int mMax = 100;
    private int mWidth;
    private int mHeight;

    private LinearGradient linearGradient;

    private int[] doughnutColors = {Color.GREEN,Color.RED};

    public float getmRadius() {
        return mRadius;
    }

    public void setmRadius(float mRadius) {
        this.mRadius = mRadius;
    }

    public int getmTargetProgress() {
        return mTargetProgress;
    }

    public int getmMax() {
        return mMax;
    }

    public void setmMax(int mMax) {
        this.mMax = mMax;
    }

    public void setmTargetProgress(int mTargetProgress) {
        this.mTargetProgress = mTargetProgress;
    }

    public WalkingNumView(Context context) {
        super(context);
        init();
    }

    public WalkingNumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WalkingNumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public WalkingNumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @SuppressLint("ResourceAsColor")
    private void init() {
        mBackPaint = new Paint();
        mBackPaint.setColor(Color.WHITE);
        mBackPaint.setAntiAlias(true);
        mBackPaint.setStyle(Paint.Style.STROKE);
        mBackPaint.setStrokeWidth(2);

        mFrontPaint = new Paint();
        mFrontPaint.setColor(Color.GREEN);
        mFrontPaint.setAntiAlias(true);
        mFrontPaint.setStyle(Paint.Style.STROKE);
        mFrontPaint.setStrokeWidth(mStrokeWidth);

        mNumPaint = new Paint();
        mNumPaint.setColor(Color.WHITE);
        mNumPaint.setAntiAlias(true);
        mNumPaint.setTextSize(mRadius / 2);
        mNumPaint.setTextAlign(Paint.Align.CENTER);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mRadius / 6);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    //重写测量大小的onMeasure方法和绘制View的核心方法onDraw()
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getRealSize(widthMeasureSpec);
        mHeight = getRealSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        initRect();
        float angle = mProgress / (float) mMax * 360;
        linearGradient = new LinearGradient(0, 0, angle/360 *(mWidth/2), angle/360 *(mHeight/2),new int[]{getResources().getColor(R.color.circle_end_color), getResources().getColor(R.color.circle_start_color)}, null, LinearGradient.TileMode.CLAMP);

//        linearGradient = new LinearGradient(0, 0, angle/360 *(mWidth/icon_cs2), angle/360 *(mHeight/icon_cs2),new int[]{Color.RED, Color.BLUE}, null, LinearGradient.TileMode.CLAMP);
        mFrontPaint.setShader(linearGradient);

        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mBackPaint);
//        mFrontPaint.setShader(new SweepGradient(mWidth / icon_cs2, mHeight / icon_cs2, doughnutColors, null));
        canvas.drawArc(mRect, -90, angle, false, mFrontPaint);
        canvas.drawText(String.valueOf(mProgress), mWidth / 2, mHeight / 2 +4*mStrokeWidth, mNumPaint);
        canvas.drawText("今日步数", mWidth / 2 , mHeight / 2 - 4*mStrokeWidth, mTextPaint);
        if (mProgress < mTargetProgress) {
            mProgress += (mMax/mTargetProgress);
            invalidate();
        }
    }

    public int getRealSize(int measureSpec) {
        int result = 1;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED) {
            //自己计算
            result = (int) (mRadius * 2 + mStrokeWidth);
        } else {
            result = size;
        }
        return result;
    }

    private void initRect() {
        if (mRect == null) {
            mRect = new RectF();
            int viewSize = (int) ((int) (mRadius * 2)-mStrokeWidth);
            int left = (mWidth - viewSize) / 2;
            int top = (mHeight - viewSize) / 2;
            int right = left + viewSize;
            int bottom = top + viewSize;
            mRect.set(left, top, right, bottom);
        }
    }

}
