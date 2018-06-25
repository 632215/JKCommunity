package com.jinke.community.service;

import com.jinke.community.service.listener.MyMeansListener;
import com.lidroid.xutils.http.RequestParams;

/**
 * Created by root on 17-8-15.
 */

public interface MyMeansBiz {
    void AlterMean(RequestParams params, MyMeansListener listener);
}
