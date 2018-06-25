package com.jinke.community.ui.activity.payment;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.HouseWithHoldBean;
import com.jinke.community.bean.WithHoldOpenBean;
import com.jinke.community.payment.alipay.SignUtils;
import com.jinke.community.presenter.WithHoldOpenPresenter;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.TextUtils;
import com.jinke.community.view.WithHoldOpenView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/11/23.
 * 开通代扣
 */

public class WithHoldOpenActivity extends BaseActivity<WithHoldOpenView, WithHoldOpenPresenter> implements WithHoldOpenView {
    @Bind(R.id.tx_hosue)
    TextView txHouse;
    @Bind(R.id.tx_owner)
    TextView txOwner;
    @Bind(R.id.image_wechat_withholding_select)
    ImageView imageWechatWithholding;
    @Bind(R.id.image_ali_withholding_select)
    ImageView imageAliWithholding;

    private HouseWithHoldBean.ListBean listBean;
    private String payType = "alisign";//签约方式1：支付宝2：微信
//    private String signType = "pay_and_sign";//签约代扣

    @Override
    public WithHoldOpenPresenter initPresenter() {
        return new WithHoldOpenPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_withhold_open;
    }

    @Override
    protected void initView() {
        setTitle(R.string.activity_withholding_open_withholding_contract);
        showBackwardView(R.string.empty, true);
        listBean = (HouseWithHoldBean.ListBean) getIntent().getExtras().get("listBean");
        initData();
    }

    private void initData() {
        if (listBean != null) {
            txHouse.setText(listBean.getCommunity_name() + listBean.getHouse_name());
        }
        String ownerString = "";
        if (listBean.getOwners() != null) {
            for (HouseWithHoldBean.ListBean.OwnersBean ownerBean : listBean.getOwners()) {
                ownerString += ownerBean.getOwnerName() + "  " + TextUtils.changTelNum(ownerBean.getPhone()) + "   ";
            }
            txOwner.setText(ownerString);
        }
        changeUi(3);
    }

    @OnClick({R.id.rl_wechat_withholding, R.id.rl_ali_withholding, R.id.image_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_wechat_withholding:
                changeUi(2);
                payType = "wxsign";
                break;

            case R.id.rl_ali_withholding:
                changeUi(3);
                payType = "alisign";
                break;

            case R.id.image_sure:
                if (StringUtils.isEmpty(payType)) {
                    ToastUtils.showShort(this, "请选择您的代扣方式！");
                    return;
                }
                if (!SignUtils.checkAliPayInstalled(this)) {
                    ToastUtils.showShort(this, "您还没有安装支付宝应用！");
                    return;
                }
                Map map = new HashMap();
                map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
                map.put("houseId", listBean.getHouse_id());
                map.put("payType", payType);
                map.put("return_url", String.valueOf(Uri.parse("sh://jkcommunity.com")));
                presenter.withHoldOpen(map);
                break;
        }
    }

    @Override
    public void payResult(int i) {
        Intent intent = new Intent(this, PaymentResultActivity.class);
        intent.putExtra("withHoldOpenResult", String.valueOf(i));
        startActivity(intent);
        finish();
    }

    /**
     * 根据int 的值判断四个支付支付方式的勾选状态 0：微信支付/1：支付宝支付/2：微信代扣支付/3：支付宝代扣支付/
     */
    private void changeUi(int i) {
        switch (i) {
            case 2:
                imageWechatWithholding.setImageResource(R.mipmap.icon_pay_method_selected);
                imageAliWithholding.setImageResource(R.mipmap.icon_pay_method_un_selected);
                break;
            case 3:
                imageWechatWithholding.setImageResource(R.mipmap.icon_pay_method_un_selected);
                imageAliWithholding.setImageResource(R.mipmap.icon_pay_method_selected);
                break;

        }
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onForward(View forwardView) {
        super.onForward(forwardView);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyUtils.setBAnalyResume(this,"开通代扣");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyUtils.setBAnalyPause(this,"开通代扣");
    }
}
