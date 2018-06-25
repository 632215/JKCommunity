package com.jinke.community.view;

import com.jinke.community.bean.SetDefaultHouseBean;
import com.jinke.community.bean.acachebean.HouseListBean;

/**
 * Created by root on 17-8-3.
 */

public interface IMyHouseView {
    void showMsg(String msg);

    void onSuccessBack(HouseListBean bean);

    void onHouseHouseData(SetDefaultHouseBean bean);

    void showLoading();

    void hideLoading();

    void getHouseListError();
}
