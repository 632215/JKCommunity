package com.jinke.community.view;

import com.jinke.community.bean.CityBean;
import com.jinke.community.bean.CommunityListBean;

/**
 * Created by root on 17-8-2.
 */

public interface IOtherCommunityView {
    void showMsg(String msg);

    void showCityData(CityBean bean);

    void showCommunityListData(CommunityListBean bean);
}
