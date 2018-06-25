package com.jinke.community.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.WithHoldOpenBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.payment.alipay.PayResult;
import com.jinke.community.service.WithHoldOpenBiz;
import com.jinke.community.service.impl.WithHoldOpenImpl;
import com.jinke.community.service.listener.WithHoldOpenListener;
import com.jinke.community.view.WithHoldOpenView;

import java.net.URISyntaxException;
import java.util.Map;

import www.jinke.com.library.utils.commont.EncodeUtils;
import www.jinke.com.library.utils.commont.LogUtils;

/**
 * Created by Administrator on 2017/11/23.
 */

public class WithHoldOpenPresenter extends BasePresenter<WithHoldOpenView> implements WithHoldOpenListener {
    private Context mContext;
    private WithHoldOpenBiz mBiz;
    private WithHoldOpenBean info;

    public WithHoldOpenPresenter(Context mContext) {
        this.mContext = mContext;
        mBiz = new WithHoldOpenImpl(mContext);
    }

    /**
     * 开通代扣
     *
     * @param map
     */
    public void withHoldOpen(Map map) {
        mBiz.withHoldOpen(map, this);
    }

    @Override
    public void onErrorMsg(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
        }
    }

    @Override
    public void withHoldOpenNext(WithHoldOpenBean info) {
        if (info != null && info.getApp_alipayDaiKou() != null) {
            Uri uri = Uri.parse("alipays://platformapi/startapp?appId=20000067&url=" + EncodeUtils.urlEncode(info.getApp_alipayDaiKou()));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            mContext.startActivity(intent);
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
                        PayTask alipay = new PayTask((Activity) mContext);
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
                        if (mView != null && info != null) {
                            mView.showMsg("支付成功");
                            mView.payResult(1);
                        }
                    } else {
                        if (mView != null && info != null) {
                            mView.showMsg("支付失败");
                            mView.payResult(0);
                        }
                    }
                    break;
                }
            }
        }

    };
}
