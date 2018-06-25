package com.jinke.community.ui.activity.payment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.ArrearsListBean;
import com.jinke.community.bean.PayBean;
import com.jinke.community.bean.PrepaidExpensesBean;
import com.jinke.community.bean.WithholdingBean;
import com.jinke.community.payment.alipay.SignUtils;
import com.jinke.community.presenter.WithholdingPresenter;
import com.jinke.community.ui.toast.PayDialog;
import com.jinke.community.ui.toast.PayTipsDialog;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.DecimalFormatUtils;
import com.jinke.community.utils.TextUtils;
import com.jinke.community.view.WithholdingView;
import com.tencent.stat.StatService;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/8/4.
 * 待缴费
 * <p>
 * getSignStateNext()方法中隐藏代扣管理按钮，需要开通自行解开
 * onResume()方法中隐藏代扣管理弹框，需要开通自行解开
 */

public class WithholdingActivity extends BaseActivity<WithholdingView, WithholdingPresenter> implements WithholdingView {
    @Bind(R.id.tx_fee_content_address)
    TextView txContentAddress;
    @Bind(R.id.tx_fee_content_time)
    TextView txContentTime;
    @Bind(R.id.tx_fee_content)
    TextView txContent;
    @Bind(R.id.tx_total_money)
    TextView txTotalMoney;
    @Bind(R.id.we_select_image)
    ImageView wxImage;
    @Bind(R.id.ali_select_image)
    ImageView payImage;
    @Bind(R.id.rl_ali_withholding)
    RelativeLayout rlAliWithholding;
    @Bind(R.id.image_wechat_withholding_select)
    ImageView imageWechatWithholding;
    @Bind(R.id.image_ali_withholding_select)
    ImageView imageAliWithholding;

    private String houseId;
    private String payMode = "pay";                //payMode决定代扣 pay_and_sign代扣   pay支付
    private String payType = "alipay";          //payType 决定微信或者支付宝  wxpay微信   alipay支付宝
    private PayTipsDialog payTipsDialog;
    ArrearsListBean arrearsListBean;
    public static WithholdingActivity withholdingInstance = null;
    private PayDialog payDialog;

    @Override
    public WithholdingPresenter initPresenter() {
        return new WithholdingPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_withholding;
    }

    @Override
    protected void initView() {
        setTitle(R.string.payment);
        showBackwardView(R.string.empty, true);
        withholdingInstance = this;
//        signType = getIntent().getStringExtra("sign_type");
        arrearsListBean = (ArrearsListBean) getIntent().getSerializableExtra("bean");
        houseId = getIntent().getStringExtra("houseId");
        if (!StringUtils.isEmpty(houseId)) {
            showProgressDialog("true");
            presenter.getSignState(houseId);//查询签约状态。如果已签约就隐藏签约代扣的按钮
        }
    }

