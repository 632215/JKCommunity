package com.jinke.community.service.listener;

import com.jinke.community.bean.UserLoginBean;

/**
 * Created by root on 17-8-2.
 */

public interface IAddHouseListener {
    void onErrorMsg(String Code, String Msg);

    void onCaptchaCode(UserLoginBean bean);

    void onSuccess(String msg);
    void checkCaptcha(UserLoginBean bean);
}
