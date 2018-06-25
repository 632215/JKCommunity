package com.jinke.community.ui.activity.vehicle;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.AuthorizedRecordBean;
import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.bean.UserCarBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.presenter.VehicleManagementPresenter;
import com.jinke.community.ui.adapter.AuthorizeAdapter;
import com.jinke.community.ui.adapter.VehicleAdapter;
import com.jinke.community.ui.widget.FillListView;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.VehicleManagementView;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/10/16.
 * 我的车辆
 */

public class VehicleManagementActivity extends BaseActivity<VehicleManagementView, VehicleManagementPresenter>
        implements VehicleManagementView, VehicleAdapter.BanVehicleListener, AuthorizeAdapter.AuthorizedVehicleListener {
    @Bind(R.id.lv_my_car)
    FillListView lvMyCar;
    @Bind(R.id.lv_authorization_car)
    FillListView lvAuthorizationCar;
    @Bind(R.id.tx_authorized_tips)
    TextView txAuthorizedTips;
    @Bind(R.id.tx_delete)
    TextView txDelete;


    private VehicleAdapter vehicleAdapter;
    private AuthorizeAdapter authorizeAdapter;

    private List<UserCarBean.ListBean> list = new ArrayList<>();
    private List<AuthorizedRecordBean.ListBean> authorizeList = new ArrayList<>();

    @Override
    public VehicleManagementPresenter initPresenter() {
        return new VehicleManagementPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_vehicle_management;
    }

    @Override
    protected void initView() {
        setTitle(R.string.activity_vehicle_my_car);
        showBackwardView(R.string.empty, true);
        initAdapter();
    }

    private void initAdapter() {
        vehicleAdapter = new VehicleAdapter(this, R.layout.item_verhicle, list, this);
        lvMyCar.setAdapter(vehicleAdapter);

        authorizeAdapter = new AuthorizeAdapter(this, R.layout.item_authorize_verhicle, authorizeList, this);
        lvAuthorizationCar.setAdapter(authorizeAdapter);
    }

    /**
     * 获取授权车辆信息
     */
    private void getAuthorizedVehicle() {
        Map map = new HashMap();
        map.put("secretKey", AppConfig.SECRETKEY);
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("phone", MyApplication.getBaseUserBean().getPhone());
        presenter.getAuthorizedVehicle(map);
    }

    @Override
    public void getAuthorizedVehicleNext(List<AuthorizedRecordBean.ListBean> infoList) {
        if (infoList != null) {
            authorizeList = infoList;
        }
        controlTextState();
        authorizeAdapter.setDataList(authorizeList);
    }

    /**
     * 获取我的车辆信息
     */
    private void getMyCar() {
        Map map = new HashMap();
        map.put("secretKey", AppConfig.SECRETKEY);
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("phone", MyApplication.getBaseUserBean().getPhone());
        presenter.getMyCar(map);
    }

    @Override
    public void getMyCarOnNext(UserCarBean info) {
        if (info.getList() != null) {
            list = info.getList();
        }
        vehicleAdapter.setDataList(list);
    }

    @Override
    public void getMyCarOnError(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showLong(this, msg);
    }

    /**
     * 禁止
     */
    @Override
    public void onBanClick(int position) {
        Map map = new HashMap();
        map.put("secretKey", AppConfig.SECRETKEY);
        map.put("carNo", list.get(position).getCar_CarNo());
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        presenter.banVehicle(map);
    }

    /**
     * 启用
     *
     * @param position
     */
    @Override
    public void onUnBanClick(int position) {
        Map map = new HashMap();
        map.put("secretKey", AppConfig.SECRETKEY);
        map.put("carNo", list.get(position).getCar_CarNo());
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        presenter.unBanVehicle(map);
    }

    @Override
    public void successOperating() {
        getMyCar();
    }

    @Override
    public void onAuthorizedVehicleItemClick(int positoin, String remark) {
        authorizeList.get(positoin).setDelete_remark(remark);
        controlTextState();
    }

    private void controlTextState() {
        for (AuthorizedRecordBean.ListBean bean : authorizeList) {
            if (StringUtils.equals(bean.getDelete_remark(), "true")) {
                txDelete.setAlpha(1f);
                txDelete.setEnabled(true);
                break;
            } else {
                txDelete.setAlpha(0.5f);
                txDelete.setEnabled(false);
            }
        }

        if (authorizeList.size() > 0) {
            txDelete.setVisibility(View.VISIBLE);
            txAuthorizedTips.setVisibility(View.GONE);
        } else {
            txAuthorizedTips.setVisibility(View.VISIBLE);
            txDelete.setVisibility(View.GONE);
        }
    }

    /**
     * 删除授权
     */
    private void deleteAuthorized() {
        for (AuthorizedRecordBean.ListBean bean : authorizeList) {
            if (StringUtils.equals(bean.getDelete_remark(), "true")) {
                Map map = new HashMap();
                map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
                map.put("secretKey", AppConfig.SECRETKEY);
                map.put("parkKey", bean.getParking_Key());
                map.put("reserNo", bean.getReserveOrder_No());
                presenter.deleteAuthorized(map);
            }
        }
    }

    @Override
    public void deleteAuthorizedOnNext() {
        getAuthorizedVehicle();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @OnClick({R.id.tx_add_authorized, R.id.tx_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_add_authorized:
                startActivity(new Intent(this, VehicleAuthorizedActivity.class));
//                presenter.getParkingInfo();
                break;
            case R.id.tx_delete:
                deleteAuthorized();
                break;
        }
    }

    @Override
    public void getParkInfoonNext(ParkInfoBean info) {
        startActivity(new Intent(this, VehicleAuthorizedActivity.class));
    }

    //展示小白
    @Override
    public void showLoading() {
        showProgressDialog(String.valueOf(true));
    }

    @Override
    public void hideLoading() {
        hideDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyCar();
        getAuthorizedVehicle();
        StatService.onResume(VehicleManagementActivity.this);
        StatService.trackBeginPage(VehicleManagementActivity.this, "我的车辆");
        AnalyUtils.setBAnalyResume(this, "我的车辆");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(VehicleManagementActivity.this);
        StatService.trackEndPage(VehicleManagementActivity.this, "我的车辆");
        AnalyUtils.setBAnalyPause(this, "我的车辆");
    }
}
