package com.jinke.community.ui.activity.payment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.PaymentRecordBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.presenter.PaymentRecordPresenter;
import com.jinke.community.ui.adapter.PaymentRecorderAdapter;
import com.jinke.community.ui.toast.SelectHouseDialog;
import com.jinke.community.ui.widget.FillListView;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.PaymentRecordView;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.pulltorefresh.PullToRefreshBase;
import www.jinke.com.library.pulltorefresh.PullToRefreshScrollView;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/8/1.
 * 缴费记录/代扣记录
 */

public class PaymentRecordActivity extends BaseActivity<PaymentRecordView, PaymentRecordPresenter>
        implements LoadingLayout.OnReloadListener, PullToRefreshBase.OnRefreshListener2<ScrollView>,
        SelectHouseDialog.onSelectHouseListener, PaymentRecordView, PaymentRecorderAdapter.RecorderOnClickListener {
    @Bind(R.id.rl_address)
    RelativeLayout rlAddress;
    @Bind(R.id.tx_select)
    TextView txSelect;
    @Bind(R.id.tx_payRecord_address)
    TextView txPayRecordAddress;
    @Bind(R.id.record_fillListView)
    FillListView recordFillListView;
    @Bind(R.id.tx_earlier_records)
    TextView txEarlierRecords;
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @Bind(R.id.scrollView)
    PullToRefreshScrollView scrollView;
    private PaymentRecorderAdapter paymentRecorderAdapter;
    private List<PaymentRecordBean.ListBean> paymentRecorderList = new ArrayList<>();
    private SelectHouseDialog houseDialog;
    private ACache aCache;
    private HouseListBean houseListBean;//缓存的房屋列表信息
    private HouseListBean.ListBean tempHouseBean;
    private String recorderType = "";//0：缴费记录，1：代扣记录
    private int page = 1;
    private int pageCounter = 1;

    @Override
    public PaymentRecordPresenter initPresenter() {
        return new PaymentRecordPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_payment_record;
    }

    @Override
    protected void initView() {
        tempHouseBean = (HouseListBean.ListBean) getIntent().getSerializableExtra("listBean");
        recorderType = getIntent().getStringExtra("recorderType");
        setTitle(StringUtils.equals(recorderType, "0") ? R.string.payment_payment_record : R.string.activity_withholding_management_recorder);
        showBackwardView(R.string.empty, true);
        initData();
    }

    private void initData() {
        scrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        scrollView.setOnRefreshListener(this);
        scrollView.getLoadingLayoutProxy(true, false).setLoadingDrawable(getResources().getDrawable(R.mipmap.icon_loading));
        scrollView.getLoadingLayoutProxy(false, true).setLoadingDrawable(getResources().getDrawable(R.mipmap.icon_loading));
        loadingLayout.setOnReloadListener(this);
        paymentRecorderAdapter = new PaymentRecorderAdapter(this, paymentRecorderList, this);
        recordFillListView.setAdapter(paymentRecorderAdapter);
        paymentRecorderAdapter.setData(paymentRecorderList);
        getCacheData();
    }

    public void getCacheData() {//获取缓存的房屋列表
        aCache = ACache.get(this);
        houseListBean = (HouseListBean) aCache.getAsObject("HouseListBean");
        if (houseListBean != null) {
            for (HouseListBean.ListBean bean : houseListBean.getList()) {
                if (tempHouseBean == null && bean.getDft_house() == 1) {
                    tempHouseBean = bean;
                }
            }
        }
        txPayRecordAddress.setText(tempHouseBean.getCommunity_name() + tempHouseBean.getHouse_name());
        rlAddress.setClickable(houseListBean.getList().size() > 1 ? true : false);//选择更多房屋可见性
        txSelect.setVisibility(houseListBean.getList().size() > 1 ? View.VISIBLE : View.GONE);
        getRecorder(page);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void onRecorderClick(PaymentRecordBean.ListBean bean) {
        startActivity(new Intent(this, PaymentDetailsActivity.class).putExtra("bean", bean).putExtra("House_id",tempHouseBean.getHouse_id()));
    }

    @OnClick({R.id.rl_address, R.id.tx_earlier_records})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_address:
                if (houseDialog == null) {
                    houseDialog = new SelectHouseDialog(this, this);
                }
                houseDialog.show();
                break;
            case R.id.tx_earlier_records:
                /*
                //查看更早以前记录进入显示6个月的记录，点击查询更早记录，查询再早6个月的，再次点击再早6个月的，最多查询2年的，之后“查询更早记录”变灰，不可点击
                 */
                pageCounter++;
                page++;
                showProgressDialog("true");
                getRecorder(page);
                break;
        }
    }

    @Override
    public void selectHouse(HouseListBean.ListBean bean) {
        tempHouseBean = bean;
        txPayRecordAddress.setText(bean.getCommunity_name() + bean.getHouse_name());
        paymentRecorderList.clear();
        page = 1;
        pageCounter = 1;
        loadingLayout.setStatus(LoadingLayout.Loading);
        txEarlierRecords.setEnabled(true);
        txEarlierRecords.setTextColor(getResources().getColor(R.color.payment_text_color26));
        getRecorder(page);
    }

    @Override
    public void showMsg(String msg) {
        hideDialog();
        getMoreRecord();
        loadingLayout.setStatus(LoadingLayout.Success);
        scrollView.onRefreshComplete();
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
    }

    @Override
    public void onRecordList(PaymentRecordBean bean) {
        hideDialog();
        getMoreRecord();
        paymentRecorderList.addAll(bean.getList());
        paymentRecorderAdapter.setData(paymentRecorderList);
        if (paymentRecorderList.size() > 0) {
            loadingLayout.setStatus(LoadingLayout.Success);
        } else {
            if (pageCounter != 1) {
                ToastUtils.showShort(this, "无更多记录");
            }
            if (StringUtils.equals(recorderType, "1")) {
                loadingLayout.setPayrecorderTips("最近6个月暂无代扣记录");
            }
            loadingLayout.setStatus(LoadingLayout.Pay_Recorder_Empty);
        }
        scrollView.onRefreshComplete();
    }

    private void getMoreRecord() {
        if (pageCounter == 4) {
            txEarlierRecords.setEnabled(false);
            txEarlierRecords.setTextColor(getResources().getColor(R.color.default_driver_line_color));
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
        paymentRecorderList.clear();
        page = 1;
        pageCounter = 1;
        loadingLayout.setStatus(LoadingLayout.Loading);
        txEarlierRecords.setEnabled(true);
        txEarlierRecords.setTextColor(getResources().getColor(R.color.payment_text_color26));
        getRecorder(page);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
//        page++;
//        getRecorder(page);
    }

    public void getRecorder(int page) {//recorderType  0：缴费记录，1：代扣记录
        Map<String, String> map = new HashMap<>();
        switch (recorderType) {
            case "0":
                map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
                map.put("houseId", tempHouseBean.getHouse_id());
                map.put("ownerPhone", MyApplication.getBaseUserBean().getPhone());
                map.put("page", String.valueOf(page));
                map.put("rows", "10");
                presenter.getPaymentRecordList(map);
                break;

            case "1":
                map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
                map.put("houseId", tempHouseBean.getHouse_id());
                map.put("page", String.valueOf(page));
                map.put("rows", "10");
                presenter.getWithHoldRecorder(map);
                break;
        }
    }

    @Override
    public void onReload(View v) {
        paymentRecorderList.clear();
        page = 1;
        getRecorder(page);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(PaymentRecordActivity.this);
        StatService.trackBeginPage(PaymentRecordActivity.this, "缴费－缴费记录");
        AnalyUtils.setBAnalyResume(this, "缴费－缴费记录");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(PaymentRecordActivity.this);
        StatService.trackEndPage(PaymentRecordActivity.this, "缴费－缴费记录");
        AnalyUtils.setBAnalyPause(this, "缴费－缴费记录");
    }
}
