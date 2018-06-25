package com.jinke.community.ui.activity.base;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.doormaster.vphone.exception.DMException;
import com.doormaster.vphone.inter.DMModelCallBack;
import com.doormaster.vphone.inter.DMVPhoneModel;
import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.BaseUserBean;
import com.jinke.community.bean.CustomerPhoneBean;
import com.jinke.community.bean.acachebean.CallCenterBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.http.door.http.DateCallBackInterface;
import com.jinke.community.http.door.http.HttpDateGet;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.presenter.SettingPresenter;
import com.jinke.community.ui.activity.web.WebActivity;
import com.jinke.community.ui.toast.CallPhoneDialog;
import com.jinke.community.ui.toast.SelectAlbumWindow;
import com.jinke.community.ui.toast.dialog.SpotsDialog;
import com.jinke.community.utils.ACache;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.DataCleanManager;
import com.jinke.community.utils.DbUtils;
import com.jinke.community.utils.SharedPreferencesUtils;
import com.jinke.community.view.SettingView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import www.jinke.com.library.utils.commont.AppUtils;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-9-8.
 */

public class SettingActivity extends BaseActivity<SettingView, SettingPresenter>
        implements SettingView, SelectAlbumWindow.OnSelectAlbumWindowListener, CompoundButton.OnCheckedChangeListener, CallPhoneDialog.onCallPhoneListener {
    @Bind(R.id.switch_view)
    Switch switchView;
    @Bind(R.id.tx_version)
    TextView version;
    @Bind(R.id.tx_cache)
    TextView txCache;
    @Bind(R.id.tx_num)
    TextView txNum;
    private ACache aCache;
    private SpotsDialog dialog;
    private CallPhoneDialog callDialog;
    private final static int AccountType = 1;//1:手机账号，2：设备账号（传入设备的序列号和设备的管理密码）
    private BaseUserBean userBean;
    private SelectAlbumWindow clearWindows;//清除缓存弹框
    private CallCenterBean callCenterBean;//缓存中的客服号码

    @Override
    public SettingPresenter initPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void showBackwardView(int backwardResid, boolean show) {
        super.showBackwardView(backwardResid, show);
        finish();
    }

    @Override
    protected void initView() {
        setTitle("设置");
        showBackwardView("", true);
        version.setText(getString(R.string.main_present_versions) + AppUtils.getAppVersionName(this));
        aCache = ACache.get(this);
        switchView.setChecked(SharedPreferencesUtils.getVisitorVideoAccess(this).equals("true") ? true : false);
        switchView.setOnCheckedChangeListener(this);
        userBean = MyApplication.getBaseUserBean();
        try {
            txCache.setText(DataCleanManager.getTotalCacheSize(this, aCache));
        } catch (Exception e) {
            e.printStackTrace();
        }
        callCenterBean = (CallCenterBean) aCache.getAsObject("CallCenterBean");
        if (callCenterBean != null) {
            callCenterBean.setIsKeeper(0);
            callCenterBean.setKeeperName("");
            callCenterBean.setKeeperPhone("");
            txNum.setText(StringUtils.isEmpty(callCenterBean.getServicePhone()) ? "400-846-1818" : callCenterBean.getServicePhone());
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("accessToken", userBean.getAccessToken());
            presenter.getCustomerPhone(map);
        }
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @OnClick({R.id.rl_problems, R.id.tx_login_out,
            R.id.rl_feed_back, R.id.rl_customer_phone,
            R.id.re_version_up, R.id.re_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_problems://常见问题
                Intent payment = new Intent(this, WebActivity.class);
                payment.putExtra("url", HttpMethods.BASE_URL.replace("uc/", "") + AppConfig.URL_QUESTION);
                payment.putExtra("title", getString(R.string.main_present_probles));
                startActivity(payment);
                break;

            case R.id.rl_feed_back://意见建议
                startActivity(new Intent(this, SuggestionsActivity.class));
                break;

            case R.id.rl_customer_phone://客户热线
                if (callCenterBean != null) {
                    callDialog = new CallPhoneDialog(this, this, callCenterBean.getServicePhone());
                    callDialog.show();
                    callDialog.setTitle("是否拨打");
                    callDialog.setContent(callCenterBean.getServicePhone());
                }
                break;

            case R.id.re_version_up://版本信息
                startActivity(new Intent(this, VersionActivity.class));
                break;

            case R.id.re_clear://清理缓存
                if (clearWindows == null) {
                    clearWindows = new SelectAlbumWindow(this);
                }
                clearWindows.setCancelVisiable(View.GONE);
                clearWindows.setCamera(getString(R.string.pop_layout_community_select_sure));
                clearWindows.setAlbum(getString(R.string.pop_layout_community_select_cancle));
                clearWindows.setOnSelectAlbumWindowListener(this);
                clearWindows.showPopWindow(view);
                break;

            case R.id.tx_login_out://退出登录
                presenter.getLoginOut();
                break;
        }
    }

    @Override
    public void getLoginOutNext() {
        if (aCache != null) {
            aCache.clear();
            //清除acache的缓存
            SharedPreferencesUtils.clearBaseUserBean(this);
            DbUtils.deleteAcache(this);
            JPushInterface.deleteAlias(this, 1);//删除极光推送的Ali
            HouseListBean houseListBean = null;
            aCache.put("HouseListBean", houseListBean, ACache.TIME_DAY);//删除房屋列表缓存数据
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void showLoading() {
        showProgressDialog(String.valueOf(true));
    }

    @Override
    public void hideLoading() {
        hideDialog();
    }

    private DateCallBackInterface dateCallBackInterface = new DateCallBackInterface() {
        @Override
        public void getEmployeesCallBack(String token) {
            if (null != token) {
                DMVPhoneModel.loginVPhoneServer(userBean.getPhone(), token, AccountType, SettingActivity.this, loginCallback);//登录
            } else {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }
    };

    private DMModelCallBack.DMCallback loginCallback = new DMModelCallBack.DMCallback() {
        @Override
        public void setResult(int i, DMException e) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    };

    @Override
    public void getCustomerPhoneNext(CustomerPhoneBean bean) {
        SharedPreferencesUtils.saveBaseUserBean(this, userBean);
        if (callCenterBean == null) {
            callCenterBean = new CallCenterBean();
        }
        callCenterBean.setServicePhone(bean.getPhone());
        txNum.setText(StringUtils.isEmpty(callCenterBean.getServicePhone()) ? AppConfig.SERVICEPHONE : callCenterBean.getServicePhone());
    }

    @Override
    public void showErrorMsg(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showLong(this, msg);
    }

    @Override
    public void relationship(String relationship) {
        if (StringUtils.equals("1", relationship)) {
            ACache aCache = ACache.get(this);
            DataCleanManager.clearAllCache(this, aCache);
            try {
                txCache.setText(DataCleanManager.getTotalCacheSize(this, aCache));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferencesUtils.setVisitorVideoAccess(this, isChecked);
        switch (SharedPreferencesUtils.getVisitorVideoAccess(this)) {
            case "true":
                if (dialog == null) {
                    dialog = new SpotsDialog(this);
                    dialog.setCanceledOnTouchOutside(true);
                }
                dialog.show();
                HttpDateGet httpDateGet = new HttpDateGet(this, dateCallBackInterface);
                httpDateGet.getEmployees(userBean.getPhone());
                break;
            case "false":
                DMVPhoneModel.exit();
                break;
        }
    }

    @Override
    public void onSure(String phone) {
        callDialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyUtils.setBAnalyResume(this, "设置");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyUtils.setBAnalyPause(this, "设置");
    }
}
