package com.jinke.community.service;

import com.jinke.community.service.listener.IAddHouseListener;
import com.jinke.community.service.listener.IVerificationListener;

import java.util.Map;

/**
 * Created by root on 17-8-2.
 */

public interface IAddHouseBiz {
    /**
     * 获取验证码
     *
     * @param map
     * @param listener
     */
    void getVerificationPhone(Map<String, String> map, IAddHouseListener listener);

    /**
     * 绑定房屋
     *
     * @param map
     * @param listener
     */
    void addHouse(Map<String, String> map, IAddHouseListener listener);

    /**
     * 校验验证码
     *
     * @param map
     * @param listener
     */
    void checkCaptcha(Map<String, String> map, IAddHouseListener listener);
}
