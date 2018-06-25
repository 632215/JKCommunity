package com.jinke.community.service;

import com.jinke.community.service.listener.IWorkingListener;

import java.util.Map;

/**
 * Created by root on 17-8-17.
 */

public interface IWorkingBiz {
    void getWorkingSort(Map<String,String> map, IWorkingListener listener);
    void getUpDate(Map<String,String> map,IWorkingListener listener);
}
