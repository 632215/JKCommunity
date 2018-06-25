package com.jinke.community.service;

import com.jinke.community.http.main.OnRequestListener;

import java.util.Map;

/**
 * Created by root on 17-8-17.
 */

public interface IPropertyWebBiz {
    void getPostItNoticeDetail(Map<String,String> map, OnRequestListener listener);
}
