package com.jinke.community.ui.activity.payment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.ParkInfoSelectBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.presenter.ParkingLotRenewalPresenter;
import com.jinke.community.ui.activity.vehicle.VehicleManagementActivity;
import com.jinke.community.ui.adapter.ParkingAdapter;
import com.jinke.community.ui.toast.SelectHouseDialog;
import com.jinke.community.ui.widget.FillListView;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.DecimalFormatUtils;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.jinke.community.utils.TextUtils;
import com.jinke.community.view.ParkingLotRenewalView;
import com.tencent.stat.StatService;

import java.io.Serializable;
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
 * Created by Administrator on 2017/10/21.
 * 车位月租
 */

public class ParkingLotRenewalActivity extends BaseActivity<ParkingLotRenewalView, ParkingLotRenewalPresenter> implements
        ParkingAdapter.ParkingAdapterListener, ParkingLotRenewalView, LoadingLayout.OnReloadListener, SelectHouseDialog.onSelectHouseListener {
    @Bind(R.id.rl_address)
    RelativeLayout rlAddress;
    @Bind(R.id.address_txw)
    TextView txAddress;
    @Bind(R.id.tx_select)
    TextView txSelect;
    @Bind(R.id.fill_list_view)
    FillListView fillListView;
    @Bind(R.id.tx_total_pending)
    TextView txTotalPengding;
    @Bind(R.id.tx_pay_immediately)
    TextView txPayImmediately;
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;

    private ACache aCache;
    private HouseListBean houseListBean;//缓存的房屋列表信息
    private HouseListBean.ListBean tempHouseBean;
    private SelectHouseDialog houseListDialog;
    private ParkingAdapter parkingAdapter;
    private List<ParkInfoSelectBean.ListBean> parkingList = new ArrayList<>();
    private float totalMoney = 0;

    @Override
    public ParkingLotRenewalPresenter initPresenter() {
        return new ParkingLotRenewalPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_parking_lot_renewal;
    }

    @Override
    protected void initView() {
        setTitle(R.string.payment_renewal_renewal_parking_space);
        showBackwardView(R.string.empty, true);
        showForwardView(R.string.payment_renewal_my_vehicle, true);
        txTotalPengding.setText("预存合计：");
        TextUtils.setText(txTotalPengding, "#FF344D", "￥0.00");
        initAdapter();
    }

    //加载缓存查看房屋信息
    private void initData() {
        aCache = ACache.get(this);
        houseListBean = (HouseListBean) aCache.getAsObject("HouseListBean");
        if (houseListBean != null) {
            for (HouseListBean.ListBean bean : houseListBean.getList()) {
                if (bean.getDft_house() == 1) {
                    tempHouseBean = bean;
                    txAddress.setText(tempHouseBean.getCommunity_name() + tempHouseBean.getHouse_name());
                }
            }
        }
        rlAddress.setClickable(houseListBean.getList().size() > 1 ? true : false);//选择更多房屋可见性
        txSelect.setVisibility(houseListBean.getList().size() > 1 ? View.VISIBLE : View.GONE);
        initAdapter();
    }

    private void initAdapter() {
        parkingAdapter = new ParkingAdapter(this, R.layout.item_parking_info, parkingList, this);
        fillListView.setAdapter(parkingAdapter);
    }

    private void getParkInfo() {
        Map map = new HashMap();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("secretKey", AppConfig.SECRETKEY);
        map.put("phone", MyApplication.getBaseUserBean().getPhone());
        presenter.getParkInfo(map);
    }

    @Override
    public void error(String msg) {
//        ToastUtils.showShort(this, msg);
        loadingLayout.setVehicleTips(msg);
    }

    @Override
    public void getParkInfoonNext(List<ParkInfoSelectBean.ListBean> info) {
        parkingList.clear();
        if (info != null) {
            parkingList.addAll(info);
        }
        countMoney();
        parkingAdapter.setDataList(parkingList);
        loadingLayout.setStatus(info.size() > 0 ? LoadingLayout.Success : LoadingLayout.Vehicle_Empty);
    }


    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onForward(View forwardView) {
        super.onForward(forwardView);
        startActivity(new Intent(this, VehicleManagementActivity.class));
    }

    @OnClick({R.id.tx_pay_immediately, R.id.rl_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_pay_immediately:
                AppConfig.trackCustomEvent(this, "tx_pay_immediately_click", "车位月租");
                boolean count = false;
                for (ParkInfoSelectBean.ListBean bean : parkingList) {
                    if (bean.getSelectFlag().equals("true")) {
                        count = true;
                        Intent intent = new Intent(ParkingLotRenewalActivity.this, VehiclePayActivity.class);
                        intent.putExtra("totalMoney", totalMoney);
                        intent.putExtra("parkingList", (Serializable) parkingList);
                        intent.putExtra("sign_type", SharedPreferencesUtils.getTempHouseBean(this).getSourceType().equals("pending") ? "" : "alipay");
                        startActivity(intent);
                    }
                }
                if (count == false) {
                    ToastUtils.showShort(this, "请选取续期车位！");
                }
                break;
            case R.id.rl_address: //房屋选择
                if (houseListDialog == null) {
                    houseListDialog = new SelectHouseDialog(this, this);
                }
                houseListDialog.show();
                break;
        }
    }

    @Override
    public void imageStateChange(int position, String stateFlag) {
        parkingList.get(position).setSelectFlag(stateFlag);
        countMoney();
    }

    @Override
    public void changeMonth(int position, String num) {
        parkingList.get(position).setCarTypeChargRules_MthNum(num);
        countMoney();
    }

    private void countMoney() {
        totalMoney = 0;
        if (parkingList != null) {
            for (ParkInfoSelectBean.ListBean bean : parkingList) {
                if (bean.getSelectFlag().equals("true") && !StringUtils.isEmpty(bean.getCarTypeChargRules_Money()) && !StringUtils.isEmpty(bean.getCarTypeChargRules_MthNum())) {
                    totalMoney += Float.parseFloat(String.valueOf((Float.parseFloat(bean.getCarTypeChargRules_Money().toString().trim()) *
                            Integer.parseInt(bean.getCarTypeChargRules_MthNum().toString().trim()))));
                }
            }
        }

        txTotalPengding.setText("预存合计：");
        TextUtils.setText(txTotalPengding, "#FF344D", "￥" + DecimalFormatUtils.format(Double.parseDouble(String.valueOf(totalMoney)), "0.00"));
        txPayImmediately.setBackgroundColor(totalMoney == 0 ?
                getResources().getColor(R.color.circle_money_color)
                : getResources().getColor(R.color.house_setting_present));
        txPayImmediately.setClickable(totalMoney == 0 ? false : true);
    }

    //展示小白
    @Override
    public void showLoading() {
        loadingLayout.setStatus(LoadingLayout.Loading);
    }

    @Override
    public void hideLoading() {
        loadingLayout.setStatus(NetWorksUtils.isConnected(this) ? (parkingList.size() > 0 ? LoadingLayout.Success : LoadingLayout.Vehicle_Empty) : LoadingLayout.No_Network);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getParkInfo();
        AnalyUtils.addAnaly(1007);
        StatService.onResume(ParkingLotRenewalActivity.this);
        StatService.trackBeginPage(ParkingLotRenewalActivity.this, "车位月租");
        AnalyUtils.setBAnalyResume(this, "车位月租");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(ParkingLotRenewalActivity.this);
        StatService.trackEndPage(ParkingLotRenewalActivity.this, "车位月租");
        AnalyUtils.setBAnalyPause(this, "车位月租");
    }

    @Override
    public void onReload(View v) {
        getParkInfo();
    }

    @Override
    public void selectHouse(HouseListBean.ListBean bean) {
        tempHouseBean = bean;
    }
}
