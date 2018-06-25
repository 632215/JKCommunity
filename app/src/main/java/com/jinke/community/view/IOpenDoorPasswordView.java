package com.jinke.community.view;

import com.jinke.community.bean.GuestCountBean;
import com.jinke.community.bean.HouseListInfo;

import java.util.List;

/**
 * Created by root on 17-8-21.
 */

public interface IOpenDoorPasswordView {
    void showMsg(String msg);
    void onReasonBean(List<GuestCountBean> listBean);

    void getHouseListDateNext(HouseListInfo info);

    void getHouseListDateError();
}
