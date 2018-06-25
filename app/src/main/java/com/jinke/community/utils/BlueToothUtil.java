package com.jinke.community.utils;

import android.app.Application;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.os.Handler;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;

import java.util.List;
import java.util.UUID;

import www.jinke.com.library.utils.commont.LogUtils;
import www.jinke.com.library.utils.commont.ToastUtils;


/**
 * Created by Administrator on 2018/6/4.
 */

public class BlueToothUtil {
    private Context context;
    private BleManager manager;
    private BleDevice blueDevice = null;//蓝牙
    public final static UUID UUID_NOTIFY =
            UUID.fromString("0000ff01-0000-1000-8000-00805f9b34fb");
    public final static UUID UUID_SERVICE =
            UUID.fromString("0000ff00-0000-1000-8000-00805f9b34fb");
    public final static UUID UUID_WRITE =
            UUID.fromString("0000ff02-0000-1000-8000-00805f9b34fb");
    private BlueToothUtilListener listener;
    private boolean sendDataFlag = false;
    private boolean scanFlag = false;
    private boolean openFlag = false;//门禁开门成功标识

    public BlueToothUtil(Context context, BlueToothUtilListener listener) {
        this.context = context;
        this.listener = listener;
    }

    //初始化蓝牙   设置蓝牙广播名称固定搜索
    public void initBlue(Application application) {
        sendDataFlag = false;
        scanFlag = false;
        openFlag = false;
        if (manager == null)
            manager = BleManager.getInstance();
        manager.init(application);
        manager.disconnectAllDevice();
        manager.enableLog(true)
                .setMaxConnectCount(1)
                .setReConnectCount(1, 5000)
                .setOperateTimeout(6000);

        if (!manager.isSupportBle()) {
            ToastUtils.showShort(context, "不支持ble");
            return;
        }
        if (!manager.isBlueEnable())
            manager.enableBluetooth();
        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                .setDeviceName(true, "Aka")         // 只扫描指定广播名的设备，可选
                .setScanTimeOut(6000)// 扫描超时时间，可选，默认10秒
                .build();
        manager.initScanRule(scanRuleConfig);
        LogUtils.e("32sJ" + "扫描门禁");
        scanDevice();
    }

    //扫描蓝牙
    private void scanDevice() {
        manager.scan(new BleScanCallback() {
            //在开始扫描时启动定时器 4s没有扫到设备就报错
            @Override
            public void onScanStarted(boolean success) {
                if (success) {
                    if (mHandler != null && runnable != null)
                        mHandler.postDelayed(runnable, 6000);
                }
            }

            @Override
            public void onLeScan(BleDevice bleDevice) {
            }

            @Override
            public void onScanning(BleDevice bleDevice) {//定制扫描结果
                LogUtils.e("32sJ" + "搜索到定制Aka设备");
                getConnect(bleDevice);
                if (mHandler != null && runnable != null)
                    mHandler.removeCallbacks(runnable);
            }

            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {//定制扫描结果合集:0没有制定设备  1只有一台设备  2+有多台设备（需要考虑信号强度问题）
//                LogUtils.e("32sJ"+"搜索到Aka设备");
//                manager.cancelScan();
//                if (scanFlag == false && scanResultList != null && scanResultList.size() > 0) {
//                    scanFlag = true;
//                    switch (scanResultList.size()) {
//                        case 1:
//                            getConnect(scanResultList.get(0));
//                            break;
//
//                        default:
//                            int deviceGssi = scanResultList.get(0).getRssi();
//                            int index = 0;
//                            for (int x = 0; x < scanResultList.size() - 1; x++) {
//                                if (deviceGssi < scanResultList.get(x).getRssi()) {
//                                    deviceGssi = scanResultList.get(x).getRssi();
//                                    index = x;
//                                }
//                            }
//                            getConnect(scanResultList.get(index));
//                            break;
//                    }
//                } else if (scanFlag == false && scanResultList != null && scanResultList.size() == 0) {
//                    scanFlag = true;
//                    listener.bTScanFail();
//                }
            }
        });
    }

