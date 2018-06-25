package com.jinke.community.service.listener;

import com.jinke.community.bean.UrlConfigBean;
import com.jinke.community.bean.UserInfo;
import com.jinke.community.bean.XiMoDriveListBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.bean.RedCircleGroupBean;

/**
 * Created by root on 17-8-9.
 */

public interface IMainListener {
    /**
     * 错误提示信息
     * @param code
     * @param msg
     */
    void onError(String code, String msg);

    /**
     * 获取url配置信息
     *
     * @param bean
     */
    void onUrlSuccess(UrlConfigBean bean);
    /**
     * 获取门禁设备列表
     *
     * @param bean
     */
    void onDeviceList(XiMoDriveListBean bean);
    /**
     * 获取用户基本信息
     *
     * @param userInfo
     */
    void onUserInfo(UserInfo userInfo);

    void onBandingControl(XiMoDriveListBean bean);

    void onDeriveError(String code,String msg);

    void getHouseListNext(HouseListBean info);

    void getRedCircleNext(RedCircleGroupBean info);
}
