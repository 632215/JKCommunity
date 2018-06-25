package com.jinke.community.ui.activity.payment;

import android.view.View;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.PaymentDetailsBean;
import com.jinke.community.bean.PaymentRecordBean;
import com.jinke.community.bean.PaymentVehicleDetailsBean;
import com.jinke.community.presenter.PaymentDetailsPresenter;
import com.jinke.community.ui.adapter.PaymentDetailsAdapter;
import com.jinke.community.ui.adapter.PaymentVehicleDetailsAdapter;
import com.jinke.community.ui.widget.FillListView;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.DecimalFormatUtils;
import com.jinke.community.view.PaymentDetailsView;
import com.tencent.stat.StatService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/8/2.
 * 缴费明细
 */

public class PaymentDetailsActivity extends BaseActivity<PaymentDetailsView, PaymentDetailsPresenter> implements PaymentDetailsView {
    @Bind(R.id.payment_details_fillList)
    FillListView paymentFillListView;
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @Bind(R.id.tx_payment_money)
    TextView totalMoney;
    @Bind(R.id.tx_payment_method)
    TextView paymentMethod;
    @Bind(R.id.tx_payment_time)
    TextView payMentTime;
    @Bind(R.id.tx_payment_recorder_time)
    TextView txRecorderTime;
    @Bind(R.id.tx_payment_serial_number)
    TextView serialNumber;
    @Bind(R.id.tx_payment_number)
    TextView paymentNumber;
    PaymentRecordBean.ListBean listBean;

    private PaymentDetailsAdapter paymentDetailsAdapter;
    private List<PaymentDetailsBean.PayListBean> detailsBeansList = new ArrayList<>();

    private PaymentVehicleDetailsAdapter vehicleDetailsAdapter;
    private List<PaymentVehicleDetailsBean.CarListBean> vehicleDetailsBeansList = new ArrayList<>();

    private String houseId = "";//当前item的房屋ID

