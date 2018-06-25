package com.jinke.community.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.jinke.community.R;

/**
 * Created by Administrator on 2018/3/13.
 */

public class CustomRadioButton extends RadioButton {
    private boolean isVisiable = false;
    private Paint mPanit = new Paint();
    private float width = 0;
    private float height = 0;

    public CustomRadioButton(Context context) {
        super(context);
        setPaint();
    }

    public CustomRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPaint();
    }

    public boolean isVisiable() {
        return isVisiable;
    }

    public void setVisiable(boolean visiable) {
        isVisiable = visiable;
        invalidate();
    }

    private void setPaint() {
        mPanit.setColor(getResources().getColor(R.color.main_them_color));
        mPanit.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec) / 2;
        height = MeasureSpec.getSize(heightMeasureSpec) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isVisiable) {
            canvas.drawOval(new RectF(width + 33, 3, width + 63, 33), mPanit);
            canvas.save();
        }
    }
}
