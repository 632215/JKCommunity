package com.jinke.community.view;

import com.jinke.community.bean.PrepaidExpensesBean;

/**
 * Created by Administrator on 2017/8/2.
 */

public interface PrepaidExpensesView {
    void onPrePayList(PrepaidExpensesBean bean);
    void showMsg(String msg);
}
