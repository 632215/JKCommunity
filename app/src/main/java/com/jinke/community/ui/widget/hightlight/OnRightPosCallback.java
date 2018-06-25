package com.jinke.community.ui.widget.hightlight;

import android.graphics.RectF;

/**
 * Created by Administrator on 2017/11/30.
 */

public class OnRightPosCallback extends OnBaseCallback {
    public OnRightPosCallback() {
    }

    public OnRightPosCallback(float offset) {
        super(offset);
    }

    @Override
    public void getPosition(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
        marginInfo.leftMargin = rectF.right + offset;
        marginInfo.topMargin = rectF.top;
    }
}