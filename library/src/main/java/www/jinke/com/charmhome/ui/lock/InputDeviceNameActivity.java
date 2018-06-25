package www.jinke.com.charmhome.ui.lock;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;
import www.jinke.com.charmhome.Base.BaseActivity;
import www.jinke.com.charmhome.R;
import www.jinke.com.charmhome.bean.LockRequestAddDeviceBean;
import www.jinke.com.charmhome.bean.LockUserBean;
import www.jinke.com.charmhome.presenter.lock.InputDeviceNamePresenter;
import www.jinke.com.charmhome.ui.dialog.LockBluetoothDialog;
import www.jinke.com.charmhome.utils.ACache;
import www.jinke.com.charmhome.utils.SharedPreferencesUtils;
import www.jinke.com.charmhome.view.lock.IInputDeviceNameView;

/**
 * Created by root on 17-12-12.
 * <p>
 * 输入设备名称
 * 此界面需要定位服务打开并且拥有定位权限
 */

public class InputDeviceNameActivity extends BaseActivity implements IInputDeviceNameView, View.OnClickListener, LockBluetoothDialog.onIKnowListener {
    EditText ed_input_device_name;
    TextView tx_lock_device_add_sure;
    InputDeviceNamePresenter presenter;
    private ACache aCache;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_input_device_name;
    }

    @Override
    protected void initView() {
        presenter = new InputDeviceNamePresenter(this, this);
        setTitleText(getString(R.string.charmHome_lock_add_device_title));
        aCache = ACache.get(this);
    }

    @Override
    protected void onBackView(View view) {
        super.onBackView(view);
        finish();
    }

    @Override
    protected void findViewById() {
        ed_input_device_name = findViewById(R.id.ed_input_device_name);
        tx_lock_device_add_sure = findViewById(R.id.tx_lock_device_add_sure);
        tx_lock_device_add_sure.setOnClickListener(this);
        ed_input_device_name.setSelection(ed_input_device_name.getText().toString().trim().length());
    }

    @Override
    public void showLoading(String msg) {
        showDialog(msg, true);
    }

    @Override
    public void hideLoading() {
        hideDialog();
    }

    public void getAddDevice(String roomName) {
        LockUserBean userBean = (LockUserBean) aCache.getAsObject("UserBean");
        if (userBean != null) {
            LockRequestAddDeviceBean bean = new LockRequestAddDeviceBean();
            //登录用户手机号码
            bean.setLoginUserMobile(userBean.getAccount());
            //登录用户昵称
            bean.setLoginUserNickName(userBean.getNickname());
            //设备名称
            bean.setLocknameString(roomName);
            //设备mac地址
            bean.setLockmacString((String) SharedPreferencesUtils.init(this).get("lock_mac", ""));
            //设备管理密码，后期废弃
            bean.setManagepwdString((String) SharedPreferencesUtils.init(this).get("lock_pwd", ""));
            //设备固件类型，例如T700_0
            bean.setMetertype((String) SharedPreferencesUtils.init(this).get("lock_Meter_type", ""));
            bean.setDevicetype((String) SharedPreferencesUtils.init(this).get("lock_type", ""));
            //latitude - 设备所在维度
            bean.setLatitude("");
            //longitude - 设备所在经度
            bean.setLongitude("");
            presenter.getAddDevice(bean);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tx_lock_device_add_sure) {
            if (!StringUtils.isEmpty(ed_input_device_name.getText().toString())) {
                LockBluetoothDialog dialog = new LockBluetoothDialog(this);
                dialog.setOnIKnowListener(this);
                dialog.show();
                dialog.setContentImage(R.drawable.icon_lock_bluetooth_dialog,
                        getString(R.string.charmHome_lock_dialog_one),
                        getString(R.string.charmHome_lock_dialog_two));
            } else {
                showToast(getString(R.string.charmHome_lock_toast_input_device_name));
            }
        }
    }

    @Override
    public void addDeviceFinish() {
        //回到首页
        if (ScanningLockCodeActivity.instance != null) {
            ScanningLockCodeActivity.instance.finish();
        }
        finish();
    }

    @Override
    public void showMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void startInputFinger() {
        Intent intent = new Intent(this, InputFingerActivity.class);
        intent.putExtra("isOver", true);
        startActivity(intent);
        finish();
    }

    @Override
    public void IKowBack() {
        //检测定位服务是否打开
        checkPermissionLocation();
    }

    //进行指纹录制
    private void openNext() {
        if (!isOpen) {
            Toast.makeText(this, "定位服务未开启，请在设置中开启定位服务！", Toast.LENGTH_SHORT).show();
            return;
        }
        BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBtAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, 1313);
        } else {
            getAddDevice(ed_input_device_name.getText().toString());
        }
    }

    private void checkPermissionLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//>23
            isOpen = isLocationEnabled();
            goSetLocationService();
        } else {
            isOpen = true;
            openNext();
        }

    }

    //打开设置定位服务开关
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void goSetLocationService() {
        if (!isOpen) {
            Toast.makeText(this, "获取位置信息失败，请打开位置服务！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        RxPermissions.getInstance(this)
                .request(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            openNext();
                        } else {
                            isOpen = false;
                            Intent localIntent = new Intent();
                            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                            startActivity(localIntent);
                        }
                    }
                });
    }

    //检测是否开启定位服务
    public boolean isLocationEnabled() {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//>19
            try {
                locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (locationProviders != null && locationProviders.trim() != "") {
                return Boolean.parseBoolean(locationProviders);
            } else {
                return false;
            }
        }
    }

    private boolean isOpen = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1313:
                switch (resultCode) {
                    // 点击确认按钮
                    case Activity.RESULT_OK: {
                        BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (!mBtAdapter.isEnabled()) {
                            Toast.makeText(this, "没有打开蓝牙，请打开蓝牙再试", Toast.LENGTH_SHORT).show();
                        } else {
                            getAddDevice(ed_input_device_name.getText().toString());
                        }
                    }
                    break;
                }
                break;
        }
    }
}
