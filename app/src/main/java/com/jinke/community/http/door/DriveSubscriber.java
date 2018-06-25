package com.jinke.community.http.door;

import android.content.Context;
import android.widget.Toast;

import com.jinke.community.bean.HttpResult;
import com.jinke.community.http.main.ProgressCancelListener;
import com.jinke.community.http.main.SubscriberOnNextListener;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;
import www.jinke.com.library.utils.commont.LogUtils;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by liukun on 16/3/10.
 */
public class DriveSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private Context context;
    private boolean isShow = true;

    public DriveSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
    }


    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
            LogUtils.i("error:" + e.getMessage());
        }
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onError("404", "网络中断，请检查您的网络状态");
        }

    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *java.lang.NullPointerException: Attempt to invoke virtual method 'java.util.List com.jinke.community.bean.PasswordInfo.getListDate()' on a null object reference
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        HttpResult httpResult = (HttpResult) t;
        if (mSubscriberOnNextListener != null) {
            if (httpResult.getErrcode() == 1) {
                mSubscriberOnNextListener.onNext(httpResult.getData());
            } else {
                mSubscriberOnNextListener.onError(String.valueOf(httpResult.getErrcode()), httpResult.getErrmsg());
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