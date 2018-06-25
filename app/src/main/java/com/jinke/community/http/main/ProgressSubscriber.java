package com.jinke.community.http.main;

import android.content.Context;
import android.content.Intent;

import com.jinke.community.bean.HttpResult;
import com.jinke.community.ui.activity.base.ErrorActivity;
import com.jinke.community.ui.activity.base.LoginActivity;
import com.jinke.community.utils.AppManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import cn.jpush.android.api.JPushInterface;
import rx.Subscriber;
import www.jinke.com.library.utils.commont.LogUtils;
import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {
    private SubscriberOnNextListener mSubscriberOnNextListener;
    private Context context;
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    private static final int SUCCESS = 200;
    private static final int LOGIN_FAILURE = 1000;
    private static final int SYSTEM_DEAL = 1001;
    private static final int INTERFACE_ABNORMAL = 4001;
    private static final int INTERFACE_ALL_ABNORMAL = 4023;
    private static final int INTERFACE_VEHICLE_ABNORMAL = 4024;

    private static boolean LOGIN_E = false;

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
    }

    @Override
    public void onStart() {
//        showProgressDialog();
    }

    @Override
    public void onCompleted() {
//        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.i("errorMsg---" + e.getLocalizedMessage());
        if (e instanceof SocketTimeoutException) {
            mSubscriberOnNextListener.onError("404", "服务器忙，请稍后重试。");
        } else if (e instanceof ConnectException) {
            mSubscriberOnNextListener.onError("404", "服务器忙，请稍后重试。");
        } else if (e instanceof UnknownHostException) {
            mSubscriberOnNextListener.onError("404", "请检查您的网络连接！");
        } else if (e instanceof UnknownServiceException) {
            mSubscriberOnNextListener.onError("404", "服务器忙，请稍后重试。");
        } else {
            mSubscriberOnNextListener.onError("404", e.getLocalizedMessage());
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        HttpResult httpResult = (HttpResult) t;
        if (mSubscriberOnNextListener != null) {
            switch (httpResult.getErrcode()) {
                case SUCCESS:
                    mSubscriberOnNextListener.onNext(httpResult.getData());
                    LOGIN_E = false;
                    break;

                case SYSTEM_DEAL://出现系统维护
                    LOGIN_E = false;
                    mSubscriberOnNextListener.onError(String.valueOf(httpResult.getErrcode()), "");
                    if (context != null && !StringUtils.equals("MainActivity", context.getClass().getSimpleName())) {
                        context.startActivity(new Intent(context, ErrorActivity.class).putExtra("error", httpResult.getErrmsg()));
                        AppManager.finishCurrentActivity();
                    }
                    break;

                case LOGIN_FAILURE:
                    if (!LOGIN_E) {
                        ToastUtils.showLong(context, "登录失效，请重新登录！");
                        JPushInterface.deleteAlias(context, 1);//删除极光推送的Ali
                        context.startActivity(new Intent(context, LoginActivity.class));
                        AppManager.finishCurrentActivity();
                        LOGIN_E = true;
                    }
                    break;

                case INTERFACE_ABNORMAL:
                case INTERFACE_ALL_ABNORMAL:
                    LOGIN_E = false;
                    mSubscriberOnNextListener.onError(String.valueOf(httpResult.getErrcode()), "服务器忙，请稍后重试。");
                    break;

                case INTERFACE_VEHICLE_ABNORMAL:
                    LOGIN_E = false;
                    mSubscriberOnNextListener.onError(String.valueOf(httpResult.getErrcode()), httpResult.getErrmsg());
                    break;

                default:
                    LOGIN_E = false;
                    mSubscriberOnNextListener.onError(String.valueOf(httpResult.getErrcode()), httpResult.getErrmsg());
                    break;
            }
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}