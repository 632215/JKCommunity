package com.jinke.community.service;

import com.jinke.community.service.listener.IBrokenDetailsListener;

import java.util.Map;

/**
 * Created by root on 17-8-11.
 */

public interface IBrokenDetailsBiz {

    void getBrokeNewsDetail(Map<String,String> map,IBrokenDetailsListener listener);

    void getPostItDetail(Map<String,String> map,IBrokenDetailsListener listener);
}
