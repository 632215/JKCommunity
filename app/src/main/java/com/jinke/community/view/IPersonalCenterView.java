package com.jinke.community.view;

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

public interface IPersonalCenterView {
    void showMsg(String msg);

    /**
     * 获取url配置信息
     *
     * @param bean
     */
    void onConfigUrl(UrlConfigBean bean);

    /**
     * 用户基本信息
     *
     * @param info
     */
//    void onUserInfo(UserInfo info);

    /**
     * 预存余额
     *
     * @param bean
     */
    void onPrePayList(PrepaidExpensesBean bean);

    /**
     * 积分
     *
     * @param bean
     */
    void getPointInfo(PrepaidExpensesBean bean);

    void showLoading();

    void hideLoading();

    void getParkInfoNext(ParkInfoBean info);

    void getHouseListNext(HouseListBean info);

    void onDefaultDataNext(DefaultHouseBean info);

    void getHouseListError();

    void getUserInfoNext(UserInfo info);

    void getPrePayListError();

    void getMsgNext(MsgBean info);
}
