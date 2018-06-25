package com.jinke.community.ui.activity.payment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.ArrearsListBean;
import com.jinke.community.bean.WithholdingBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.presenter.PendingPaymentPresenter;
import com.jinke.community.ui.adapter.MyExpandableListAdapter;
import com.jinke.community.ui.toast.SelectHouseDialog;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.ui.widget.MyExpandableListView;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.DecimalFormatUtils;
import com.jinke.community.utils.TextUtils;
import com.jinke.community.view.PendingPaymentView;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.NetWorksUtils;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/8/3.
 * 待缴费
 */
public class PendingPaymentActivity extends BaseActivity<PendingPaymentView, PendingPaymentPresenter> implements PendingPaymentView, SelectHouseDialog.onSelectHouseListener {
    @Bind(R.id.rl_address)
    RelativeLayout rlAddress;
    @Bind(R.id.address_txw)
    TextView txAddress;
    @Bind(R.id.tx_select)
    TextView txSelect;
    @Bind(R.id.expandableListView)
    MyExpandableListView expandableListView;
    @Bind(R.id.total_pending_text)
    TextView totalPendingMoney;
    @Bind(R.id.rl_sign_type)
    RelativeLayout signTypeView;
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @Bind(R.id.pay_immediately_text)
    TextView payBtn;
    @Bind(R.id.payment_sign_title)
    TextView txPayType;
    @Bind(R.id.pending_payment_tips_txw)
    TextView pendingTips;

    public static PendingPaymentActivity pendingPaymentInstance = null;
    private MyExpandableListAdapter expandableListAdapter;
    private ArrearsListBean bean = null;
    private List<ArrearsListBean.ListBean> list = new ArrayList<>();
    private ACache aCache = null;
    private HouseListBean houseListBean = null;//缓存的房屋列表信息
    private HouseListBean.ListBean tempHouseBean = null;
    private String sourceType = null;
    private SelectHouseDialog houseListDialog = null;
    private WithholdingBean withholdingBean = null;

    @Override
    public PendingPaymentPresenter initPresenter() {
        return new PendingPaymentPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_pending_payment;
    }

    @Override
    protected void initView() {
        initUI();
    }

    private void initUI() {
        tempHouseBean = (HouseListBean.ListBean) getIntent().getSerializableExtra("listBean");
        sourceType = getIntent().getStringExtra("sourceType");
        setTitle(sourceType.equals("pending") ? "待缴费" : "代扣缴费");
        showBackwardView(R.string.empty, true);
        initData();
    }

