package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.CustomerPhoneBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.SettingBiz;
import com.jinke.community.service.listener.SettingListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/20.
 */

public class SettingImpl implements SettingBiz {
    private Context mContext;

    public SettingImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getCustomerPhone(Map<String, String> map, final SettingListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<CustomerPhoneBean>() {
            @Override
            public void onNext(CustomerPhoneBean bean) {
                listener.getCustomerPhoneNext(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getCustomerPhone(new ProgressSubscriber<HttpResult<CustomerPhoneBean>>(onNextListener, mContext), map, MyApplication.creatSign(map));

    }
}
