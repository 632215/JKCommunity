package com.jinke.community.ui.activity.base;

import android.content.Intent;
import android.widget.RelativeLayout;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.presenter.StartupPagePresenter;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.DrawableUtils;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.jinke.community.view.StartupPageView;
import com.tencent.stat.StatService;

import butterknife.Bind;

/**
 * Created by root on 17-7-22.
 */

public class StartupPageActivity extends BaseActivity<StartupPageView, StartupPagePresenter> implements StartupPageView {
    @Bind(R.id.startup)
    RelativeLayout startup;

    @Override
    public StartupPagePresenter initPresenter() {
        return new StartupPagePresenter(StartupPageActivity.this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_startuppage;
    }

    @Override
    protected void initView() {
        presenter.getTheme();
        DrawableUtils.setMainDrawable(this);
        presenter.stratAnminatoin(startup);
        //统计app启动的次数
        StatService.startNewSession(this);
        //百度统计初始化
        com.baidu.mobstat.StatService.setDebugOn(false);
        com.baidu.mobstat.StatService.start(this);
    }

    @Override
    public void startActivity(Class activity) {
        if (activity.equals(MainActivity.class)) {
            BaseUserBean baseUserBean = MyApplication.getBaseUserBean();
            baseUserBean.setShow(false);
            SharedPreferencesUtils.saveBaseUserBean(this, baseUserBean);
            Intent intent = new Intent(StartupPageActivity.this, activity);
            startActivity(intent);
            finish();
        } else {
            startActivity(new Intent(StartupPageActivity.this, activity));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(StartupPageActivity.this);
        StatService.trackBeginPage(StartupPageActivity.this, "启动页");
        AnalyUtils.setBAnalyResume(this, "启动页");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(StartupPageActivity.this);
        StatService.trackEndPage(StartupPageActivity.this, "启动页");
        AnalyUtils.setBAnalyPause(this, "启动页");
    }
}
