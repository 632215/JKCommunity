package com.jinke.community.service;

import com.jinke.community.service.listener.IRequestListener;
import com.jinke.community.service.listener.PropertyNewsListener;
import com.lidroid.xutils.http.RequestParams;

import java.util.Map;

/**
 * Created by root on 17-8-11.
 */

public interface IPropertyNewsBiz {
//    void getAddPostIt(Map<String, String> map, PropertyNewsListener listener);

    void getHouseList(Map<String, String> map, PropertyNewsListener listener);

    void addPostIt(RequestParams params,PropertyNewsListener listener);
}
