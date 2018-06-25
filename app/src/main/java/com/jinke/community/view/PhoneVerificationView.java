package com.jinke.community.view;

import com.jinke.community.bean.RegisterLoginBean;

/**
 * Created by root on 17-8-1.
 */

public interface PhoneVerificationView {
    void showLoading();

    void hideLoading();

    void finishActivity();

    void showMsg(String msg);

    void getRegisterDataNext(RegisterLoginBean info);
}