    private void initData() {//加载缓存数据，查看房屋列表信息
        aCache = ACache.get(this);
        houseListBean = (HouseListBean) aCache.getAsObject("HouseListBean");
        if (houseListBean != null) {
            for (HouseListBean.ListBean bean : houseListBean.getList()) {
                if (bean.getDft_house() == 1 && tempHouseBean == null) {
                    tempHouseBean = bean;
                }
            }
        }
        txAddress.setText(tempHouseBean.getCommunity_name() + tempHouseBean.getHouse_name());
        pendingPaymentInstance = this;
        rlAddress.setClickable(houseListBean.getList().size() > 1 ? true : false);//选择更多房屋可见性
        txSelect.setVisibility(houseListBean.getList().size() > 1 ? View.VISIBLE : View.GONE);
        getArrearsMap();
        //判断是从待缴费还是代扣页面的来源
        payBtn.setText(sourceType.equals("pending") ? "立即缴费" : "手动缴费");
        payBtn.setClickable(false);
        payBtn.setBackgroundColor(getResources().getColor(R.color.circle_money_color));
        expandableListAdapter = new MyExpandableListAdapter(list, this);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListAdapter.setData(list);
        totalPendingMoney.setText(R.string.payment_total_pending);
        TextUtils.setText(totalPendingMoney, "big", "#FF344D", "￥ 0.00");
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void onUnSign(WithholdingBean bean) {
        signTypeView.setVisibility(View.GONE);
        ToastUtils.showShort(this, "解约成功！");
        sourceType = "pending";
        //修改支付状态
        payBtn.setText(sourceType.equals("pending") ? "立即缴费" : "手动缴费");
    }

    @OnClick({R.id.rl_address, R.id.pay_immediately_text, R.id.icon_sign_un})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_address: //房屋选择
                if (houseListDialog == null) {
                    houseListDialog = new SelectHouseDialog(this, this);
                }
                houseListDialog.show();
                break;

            case R.id.pay_immediately_text: //立即缴费
                if (bean != null && Double.valueOf(bean.getTotal_money()) > 0) {
                    if (tempHouseBean != null)
                        AnalyUtils.addAnaly(10030, tempHouseBean.getHouse_id());
                    else
                        AnalyUtils.addAnaly(10030);
                    Intent intent = new Intent(PendingPaymentActivity.this, WithholdingActivity.class);
                    intent.putExtra("bean", bean);
                    intent.putExtra("houseId", tempHouseBean.getHouse_id());
                    intent.putExtra("house_name", (StringUtils.isEmpty(tempHouseBean.getArea_name()) ? "" : tempHouseBean.getArea_name()) + (StringUtils.isEmpty(tempHouseBean.getCommunity_name()) ? "" : tempHouseBean.getCommunity_name()) + tempHouseBean.getHouse_name());
                    intent.putExtra("sign_type", sourceType.equals("pending") ? "" : "alipay");
                    startActivity(intent);
                } else {
                    ToastUtils.showShort(this, "没有查询房屋欠费");
                }
                break;

            case R.id.icon_sign_un:
//                if (withholdingBean != null) { //代扣类型解约
//                    Map<String, String> unSign = new HashMap<>();
//                    unSign.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
//                    unSign.put("houseId", tempHouseBean.getHouse_id());
//                    unSign.put("signType", withholdingBean.getWx_sign_status() == 1 ? "wxsign" : "alisign");
//                    presenter.getUnSign(unSign);
//                }
                break;
        }
    }

    @Override
    public void onWithholdingInfo(WithholdingBean bean) {//获取代扣签约状态
        this.withholdingBean = bean;
        signTypeView.setVisibility(bean.getAli_sign_status() == 0 && bean.getWx_sign_status() == 0 ? View.GONE : View.VISIBLE);//判断是否已签代扣协议
        txPayType.setText(bean.getAli_sign_status() == 1 && bean.getWx_sign_status() == 0 ? "您已开通支付宝代扣服务" : "您已开通微信代扣服务");
        payBtn.setText(bean.getAli_sign_status() == 0 && bean.getWx_sign_status() == 0 ? "立即缴费" : "手动缴费");
        sourceType = bean.getAli_sign_status() == 0 && bean.getWx_sign_status() == 0 ? "" : "pending";//如果没有签约代扣，则显示立即缴费　
        getArrearsMap();
    }

    @Override
    public void showTopLayout(String msg) {
        signTypeView.setVisibility(View.GONE);
    }

    // 获取欠费列表
    public void getArrearsMap() {
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("houseId", tempHouseBean.getHouse_id());
        loadingLayout.setStatus(LoadingLayout.Loading);
        presenter.getArrearsList(map);
    }

    @Override
    public void onArrearsList(ArrearsListBean bean) {
        this.bean = bean;
        if (bean != null) {
            pendingTips.setVisibility(View.VISIBLE);
            loadingLayout.setStatus(bean.getList().size() > 0 ? LoadingLayout.Success : LoadingLayout.WithHold_Empty_Payment);
            expandableListAdapter.setData(bean.getList());
            totalPendingMoney.setText(R.string.payment_total_pending);
            TextUtils.setText(totalPendingMoney, "big", "#FF344D", "￥" + DecimalFormatUtils.format(Double.parseDouble(bean.getTotal_money()), "0.00"));
            payBtn.setBackgroundColor(bean.getList().size() <= 0 ?
                    getResources().getColor(R.color.circle_money_color)
                    : getResources().getColor(R.color.house_setting_present));
            payBtn.setClickable(bean.getList().size() > 0 ? true : false);
        }
    }

    @Override
    public void showMsg(String msg) {
        pendingTips.setVisibility(View.GONE);
        loadingLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Success : LoadingLayout.No_Network);
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
    }

    @Override
    public void selectHouse(HouseListBean.ListBean bean) {
        tempHouseBean = bean;
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tempHouseBean != null)
            AnalyUtils.addAnaly(1005, tempHouseBean.getHouse_id());
        else
            AnalyUtils.addAnaly(1005);
        StatService.onResume(PendingPaymentActivity.this);
        StatService.trackBeginPage(PendingPaymentActivity.this, "缴费-待缴费");
        AnalyUtils.setBAnalyResume(this, "缴费-待缴费");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(PendingPaymentActivity.this);
        StatService.trackEndPage(PendingPaymentActivity.this, "缴费－待缴费");
        AnalyUtils.setBAnalyPause(this, "缴费-待缴费");
    }
}