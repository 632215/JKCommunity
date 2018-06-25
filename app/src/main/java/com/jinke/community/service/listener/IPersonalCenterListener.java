package com.jinke.community.service.listener;

import com.jinke.community.bean.DefaultHouseBean;
import com.jinke.community.bean.MsgBean;
import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.bean.PrepaidExpensesBean;
import com.jinke.community.bean.UrlConfigBean;
import com.jinke.community.bean.UserInfo;
import com.jinke.community.bean.acachebean.HouseListBean;

/**
 * Created by root on 17-8-16.
 */

public interface IPersonalCenterListener {
    void onError(String code, String msg);

    void onConfigUrl(UrlConfigBean bean);

    /**
     * 获取用户基本信息
     *
     * @param userInfo
     */
    void getUserInfoNext(UserInfo userInfo);

    /**
     * 预存余额
     *
     * @param bean
     */
    void onPrePayList(PrepaidExpensesBean bean);

    /**
     * 获取积分
     *
     * @param bean
     */
    void getPointInfo(PrepaidExpensesBean bean);

    /**
     * 车位信息
     *
     * @param info
     */
    void getParkInfoNext(ParkInfoBean info);

    /**
     * 房屋列表
     *
     * @param info
     */
    void getHouseListNext(HouseListBean info);

    /**
     * 获取用户基本信息
     *
     * @param info
     */
    void onDefaultDataNext(DefaultHouseBean info);

    void getHouseListError();

    void getPrePayListError();

    /**
     * 获取消息列表
     * @param info
     */
    void getMsgNext(MsgBean info);
}
