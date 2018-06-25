package com.jinke.community.presenter;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.intelligoo.sdk.LibDevModel;
import com.intelligoo.sdk.LibInterface;
import com.intelligoo.sdk.ScanCallback;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.ListDateInfo;
import com.jinke.community.service.impl.OpenDoorImpl;
import com.jinke.community.service.listener.IOpenDoorListener;
import com.jinke.community.ui.activity.control.OpenDoorActivity;
import com.jinke.community.view.IOpenDoorView;
import com.jkapi.entrancelibrary.Account;
import com.jkapi.entrancelibrary.BeaconManager;
import com.jkapi.entrancelibrary.BleConnectListener;
import com.jkapi.entrancelibrary.BleScannerListener;
import com.jkapi.entrancelibrary.BluetoothDeviceBean;
import com.jkapi.entrancelibrary.ClientConnectManage;
import com.jkapi.entrancelibrary.Equipment;
import com.jkapi.entrancelibrary.ResultBean;
import com.jkapi.entrancelibrary.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import www.jinke.com.library.utils.commont.LogUtils;

import static com.jkapi.entrancelibrary.Constant.PKT_BLT_TIME_S;
import static com.jkapi.entrancelibrary.Constant.PKT_DOOR_UNLOCK_S;
import static com.jkapi.entrancelibrary.Constant.PKT_NFC_TIME_S;
import static com.jkapi.entrancelibrary.Constant.PKT_PASSWORD_TIME_S;

/**
 * Created by root on 17-8-21.
 */

public class OpenDoorPresenter extends BasePresenter<IOpenDoorView> implements IOpenDoorListener {
    private FragmentActivity mContext;
    private final static String OPEN_STATUS_SUCCESS = "1";//开门成功
    private final static String OPEN_STATUS_CONNECTIONLESS = "2";//开门失败 连接异常
    private final static String OPEN_STATUS_NO_EQUIPMENT = "3";//开门失败 未扫描到设备
    private final static String OPEN_STATUS_SEND_DATA = "4";//开门失败 发送数据失败
    private final static int SUCCESS_CODE = 9211;//开门成功
    private final static int DEFEATED_CODE = 9215;//开门失败
    private final static int DEFEATED_DATE_CODE = 9212;//同步开门记录失败
    private String openStatus;
    OpenDoorImpl openDoor;


    public OpenDoorPresenter(FragmentActivity mContext) {
        this.mContext = mContext;
        openDoor = new OpenDoorImpl(mContext);
    }

    public void openDoorXimo() {
        LibDevModel.scanDevice(mContext, true, 2000, showAllScanCallback);
    }


    private boolean tryOpen = true;
    ScanCallback showAllScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(ArrayList<String> arrayList, ArrayList<Integer> arrayList1) {
            Log.e("JK", "Integer openDoor : " + arrayList.size());
            if (arrayList.size() == 0) {
                openDoorZHC();
            }
        }

