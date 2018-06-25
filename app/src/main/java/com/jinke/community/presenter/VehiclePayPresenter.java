package com.jinke.community.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.PayBean;
import com.jinke.community.payment.alipay.PayResult;
import com.jinke.community.service.VehiclePayBiz;
import com.jinke.community.service.impl.VehiclePayImpl;
import com.jinke.community.service.listener.VehiclePayListener;
import com.jinke.community.view.VehiclePayView;

import java.util.Map;

import www.jinke.com.library.utils.commont.LogUtils;

/**
 * Created by Administrator on 2017/10/23.
 */

public class VehiclePayPresenter extends BasePresenter<VehiclePayView> implements VehiclePayListener {
    Activity mContext;
    VehiclePayBiz biz;
    private String payType;
    private PayBean payBean = null;

    public VehiclePayPresenter(Activity mContext) {
        this.mContext = mContext;
        biz = new VehiclePayImpl(mContext);
    }

    public void getPayment(Map<String, String> map) {
        if (mView != null) {
            biz.getPayment(map, this);
            mView.showLoading();
        }
    }

    @Override
    public void getPaymentNext(PayBean bean) {
        if (mView != null) {
            mView.hideLoading();
            this.payBean = bean;
            Message message = new Message();
            message.what = 100;
            message.obj = bean.getApp_alipay();
            handler.sendMessage(message);
        }
    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
            mView.hideLoading();
        }
    }

    private static final int SDK_PAY_FLAG = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final String orderInfo = (String) msg.obj;
            if (orderInfo != null) {
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(mContext);
                        LogUtils.i("orderInfo------" + orderInfo);
                        Map<String, String> result = alipay.payV2(orderInfo, true);
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (android.text.TextUtils.equals(resultStatus, "9000")) {
                        if (mView != null && payBean != null) {
                            mView.showMsg("支付成功");
                            payBean.setState("1");
                            mView.payResult(payBean);
                        }
                    } else {
                        if (mView != null && payBean != null) {
                            mView.showMsg("支付失败");
                            payBean.setState("0");
                            mView.payResult(payBean);
                        }
                    }
                    break;
                }
            }
        }

    };
}
