package com.jinke.community.ui.activity.payment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.ParkInfoSelectBean;
import com.jinke.community.bean.PayBean;
import com.jinke.community.bean.VehicleChargeBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.payment.alipay.SignUtils;
import com.jinke.community.presenter.VehiclePayPresenter;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.GsonUtil;
import com.jinke.community.utils.TextUtils;
import com.jinke.community.view.VehiclePayView;
import com.tencent.stat.StatService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/8/4.
 * 车位月租 代扣/支付
 */

public class VehiclePayActivity extends BaseActivity<VehiclePayView, VehiclePayPresenter> implements VehiclePayView {
    @Bind(R.id.payment_withholding_money)
    TextView withholdingMoney;
    @Bind(R.id.we_select_image)
    ImageView wxImage;
    @Bind(R.id.payment_we_text)
    TextView wchatTitle;
    @Bind(R.id.tx_WchatPay)
    TextView wchatSign;
    @Bind(R.id.we_tips_text)
    TextView wchatContext;
    @Bind(R.id.payment_ali_text)
    TextView aliTitle;
    @Bind(R.id.tx_aliaPay)
    TextView aliSign;
    @Bind(R.id.ali_tips_text)
    TextView aliContent;
    @Bind(R.id.ali_select_image)
    ImageView payImage;

    private String houseId;
    private String signType = "";
    private float totalMoney = 0;

    @Override
    public VehiclePayPresenter initPresenter() {
        return new VehiclePayPresenter(this);
    }

    private List<ParkInfoSelectBean.ListBean> parkingList;


    public static VehiclePayActivity withholdingInstance = null;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_vehicle_pay;
    }

    @Override
    protected void initView() {
        setTitle(R.string.payment);
        showBackwardView(R.string.empty, true);
        aliContent.setVisibility(View.GONE);
        aliTitle.setVisibility(View.GONE);

        withholdingInstance = this;
        payImage.setBackgroundResource(R.drawable.icon_my_house_chose);
        signType = getIntent().getStringExtra("sign_type");
        totalMoney = getIntent().getFloatExtra("totalMoney", 0);
        parkingList = (List<ParkInfoSelectBean.ListBean>) getIntent().getSerializableExtra("parkingList");
        initData();
    }

    private void initData() {
        if (parkingList != null) {
            TextUtils.setText(withholdingMoney, "#FF001F", "￥" + totalMoney);
        }
        wchatTitle.setVisibility(StringUtils.isEmpty(signType) ? View.GONE : View.VISIBLE);
        wchatSign.setVisibility(StringUtils.isEmpty(signType) ? View.VISIBLE : View.GONE);

        aliSign.setVisibility(StringUtils.isEmpty(signType) ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void getPaymentOnNext(PayBean info) {
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(VehiclePayActivity.this, msg);
    }

    @Override
    public void payResult(PayBean payBean) {
        Intent intent = new Intent(this, PaymentResultActivity.class);
        intent.putExtra("bean", (Serializable) payBean);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoading() {
        showProgressDialog(null);
    }

    @Override
    public void hideLoading() {
        hideDialog();
    }

    private String payType = "alipay";//微信支付

    @OnClick({R.id.ali_layout, R.id.we_layout, R.id.forward_text})
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.we_layout:
//                payImage.setBackgroundResource(R.drawable.icon_un_select);
//                wxImage.setBackgroundResource(R.drawable.icon_my_house_chose);
//                payType = "wxpay";
//                break;
            case R.id.ali_layout:
                wxImage.setBackgroundResource(R.drawable.icon_un_select);
                payImage.setBackgroundResource(R.drawable.icon_my_house_chose);
                payType = "alipay";
                break;

            case R.id.forward_text:
                AnalyUtils.addAnaly(1008);
                AppConfig.trackCustomEvent(this, "forward_text_click", "车月租缴费");
                if (!SignUtils.checkAliPayInstalled(this)) {
                    ToastUtils.showShort(this, "您还没有安装支付宝应用！");
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
                if (MyApplication.getInstance().getDefaultHouse() != null)
                    map.put("houseId", MyApplication.getInstance().getDefaultHouse().getHouse_id());
                else
                    return;
                map.put("payMoney", String.valueOf(totalMoney));
                map.put("payType", payType);
                if (MyApplication.getInstance().getDefaultHouse() != null)
                    AnalyUtils.addAnaly(10032, MyApplication.getInstance().getDefaultHouse().getHouse_id());
                else
                    AnalyUtils.addAnaly(10032);
                List<VehicleChargeBean.DataBean> beanList = new ArrayList<>();
                VehicleChargeBean.DataBean dataBean;
                for (ParkInfoSelectBean.ListBean bean : parkingList) {
                    if (bean.getSelectFlag().equals("true")) {
                        dataBean = new VehicleChargeBean.DataBean();
                        dataBean.setParking_Key(bean.getParking_Key());
                        dataBean.setPark_Name(bean.getPark_Name());
                        dataBean.setPay_month(bean.getCarTypeChargRules_MthNum());
                        dataBean.setStandard_price(bean.getCarTypeChargRules_Money());
                        dataBean.setValid_date(String.valueOf(new Date().getTime()));
                        dataBean.setCarType_No(bean.getCarSpace_No());
                        dataBean.setEnd_valid_date(bean.getCarSpace_EndTime());
                        beanList.add(dataBean);
                    }
                }
                map.put("carList", GsonUtil.GsonString(beanList));
                presenter.getPayment(map);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(VehiclePayActivity.this);
        StatService.trackBeginPage(VehiclePayActivity.this, "车位月租-发起支付");
        AnalyUtils.setBAnalyResume(this, "车位月租-发起支付");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(VehiclePayActivity.this);
        StatService.trackEndPage(VehiclePayActivity.this, "车位月租－发起支付");
        AnalyUtils.setBAnalyPause(this, "车位月租-发起支付");
    }
}
