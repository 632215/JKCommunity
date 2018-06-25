package com.jinke.community.service.listener;

import com.jinke.community.bean.PrepaidExpensesBean;

/**
 * Created by root on 17-8-19.
 */

public interface IPrepaidExpensesListener {
    void onError(String code, String msg);

    void onPrePayList(PrepaidExpensesBean bean);
}
