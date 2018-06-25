package com.jinke.community.ui.activity.payment;

import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.HouseWithHoldBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.presenter.WithholdingManagementPresenter;
import com.jinke.community.ui.activity.web.WebActivity;
import com.jinke.community.ui.adapter.WithholdingManagementAdapter;
import com.jinke.community.ui.widget.FillListView;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.WithholdingManagementView;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/11/18.
 * 代扣管理
 */

public class WithholdingManagementActivity extends BaseActivity<WithholdingManagementView, WithholdingManagementPresenter>
        implements WithholdingManagementView, WithholdingManagementAdapter.ItemWithholdingManagementListener {
    @Bind(R.id.tx_date)
    TextView txDate;
    @Bind(R.id.tx_explain)
    TextView tx_explain;
    @Bind(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @Bind(R.id.list_view)
    FillListView listView;
    private WithholdingManagementAdapter withholdingManagementAdapter;

    @Override
    public WithholdingManagementPresenter initPresenter() {
        return new WithholdingManagementPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_withholding_management;
    }

    @Override
    protected void initView() {
        setTitle(R.string.activity_withholding_management_title);
        showBackwardView(R.string.empty, true);
        SpannableString spannableString = new SpannableString(txDate.getText().toString());
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.activity_withholding_management_color4)), 2, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        txDate.setText(spannableString);
        loadingLayout.setStatus(LoadingLayout.Loading);
    }

    @Override
    public void getHouseWithHoldNext(HouseWithHoldBean info) {
        loadingLayout.setStatus(LoadingLayout.Success);
        withholdingManagementAdapter = new WithholdingManagementAdapter(this, R.layout.item_withholding_management, info.getList());
        withholdingManagementAdapter.setListener(this);
        listView.setAdapter(withholdingManagementAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getHouseList();
        AnalyUtils.setBAnalyResume(this, "代扣管理");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyUtils.setBAnalyPause(this, "代扣管理");
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    /**
     * 跳转界面代扣记录/待缴费
     */
    @Override
    public void recorderClick(HouseWithHoldBean.ListBean listBean) {//如果存在代扣——代扣记录，否则——待缴费明细
        HouseListBean.ListBean tempListBean = new HouseListBean.ListBean();
        tempListBean.setAddress(listBean.getAddress());
        tempListBean.setCommunity_id(listBean.getCommunity_id());
        tempListBean.setDft_house(listBean.getDft_house());
        tempListBean.setIsgrant(listBean.getIsgrant());
        tempListBean.setHouse_name(listBean.getHouse_name());
        tempListBean.setHouse_id(listBean.getHouse_id());
        tempListBean.setCommunity_name(listBean.getCommunity_name());
        if (listBean.getSign_status().getAli_sign_status() == 1 || listBean.getSign_status().getWx_sign_status() == 1) {
            Intent payRecorderIntent = new Intent(this, PaymentRecordActivity.class);
            payRecorderIntent.putExtra("recorderType", "1");//0：缴费记录，1：代扣记录
            payRecorderIntent.putExtra("listBean", tempListBean);
            startActivity(payRecorderIntent);
        } else {
            Intent pendingIntent = new Intent(this, PendingPaymentActivity.class);
            //转型
            pendingIntent.putExtra("sourceType", "pending");
            pendingIntent.putExtra("listBean", tempListBean);
            startActivity(pendingIntent);
        }
    }

    /**
     * 开通代扣
     */
    @Override
    public void openWithHold(HouseWithHoldBean.ListBean listBean) {
        Intent withHoldOpenIntent = new Intent(this, WithHoldOpenActivity.class);
        withHoldOpenIntent.putExtra("listBean", listBean);
        startActivity(withHoldOpenIntent);
    }

    /**
     * 解约
     */
    @Override
    public void closeWithHold(HouseWithHoldBean.ListBean listBean) {
        loadingLayout.setStatus(LoadingLayout.Loading);
        presenter.withholdBreak(listBean);
    }

    @Override
    public void showMessage(String msg) {
        loadingLayout.setStatus(LoadingLayout.Success);
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showLong(this, msg);
    }


    @OnClick({R.id.tx_explain})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_explain:
                Intent payment = new Intent(this, WebActivity.class);
                payment.putExtra("url", HttpMethods.BASE_URL.replace("uc/", "") + AppConfig.URL_WITHHOLDING_MANAGEMENT);
                payment.putExtra("title", getString(R.string.activity_withholding_management_explain));
                startActivity(payment);
                break;
        }
    }
}

