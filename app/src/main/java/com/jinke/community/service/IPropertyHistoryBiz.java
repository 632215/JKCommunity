package com.jinke.community.service;

import com.jinke.community.service.listener.IPropertyHistoryListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 17-8-11.
 */

public interface IPropertyHistoryBiz {
    /**
     * 报事列表
     *  @param map
     * @param listener
     */
    void getKeepPostItList(HashMap map, IPropertyHistoryListener listener);
}
