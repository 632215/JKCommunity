package com.jinke.community.view;

import com.jinke.community.bean.PraiseresultBean;
import com.jinke.community.bean.DefaultHouseBean;
import com.jinke.community.bean.acachebean.HouseValueBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.bean.acachebean.NoticeListBean;
import com.jinke.community.bean.acachebean.WeatherBean;

/**
 * Created by root on 17-8-6.
 */

public interface IHouseKeeperView {

    void showLoading();

    void hideLoading();

    void showMsg(String msg);

    /**
     * 首页通知列表
     *
     * @param bean
     */
    void onNoticeList(NoticeListBean bean);

    /**
     * 默认房屋
     *
     * @param bean
     */
    void onDefaultHouse(DefaultHouseBean bean);

    /**
     * 房屋估价
     *
     * @param bean
     */
    void onHouseValue(HouseValueBean bean);

    /**
     * 点赞
     */
    void praiseClickNext(PraiseresultBean info);

    /**
     * 获取天气
     *
     * @param bean
     */
    void getWeatheInfoNext(WeatherBean bean);

    void getHouseListNext(HouseListBean info);

    /**
     * 设置下默认房屋（在只有小区Id的情况下）
     *
     * @param substring
     */
    void setDefaultData(String substring);

    void getHouseListEmpty();
}
