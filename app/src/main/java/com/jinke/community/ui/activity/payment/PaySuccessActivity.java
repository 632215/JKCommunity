package com.jinke.community.ui.activity.payment;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.acachebean.CallCenterBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.ui.activity.base.MainActivity;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.AndroidUtils;
import com.jinke.community.utils.AppManager;

import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-9-4.
 */

public class PaySuccessActivity extends BaseActivity {
    private ACache aCache;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void initView() {
        aCache = ACache.get(this);
        setTitle("支付成功");
        showBackwardView("", false);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        AppManager.finishPending();
        finish();
    }

    @OnClick({R.id.tx_payment_back, R.id.tx_payment_forward, R.id.phone_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_payment_forward:
                AppManager.finishPending();
                Intent payRecorderIntent = new Intent(this, PaymentRecordActivity.class);
                payRecorderIntent.putExtra("recorderType", "0");//0：缴费记录，1：代扣记录
                startActivity(payRecorderIntent);
                finish();
                break;

            case R.id.tx_payment_back:
                startActivity(new Intent(this, MainActivity.class));
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getIntent().getScheme() != null) {
                AppManager.finishPending();
                if (WithholdingActivity.withholdingInstance != null) {
                    WithholdingActivity.withholdingInstance.finish();
                }
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyUtils.addAnaly(1009);
        AnalyUtils.setBAnalyResume(this, "支付成功");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyUtils.setBAnalyPause(this, "支付成功");
    }
}
