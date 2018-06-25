package com.jinke.community.ui.activity.wisdom;

import android.view.View;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.ui.activity.web.WebActivity;
import com.jinke.community.utils.AnalyUtils;
import com.tencent.stat.StatService;

/**
 * Created by root on 17-8-14.
 */

public class HealthActivity extends BaseActivity {
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_health;
    }

    @Override
    protected void initView() {
        setTitle("我的健康");
        showBackwardView("",true);

    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(HealthActivity.this);
        StatService.trackBeginPage(HealthActivity.this, "我的健康");
        AnalyUtils.setBAnalyResume(this, "我的健康");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(HealthActivity.this);
        StatService.trackEndPage(HealthActivity.this, "我的健康");
        AnalyUtils.setBAnalyPause(this, "我的健康");
    }
}
