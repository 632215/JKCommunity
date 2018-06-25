package com.jinke.community.service;

import com.jinke.community.service.listener.IPrepaidExpensesListener;

import java.util.Map;

/**
 * Created by root on 17-8-19.
 */

public interface IPrepaidExpensesBiz {

    /**
     * 预存余额列表
     * @param map
     * @param listener
     */
    void getPrePayList(Map<String,String> map,IPrepaidExpensesListener listener);
}