    @Override
    public void getSignStateNext(WithholdingBean info) {
        hideDialog();
//        if (info != null && info.getAli_sign_status() == 1 || info.getWx_sign_status() == 1)
//            rlAliWithholding.setVisibility(View.GONE);
//        else
//            rlAliWithholding.setVisibility(View.VISIBLE);
        txContentAddress.setText(StringUtils.isEmpty(getIntent().getStringExtra("house_name")) ? "暂无" :
                getIntent().getStringExtra("house_name"));
        initData();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    private void initData() {
        if (arrearsListBean != null) {
            TextUtils.setText(txTotalMoney, "#FF001F", "￥" + DecimalFormatUtils.format(Double.parseDouble(arrearsListBean.getTotal_money()), "0.00"));
            for (int x = 0; x < arrearsListBean.getList().size(); x++) {
                txContentTime.setText(x == arrearsListBean.getList().size() - 1 ? txContentTime.getText() + arrearsListBean.getList().get(x).getMonth() :
                        txContentTime.getText() + arrearsListBean.getList().get(x).getMonth() + "、");
                for (int y = 0; y < arrearsListBean.getList().get(x).getDetail().size(); y++) {
                    txContent.setText(y == arrearsListBean.getList().get(x).getDetail().size() - 1 ? txContent.getText() + arrearsListBean.getList().get(x).getDetail().get(y).getItem() :
                            txContent.getText() + arrearsListBean.getList().get(x).getDetail().get(y).getItem() + "、");
                }
                txContent.setText(x == arrearsListBean.getList().size() - 1 ? txContent.getText() : txContent.getText() + "、");
            }
        }
        changeUi(1);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(WithholdingActivity.this, msg);
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
        if (payDialog == null) {
            payDialog = new PayDialog(this);
            payDialog.setCanceledOnTouchOutside(false);
            payDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        hideDialog();
        if (payDialog != null && payDialog.isShowing())
            payDialog.dismiss();
    }

    @Override
    public void onDoPay(PrepaidExpensesBean bean) {
    }

    @OnClick({R.id.we_layout, R.id.ali_layout, R.id.rl_wechat_withholding, R.id.rl_ali_withholding, R.id.tx_sure_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.we_layout:
                changeUi(0);
                payType = "wxpay";
                break;

            case R.id.ali_layout:
                changeUi(1);
                payType = "alipay";
                break;

            case R.id.rl_wechat_withholding:
                changeUi(2);
                payMode = "pay_and_sign";
                payType = "wxpay";
                break;

            case R.id.rl_ali_withholding:
                changeUi(3);
                payMode = "pay_and_sign";
                payType = "alipay";
                break;

            case R.id.tx_sure_pay:
                AnalyUtils.addAnaly(1008);
                if (!SignUtils.checkAliPayInstalled(this)) {
                    ToastUtils.showShort(this, "您还没有安装支付宝应用！");
                    return;
                }
                String chargeIds = null;
                if (houseId != null && arrearsListBean != null && arrearsListBean.getList() != null) {
                    Map<String, String> map = new HashMap<>();
                    map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
                    map.put("houseId", houseId);
                    for (ArrearsListBean.ListBean listbean : arrearsListBean.getList()) {
                        for (ArrearsListBean.ListBean.DetailBean detailBean : listbean.getDetail()) {
                            chargeIds = chargeIds + detailBean.getChargeId() + ",";
                        }
                    }
                    if (chargeIds == null) {
                        return;
                    }
                    map.put("chargeIds", chargeIds.substring(4, chargeIds.length() - 1));
                    map.put("totalMoney", arrearsListBean.getTotal_money());
                    map.put("payMode", payMode);
                    map.put("requestFromUrl", "jkcommunity://fail");
                    map.put("returnUrl", "jkcommunity://success");
                    map.put("payType", payType);
                    presenter.getDoPayment(map);
                }
                break;
        }
    }

    /**
     * 根据int 的值判断四个支付支付方式的勾选状态 0：微信支付/1：支付宝支付/2：微信代扣支付/3：支付宝代扣支付/
     */
    private void changeUi(int i) {
        wxImage.setImageResource(R.mipmap.icon_pay_method_un_selected);
        payImage.setImageResource(R.mipmap.icon_pay_method_un_selected);
        imageWechatWithholding.setImageResource(R.mipmap.icon_pay_method_un_selected);
        imageAliWithholding.setImageResource(R.mipmap.icon_pay_method_un_selected);
        switch (i) {
            case 0:
                wxImage.setImageResource(R.mipmap.icon_pay_method_selected);
                break;
            case 1:
                payImage.setImageResource(R.mipmap.icon_pay_method_selected);
                break;
            case 2:
                imageWechatWithholding.setImageResource(R.mipmap.icon_pay_method_selected);
                break;
            case 3:
                imageAliWithholding.setImageResource(R.mipmap.icon_pay_method_selected);
                break;
        }
//        switch (i) {
//            case 0:
//                wxImage.setImageResource(R.mipmap.icon_pay_method_selected);
//                payImage.setImageResource(R.mipmap.icon_pay_method_un_selected);
//                imageWechatWithholding.setImageResource(R.mipmap.icon_pay_method_un_selected);
//                imageAliWithholding.setImageResource(R.mipmap.icon_pay_method_un_selected);
//                break;
//            case 1:
//                wxImage.setImageResource(R.mipmap.icon_pay_method_un_selected);
//                payImage.setImageResource(R.mipmap.icon_pay_method_selected);
//                imageWechatWithholding.setImageResource(R.mipmap.icon_pay_method_un_selected);
//                imageAliWithholding.setImageResource(R.mipmap.icon_pay_method_un_selected);
//                break;
//            case 2:
//                wxImage.setImageResource(R.mipmap.icon_pay_method_un_selected);
//                payImage.setImageResource(R.mipmap.icon_pay_method_un_selected);
//                imageWechatWithholding.setImageResource(R.mipmap.icon_pay_method_selected);
//                imageAliWithholding.setImageResource(R.mipmap.icon_pay_method_un_selected);
//                break;
//            case 3:
//                wxImage.setImageResource(R.mipmap.icon_pay_method_un_selected);
//                payImage.setImageResource(R.mipmap.icon_pay_method_un_selected);
//                imageWechatWithholding.setImageResource(R.mipmap.icon_pay_method_un_selected);
//                imageAliWithholding.setImageResource(R.mipmap.icon_pay_method_selected);
//                break;
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(WithholdingActivity.this);
        StatService.trackBeginPage(WithholdingActivity.this, "缴费-发起支付");
        AnalyUtils.setBAnalyResume(this, "缴费-发起支付");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(WithholdingActivity.this);
        StatService.trackEndPage(WithholdingActivity.this, "缴费－发起支付");
        AnalyUtils.setBAnalyPause(this, "缴费-发起支付");
    }
}
