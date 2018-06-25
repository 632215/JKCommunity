package com.jinke.community.ui.activity.payment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.ExpensesPayBean;
import com.jinke.community.bean.PayBean;
import com.jinke.community.bean.PrepaidExpensesBean;
import com.jinke.community.bean.WithholdingBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.payment.alipay.SignUtils;
import com.jinke.community.presenter.WithholdingPresenter;
import com.jinke.community.ui.toast.PayDialog;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.TextUtils;
import com.jinke.community.utils.WXUtils;
import com.jinke.community.view.WithholdingView;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.stat.StatService;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/8/4.
 * 代扣/支付
 */

public class ExpensesPayActivity extends BaseActivity<WithholdingView, WithholdingPresenter> implements WithholdingView {
    @Bind(R.id.payment_withholding_money)
    TextView withholdingMoney;
    @Bind(R.id.we_select_image)
    ImageView wxImage;
    @Bind(R.id.ali_select_image)
    ImageView payImage;
    private String houseId;
    ExpensesPayBean expensesPayBean;
    private PayDialog payDialog;

    @Override
    public WithholdingPresenter initPresenter() {
        return new WithholdingPresenter(ExpensesPayActivity.this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_expenses_pay;
    }

    @Override
    protected void initView() {
        setTitle(R.string.payment);
        showBackwardView(R.string.empty, true);
        payImage.setBackgroundResource(R.drawable.icon_my_house_chose);
        expensesPayBean = (ExpensesPayBean) getIntent().getSerializableExtra("bean");
        houseId = getIntent().getStringExtra("houseId");
        initData();
    }

    private void initData() {
        TextUtils.setText(withholdingMoney, "#FF001F", "￥" + expensesPayBean.getMoneyTotal());
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }


    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(ExpensesPayActivity.this, msg);
    }

    @Override
    public void payResult(PayBean bean) {
        Intent intent = new Intent(this, PaymentResultActivity.class);
        intent.putExtra("bean", bean);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoading() {
//        showProgressDialog(null);
        if (payDialog == null) {
            payDialog = new PayDialog(this);
            payDialog.setCanceledOnTouchOutside(false);
            payDialog.show();
        }
    }

    @Override
    public void hideLoading() {
//        hideDialog();
        payDialog.dismiss();
    }

    @Override
    public void onDoPay(PrepaidExpensesBean bean) {
    }

    @Override
    public void getSignStateNext(WithholdingBean info) {
    }

    @Override
    public void finishActivity() {
        finish();
    }

    private String payType = "alipay";//微信支付

    @OnClick({R.id.ali_layout, R.id.we_layout, R.id.forward_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.we_layout:
                payImage.setBackgroundResource(R.drawable.icon_un_select);
                wxImage.setBackgroundResource(R.drawable.icon_my_house_chose);
                payType = "wxpay";
                break;

            case R.id.ali_layout:
                AppConfig.trackCustomEvent(this, "ali_layout_click", "代扣－选择支付宝支付");
                wxImage.setBackgroundResource(R.drawable.icon_un_select);
                payImage.setBackgroundResource(R.drawable.icon_my_house_chose);
                payType = "alipay";
                break;

            case R.id.forward_text:
                AnalyUtils.addAnaly(1008);
                callPayPlatform();
                break;
        }
    }

    private void callPayPlatform() {
        AppConfig.trackCustomEvent(this, "forward_text_click", "代扣－确认支付");
        switch (payType) {
            case "wxpay":
                if (WXAPIFactory.createWXAPI(this, null).getWXAppSupportAPI() < Build.PAY_SUPPORTED_SDK_INT) {
                    ToastUtils.showShort(this, "您未安装最新版本微信，不支持微信支付，请安装或升级微信版本！");
                    return;
                }
                break;

            case "alipay":
                if (!SignUtils.checkAliPayInstalled(this)) {
                    ToastUtils.showShort(this, "您还没有安装支付宝应用！");
                    return;
                }
                break;
        }
        if (expensesPayBean != null) {
            Map<String, String> map = new HashMap<>();
            map.put("accessToken", expensesPayBean.getAccessToken());
            map.put("houseId", expensesPayBean.getHouseId());
            map.put("prepayList", expensesPayBean.getPrepayList());
            map.put("payType", payType);
            map.put("returnUrl", "jkcommunity://success");
            presenter.getDoPrePay(map);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(ExpensesPayActivity.this);
        StatService.trackBeginPage(ExpensesPayActivity.this, "缴费－支付");
        AnalyUtils.setBAnalyResume(this, "缴费－支付");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(ExpensesPayActivity.this);
        StatService.trackEndPage(ExpensesPayActivity.this, "缴费－支付");
        AnalyUtils.setBAnalyPause(this, "缴费－支付");
    }
}