    //连接蓝牙
    private void getConnect(final BleDevice bleDevice) {
        manager.connect(bleDevice, new BleGattCallback() {
            @Override
            public void onStartConnect() {
            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                listener.bTFail("佳乐蓝牙门禁连接失败！");
            }

            @Override//连接成功并发现服务
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                LogUtils.e("32sJ" + "门禁连接成功");
                manager.cancelScan();
                blueDevice = bleDevice;
                listener.bTConnectSuccess();
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
            }
        });
    }

    //是否连接到设备
    public boolean getDevice() {
        if (blueDevice == null) {
            return false;
        } else {
            return true;
        }
    }

    //发送密码开门
    public void sendData(final byte[] data) {
        openIndicate();
        if (blueDevice != null && sendDataFlag == false) {
            LogUtils.e("32sJ" + "发送数据长度：" + data.length);
            sendDataFlag = true;
            try {
                Thread.sleep(100);
                manager.write(
                        blueDevice,
                        UUID_SERVICE.toString(),
                        UUID_WRITE.toString(),
                        data,
                        data.length > 20 ? true : false,
                        new BleWriteCallback() {
                            @Override
                            public void onWriteSuccess(int current, int total, byte[] justWrite) {
                                // 发送数据到设备成功（分包发送的情况下，可以通过方法中返回的参数可以查看发送进度）
                                bleWriteFlag = true;
                            }

                            @Override
                            public void onWriteFailure(BleException exception) {
                                // 发送数据到设备失败
                                if (bleWriteFlag) {
                                    bleWriteFlag = false;
                                    listener.bTFail("佳乐蓝牙门禁写入数据错误，请重试！");
                                } else {
                                    sendData(data);
                                }
                            }
                        });
            } catch (Exception e) {
                ToastUtils.showShort(context, "线程错误！");
                e.printStackTrace();
            }
        }
    }

    private boolean bleWriteFlag = true;

    private void openIndicate() {
        manager.notify(
                blueDevice,
                UUID_SERVICE.toString(),
                UUID_NOTIFY.toString(),
                new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                    }

                    //                            "Check Sum error\n";密码不完整
//                            "Door already opened\n";门禁已开
//                            "Found BlackList\n";黑名单
//                            "Invalid Entrance\n";此单元门暂无授权
//                            "repeat Entry\n";尝试重复开门一次。
//                            "Check succeed\n";开门成功
                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        // 打开通知后，设备发过来的数据将在这里出现
                        String result = new String(data);
                        LogUtils.e("32sJ蓝牙返回数据：" + result);
                        if (result.contains("repeat Entry") && reTryFlag == 0) {
                            reTryFlag++;
                            sendDataFlag = false;
                            listener.bTConnectSuccess();
                            return;
                        }

                        if (openFlag == false) {
                            openFlag = true;
                            manager.stopNotify(blueDevice, UUID_SERVICE.toString(), UUID_NOTIFY.toString());
                            if (result.contains("Check succeed")) {
                                LogUtils.e("32sJ" + "门禁开门成功");
                                listener.bTOpenedSuccess();
                            } else if (result.contains("Check Sum error")) {
                                listener.bTFail("佳乐蓝牙门禁密码错误！");
                            } else if (result.contains("Door already opened")) {
                                listener.bTFail("佳乐蓝牙门禁已开！");
                            } else if (result.contains("Found BlackList")) {
                                listener.bTFail("佳乐蓝牙门禁暂无权限");
                            } else if (result.contains("Invalid Entrance")) {
                                listener.bTFail("佳乐蓝牙门禁此单元门暂无授权");
                            } else if (result.contains("repeat Entry")) {
                                listener.bTFail("佳乐蓝牙门禁密码错误，请重试！");
                            } else {
                                listener.bTFail("开门失败！");
                            }
                        }
                        manager.disconnectAllDevice();
                    }
                });
    }

    private int reTryFlag = 0;
    Handler mHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (blueDevice == null) {
                manager.cancelScan();
                destoryConnect();
                listener.bTFail("蓝牙扫描失败，请重试！");
            }
        }
    };

    public void destoryConnect() {
        manager.disconnect(blueDevice);
    }

    public interface BlueToothUtilListener {
        void bTFail(String reason);

        void bTOpenedSuccess();

        void bTConnectSuccess();
    }
}
