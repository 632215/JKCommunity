package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.PrepaidExpensesBean;
import com.jinke.community.service.IPrepaidExpensesBiz;
import com.jinke.community.service.impl.PrepaidExpensesImpl;
import com.jinke.community.service.listener.IPrepaidExpensesListener;
import com.jinke.community.view.PrepaidExpensesView;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/2.
 */

public class PrepaidExpensesPresenter extends BasePresenter<PrepaidExpensesView> implements IPrepaidExpensesListener {
    private IPrepaidExpensesBiz expensesBiz;

    public PrepaidExpensesPresenter(Context mContext) {
        expensesBiz = new PrepaidExpensesImpl(mContext);
    }

    public void getPrePayList(Map<String, String> map) {
        expensesBiz.getPrePayList(map, this);

    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
        }

    }

    @Override
    public void onPrePayList(PrepaidExpensesBean bean) {
        if (mView != null) {
            mView.onPrePayList(bean);
        }

    }
}
