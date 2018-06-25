package com.jinke.community.ui.activity.base;

import android.view.View;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.utils.AnalyUtils;
import com.tencent.stat.StatService;

/**
 * Created by root on 17-8-22.
 */

public class CompetitionActivity  extends BaseActivity{
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_competition_community;
    }

    @Override
    protected void initView() {
        setTitle("社区赛事");
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
        StatService.onResume(CompetitionActivity.this);
        StatService.trackBeginPage(CompetitionActivity.this, "社区赛事");
        AnalyUtils.setBAnalyResume(this, "社区赛事");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(CompetitionActivity.this);
        StatService.trackEndPage(CompetitionActivity.this, "社区赛事");
        AnalyUtils.setBAnalyPause(this, "社区赛事");
    }
}
