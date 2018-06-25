package com.jinke.community.ui.activity.base;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.acachebean.CallCenterBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.presenter.CallPresenter;
import com.jinke.community.ui.activity.web.WebActivity;
import com.jinke.community.ui.toast.SelectHouseDialog;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.view.CallView;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2018/3/22.
 */

public class CallActivity extends BaseActivity<CallView, CallPresenter> implements
        SelectHouseDialog.onSelectHouseListener, CallView {
    @Bind(R.id.rl_address)
    RelativeLayout rlAddress;
    @Bind(R.id.address_txw)
    TextView txAddress;
    @Bind(R.id.tx_select)
    TextView txSelect;
    @Bind(R.id.tx_call_housekeeper)
    TextView txCallHousekeeper;
    @Bind(R.id.tx_call_hot_line)
    TextView txCallHotLine;
    @Bind(R.id.tx_call_service)
    TextView txCallService;

    private CallCenterBean callCenterBean = null;
    private ACache aCache = null;
    private HouseListBean houseListBean = null;//缓存的房屋列表信息
    private HouseListBean.ListBean tempHouseBean = null;//缓存的默认房屋信息
    private SelectHouseDialog houseListDialog = null;

    @Override
    public CallPresenter initPresenter() {
        return new CallPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_call;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_call_title));
        showBackwardView("", true);
        initAddressLayout();
    }

    private void initAddressLayout() {
        aCache = ACache.get(this);
        houseListBean = (HouseListBean) aCache.getAsObject("HouseListBean");
        if (houseListBean == null)
            return;
        if (tempHouseBean == null)
            tempHouseBean = MyApplication.getInstance().getDefaultHouse();
        if (tempHouseBean == null)
            return;
        txAddress.setText(tempHouseBean.getCommunity_name() + tempHouseBean.getHouse_name());
        rlAddress.setClickable(houseListBean.getList().size() > 1 ? true : false);//选择更多房屋可见性
        txSelect.setVisibility(houseListBean.getList().size() > 1 ? View.VISIBLE : View.GONE);
        if (callCenterBean != null) {
            txCallHousekeeper.setVisibility(StringUtils.isEmpty(callCenterBean.getKeeperPhone()) ? View.GONE : View.VISIBLE);
        } else
            presenter.getPhone(tempHouseBean);
    }

    @OnClick({R.id.tx_call_housekeeper, R.id.tx_call_hot_line, R.id.tx_call_service, R.id.rl_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_call_housekeeper://管家
                if (callCenterBean == null || StringUtils.isEmpty(callCenterBean.getKeeperPhone()) || callCenterBean.getIsKeeper() != 1)
                    return;
                startActivity(new Intent(Intent.ACTION_DIAL)
                        .setData(Uri.parse("tel:" + callCenterBean.getKeeperPhone())));
                break;

            case R.id.tx_call_hot_line://400热线
                startActivity(new Intent(Intent.ACTION_DIAL)
                        .setData(Uri.parse("tel:" + (callCenterBean == null || StringUtils.isEmpty(callCenterBean.getServicePhone()) ?
                                AppConfig.SERVICEPHONE : callCenterBean.getServicePhone()))));
                break;

            case R.id.tx_call_service://在线客服
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra("url", AppConfig.ONLINESERVICE + MyApplication.getBaseUserBean().getAccessToken())
                        .putExtra("title", "在线客服")
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    /**
     * 选择房屋成功回调
     *
     * @param bean
     */
    @Override
    public void selectHouse(HouseListBean.ListBean bean) {
        tempHouseBean = bean;
        txAddress.setText(tempHouseBean.getCommunity_name() + tempHouseBean.getHouse_name());
        presenter.getPhone(bean);
    }

    /**
     * 获取管家号码成功回调
     *
     * @param info
     */
    @Override
    public void getPhoneNext(CallCenterBean info) {
        callCenterBean = info;
        if (callCenterBean != null)
            txCallHousekeeper.setVisibility(StringUtils.isEmpty(callCenterBean.getKeeperPhone()) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
    }

    @Override
    public void showLoading() {
        showProgressDialog("");
    }

    @Override
    public void hideLoading() {
        hideDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyUtils.setBAnalyResume(this,"呼叫");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyUtils.setBAnalyPause(this,"呼叫");
    }
}
