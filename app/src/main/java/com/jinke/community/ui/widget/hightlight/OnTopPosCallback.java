package com.jinke.community.ui.widget.hightlight;

import android.graphics.RectF;


/**
 * Created by Administrator on 2017/11/30.
 */
public class OnTopPosCallback extends OnBaseCallback {
    public OnTopPosCallback() {
    }

    public OnTopPosCallback(float offset) {
        super(offset);
    }

    @Override
    public void getPosition(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
//        marginInfo.leftMargin = rectF.right - rectF.width() / 2;
        marginInfo.bottomMargin = bottomMargin+rectF.height()+offset;
    }
}
