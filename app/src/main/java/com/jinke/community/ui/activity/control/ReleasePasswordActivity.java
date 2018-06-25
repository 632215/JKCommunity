package com.jinke.community.ui.activity.control;

import android.view.View;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.presenter.ReleasePasswordPresenter;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.ReleasePasswordView;
import com.tencent.stat.StatService;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/8/15.
 * 放行密码
 */

public class ReleasePasswordActivity extends BaseActivity<ReleasePasswordView,ReleasePasswordPresenter> {
    @Bind(R.id.tx_visit_w_address)
    TextView visitAddress;
    @Bind(R.id.tx_visit_w_purpose)
    TextView visitPurpose;
    @Bind(R.id.tx_visit_w_time)
    TextView visitTime;
    @Bind(R.id.tx_visit_w_times)
    TextView visitTimes;
    @Override
    public ReleasePasswordPresenter initPresenter() {
        return new ReleasePasswordPresenter();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_release_password;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_release_password_title));
        showBackwardView(R.string.empty,true);
        setTitleColor(getResources().getColor(R.color.white));
        showBackwardViewIco(R.mipmap.icon_activity_access_arrow_back);
        setTitleBarBgColor(R.color.activity_access_control_title_bg);
        visitAddress.setText("洋房区12-14");
        visitPurpose.setText("中介看房");
        visitTime.setText("2017年8月15日当天");
        visitTimes.setText("一次");
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(ReleasePasswordActivity.this);
        StatService.trackBeginPage(ReleasePasswordActivity.this, "门禁－重置密码");
        AnalyUtils.setBAnalyResume(this, "门禁－重置密码");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(ReleasePasswordActivity.this);
        StatService.trackEndPage(ReleasePasswordActivity.this, "门禁－重置密码");
        AnalyUtils.setBAnalyPause(this, "门禁－重置密码");
    }
}
