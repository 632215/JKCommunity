package com.jinke.community.ui.activity.broken;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.config.AppConfig;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.tencent.stat.StatService;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-8-4.
 * 爆料报事的选择
 */

public class BrokeNewsActivity extends BaseActivity {
    @Bind(R.id.rl_tips)
    RelativeLayout rlTips;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_broke_news;
    }

    @Override
    protected void initView() {
        if (StringUtils.isEmpty(SharedPreferencesUtils.getFirstReport(this))) {
            rlTips.setVisibility(View.VISIBLE);
        } else {
            setTitle(R.string.property_title);
            showBackwardView("", true);
            rlTips.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.property_image, R.id.image_broken, R.id.img_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.property_image:
                AnalyUtils.addAnaly(10019);
                AppConfig.trackCustomEvent(this, "property_view_click", "物业报事");
                Intent property = new Intent(BrokeNewsActivity.this, PropertyNewsActivity.class);
                startActivity(property);
                break;

            case R.id.image_broken:
                AnalyUtils.addAnaly(10020);
                AppConfig.trackCustomEvent(this, "broken_view_click", "小金妹爆料");
                Intent intent = new Intent(BrokeNewsActivity.this, BrokeUpActivity.class);
                startActivity(intent);
                break;

            case R.id.img_close:
                rlTips.setVisibility(View.GONE);
                setTitle(R.string.property_title);
                showBackwardView("", true);
                SharedPreferencesUtils.setFirstReport(this, "false");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(BrokeNewsActivity.this);
        StatService.trackBeginPage(BrokeNewsActivity.this, getString(R.string.property_title));
        AnalyUtils.setBAnalyResume(this, getString(R.string.property_title));
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(BrokeNewsActivity.this);
        StatService.trackEndPage(BrokeNewsActivity.this, getString(R.string.property_title));
        AnalyUtils.setBAnalyPause(this, getString(R.string.property_title));
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }
}
