package com.jinke.community.view;

/**
 * Created by root on 17-8-2.
 */

public interface AddHouseView {
    void showDialog();

    void hideDialog();

    void showMsg(String msg);

    void captchaCode(String code);

    void checkCaptchaCode(String msg);

    void finishActivity();
}
