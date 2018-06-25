package com.jinke.community.service;

import com.jinke.community.http.main.OnRequestListener;

import java.util.Map;

/**
 * Created by root on 17-8-25.
 */

public interface IDynamicBiz {
    void getDynamicData(Map<String,String> map, OnRequestListener listener);
}
