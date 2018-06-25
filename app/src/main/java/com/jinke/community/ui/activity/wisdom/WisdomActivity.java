package com.jinke.community.ui.activity.wisdom;

import android.view.View;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.utils.AnalyUtils;
import com.tencent.stat.StatService;

/**
 * Created by root on 17-8-14.
 */

public class WisdomActivity extends BaseActivity {
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_wisdom;
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void initView() {
        setTitle("魅力家");
        showBackwardView("", true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(WisdomActivity.this);
        StatService.trackBeginPage(WisdomActivity.this, "魅力家");
        AnalyUtils.setBAnalyResume(this, "魅力家");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(WisdomActivity.this);
        StatService.trackEndPage(WisdomActivity.this, "魅力家");
        AnalyUtils.setBAnalyPause(this, "魅力家");
    }
}
