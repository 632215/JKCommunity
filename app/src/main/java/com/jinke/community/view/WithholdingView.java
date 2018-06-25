package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.PayBean;
import com.jinke.community.bean.PrepaidExpensesBean;
import com.jinke.community.bean.WithholdingBean;

/**
 * Created by Administrator on 2017/8/4.
 */

public interface WithholdingView {


    void showMsg(String msg);

    void payResult(PayBean payBean);

    void showLoading();

    void hideLoading();

    void onDoPay(PrepaidExpensesBean bean);

    void getSignStateNext(WithholdingBean info);

    void finishActivity();
}
