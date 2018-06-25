package com.jinke.community.ui.activity.control;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.example.jni.TestJni;
import com.intelligoo.sdk.LibDevModel;
import com.intelligoo.sdk.LibInterface;
import com.intelligoo.sdk.ScanCallBackSort;
import com.jinke.community.R;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BaseActivity;
import com.jinke.community.bean.ControlBean;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.ListDateInfo;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.http.door.DriveHttpMethods;
import com.jinke.community.http.door.DriveSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.presenter.OpenDoorPresenter;
import com.jinke.community.ui.widget.DiffuseView;
import com.jinke.community.utils.AnalyUtils;
import com.jinke.community.utils.BlueToothUtil;
import com.jinke.community.utils.SharedPreferencesUtils;
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
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import www.jinke.com.library.utils.commont.LogUtils;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

import static com.jkapi.entrancelibrary.Constant.PKT_BLT_TIME_S;
import static com.jkapi.entrancelibrary.Constant.PKT_DOOR_UNLOCK_S;
import static com.jkapi.entrancelibrary.Constant.PKT_NFC_TIME_S;
import static com.jkapi.entrancelibrary.Constant.PKT_PASSWORD_TIME_S;

public class OpenDoorActivity extends BaseActivity<IOpenDoorView, OpenDoorPresenter>
        implements BlueToothUtil.BlueToothUtilListener, IOpenDoorView {
    @Bind(R.id.apk_page_body_wait)
    View bodyWait;
    @Bind(R.id.apk_page_body_success)
    View bodySuccess;
    @Bind(R.id.apk_page_body_defeated)
    View bodyDefeated;
    @Bind(R.id.diffuseView)
    DiffuseView diffuseView;
    @Bind(R.id.open_fail_hint)
    TextView openFailHint;
    @Bind(R.id.dome_shut_button)
    TextView domeShutButton;

    private boolean isOpen = false;
    private final static String OPEN_STATUS_SUCCESS = "1";//开门成功
    private final static String OPEN_STATUS_CONNECTIONLESS = "2";//开门失败 连接异常
    private final static String OPEN_STATUS_NO_EQUIPMENT = "3";//开门失败 未扫描到设备
    private final static String OPEN_STATUS_SEND_DATA = "4";//开门失败 发送数据失败
    private final static int SUCCESS_CODE = 9211;//开门成功
    private final static int DEFEATED_CODE = 9215;//开门失败
    private final static int DEFEATED_DATE_CODE = 9212;//同步开门记录失败
    private String openStatus;

    private BeaconManager beaconManager;
    private ClientConnectManage clientConnectManage;
    /* 取得默认的蓝牙适配器 */
    private BluetoothAdapter mBtAdapter;
    private String nfcString;
    private String bleString;
    private String passwordString;
    private String deviceId;
    private String failedReason = "";

    private ArrayList<Equipment> equipments;
    private Equipment equipmentFind;

    private Long nowTime = 0L;

    //佳乐
    private BlueToothUtil blueToothUtil;
    private String qrCodePassWord = "";

    @Override
    public OpenDoorPresenter initPresenter() {
        return new OpenDoorPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_open_door;
    }

    protected void initView() {
        setTitle("手机开门");
        showBackwardView("", true);
        diffuseView.start();
        domeShutButton.setOnClickListener(onClickListener);
        blueToothUtil = new BlueToothUtil(this, this);
//        presenter.getQrCode();//获取佳乐门禁密码（现目前全部使用离线蓝牙开门）
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isOpen == false)
            openDoorXimo();
    }

    private LibDevModel libDevModel;

    private void openDoorXimo() {
        if (null != Account.getDevices(this) && Account.getDevices(this).size() > 0) {
            isOpen = true;
            libDevModel = new LibDevModel();
            libDevModel.cardno = "-1";
            int code = LibDevModel.scanDeviceSort(OpenDoorActivity.this, true, 2000, showAllScanCallback);
        } else {
            failedReason = "当前房屋没有绑定门禁设备！";
            updateUI(false);
//            if (isOpen == false) {
//                isOpen = true;
//                blueToothUtil.initBlue(MyApplication.getInstance());
//            }
        }
    }

    private ScanCallBackSort showAllScanCallback = new ScanCallBackSort() {
        @Override
        public void onScanResult(ArrayList<Map<String, Integer>> arrayList) {
            boolean isBreak = false;
            for (Map<String, Integer> map : arrayList) {
                for (Equipment dateBean : Account.getDevices(OpenDoorActivity.this)) {
                    if (null != map.get(dateBean.getDeviceNum())) {
                        LibDevModel.stopScanDevice();
                        libDevModel.devSn = dateBean.getDeviceNum();
                        if (dateBean.getDeviceType() != null) {
                            libDevModel.devType = Integer.parseInt(dateBean.getDeviceType());
                        }
                        libDevModel.devMac = dateBean.getMAC_Addr();
                        libDevModel.eKey = dateBean.getDeviceSecret();
                        deviceId = dateBean.getDeviceId();
                        int code = LibDevModel.openDoor(OpenDoorActivity.this, libDevModel, managerCallback);
                        if (0 != code) {
                            Message message = new Message();
                            message.what = DEFEATED_CODE;
                            handler.sendMessage(message);
                            openStatus = OPEN_STATUS_CONNECTIONLESS;
                        }
                        isBreak = true;
                        break;
                    }
                }
                if (isBreak) {
                    break;
                }
            }
            if (!isBreak) {
                Message message = new Message();
                message.what = DEFEATED_CODE;
                handler.sendMessage(message);
                openStatus = OPEN_STATUS_NO_EQUIPMENT;
            }
        }

        @Override
        public void onScanResultAtOnce(String s, int i) {

        }
    };

    private LibInterface.ManagerCallback managerCallback = new LibInterface.ManagerCallback() {
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

    @Override
    protected void onStop() {
        diffuseView.stop();
        if (null != beaconManager)
            beaconManager.cancelScan();
        if (null != clientConnectManage)
            clientConnectManage.shutdownClient();
        if (null != timer) {
            timer.cancel();
            timer = null;
        }
        super.onStop();
    }

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

    private boolean openTime = true;
    /**
     * 自定义的打开 Bluetooth 的请求码，与 onActivityResult 中返回的 requestCode 匹配。
     */
    private static final int REQUEST_CODE_BLUETOOTH_ON = 1313;
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
                        if (null != openFailHint) {
                            openFailHint.setText("没有权限，请重新登录再试");
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
        }
    };

    /**
     * 更新UI
     *
     * @param b true 成功 false失败
     */
    private void updateUI(boolean b) {
        if (null != timer) {
            timer.cancel();
            timer = null;
        }
        if (b) {
            AnalyUtils.addAnaly(10014);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (null != bodyWait && null != bodySuccess && null != bodyDefeated) {
                        bodyWait.setVisibility(View.GONE);
                        bodySuccess.setVisibility(View.VISIBLE);
                        bodyDefeated.setVisibility(View.GONE);
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                //休眠2000毫秒 回到home
                                try {
                                    Thread.sleep(2000);
                                    finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (null != bodyWait && null != bodySuccess && null != bodyDefeated) {
                        bodyWait.setVisibility(View.GONE);
                        bodySuccess.setVisibility(View.GONE);
                        bodyDefeated.setVisibility(View.VISIBLE);
                        openFailHint.setText(failedReason);
                    }
                }
            });
        }

    }

    /**
     * @param equipment
     */
    private void openClient(Equipment equipment) {
        clientConnectManage = new ClientConnectManage(OpenDoorActivity.this, mBtAdapter
                .getRemoteDevice(equipment.getMAC_Addr()));
        deviceId = equipment.getDeviceId();
        clientConnectManage.setBleConnectListener(bleConnectListener);
        clientConnectManage.openClient();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS_CODE:
                    updateUI(true);
                    break;

                case DEFEATED_CODE:
                    if (null != clientConnectManage) {
                        clientConnectManage.shutdownClient();
                    }
                    failedReason = "开门失败!";
                    updateUI(false);
//                    blueToothUtil.initBlue(MyApplication.getInstance());
                    break;

                case DEFEATED_DATE_CODE:
                    if (null != clientConnectManage) {
                        clientConnectManage.shutdownClient();
                    }
                    updateUI(false);
                    break;
            }
            SubscriberOnNextListener nextListener = new SubscriberOnNextListener<EmptyObjectBean>() {
                @Override
                public void onNext(EmptyObjectBean infos) {
                }

                @Override
                public void onError(String Code, String Msg) {
                    ToastUtils.showShort(OpenDoorActivity.this, Msg);

                }
            };
            ControlBean bean = MyApplication.getInstance().getControlInfo();
            Map<String, String> map = new HashMap<>();
            map.put("tq_uId", bean.getTqUid());
            if (null != deviceId) {
                map.put("deviceId", deviceId);
            }
            map.put("openStatus", openStatus);
            DriveHttpMethods.getInstance(true).getOpenLogDate(new DriveSubscriber<HttpResult<EmptyObjectBean>>(nextListener, OpenDoorActivity.this), map);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_BLUETOOTH_ON) {
            switch (resultCode) {
                // 点击确认按钮
                case Activity.RESULT_OK: {
                    mBtAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (!mBtAdapter.isEnabled()) {
                        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableIntent, REQUEST_CODE_BLUETOOTH_ON);
                    } else {
                        diffuseView.start();
                        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
                        beaconManager = new BeaconManager(this, mBtAdapter, Account.getDevices(this));
                        if (null == beaconManager.getEquipments()) {
                            finish();
                        }
                        beaconManager.setBleFindListener(bleScannerListener);
                        beaconManager.startScan();
                    }
                }
                break;

                // 点击取消按钮或点击返回键
                case Activity.RESULT_CANCELED: {
                    Message message = new Message();
                    message.what = DEFEATED_CODE;
                    openStatus = OPEN_STATUS_NO_EQUIPMENT;
                    handler.sendMessage(message);
                    ToastUtils.showShort(OpenDoorActivity.this, "没有打开蓝牙，请打开蓝牙再试");
                    finish();
                }
                break;
                default:
                    break;
            }
        }
    }

    private void getDate() {
        SubscriberOnNextListener subscriberOnNextListener = new SubscriberOnNextListener<ListDateInfo>() {
            @Override
            public void onNext(ListDateInfo infos) {
                nowTime = infos.getListDate().get(0).getDateTime();
            }

            @Override
            public void onError(String Code, String Msg) {
                ToastUtils.showShort(OpenDoorActivity.this, Msg);
            }
        };
        Map<String, String> hashMap = new HashMap<>();
        DriveHttpMethods.getInstance().getDateTimeData(new DriveSubscriber<HttpResult<ListDateInfo>>(subscriberOnNextListener, OpenDoorActivity.this), hashMap);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dome_shut_button:
                    finish();
                    break;
            }
        }
    };


    /**
     * BlueToothUtil 蓝牙  扫描失败
     */
    @Override
    public void bTFail(String reason) {
        failedReason = reason;
        updateUI(false);
    }

    /**
     * BlueToothUtil 蓝牙  开门成功
     */
    @Override
    public void bTOpenedSuccess() {
        blueToothUtil.destoryConnect();
        updateUI(true);
    }

    /**
     * BlueToothUtil蓝牙连接成功 回调
     */
    @Override
    public void bTConnectSuccess() {
        HouseListBean.ListBean defaultHouseBean = MyApplication.getInstance().getDefaultHouse();
        if (defaultHouseBean == null || defaultHouseBean.getUid() == null) {
            bTFail("默认房屋信息有误");
        }
        HouseListBean.ListBean.Uid uidBean = defaultHouseBean.getUid();
        if (StringUtils.isEmpty(uidBean.getCno()) ||
                StringUtils.isEmpty(uidBean.getAno()) ||
                StringUtils.isEmpty(uidBean.getBno()) ||
                StringUtils.isEmpty(uidBean.getHno()) ||
                StringUtils.isEmpty(uidBean.getUid())) {
            bTFail("默认房屋信息有误");
        } else {
            String cno = uidBean.getCno();//小区编号
            if (cno.length() < 8) {
                for (int x = cno.length(); x < 8; x++)
                    cno += 0;
            }
            byte[] cnokey = cno.getBytes();
            int ano = Integer.parseInt(uidBean.getAno());//楼栋号
            int bno = Integer.parseInt(uidBean.getBno());//单元号
            int room = Integer.parseInt(uidBean.getHno());//房间号
            int uid = Integer.parseInt(uidBean.getUid());//用户id
            int count = SharedPreferencesUtils.getDoorCounter(this);
            LogUtils.e("32sJ" + "开始计算密码:" + "ano:" + ano + "---bno:" + bno + "---room:" + room + "---uid:" + uid + "---count:" + count);
            byte[] result = TestJni.encoder(ano, bno, room, (byte) uid, count, cnokey);
            blueToothUtil.sendData(result);
//        if (!StringUtils.isEmpty(qrCodePassWord) && blueToothUtil.getDevice())//网络开门
//            blueToothUtil.sendData(qrCodePassWord);
        }
    }

    /**
     * 获取佳乐 二维码成功 回调
     *
     * @param info
     */
    @Override
    public void getQrCodeNext(String info) {
        qrCodePassWord = info;
        if (!StringUtils.isEmpty(qrCodePassWord) && blueToothUtil.getDevice())
            blueToothUtil.sendData(qrCodePassWord.getBytes());
    }

    @Override
    public void showMsg(String msg) {
    }

    @Override
    public void startDiffuseView() {
    }

    @Override
    public void finishActivity() {
    }

    @Override
    public void openFailHint(String content) {
    }

    @Override
    public void onUpdateUI(Boolean isTrue, Timer timer) {
    }


    @Override
    protected void onResume() {
        super.onResume();
        AnalyUtils.addAnaly(10012);
        StatService.onResume(OpenDoorActivity.this);
        StatService.trackBeginPage(OpenDoorActivity.this, "门禁－蓝牙开门");
        AnalyUtils.setBAnalyResume(this, "门禁－蓝牙开门");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(OpenDoorActivity.this);
        StatService.trackEndPage(OpenDoorActivity.this, "门禁－蓝牙开门");
        AnalyUtils.setBAnalyPause(this, "门禁－蓝牙开门");
    }
}
