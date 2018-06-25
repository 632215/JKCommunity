package com.jinke.community.ui.activity.payment;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.PayBean;
import com.jinke.community.bean.acachebean.CallCenterBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.presenter.PaymentResultPresenter;
import com.jinke.community.ui.activity.base.MainActivity;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.AndroidUtils;
import com.jinke.community.utils.AppManager;
import com.jinke.community.view.PaymentResultView;
import com.tencent.stat.StatService;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/8/4.
 * 支付成功/失败
 */

public class PaymentResultActivity extends BaseActivity<PaymentResultView, PaymentResultPresenter> implements PaymentResultView {
    @Bind(R.id.image_result_image)
    ImageView imageResult;
    @Bind(R.id.tx_result_tips)
    TextView txResultTips;
    @Bind(R.id.tx_payment_forward)
    TextView txPaymentForward;
    @Bind(R.id.tx_payment_back)
    TextView txPaymentBack;
    @Bind(R.id.tip_text)
    TextView tipText;
    @Bind(R.id.phone_text)
    TextView phoneText;

    private ACache aCache;
    private PayBean payBean;//支付返回参数
    private String withHoldOpenResult = "";//开通代扣返回参数

    @Override
    public PaymentResultPresenter initPresenter() {
        return new PaymentResultPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_payment_result;
    }

    @Override
    protected void initView() {
        aCache = ACache.get(this);
        withHoldOpenResult = getIntent().getStringExtra("withHoldOpenResult");
        if (StringUtils.isEmpty(withHoldOpenResult)) {
            payBean = (PayBean) getIntent().getSerializableExtra("bean");
            setTitle(payBean.getState().equals("1") ? R.string.payment_success : R.string.payment_failed);
            showBackwardView(R.string.empty, true);
            imageResult.setBackgroundResource(payBean.getState().equals("1") ? R.mipmap.icon_pay_success : R.mipmap.icon_payment_fail);
            txPaymentForward.setText(payBean.getState().equals("1") ? "缴费记录" : "重新支付");
//            txResultTips.setText(payBean.getState().equals("1") ? "恭喜您支付成功" : "支付失败请重新支付");
            txResultTips.setText(payBean.getState().equals("1") ? R.string.payment_success : R.string.payment_failed);
            if (!payBean.getState().equals("1")) {
                if (MyApplication.getInstance().getDefaultHouse() != null)
                    AnalyUtils.addAnaly(10033, MyApplication.getInstance().getDefaultHouse().getHouse_id());
                else
                    AnalyUtils.addAnaly(10033);
            }
        }
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
            case R.id.tx_payment_back:
                AppConfig.trackCustomEvent(this, "tx_payment_back_click", "支付结果－回到首页");
                AppManager.finishPending();
                startActivity(new Intent(PaymentResultActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.tx_payment_forward:
                AppManager.finishPending();
                AppConfig.trackCustomEvent(this, "tx_payment_forward_click", "支付结果－重新支付");
                if (!txPaymentForward.getText().toString().equals("重新支付")) {//判断用户点击的是重新支付还是缴费记录
                    Intent payRecorderIntent = new Intent(this, PaymentRecordActivity.class);
                    payRecorderIntent.putExtra("recorderType", "0");//0：缴费记录，1：代扣记录
                    startActivity(payRecorderIntent);
                }
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
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
    }

    @Override
    public void payResult(String state) {
        imageResult.setBackgroundResource(state.equals("1") ? R.mipmap.icon_pay_success : R.mipmap.icon_payment_fail);
        txPaymentForward.setText(state.equals("1") ? "缴费记录" : "重新支付");
        setTitle(state.equals("1") ? R.string.payment_success : R.string.payment_failed);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppManager.finishPending();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (payBean != null) {
            AnalyUtils.addAnaly(payBean.getState().equals("1") ? 1009 : 10010);
        }
        StatService.onResume(PaymentResultActivity.this);
        StatService.trackBeginPage(PaymentResultActivity.this, "缴费-支付结果");
        AnalyUtils.setBAnalyResume(this, "缴费-支付结果");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(PaymentResultActivity.this);
        StatService.trackEndPage(PaymentResultActivity.this, "缴费－支付结果");
        AnalyUtils.setBAnalyPause(this, "缴费-支付结果");
    }
}
