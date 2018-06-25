package com.jinke.community.ui.activity.payment;

import android.content.Intent;
import android.view.View;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.acachebean.CallCenterBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.ui.activity.base.MainActivity;
import com.jinke.community.ui.activity.base.StartupPageActivity;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.AndroidUtils;

import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-9-4.
 */

public class PayFailActivity extends BaseActivity {
    private ACache aCache;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_pay_fail;
    }

    @Override
    protected void initView() {
        if (MyApplication.getInstance().getDefaultHouse() != null)
            AnalyUtils.addAnaly(10033, MyApplication.getInstance().getDefaultHouse().getHouse_id());
        else
            AnalyUtils.addAnaly(10033);
        aCache = ACache.get(this);
        setTitle("支付失败");
        showBackwardView("", false);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        startActivity(new Intent(this, StartupPageActivity.class));
    }

    @OnClick({R.id.tx_payment_back, R.id.tx_payment_forward, R.id.phone_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_payment_back:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.tx_payment_forward:
                finish();
                break;

            case R.id.phone_text:
                CallCenterBean bean = (CallCenterBean) aCache.getAsObject("CallCenterBean");
                AndroidUtils.callPhone(this, bean != null && !StringUtils.isEmpty(bean.getServicePhone()) ? bean.getServicePhone()
                        : AppConfig.SERVICEPHONE);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyUtils.addAnaly(10010);
        AnalyUtils.setBAnalyResume(this, "支付失败");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyUtils.setBAnalyPause(this, "支付失败");
    }
}
