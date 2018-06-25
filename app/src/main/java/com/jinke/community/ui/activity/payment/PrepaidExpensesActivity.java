package com.jinke.community.ui.activity.payment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.DoPayBean;
import com.jinke.community.bean.ExpensesPayBean;
import com.jinke.community.bean.PrepaidExpensesBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.presenter.PrepaidExpensesPresenter;
import com.jinke.community.ui.adapter.PrepaidRecAdapter;
import com.jinke.community.ui.toast.SelectHouseDialog;
import com.jinke.community.ui.widget.FillRecyclerView;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.DecimalFormatUtils;
import com.jinke.community.view.PrepaidExpensesView;
import com.tencent.stat.StatService;

import java.math.BigDecimal;
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
 * Created by Administrator on 2017/8/2.
 * 预存费用
 */

public class PrepaidExpensesActivity extends BaseActivity<PrepaidExpensesView, PrepaidExpensesPresenter>
        implements PrepaidExpensesView, SelectHouseDialog.onSelectHouseListener, PrepaidRecAdapter.PrepaidRecListener {
    @Bind(R.id.rl_address)
    RelativeLayout rlAddress;
    @Bind(R.id.tx_select)
    TextView txSelect;
    @Bind(R.id.tx_money)
    TextView txMoney;
    @Bind(R.id.total_prepaid_text)
    TextView total_prepaid;
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @Bind(R.id.address_text)
    TextView txAddress;
    @Bind(R.id.fill_recycler_view)
    FillRecyclerView fillRecyclerView;
    @Bind(R.id.prepaid_now_text)
    TextView prepaidNowText;
    @Bind(R.id.tx_total_money)
    TextView totalMoney;

    List<PrepaidExpensesBean.ListBean> list = new ArrayList<>();
    PrepaidRecAdapter prepaidRecAdapter = null;
    public static PrepaidExpensesActivity prepaidExpensesInstance = null;
    private ACache aCache;
    private HouseListBean houseListBean;//缓存的房屋列表信息
    private HouseListBean.ListBean tempHouseBean = null;
    private SelectHouseDialog houseListDialog;
    private PrepaidExpensesBean expensesBean;
    private BigDecimal totalM;

    @Override
    public PrepaidExpensesPresenter initPresenter() {
        return new PrepaidExpensesPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_prepaid_expenses;
    }

    @Override
    protected void initView() {
        setTitle(R.string.prepaid_expenses);
        showBackwardView(R.string.empty, true);
        prepaidNowText.setBackgroundColor(getResources().getColor(R.color.circle_money_color));
        loadingLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Loading : LoadingLayout.No_Network);
        prepaidExpensesInstance = this;
        prepaidRecAdapter = new PrepaidRecAdapter(this, list, this);
        fillRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fillRecyclerView.setAdapter(prepaidRecAdapter);
        initHouseInfo();
    }

    private void initHouseInfo() {//加载缓存房屋列表信息
        aCache = ACache.get(this);
        houseListBean = (HouseListBean) aCache.getAsObject("HouseListBean");
        if (houseListBean != null) {
            for (HouseListBean.ListBean bean : houseListBean.getList()) {
                if (tempHouseBean == null && bean.getDft_house() == 1) {
                    tempHouseBean = bean;
                }
            }
        }
        if (tempHouseBean == null)
            return;
        if (StringUtils.isEmpty(tempHouseBean.getCommunity_name()) || StringUtils.isEmpty(tempHouseBean.getHouse_name()))
            return;
        txAddress.setText(tempHouseBean.getCommunity_name() + tempHouseBean.getHouse_name());
        if (houseListBean.getList() == null)
            return;
        rlAddress.setClickable(houseListBean.getList().size() > 1 ? true : false);//选择更多房屋可见性
        txSelect.setVisibility(houseListBean.getList().size() > 1 ? View.VISIBLE : View.GONE);
        getPrePayList();
    }

    private void getPrePayList() {
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        if (tempHouseBean.getHouse_id() != null) {
            map.put("houseId", tempHouseBean.getHouse_id());
        }
        loadingLayout.setStatus(LoadingLayout.Loading);
        presenter.getPrePayList(map);
    }

    /**
     * 获取预存列表成功回调
     * @param bean
     */
    @Override
    public void onPrePayList(PrepaidExpensesBean bean) {
        this.expensesBean = bean;
        totalM = new BigDecimal("0.00");
        txMoney.setText(DecimalFormatUtils.format(Double.parseDouble(bean.getPre_money()), "0.00"));
        prepaidRecAdapter.setDataList(bean.getList());
        loadingLayout.setStatus(LoadingLayout.Success);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void showMsg(String msg) {
        loadingLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Success : LoadingLayout.No_Network);
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
    }

    @OnClick({R.id.rl_address, R.id.prepaid_now_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_address:
                if (houseListDialog == null) {
                    houseListDialog = new SelectHouseDialog(this, this);
                }
                houseListDialog.show();
                break;

            case R.id.prepaid_now_text:
                ExpensesPayBean payBean = new ExpensesPayBean();
                payBean.setAccessToken(MyApplication.getBaseUserBean().getAccessToken());
                if (tempHouseBean.getHouse_id() != null) {
                    payBean.setHouseId(tempHouseBean.getHouse_id());
                }
                String json = new Gson().toJson(getChildView(expensesBean));
                double money =Double.parseDouble(totalMoney.getText().toString().trim().substring(1));
                payBean.setMoneyTotal(String.valueOf(money));
                payBean.setPrepayList(json);
                if (money > 0) {
                    if (tempHouseBean.getHouse_id() != null) {
                        AnalyUtils.addAnaly(10031);
                    } else {
                        AnalyUtils.addAnaly(10031, tempHouseBean.getHouse_id());
                    }
                    Intent intent = new Intent(PrepaidExpensesActivity.this, ExpensesPayActivity.class);
                    intent.putExtra("bean", payBean);
                    startActivity(intent);
                    AppConfig.trackCustomEvent(this, "prepaid_now_text_click", "预存－发起支付");
                } else {
                    ToastUtils.showShort(this, "请输入各项预存费用");
                }
                break;
        }
    }

    public List<DoPayBean> getChildView(PrepaidExpensesBean expensesBean) {
        List<DoPayBean> list = new ArrayList<>();
        try {
            for (PrepaidExpensesBean.ListBean bean : expensesBean.getList()) {
                DoPayBean payBean = new DoPayBean();
                BigDecimal money = new BigDecimal(StringUtils.isEmpty(bean.getMoney()) ? "0.00" : bean.getMoney().trim());
                payBean.setAmount(money.doubleValue() + "");
                payBean.setPrepayId(bean.getPrepayId());
                list.add(payBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort(this, "重置金额输入不合法，请重新输入!");
        }
        return list;
    }

    /**
     * 房屋选择回调
     *
     * @param bean
     */
    @Override
    public void selectHouse(HouseListBean.ListBean bean) {
        tempHouseBean = bean;
        initHouseInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tempHouseBean != null)
            AnalyUtils.addAnaly(1006, tempHouseBean.getHouse_id());
        else
            AnalyUtils.addAnaly(1006);
        StatService.onResume(PrepaidExpensesActivity.this);
        StatService.trackBeginPage(PrepaidExpensesActivity.this, "缴费-预存费用");
        AnalyUtils.setBAnalyResume(this, "缴费-预存费用");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(PrepaidExpensesActivity.this);
        StatService.trackEndPage(PrepaidExpensesActivity.this, "缴费－预存费用");
        AnalyUtils.setBAnalyPause(this, "缴费-预存费用");
    }

    /**
     * PrepaidRecAdapter 回调
     *
     * @param list
     */
    @Override
    public void inPut(List<PrepaidExpensesBean.ListBean> list) {
        totalM = new BigDecimal("0.00");
        for (PrepaidExpensesBean.ListBean bean : list) {
            totalM = totalM.add(!StringUtils.isEmpty(bean.getMoney()) ? new BigDecimal(bean.getMoney()) : new BigDecimal(0));
        }
        this.list = list;
        prepaidNowText.setBackgroundColor(getResources().getColor(R.color.main_them_color));
        totalMoney.setText("￥" + DecimalFormatUtils.format(totalM.doubleValue(), "0.00"));
    }
}
