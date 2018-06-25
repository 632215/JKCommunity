package com.jinke.community.view;

import com.jinke.community.bean.CityBean;
import com.jinke.community.bean.CommunityGPSBean;
import com.jinke.community.bean.CommunityListBean;
import com.jinke.community.bean.LocationBean;

/**
 * Created by root on 17-8-2.
 */

public interface ISelectCommunityView {
    void showMsg(String msg);

    void showCommunityGpsData(CommunityGPSBean bean);

    void showLocationInfo(LocationBean bean);

    void showQueryCommunity(CommunityGPSBean bean);

    void showCityData(CityBean bean);

    void showCommunityListData(CommunityListBean bean);

    void showLoading();

    void hideLoading();

    void locationBackFailed(int errorCode);
}
