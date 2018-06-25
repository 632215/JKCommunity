package com.jinke.community.service.listener;

import com.jinke.community.bean.HouseBean;
import com.jinke.community.bean.RegisterLoginBean;
import com.jinke.community.bean.UserLoginBean;

/**
 * Created by root on 17-8-1.
 */

public interface IVerificationListener {
    void onCaptchaCode(UserLoginBean bean);

    void onErrorMsg(String code, String msg);

    void getRegisterDataNext(RegisterLoginBean info);
}
