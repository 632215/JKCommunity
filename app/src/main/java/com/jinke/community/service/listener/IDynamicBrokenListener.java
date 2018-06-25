package com.jinke.community.service.listener;

import com.jinke.community.bean.BrokeNoticeListBean;
import com.jinke.community.bean.UserCommunityBean;
import com.jinke.community.bean.acachebean.HouseListBean;

/**
 * Created by root on 17-8-14.
 */

public interface IDynamicBrokenListener {
    void onError(String code,String msg);
    void BrokeNewsNoticeList(BrokeNoticeListBean bean);

    void getHouseListNext(HouseListBean info);

    void getCommunityNext(UserCommunityBean info);
}
