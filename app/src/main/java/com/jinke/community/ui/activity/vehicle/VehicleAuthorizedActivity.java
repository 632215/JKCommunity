package com.jinke.community.ui.activity.vehicle;

import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.presenter.VehicleAuthorizedPresenter;
import com.jinke.community.ui.widget.CommunityPopWindows;
import com.jinke.community.ui.widget.LicensePopWindows;
import com.jinke.community.ui.widget.TimePopWindows;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.DrawableUtils;
import com.jinke.community.utils.EmTextWatch;
import com.jinke.community.utils.MyTextWatcher;
import com.jinke.community.view.VehicleAuthorizedView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/10/17.
 * 车辆授权
 */

public class VehicleAuthorizedActivity extends BaseActivity<VehicleAuthorizedView, VehicleAuthorizedPresenter>
        implements LicensePopWindows.OnLicenseSeclectListener, PopupWindow.OnDismissListener, CommunityPopWindows.OnItemSelectedListener
        , TimePopWindows.OnSureClickListense, VehicleAuthorizedView {
    @Bind(R.id.rl_root_view)
    RelativeLayout rlRootView;
    @Bind(R.id.tx_license_address)
    TextView licenseAddress;
    @Bind(R.id.ed_license_num)
    EditText licenseNum;
    @Bind(R.id.tx_authorized_address)
    TextView authorizedAddress;
    @Bind(R.id.tx_authorized_time)
    TextView authorizedTime;
    @Bind(R.id.ed_authorized_name)
    EditText authorizedName;
    @Bind(R.id.ed_authorized_phone)
    EditText authorizedPhone;
    @Bind(R.id.tx_sure)
    TextView txSure;

    private LicensePopWindows licensePopWindows;
    private CommunityPopWindows communityPopWindows;
    private TimePopWindows timePopWindows;
    private List<ParkInfoBean.ListBean> parkList;
    private String parkKey;

    @Override
    public VehicleAuthorizedPresenter initPresenter() {
        return new VehicleAuthorizedPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_vehicle_authorized;
    }

    @Override
    protected void initView() {
        setTitle(R.string.activity_vehicle_authorized);
        showBackwardView(R.string.empty, true);
        licenseAddress.setText("渝A");
        //设置授权用户姓名输入限制
        authorizedName.addTextChangedListener(new EmTextWatch(authorizedName, this));
        presenter.getParkInfo(); //获取停车场信息
        initPopwindows();
        addTextWatch();
    }

    @Override
    public void getParkInfoonNext(ParkInfoBean info) {
        parkList = info.getList();
        if (parkList != null && parkList.size() == 1) {
            authorizedAddress.setText(parkList.get(0).getPark_Name());
            authorizedAddress.setClickable(false);
            parkKey = parkList.get(0).getParking_Key();
        } else if (parkList != null && parkList.size() > 1) {
            authorizedAddress.setClickable(true);
            DrawableUtils.setDrawableRight(this, authorizedAddress, R.drawable.icon_authorized_select);
        }
    }

    /**
     * 初始化弹出框
     */
    private void initPopwindows() {
        licensePopWindows = new LicensePopWindows(this, this);
        licensePopWindows.setOnDismissListener(this);
        communityPopWindows = new CommunityPopWindows(this, this);
        communityPopWindows.setOnDismissListener(this);
        timePopWindows = new TimePopWindows(this, this);
        timePopWindows.setOnDismissListener(this);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @OnClick({R.id.tx_license_address, R.id.tx_authorized_address, R.id.tx_authorized_time, R.id.tx_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_license_address://车牌选择
                if (licensePopWindows != null && !licensePopWindows.isShowing()) {
                    licenseAddress.setBackgroundResource(R.drawable.icon_authorize_select);
                    licensePopWindows.setContentView(View.inflate(this, R.layout.pop_layout_license_select, null));
                    licensePopWindows.showAsDropDown(licenseAddress);
                }
                break;
            case R.id.tx_authorized_address://授权进入
                if (communityPopWindows != null && !communityPopWindows.isShowing()) {
                    communityPopWindows.setContentView(View.inflate(this, R.layout.pop_layout_community_select, null));
                    if (parkList != null && parkList.size() > 0) {
                        communityPopWindows.setListData(parkList);
                        communityPopWindows.showAtLocation(rlRootView, Gravity.CENTER, 0, 0);
                        backgroundAlpha(0.5f);
                    }
                }
                break;
            case R.id.tx_authorized_time://有效期
                if (timePopWindows != null && !timePopWindows.isShowing()) {
                    timePopWindows.setContentView(View.inflate(this, R.layout.pop_layout_time_select, null));
                    timePopWindows.showAtLocation(rlRootView, Gravity.CENTER, 0, 0);
                    backgroundAlpha(0.5f);
                }
                break;
            case R.id.tx_sure://确定
                addAuthorized();
                break;
        }
    }

    //添加授权
    private void addAuthorized() {
        if (StringUtils.isEmpty(licenseAddress.getText().toString().trim())) {
            ToastUtils.showShort(this, getResources().getString(R.string.activity_vehicle_authorized_please_inout_license_address));
            return;
        } else if (!(licenseAddress.getText().toString().trim().length() > 1)) {
            ToastUtils.showShort(this, getResources().getString(R.string.activity_vehicle_authorized_please_inout_license_address_error));
            return;
        }

        if (StringUtils.isEmpty(licenseNum.getText().toString().trim())) {
            ToastUtils.showShort(this, getResources().getString(R.string.activity_vehicle_authorized_please_inout_license));
            return;
        }

        if (licenseNum.getText().toString().trim().length() >= 5) {
            char[] chars = licenseNum.getText().toString().trim().toCharArray();
            for (char charsLetter : chars) {
                if (!String.valueOf(charsLetter).matches("[0-9A-Z]")) {
                    ToastUtils.showShort(this, getResources().getString(R.string.activity_vehicle_authorized_please_inout_too_long));
                    return;
                }
            }
        } else {
            ToastUtils.showShort(this, getResources().getString(R.string.activity_vehicle_authorized_please_inout_too_long));
            return;
        }

        if (StringUtils.isEmpty(authorizedAddress.getText().toString().trim())) {
            ToastUtils.showShort(this, getResources().getString(R.string.activity_vehicle_authorized_please_address));
            return;
        }
        if (StringUtils.isEmpty(authorizedTime.getText().toString().trim())) {
            ToastUtils.showShort(this, getResources().getString(R.string.activity_vehicle_authorized_please_time));
            return;
        }
        Map map = new HashMap();
        map.put("secretKey", AppConfig.SECRETKEY);
        map.put("parkKey", parkKey);
        map.put("reserveTime", authorizedTime.getText().toString().trim());
        map.put("carNo", licenseAddress.getText().toString().trim() + licenseNum.getText().toString().trim());
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
//                map.put("phone", MyApplication.getBaseUserBean().getPhone());
        if (!StringUtils.isEmpty(authorizedName.getText().toString().trim())) {
            map.put("name", authorizedName.getText().toString().trim());
        }
        if (!StringUtils.isEmpty(authorizedPhone.getText().toString().trim())) {
            map.put("phone", authorizedPhone.getText().toString().trim());
        }
        presenter.addAuthorize(map);
    }

    @Override
    public void addAuthorizeonNext() {
        ToastUtils.showShort(this, "授权成功");
        finish();
    }

    @Override
    public void error(String msg) {
        if (!StringUtils.isEmpty(msg))
            ToastUtils.showShort(this, msg);
    }

    //车牌信息弹框回调接口
    @Override
    public void onLicenseClick(String text, int popwindowsShowFlag) {
        switch (popwindowsShowFlag) {
            case 1:
                licenseAddress.setText(text);
                licensePopWindows.setLetterList();
                break;
            case 2:
                licenseAddress.setText(licenseAddress.getText() + text);
                licensePopWindows.setLetterList();
                licensePopWindows.dismiss();
                licenseAddress.setBackgroundResource(R.drawable.icon_authorize_un_select);
                break;
        }
    }

    //车牌信息弹框回调接口
    @Override
    public void checkLicense() {
        if (licenseAddress.getText().toString().trim().length() == 1) {
            licenseAddress.setText(licenseAddress.getText().toString().trim() + "A");
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    /**
     * 当关闭communityPopWindows时恢复透明度
     */
    @Override
    public void onDismiss() {
        if (communityPopWindows != null) {
            communityPopWindows.dismiss();
            licenseAddress.setBackgroundResource(R.drawable.icon_authorize_un_select);
            backgroundAlpha(1);
        }
        if (communityPopWindows != null) {
            communityPopWindows.dismiss();
            backgroundAlpha(1);
        }
    }

    /**
     * 小区滚动选择接收
     *
     * @param text
     */
    @Override
    public void onItemSelected(String text) {
        authorizedAddress.setText(text);
        for (ParkInfoBean.ListBean bean : parkList) {
            if (StringUtils.equals(text, bean.getPark_Name()))
                parkKey = bean.getParking_Key();
        }
    }

    /**
     * 时间选择确定接受
     *
     * @param time
     */
    @Override
    public void onSureClick(String time) {
        authorizedTime.setText(time);
    }

    //对必要的输入信息进行监听，控制“确定”按钮的显示状态
    private void addTextWatch() {
        licenseAddress.addTextChangedListener(new MyTextWatcher(new MyTextWatcher.MyTextWatcherListener() {
            @Override
            public void onTextChanged() {
                changeUI();
            }
        }));

        licenseNum.addTextChangedListener(new MyTextWatcher(new MyTextWatcher.MyTextWatcherListener() {
            @Override
            public void onTextChanged() {
                changeUI();
            }
        }));

        licenseNum.addTextChangedListener(new EmTextWatch(licenseNum, this, new EmTextWatch.EmTextWatchListener() {
            @Override
            public void EmTextWatchChanged() {
                changeUI();
            }
        }));
        authorizedAddress.addTextChangedListener(new MyTextWatcher(new MyTextWatcher.MyTextWatcherListener() {
            @Override
            public void onTextChanged() {
                changeUI();
            }
        }));
        authorizedTime.addTextChangedListener(new MyTextWatcher(new MyTextWatcher.MyTextWatcherListener() {
            @Override
            public void onTextChanged() {
                changeUI();
            }
        }));
    }

    /**
     * 更换确定按钮的样式
     */
    private void changeUI() {
        if ((licenseAddress.getText().toString().trim().length() > 1 && licenseNum.getText().toString().trim().length() == 5
                && !StringUtils.isEmpty(authorizedAddress.getText().toString().trim())
                && !StringUtils.isEmpty(authorizedTime.getText().toString().trim()))
                || (licenseAddress.getText().toString().trim().length() > 1 && licenseNum.getText().toString().trim().length() == 6
                && !StringUtils.isEmpty(authorizedAddress.getText().toString().trim())
                && !StringUtils.isEmpty(authorizedTime.getText().toString().trim()))) {
            txSure.setAlpha(1f);
            txSure.setEnabled(true);
        } else {
            txSure.setAlpha(0.5f);
            txSure.setEnabled(false);
        }
    }

    //展示小白
    @Override
    public void showLoading() {
        showProgressDialog(null);
    }

    @Override
    public void hideLoading() {
        hideDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyUtils.setBAnalyResume(this, "车辆授权");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyUtils.setBAnalyPause(this, "车辆授权");
    }
}
