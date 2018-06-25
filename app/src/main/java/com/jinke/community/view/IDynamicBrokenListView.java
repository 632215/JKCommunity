package com.jinke.community.view;

import com.jinke.community.bean.BrokeNoticeListBean;
import com.jinke.community.bean.UserCommunityBean;
import com.jinke.community.bean.acachebean.HouseListBean;

/**
 * Created by root on 17-8-14.
 */

public interface IDynamicBrokenListView{
    void showMsg(String msg);

    void onBrokeNewsNoticeList(BrokeNoticeListBean bean);

    void getHouseListNext(HouseListBean info);

    void getCommunityNext(UserCommunityBean info);
}
