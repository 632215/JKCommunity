package com.jinke.community.ui.activity.base;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.config.AppConfig;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.ui.activity.web.WebActivity;
import com.jinke.community.utils.AnalyUtils;
import com.tencent.bugly.beta.Beta;
import com.tencent.stat.StatService;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.AppUtils;

/**
 * Created by root on 17-8-7.
 */

public class VersionActivity extends BaseActivity {
    @Bind(R.id.tx_version)
    TextView txVersion;
    @Bind(R.id.tx_user_agreement)
    TextView txUserAgreement;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_version_up;
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void initView() {
        setTitle("版本信息");
        showBackwardView("", true);
        txVersion.setText("版本号：v" + AppUtils.getAppVersionName(this));
    }

    @OnClick({R.id.tx_check_version, R.id.tx_user_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            //检查更新
            case R.id.tx_check_version:
                AppConfig.trackCustomEvent(this, "tx_check_version_click", "版本更新");
                Beta.checkUpgrade();
                break;
            case R.id.tx_user_agreement:
//                startActivity(new Intent(this, UserAgreementActivity.class));
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra("url", HttpMethods.BASE_URL.replace("uc/", "") + AppConfig.URL_USER_PROTOL)
                        .putExtra("title", getString(R.string.activity_login_protocol))
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(VersionActivity.this);
        StatService.trackBeginPage(VersionActivity.this, "版本信息");
        AnalyUtils.setBAnalyResume(this, "版本信息");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(VersionActivity.this);
        StatService.trackEndPage(VersionActivity.this, "版本信息");
        AnalyUtils.setBAnalyPause(this, "版本信息");
    }
}
