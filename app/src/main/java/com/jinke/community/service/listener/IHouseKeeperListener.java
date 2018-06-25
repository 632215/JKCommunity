package com.jinke.community.service.listener;

import com.jinke.community.bean.PraiseresultBean;
import com.jinke.community.bean.DefaultHouseBean;
import com.jinke.community.bean.acachebean.HouseValueBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.bean.acachebean.NoticeListBean;
import com.jinke.community.bean.acachebean.WeatherBean;

/**
 * Created by root on 17-8-14.
 */

public interface IHouseKeeperListener {
    void onError(String code, String msg);

    void onNoticeList(NoticeListBean bean);

    void onDefaultDataNext(DefaultHouseBean bean);

    void onHouseValue(HouseValueBean bean);

    void praiseClickNext(PraiseresultBean info);

    void getWeatheInfoNext(WeatherBean info);

    void getHouseListNext(HouseListBean info);
}