    @Override
    public PaymentDetailsPresenter initPresenter() {
        return new PaymentDetailsPresenter(PaymentDetailsActivity.this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_payment_details;
    }

    @Override
    protected void initView() {
        showBackwardView(R.string.empty, true);
        houseId = getIntent().getStringExtra("House_id");
        listBean = (PaymentRecordBean.ListBean) getIntent().getSerializableExtra("bean");
        initData();
    }

    private void initData() {//判断费用明细 请求的数据
        paymentDetailsAdapter = new PaymentDetailsAdapter(this, detailsBeansList);
        paymentFillListView.setAdapter(paymentDetailsAdapter);
        if (listBean != null) {
            Map<String, String> map = new HashMap<>();
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            map.put("pay_id", listBean.getPay_id());
            map.put("houseId", houseId);//现目前

            switch (listBean.getPay_name()) {
                case "预存":
                    map.put("type", "2");
                    presenter.getPrePayDetail(map);
                    setTitle("预存明细");
                    break;

                case "缴费":
                    map.put("type", "1");
                    presenter.getPaymentRecordDetail(map);
                    setTitle("缴费明细");
                    break;

                case "代扣":
                    map.put("type", "3");
                    presenter.getPaymentRecordDetail(map);
                    setTitle("代扣明细");
                    break;

                case "车位":
                    map.remove("pay_id");
                    map.put("orderNo", listBean.getPay_id());
                    presenter.getPaymentVehicleDetail(map);
                    setTitle("车位费充值明细");
                    break;
            }
        }
    }

    @Override
    public void getVehicleDetailNext(PaymentVehicleDetailsBean bean) {
        setAccountState(bean.getInto_account_status(), bean.getInto_account_time());
        if (!StringUtils.isEmpty(bean.getTotal_money()))
            totalMoney.setText("+" + DecimalFormatUtils.format(Double.parseDouble(bean.getTotal_money()), "0.00"));
        if (!StringUtils.isEmpty(bean.getPay_time()))
            payMentTime.setText("支付时间: " + changeTimeStyle(bean.getPay_time()));
        if (!StringUtils.isEmpty(bean.getJk_trade_no()))
            serialNumber.setText("金科流水号: " + bean.getJk_trade_no());
        if (!StringUtils.isEmpty(bean.getTrade_no()))
            paymentNumber.setText("支付订单号: " + bean.getTrade_no());
        if (!StringUtils.isEmpty(bean.getPayType()))
            switch (bean.getPayType()) {
                case "app_alipay":
                case "alipay":
                    paymentMethod.setText(R.string.payment_details_pay_method_ali_app);
                    break;
            }
        if (bean.getCar_list() != null)
            vehicleDetailsBeansList = bean.getCar_list();
        vehicleDetailsAdapter = new PaymentVehicleDetailsAdapter(this, R.layout.item_payment_vehicle_details, vehicleDetailsBeansList);
        paymentFillListView.setAdapter(vehicleDetailsAdapter);
        vehicleDetailsAdapter.setDataList(vehicleDetailsBeansList);
        loadingLayout.setStatus(LoadingLayout.Success);
    }

    @Override
    public void onSuccess(PaymentDetailsBean bean) {//缴费/代扣/预存
        setAccountState(bean.getInto_account_status(), bean.getInto_account_time());
        String symbol = "";
        if (!StringUtils.isEmpty(bean.getPay_name()))
            switch (bean.getPay_name()) {
                case "预存":
                    symbol = "+";
                    break;

                case "代扣":
                case "扣费":
                case "缴费":
                    symbol = "￥";
                    break;
            }
//        totalMoney.setText(symbol + String.valueOf(bean.getTotal_money()));
        if (!StringUtils.isEmpty(bean.getTotal_money()))
            totalMoney.setText(symbol + DecimalFormatUtils.format(Double.parseDouble(bean.getTotal_money()), "0.00"));
        if (!StringUtils.isEmpty(bean.getPay_time()))
            payMentTime.setText("支付时间: " + changeTimeStyle(bean.getPay_time()));
        if (!StringUtils.isEmpty(bean.getJk_serial_num()))
            serialNumber.setText("金科流水号: " + bean.getJk_serial_num());
        if (!StringUtils.isEmpty(bean.getOrder_num()))
            paymentNumber.setText("支付订单号: " + bean.getOrder_num());
        if (bean.getPay_list() != null)
            paymentDetailsAdapter.setData(bean.getPay_list());
        if (!StringUtils.isEmpty(bean.getPay_type()))
            switch (bean.getPay_type()) {
                case "mp_wxpay":
                    paymentMethod.setText(R.string.payment_details_pay_method_wechat_public);
                    break;
                case "mp_alipay":
                    paymentMethod.setText(R.string.payment_details_pay_method_ali_public);
                    break;
                case "app_wxpay":
                    paymentMethod.setText(R.string.payment_details_pay_method_wechat_app);
                    break;
                case "app_alipay":
                    paymentMethod.setText(R.string.payment_details_pay_method_ali_app);
                    break;
            }
        loadingLayout.setStatus(LoadingLayout.Success);
    }

    private void setAccountState(String into_account_status, String time) {
        if (!StringUtils.isEmpty(into_account_status)) {
            switch (into_account_status) {
                case "0":        //0:下账中
                    txRecorderTime.setText("入账时间: " + getString(R.string.payment_vehicle_in));
                    txRecorderTime.setTextColor(getResources().getColor(R.color.payment_text_color16));
                    break;
                case "1":        //1:失败
                    txRecorderTime.setText("入账时间: " + getString(R.string.payment_vehicle_failed));
                    txRecorderTime.setTextColor(getResources().getColor(R.color.payment_text_color16));
                    break;
                case "2":        //2:成功
                    txRecorderTime.setText("入账时间: " + changeTimeStyle(time));
                    txRecorderTime.setTextColor(getResources().getColor(R.color.payment_text_color1));
                    break;
            }
        }
    }

    private String changeTimeStyle(String time) {
        if (!StringUtils.isEmpty(time)) {
            if (time.contains("年")) {
                return time;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            Date date = new Date(Long.parseLong(time) * 1000);
            return sdf.format(date);
        } else {
            return "";
        }
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
    protected void onResume() {
        super.onResume();
        StatService.onResume(PaymentDetailsActivity.this);
        StatService.trackBeginPage(PaymentDetailsActivity.this, "缴费－缴费明细");
        AnalyUtils.setBAnalyResume(this, "缴费－缴费明细");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(PaymentDetailsActivity.this);
        StatService.trackEndPage(PaymentDetailsActivity.this, "缴费－缴费明细");
        AnalyUtils.setBAnalyPause(this, "缴费－缴费明细");
    }
}
