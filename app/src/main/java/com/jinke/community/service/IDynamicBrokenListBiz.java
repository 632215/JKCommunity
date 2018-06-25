package com.jinke.community.service;

import com.jinke.community.service.listener.IDynamicBrokenListener;

import java.util.Map;

/**
 * Created by root on 17-8-14.
 */

public interface IDynamicBrokenListBiz {
    void getBrokeNewsNoticeList(Map<String,String> map, IDynamicBrokenListener listener);

    void getPostItNoticeList(Map<String,String> map, IDynamicBrokenListener listener);
}
