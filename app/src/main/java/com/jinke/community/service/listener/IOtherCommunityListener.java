package com.jinke.community.service.listener;

import com.jinke.community.bean.CityBean;
import com.jinke.community.bean.CommunityListBean;

/**
 * Created by root on 17-8-2.
 */

public interface IOtherCommunityListener {
    void onErrorMsg(String code, String msg);

    void onCityData(CityBean info);

    void onCommunityListData(CommunityListBean bean);
}
