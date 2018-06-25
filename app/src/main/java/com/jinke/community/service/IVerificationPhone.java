package com.jinke.community.service;

import com.jinke.community.presenter.PhoneVerificationPresent;
import com.jinke.community.service.listener.IVerificationListener;

import java.util.Map;

/**
 * Created by root on 17-8-1.
 */

public interface IVerificationPhone {
    void getVerificationPhone(Map<String, String> map, IVerificationListener listener);

    void getRegisterData(Map<String, String> map, IVerificationListener listener);
}
