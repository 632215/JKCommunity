package com.jinke.community.service.listener;

import com.jinke.community.bean.CityBean;
import com.jinke.community.bean.CommunityGPSBean;
import com.jinke.community.bean.CommunityListBean;

/**
 * Created by root on 17-8-2.
 */

public interface ISelectCommunityListener {
    void onErrorMsg(String code, String msg);

    void getCommunityGpsData(CommunityGPSBean info);

    void getQueryCommunity(CommunityGPSBean bean);

    void onCityData(CityBean info);

    void onCommunityListData(CommunityListBean bean);
}
