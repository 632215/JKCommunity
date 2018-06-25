package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.PrepaidExpensesBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.IPrepaidExpensesBiz;
import com.jinke.community.service.listener.IPrepaidExpensesListener;

import java.util.Map;

/**
 * Created by root on 17-8-19.
 */

public class PrepaidExpensesImpl implements IPrepaidExpensesBiz {
    Context mContext;

    public PrepaidExpensesImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getPrePayList(Map<String, String> map, final IPrepaidExpensesListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<PrepaidExpensesBean>() {
            @Override
            public void onNext(PrepaidExpensesBean bean) {
                listener.onPrePayList(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getPrePayList(new ProgressSubscriber<HttpResult<PrepaidExpensesBean>>(onNextListener, mContext), map, MyApplication.creatSign(map));
    }
}
