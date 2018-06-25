package com.jinke.community.ui.activity.payment;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.HouseBean;
import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.presenter.PropertyPaymentPresenter;
import com.jinke.community.ui.activity.base.StartupPageActivity;
import com.jinke.community.ui.activity.web.WebActivity;
import com.jinke.community.ui.adapter.OwnerAdapter;
import com.jinke.community.ui.widget.FillListView;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.AppManager;
import com.jinke.community.view.PropertyPaymentView;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/8/1.
 * 物业缴费
 */

public class PropertyPaymentActivity extends BaseActivity<PropertyPaymentView, PropertyPaymentPresenter> implements PropertyPaymentView {
    @Bind(R.id.owner_fillListView)
    FillListView ownerFillListView;
    @Bind(R.id.rl_vehicle_fee)
    RelativeLayout rlVehicleFee;

    private OwnerAdapter ownerAdapter;
    private List<HouseListBean.ListBean.OwnersBean> ownerBeanList = new ArrayList<>();
    private HouseBean.HouseListBean houseBean;
    private List<HouseBean.HouseListBean> houseList;
    public static PropertyPaymentActivity propertyPaymentInstance = null;
    private ACache aCache;
    private HouseListBean houseListBean;//缓存的房屋列表信息

    @Override
    public PropertyPaymentPresenter initPresenter() {
        return new PropertyPaymentPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_property_payment;
    }

    @Override
    protected void initView() {
        setTitle(R.string.payment_property_payment);
        showBackwardView(R.string.empty, true);
        propertyPaymentInstance = this;
        ownerAdapter = new OwnerAdapter(this, ownerBeanList);
        ownerFillListView.setAdapter(ownerAdapter);
        //判断用户所属小区是否存在月租车位
        presenter.getParkInfo();
        getHouseList();
    }

    private void getHouseList() {
        aCache = ACache.get(this);
        houseListBean = (HouseListBean) aCache.getAsObject("HouseListBean");
        if (houseListBean == null) {
            presenter.getHouseList();
        }
    }

    @Override
    public void showMessage(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showLong(this, msg);
    }

    @Override
    public void getHouseListNext(HouseListBean info) {
        aCache.put("HouseListBean", info, ACache.MAX_COUNT);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        if (getIntent().getScheme() == null) {
            finish();
        } else {
            startActivity(new Intent(this, StartupPageActivity.class));
        }
    }

    @OnClick({R.id.payment_notes_text})
    public void get(View view) {
        switch (view.getId()) {
            case R.id.payment_notes_text://缴费注意事项
//                Intent payment = new Intent(this, DealActivity.class);
                Intent payment = new Intent(this, WebActivity.class);
                payment.putExtra("url", HttpMethods.BASE_URL.replace("uc/", "") + AppConfig.URL_PAYMENT_NOTES);
                payment.putExtra("title", "缴费注意事项");
                startActivity(payment);
                break;
        }
    }

    @OnClick({R.id.payment_recorder_text, R.id.pre_stored_layout
            , R.id.pending_layout, R.id.withholding_layout
            , R.id.rl_monthly_parking_fee, R.id.rl_withholding_management})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pending_layout://待缴费查询
                AppConfig.trackCustomEvent(this, "pending_layout_click", "缴费－待缴费查询");
                Intent pendingIntent = new Intent(this, PendingPaymentActivity.class);
                pendingIntent.putExtra("sourceType", "pending");
                startActivity(pendingIntent);
                break;

            case R.id.pre_stored_layout://预存费用
                AppConfig.trackCustomEvent(this, "pre_stored_layout_click", "缴费－预存费用");
                Intent prepaidExpenses = new Intent(this, PrepaidExpensesActivity.class);
                prepaidExpenses.putExtra("isPaymentChange", "1");//缴费进入预存
                startActivity(prepaidExpenses);
                break;

            case R.id.rl_withholding_management://代扣管理
                AppConfig.trackCustomEvent(this, "withholding_layout_click", "缴费－代扣管理");
                startActivity(new Intent(this, WithholdingManagementActivity.class));
                break;

            case R.id.rl_monthly_parking_fee://月租充值
                AppConfig.trackCustomEvent(this, "withholding_layout_click", "缴费－代扣费用");
                startActivity(new Intent(this, ParkingLotRenewalActivity.class));
                break;

            case R.id.payment_recorder_text://缴费记录
                AppConfig.trackCustomEvent(this, "payment_recorder_text_click", "缴费－缴费记录");
                Intent payRecorderIntent = new Intent(this, PaymentRecordActivity.class);
                payRecorderIntent.putExtra("recorderType", "0");//0：缴费记录，1：代扣记录
                startActivity(payRecorderIntent);
                break;

        }
    }

    @Override
    public void getParkInfoNext(ParkInfoBean info) {
        if (info != null && info.getList() != null && info.getList().size() > 0) {
            rlVehicleFee.setVisibility(View.VISIBLE);
        } else {
            rlVehicleFee.setVisibility(View.GONE);
//            showMessage("未找到车辆绑定信息或当前社区暂未开通此功能！");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyUtils.addAnaly(1006);
        StatService.onResume(PropertyPaymentActivity.this);
        StatService.trackBeginPage(PropertyPaymentActivity.this, "缴费-物业缴费");
        AnalyUtils.setBAnalyResume(this, "缴费-物业缴费");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(PropertyPaymentActivity.this);
        StatService.trackEndPage(PropertyPaymentActivity.this, "缴费－物业缴费");
        AnalyUtils.setBAnalyPause(this, "缴费-物业缴费");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getIntent().getScheme() != null) {
                AppManager.finishPending();
                if (WithholdingActivity.withholdingInstance != null) {
                    WithholdingActivity.withholdingInstance.finish();
                }
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showLoading() {
        showProgressDialog(String.valueOf(false));
    }

    @Override
    public void hideLoading() {
        hideDialog();
    }
}
