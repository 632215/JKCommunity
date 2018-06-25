package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.ThemeBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.StartupPageBiz;
import com.jinke.community.service.listener.StartupPageListener;

/**
 * Created by Administrator on 2018/3/29.
 */

public class StartupPageImpl implements StartupPageBiz {
    Context mContext;

    public StartupPageImpl(Context mContext) {
        this.mContext = mContext;
    }
}
