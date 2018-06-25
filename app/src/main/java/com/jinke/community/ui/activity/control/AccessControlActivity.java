package com.jinke.community.ui.activity.control;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jinke.community.R;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.config.AppConfig;
import com.jinke.community.presenter.AccessControlPresenter;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.LocationServiceUtils;
import com.jinke.community.view.AccessControlView;
import com.tencent.stat.StatService;

import butterknife.Bind;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import www.jinke.com.library.utils.commont.LogUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/8/15.
 * 访客通行
 */

public class AccessControlActivity extends BaseActivity<AccessControlView, AccessControlPresenter> {
    @Bind(R.id.layout_root)
    RelativeLayout layout_root;
    @Bind(R.id.tx_access_control_key)
    TextView txKey;
    @Bind(R.id.tx_access_control_visitor)
    TextView txVisitor;

    @Override
    public AccessControlPresenter initPresenter() {
        return new AccessControlPresenter();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_access_control;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_access_control_title));
        showBackwardView(R.string.empty, true);
        showBackwardViewIco(R.mipmap.icon_activity_access_arrow_back);
        hindTitleLine();
        setTitleColor(getResources().getColor(R.color.white));
        setTitleBarBgColor(R.color.activity_access_control_title_bg);
        PermissionGen.with(AccessControlActivity.this)
                .addRequestCode(107)
                .permissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @OnClick({R.id.tx_access_control_key, R.id.tx_access_control_visitor, R.id.layout_root})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_access_control_key:
                openDoor();
                break;
            case R.id.tx_access_control_visitor:
                AppConfig.trackCustomEvent(this, "visitor_click", "门禁－访客通行");
                startActivity(new Intent(this, OpenDoorPasswordActivity.class));
                break;
            case R.id.layout_root:
                break;
        }
    }

    public void openDoor() {
        AppConfig.trackCustomEvent(this, "AccessControlActivity_click", "门禁－蓝牙开门");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0过后需要判断位置服务
            isOpen = LocationServiceUtils.checkLocationEnable(this);
            if (!isOpen) {
                Toast.makeText(this, "获取位置信息失败，请打开位置服务！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                return;
            }
        }
        if (!isOpen) {
            ToastUtils.showShort(this, "蓝牙权限获取失败，请前往应用权限管理中，进行授权!");
            return;
        }
        BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBtAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, 1313);
        } else {
            openDoorIntent();
        }
    }

    private void openDoorIntent() {
//        if (null != Account.getDevices(AccessControlActivity.this) && Account.getDevices(AccessControlActivity.this).size() > 0) {
//            startActivity(new Intent(AccessControlActivity.this, OpenDoorActivity.class));
//        } else {
//            ToastUtils.showShort(AccessControlActivity.this, "暂无门禁设备");
//        }
        startActivity(new Intent(AccessControlActivity.this, OpenDoorActivity.class));

    }

    @PermissionSuccess(requestCode = 107)
    public void loactionSuccess() {
        LogUtils.i("权限获取成功，正在定位!");
        isOpen = true;
    }

    private boolean isOpen = false;

    @PermissionFail(requestCode = 107)
    public void loactionFail() {
        ToastUtils.showLong(this, "权限获取失败，请前往应用权限管理中进行授权!");
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(localIntent);
        isOpen = false;
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1313) {
            switch (resultCode) {
                // 点击确认按钮
                case Activity.RESULT_OK: {
                    BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (!mBtAdapter.isEnabled()) {
                        ToastUtils.showShort(this, "没有打开蓝牙，请打开蓝牙再试");
                    } else {
                        openDoorIntent();
                    }
                }
                break;
            }
        }
    }

    protected void onResume() {
        super.onResume();
        AnalyUtils.addAnaly(10011);
        StatService.onResume(AccessControlActivity.this);
        StatService.trackBeginPage(AccessControlActivity.this, "访客通行页面");
        AnalyUtils.setBAnalyResume(this,"访客通行页面");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(AccessControlActivity.this);
        StatService.trackEndPage(AccessControlActivity.this, "访客通行页面");
        AnalyUtils.setBAnalyPause(this,"访客通行页面");
    }
}
