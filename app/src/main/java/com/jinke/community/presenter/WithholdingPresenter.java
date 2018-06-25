package com.jinke.community.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.PayBean;
import com.jinke.community.bean.WXPayBean;
import com.jinke.community.bean.WithholdingBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.payment.alipay.PayResult;
import com.jinke.community.service.IWithholdingBiz;
import com.jinke.community.service.impl.WithholdingImpl;
import com.jinke.community.service.listener.IWithholdingListener;
import com.jinke.community.view.WithholdingView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import www.jinke.com.library.utils.commont.LogUtils;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/8/4.
 */

public class WithholdingPresenter extends BasePresenter<WithholdingView> implements IWithholdingListener {
    Activity mContext;
    IWithholdingBiz biz;
    private IWXAPI api;
    private PayBean payBean = null;

    public WithholdingPresenter(Activity mContext) {
        this.mContext = mContext;
        biz = new WithholdingImpl(mContext);
    }

    private String payType;//支付方式
    private String payMode;//签约与否

    public void getDoPayment(Map<String, String> map) { //payMode决定代扣 pay_and_sign代扣   pay支付
        if (mView != null) {                            //payType 决定微信或者支付宝  wxpay微信   alipay支付宝
            payType = map.get("payType");
            payMode = map.get("payMode");
            biz.getDoPayment(map, this);
            mView.showLoading();
        }
    }

    public void getDoPrePay(Map<String, String> map) {
        if (mView != null) {
            payType = map.get("payType");
            biz.getDoPay(map, this);
            mView.showLoading();
        }
    }

    /**
     * 缴费 成功回调
     *
     * @param bean
     */
    @Override
    public void onDoPayMent(PayBean bean) {
        if (mView != null) {
            this.payBean = bean;
            mView.hideLoading();
            if (StringUtils.equals("alipay", payType)) {
                callAliPay(bean);
            } else if (StringUtils.equals("wxpay", payType)) {
                callWxPay(bean);
            }
        }
    }

    //唤起微信支付
    private void callWxPay(PayBean bean) {
        try {
            WXPayBean wxPayBean = new Gson().fromJson(bean.getApp_alipay(), WXPayBean.class);
            PayReq request = new PayReq();
            request.appId = wxPayBean.getAppid();
            request.partnerId = wxPayBean.getPartnerid();
            request.prepayId = wxPayBean.getPrepayid();
            request.packageValue = wxPayBean.getPackageValue();
            request.nonceStr = wxPayBean.getNoncestr();
            request.timeStamp = wxPayBean.getTimestamp() + "";
            request.sign = wxPayBean.getSign();
            if (api == null) {
                api = WXAPIFactory.createWXAPI(mContext, null, false);
                api.registerApp(AppConfig.WEIXIN_APPID);
            }
            api.sendReq(request);
        } catch (Exception e) {
            LogUtils.e("32s:" + e);
            ToastUtils.showShort(mContext, "数据异常！");
            return;
        }
    }

    //唤起支付宝支付
    private void callAliPay(PayBean bean) {
        Message message = new Message();
        message.what = 100;
        message.obj = bean.getApp_alipay();
        handler.sendMessage(message);
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

    /**
     * 查询签约状态
     *
     * @param houseId
     */
    public void getSignState(String houseId) {
        Map map = new HashMap();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("houseId", houseId);
        biz.getSignState(map, this);
    }

    @Override
    public void getSignStateNext(WithholdingBean info) {
        if (mView != null) {
            mView.getSignStateNext(info);
        }
    }
}