        @Override
        public void onScanResultAtOnce(String s, int i) {
            for (Equipment dateBean : Account.getDevices(mContext)) {
                if (s.equals(dateBean.getDeviceNum()) && tryOpen) {
                    tryOpen = false;
                    LibDevModel.stopScanDevice();
                    LibDevModel libDevModel = new LibDevModel();
                    libDevModel.devSn = s;
                    libDevModel.devMac = dateBean.getMAC_Addr();
                    libDevModel.devType = 10;
                    libDevModel.cardno = "-1";
                    libDevModel.eKey = dateBean.getDeviceSecret();
                    int code = LibDevModel.openDoor(mContext, libDevModel, managerCallback);
                    if (0 != code) {
                        Message message = new Message();
                        message.what = DEFEATED_CODE;
                        handler.sendMessage(message);
                        openStatus = OPEN_STATUS_CONNECTIONLESS;
                    }
                }
            }
        }
    };

    LibInterface.ManagerCallback managerCallback = new LibInterface.ManagerCallback() {

        @Override
        public void setResult(int i, Bundle bundle) {
            if (0 == i) {
                Message message = new Message();
                message.what = SUCCESS_CODE;
                handler.sendMessage(message);
                openStatus = OPEN_STATUS_SUCCESS;
            } else {
                Message message = new Message();
                message.what = DEFEATED_CODE;
                handler.sendMessage(message);
                openStatus = OPEN_STATUS_CONNECTIONLESS;
            }
        }
    };


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS_CODE:
                    if (mView != null) {
                        mView.onUpdateUI(true, timer);
                    }

                    break;
                case DEFEATED_CODE:
                    if (null != clientConnectManage) {
                        clientConnectManage.shutdownClient();
                    }
                    if (mView != null) {
                        mView.onUpdateUI(false, timer);
                    }
                    break;
                case DEFEATED_DATE_CODE:
                    if (null != clientConnectManage) {
                        clientConnectManage.shutdownClient();
                    }
                    if (mView != null) {
                        mView.onUpdateUI(false, timer);
                    }
                    Utils.loge("同步开门记录失败");
                    break;
            }

        }
    };

    private BeaconManager beaconManager;
    private ArrayList<Equipment> equipments;
    private BluetoothAdapter mBtAdapter;

    /**
     * 门禁初始化配置
     */
    public void openDoorZHC() {
        Map<String, String> map = new HashMap<>();
        openDoor.getDataTime(map, this);
        Calendar calendar = Calendar.getInstance();

        String time = String.valueOf(calendar.getTime().getHours());
        if ("1".equals(time)) {
            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(mContext);
            preference.edit().putBoolean("send_state", true).commit();
        }
        equipments = Account.getDevices(mContext);
        openStatus = OPEN_STATUS_NO_EQUIPMENT;
        BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBtAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mContext.startActivityForResult(enableIntent, REQUEST_CODE_BLUETOOTH_ON);
        } else {
            if (mView != null) {
                mView.startDiffuseView();
                beaconManager = new BeaconManager(mContext, mBtAdapter, Account.getDevices(mContext));
                if (null == beaconManager.getEquipments()) {
                    mView.finishActivity();
                }
                beaconManager.setBleFindListener(bleScannerListener);
                beaconManager.startScan();
            }

        }
    }


    private Equipment equipmentFind;
    private BleScannerListener bleScannerListener = new BleScannerListener() {
        @Override
        public void onActionDeviceFound(List<BluetoothDeviceBean> list) {
            if (null != list && 0 != list.size()) {
                beaconManager.cancelScan();
                for (Equipment equipment : equipments) {
                    if (list.get(0).getDeviceMac().equalsIgnoreCase(equipment.getMAC_Addr())) {
                        equipmentFind = equipment;
                        break;
                    }
                }
                openClient(equipmentFind);
            }
        }

        @Override
        public void onBluetoothOpen() {
        }

        @Override
        public void onActionDeviceNotFound() {
            Message message = new Message();
            message.what = DEFEATED_CODE;
            openStatus = OPEN_STATUS_NO_EQUIPMENT;
            handler.sendMessage(message);
        }

        @Override
        public void onActionEndFound() {
        }
    };

    private ClientConnectManage clientConnectManage;
    private String deviceId;

    private void openClient(Equipment equipment) {
        clientConnectManage = new ClientConnectManage(mContext, mBtAdapter
                .getRemoteDevice(equipment.getMAC_Addr()));
        deviceId = equipment.getDeviceId();
        clientConnectManage.setBleConnectListener(bleConnectListener);
        clientConnectManage.openClient();
    }


    private static final int REQUEST_CODE_BLUETOOTH_ON = 1313;
    private boolean openTime = true;
    private String nfcString;
    private String bleString;
    private String passwordString;
    private BleConnectListener bleConnectListener = new BleConnectListener() {
        @Override
        public void onActionConnected() {
            clientConnectManage.readNFCTime();
        }

        @Override
        public void matchFail(int i) {
            if (1 == i) {
            } else {
                Message message = new Message();
                message.what = DEFEATED_CODE;
                handler.sendMessage(message);
                openStatus = OPEN_STATUS_CONNECTIONLESS;
            }
        }

        @Override
        public void onActionDisConnected(int i) {
            if (2 == i) {
                Message message = new Message();
                message.what = DEFEATED_CODE;
                openStatus = OPEN_STATUS_CONNECTIONLESS;
                handler.sendMessage(message);
            }
        }

        @Override
        public void onActionWrite(boolean b) {
            if (b && openTime) {
                openTime = false;
                timer = new Timer();
                timer.schedule(task, 6000, 6000);
            }
        }

        @Override
        public void onActionNotify(ResultBean resultBean) {
            switch (resultBean.getType()) {
                case PKT_DOOR_UNLOCK_S:
                    if (resultBean.isState()) {
                        Message message = new Message();
                        message.what = SUCCESS_CODE;
                        handler.sendMessage(message);
                        openStatus = OPEN_STATUS_SUCCESS;
                    } else {
                        if (mView != null) {
                            mView.openFailHint("没有权限，请重新登录再试");
                        }
                        Message message = new Message();
                        message.what = DEFEATED_CODE;
                        handler.sendMessage(message);
                        openStatus = OPEN_STATUS_SEND_DATA;
                    }
                    clientConnectManage.shutdownClient();
                    break;
                //      获取六个月密码开门次数
                case PKT_PASSWORD_TIME_S:
                    if (resultBean.isState()) {
                        passwordString = resultBean.getDate().split("C")[1];
                        passwordString = passwordString.substring(1, passwordString.length() - 1);
                        clientConnectManage.readBltTime();
                        Utils.loge("获取密码开门记录成功" + passwordString);
                    } else {
                        Message message = new Message();
                        message.what = DEFEATED_DATE_CODE;
                        handler.sendMessage(message);
                        openStatus = OPEN_STATUS_SEND_DATA;
                        Utils.loge("获取密码开门记录失败，消息 :" + resultBean.getDate());
                    }
                    break;

                // 获取六个月蓝牙开门次数
                case PKT_BLT_TIME_S:
                    if (resultBean.isState()) {
                        bleString = resultBean.getDate().split("C")[1];
                        bleString = bleString.substring(1, bleString.length() - 1);
                        clientConnectManage.openDoor(nowTime);
                        Utils.loge("获取蓝牙开门记录成功" + bleString);
                    } else {
                        Message message = new Message();
                        message.what = DEFEATED_DATE_CODE;
                        handler.sendMessage(message);
                        openStatus = OPEN_STATUS_SEND_DATA;
                        Utils.loge("获取蓝牙开门记录失败，消息 :" + resultBean.getDate());
                    }
                    break;

                //获取六个月NFC开门次数
                case PKT_NFC_TIME_S:
                    if (resultBean.isState()) {
                        nfcString = resultBean.getDate().split("C")[1];
                        nfcString = nfcString.substring(1, nfcString.length() - 1);
                        clientConnectManage.readPasswordTime();
                        Utils.loge("获取NFC开门记录失败" + nfcString);
                    } else {
                        Message message = new Message();
                        message.what = DEFEATED_DATE_CODE;
                        handler.sendMessage(message);
                        openStatus = OPEN_STATUS_SEND_DATA;
                        Utils.loge("获取NFC开门记录失败，消息 :" + resultBean.getDate());
                    }
                    break;
            }
            Map<String, String> map = new HashMap<>();

            openDoor.getOpenLogDate(map);
        }
    };

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = DEFEATED_CODE;
            handler.sendMessage(message);
            openStatus = OPEN_STATUS_SEND_DATA;
        }
    };


    private Long nowTime = 0L;

    @Override
    public void onListDateInfo(ListDateInfo info) {
        if (mView != null) {
            nowTime = info.getListDate().get(0).getDateTime();
        }
    }

    @Override
    public void onDateTime(EmptyObjectBean bean) {

    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
        }
    }

    //佳乐   获取二维码
    public void getQrCode() {
        LogUtils.e("32sJ"+"开始请求密码");
        Map map = new HashMap();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        if (openDoor != null)
            openDoor.getQrCode(map, this);
    }

    /**
     *佳乐   获取二维码 成功回调
     * @param info
     */
    @Override
    public void getQrCodeNext(String info) {
        if (mView!=null){
            mView.getQrCodeNext( info);
        }
    }
}
